package seedu.address.testutil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.util.Pair;
import seedu.address.logic.graph.BipartiteGraph;
import seedu.address.logic.graph.InterviewSlot;
import seedu.address.logic.graph.InterviewSlotVertex;
import seedu.address.logic.graph.IntervieweeVertex;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;

/**
 * Provides sample bipartite graphs of interviewees and interview tine slots.
 */
public class SampleGraph {
    public static BipartiteGraph getSampleGraphOne() {
        List<Interviewer> interviewers = SampleInterviewer.getSampleInterviewersForGraphOne();
        List<Interviewee> interviewees = SampleInterviewee.getSampleIntervieweesForGraphOne();

        InterviewSlot interviewSlot0 = new InterviewSlot("26/10/2019 18:00-18:30", interviewers.get(0));
        InterviewSlot interviewSlot1 = new InterviewSlot("26/10/2019 18:30-19:00", interviewers.get(0));
        InterviewSlot interviewSlot2 = new InterviewSlot("27/10/2019 20:00-20:30", interviewers.get(1));
        InterviewSlot interviewSlot3 = new InterviewSlot("27/10/2019 20:30-21:00", interviewers.get(1));
        InterviewSlot interviewSlot4 = new InterviewSlot("28/10/2019 19:00-19:30", interviewers.get(2));

        InterviewSlotVertex interviewSlotVertex0 = new InterviewSlotVertex(interviewSlot0, 0);
        InterviewSlotVertex interviewSlotVertex1 = new InterviewSlotVertex(interviewSlot1, 1);
        InterviewSlotVertex interviewSlotVertex2 = new InterviewSlotVertex(interviewSlot2, 2);
        InterviewSlotVertex interviewSlotVertex3 = new InterviewSlotVertex(interviewSlot3, 3);
        InterviewSlotVertex interviewSlotVertex4 = new InterviewSlotVertex(interviewSlot4, 4);

        LinkedList<InterviewSlotVertex> list0 = new LinkedList<>();
        list0.add(interviewSlotVertex0);
        IntervieweeVertex interviewee0 = new IntervieweeVertex(interviewees.get(0), 0);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex0 = new Pair<>(interviewee0, list0);

        LinkedList<InterviewSlotVertex> list1 = new LinkedList<>();
        list1.add(interviewSlotVertex0);
        list1.add(interviewSlotVertex1);
        list1.add(interviewSlotVertex2);
        IntervieweeVertex interviewee1 = new IntervieweeVertex(interviewees.get(1), 1);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex1 = new Pair<>(interviewee1, list1);

        LinkedList<InterviewSlotVertex> list2 = new LinkedList<>();
        list2.add(interviewSlotVertex0);
        list2.add(interviewSlotVertex3);
        IntervieweeVertex interviewee2 = new IntervieweeVertex(interviewees.get(2), 2);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex2 = new Pair<>(interviewee2, list2);

        LinkedList<InterviewSlotVertex> list3 = new LinkedList<>();
        list3.add(interviewSlotVertex1);
        list3.add(interviewSlotVertex4);
        IntervieweeVertex interviewee3 = new IntervieweeVertex(interviewees.get(3), 3);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex3 = new Pair<>(interviewee3, list3);

        LinkedList<InterviewSlotVertex> list4 = new LinkedList<>();
        list4.add(interviewSlotVertex2);
        list4.add(interviewSlotVertex3);
        IntervieweeVertex interviewee4 = new IntervieweeVertex(interviewees.get(4), 4);
        Pair<IntervieweeVertex, List<InterviewSlotVertex>> vertex4 = new Pair<>(interviewee4, list4);

        List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph = new ArrayList<>();
        graph.add(vertex0);
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);

        return new BipartiteGraph(graph, interviewees.size(), 5);
    }
}
