package seedu.address.logic.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import seedu.address.commons.util.Pair;
import seedu.address.model.person.Department;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;

/**
 * Generates a bipartite graph of interviewees linked to the the available interview slots.
 */
public class BipartiteGraphGenerator {
    private List<Interviewer> interviewers;
    private List<Interviewee> interviewees;

    public BipartiteGraphGenerator(List<Interviewer> interviewers, List<Interviewee> interviewees) {
        this.interviewers = interviewers;
        this.interviewees = interviewees;
    }

    /**
     * Returns a graph of interviewees matched to available interview slots.
     * The interviewees and interview slots are each wrapped in a vertex.
     * An interviewee is only added to the graph if it can match at least one of the interview slots and vice versa.
     */
    private List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> getGraph() {
        List<Pair<Department, List<InterviewSlot>>> list = generateInterviewSlots(interviewers);
        List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph = new LinkedList<>();

        for (Interviewee interviewee : interviewees) {
            // Create a list to store the interview slots that the interviewee can match to
            List<InterviewSlotVertex> interviewSlotVertices = new LinkedList<>();

            // Get the sorted available interview slots based on the department of choice of the interviewee
            // and also the sorted desired slots of the interviewee
            Department department = interviewee.getDepartmentChoices().get(0);
            List<InterviewSlot> availableSlots = getInterviewSlot(list, department);
            List<Slot> desiredSlots = interviewee.getAvailableTimeslots();

            // Based on the desired slots, fill up the list with the matching available interview slots (wrapping
            // each of them in a vertex)
            fill(interviewSlotVertices, availableSlots, desiredSlots);

            // Only add the interviewee into the graph if it can match to at least one available interview slots
            if (!interviewSlotVertices.isEmpty()) {
                IntervieweeVertex intervieweeVertex = new IntervieweeVertex(interviewee);
                Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertexListPair =
                    new Pair<>(intervieweeVertex, interviewSlotVertices);
                graph.add(vertexListPair);
            }
        }

        return graph;
    }

    /**
     * Returns the sorted available interview slots based on the department of choice of the interviewee.
     */
    private List<InterviewSlot> getInterviewSlot(List<Pair<Department, List<InterviewSlot>>> list,
                                                 Department departmentOfChoice) {
        Pair<Department, List<InterviewSlot>> departmentListPair = getPair(list, departmentOfChoice);

        return departmentListPair.getTail();
    }

    /**
     * Fill up the given list with the matching available interview slots (wrapping each of them in a vertex)
     * based on the desired slots of the interviewee.
     */
    private void fill(List<InterviewSlotVertex> interviewSlotVertices, List<InterviewSlot> availableSlots,
                      List<Slot> desiredSlots) {
        ListIterator<InterviewSlot> availableSlotsIterator = availableSlots.listIterator();
        ListIterator<Slot> desiredSlotsIterator = desiredSlots.listIterator();

        if (!(availableSlotsIterator.hasNext() && desiredSlotsIterator.hasNext())) {
            return;
        }

        Slot desiredSlot = desiredSlotsIterator.next();
        InterviewSlot availableSlot = availableSlotsIterator.next();

        while (true) {
            int comp = desiredSlot.compareTo(availableSlot);
            try {
                if (comp == 0) {
                    interviewSlotVertices.add(new InterviewSlotVertex(availableSlot));
                    desiredSlot = desiredSlotsIterator.next();
                    availableSlot = availableSlotsIterator.next();
                } else if (comp > 0) {
                    availableSlot = availableSlotsIterator.next();
                } else if (comp < 0) {
                    desiredSlot = desiredSlotsIterator.next();
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    /**
     * Returns a list of interview slots based on the availabilities of the interviewers.
     * The list is structured in such a way: the interview slots are categorised based on the department of the
     * interviewer. Thus, the actual structure of the list is a list of pairs, with the head being the department,
     * and the tail being the list of available interview slots of that department.
     */
    private List<Pair<Department, List<InterviewSlot>>> generateInterviewSlots(List<Interviewer> interviewers) {
        List<Pair<Department, List<InterviewSlot>>> list = new LinkedList<>();

        for (Interviewer interviewer : interviewers) {
            Department department = interviewer.getDepartment();
            Pair<Department, List<InterviewSlot>> pair = getPair(list, department);

            List<Slot> slots = interviewer.getAvailabilities();
            List<InterviewSlot> interviewSlots = pair.getTail();
            for (Slot slot : slots) {
                interviewSlots.add(new InterviewSlot(slot.toString(), interviewer));
            }
        }

        // Sort the slots
        for (Pair<Department, List<InterviewSlot>> pair : list) {
            Collections.sort(pair.getTail());
        }

        return list;
    }

    /**
     * Returns the pair of department and the list of associated interview slots from the given list.
     * If the department does not already exist in the list, a new pair for the department will be created, with
     * the list of associated interview slots being empty.
     */
    private Pair<Department, List<InterviewSlot>> getPair(List<Pair<Department, List<InterviewSlot>>> list,
                                                          Department department) {
        int size = list.size();
        Pair<Department, List<InterviewSlot>> pair = null;

        for (int i = 0; i < size; i++) {
            Pair<Department, List<InterviewSlot>> currPair = list.get(i);
            Department currDepartment = pair.getHead();
            if (currDepartment.equals(department)) {
                pair = currPair;
                break;
            }
        }

        if (pair == null) {
            pair = new Pair<>(department, new LinkedList<>());
            list.add(pair);
        }

        return pair;
    }
}
