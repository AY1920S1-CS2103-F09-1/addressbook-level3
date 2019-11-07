package seedu.scheduler.logic.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.graph.BipartiteGraph;
import seedu.scheduler.logic.graph.BipartiteGraphGenerator;
import seedu.scheduler.logic.graph.HopCroftKarp;
import seedu.scheduler.logic.graph.IntervieweeVertex;
import seedu.scheduler.logic.graph.InterviewerSlot;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.IntervieweeSlot;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;

/**
 * Schedules the interviews using the interviewer's availability data and interviewee's selected slots.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule the interviews using the interviewer's "
        + "availability and the interviewee's selected time slot.\n"
        + "Parameters: none\n"
        + "Example: " + COMMAND_WORD + " (no other word should follow after it)";
    private static final String FEEDBACK_PREFIX = "Successfully scheduled!\n";
    private static final String ALL_MATCHING_FOUND = FEEDBACK_PREFIX + "All interviewees are allocated with a slot!";
    private static final String NO_MATCHING_FOUND = FEEDBACK_PREFIX + "No matching is found :(";
    private static final Logger logger = LogsCenter.getLogger(ScheduleCommand.class);


    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Starting to schedule interviews");
        String feedback;

        if (model.getUnfilteredIntervieweeList().isEmpty() || model.getUnfilteredInterviewerList().isEmpty()) {
            feedback = NO_MATCHING_FOUND;
        } else {
            if (model.isScheduled()) {
                model.resetDataBeforeScheduling();
            }

            List<Interviewer> interviewers = new LinkedList<>(model.getUnfilteredInterviewerList());
            List<Interviewee> interviewees = new LinkedList<>(model.getUnfilteredIntervieweeList());

            // To ensure fairer scheduling
            Collections.shuffle(interviewers);
            Collections.shuffle(interviewees);

            BipartiteGraph graph = new BipartiteGraphGenerator(interviewers, interviewees).generate();
            HopCroftKarp algorithm = new HopCroftKarp(graph);
            algorithm.execute();
            assignSlots(graph);

            try {
                model.updateSchedulesAfterScheduling();
            } catch (ScheduleException e) {
                throw new CommandException("Error occurs!", e);
            }

            feedback = generateResultMessage(graph);
        }

        logger.info("Finish scheduling interviews");
        model.setScheduled(true);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ScheduleCommand; // instanceof handles nulls
    }

    /**
     * Attaches the allocated interview slot the corresponding interviewee and also to the interviewer (after running
     * the HopCroftKarp algorithm). Returns true if at least one interviewee is allocated with a slot.
     */
    private void assignSlots(BipartiteGraph graph) {
        int numInterviewees = graph.getNumInterviewees();

        for (int i = 0; i < numInterviewees; i++) {
            IntervieweeVertex intervieweeVertex = graph.getIntervieweePair(i).getHead();

            if (intervieweeVertex.isMatched()) {
                Interviewee interviewee = intervieweeVertex.getItem();
                InterviewerSlot interviewerSlot = intervieweeVertex.getPartner().getItem();
                Interviewer interviewer = interviewerSlot.getInterviewer();
                Slot slot = interviewerSlot.getSlot();

                interviewee.setAllocatedSlot(slot);
                interviewer.addAllocatedSlot(new IntervieweeSlot(interviewee, slot));
            }
        }
    }

    /**
     * Generates the result of message of the scheduling.
     */
    private String generateResultMessage(BipartiteGraph graph) {
        List<Interviewee> notAllocatedInterviewees = getNotAllocatedInterviewees(graph);

        if (notAllocatedInterviewees.isEmpty()) {
            return ALL_MATCHING_FOUND;
        } else if (notAllocatedInterviewees.size() == graph.getNumInterviewees()) {
            return NO_MATCHING_FOUND;
        } else {
            return FEEDBACK_PREFIX + getNotAllocatedResult(notAllocatedInterviewees);
        }
    }

    /**
     * Returns the list of interviewees that are not allocated with any slot.
     */
    private List<Interviewee> getNotAllocatedInterviewees(BipartiteGraph graph) {
        int numInterviewees = graph.getNumInterviewees();

        return IntStream.range(0, numInterviewees)
                .mapToObj(i -> graph.getIntervieweePair(i).getHead())
                .filter(intervieweeVertex -> !intervieweeVertex.isMatched())
                .map(IntervieweeVertex::getItem)
                .collect(Collectors.toList());
    }

    /**
     * Returns the message showing the number of interviewees that are not allocated with a slot and their name
     * , each separated with a new line character.
     */
    private String getNotAllocatedResult(List<Interviewee> notAllocatedInterviewees) {
        String prefix = "Number of interviewees that are not allocated a slot: "
                + notAllocatedInterviewees.size() + "\nInterviewees that are not allocated a slot:\n";

        return notAllocatedInterviewees.stream()
                .map(interviewee -> interviewee.getName().toString())
                .collect(Collectors.joining("\n", prefix, "\n"));
    }
}
