package seedu.scheduler.logic.commands;

import java.util.List;
import java.util.logging.Logger;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.commons.util.Pair;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.graph.BipartiteGraph;
import seedu.scheduler.logic.graph.BipartiteGraphGenerator;
import seedu.scheduler.logic.graph.HopCroftKarp;
import seedu.scheduler.logic.graph.IntervieweeVertex;
import seedu.scheduler.logic.graph.InterviewerSlot;
import seedu.scheduler.logic.graph.InterviewerSlotVertex;
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
    private static final Logger logger = LogsCenter.getLogger(ScheduleCommand.class);

    private List<Pair<IntervieweeVertex, List<InterviewerSlotVertex>>> graph;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Starting to schedule interviews");
        model.clearAllAllocatedSlot();

        List<Interviewer> interviewers = model.getUnfilteredInterviewerList();
        List<Interviewee> interviewees = model.getUnfilteredIntervieweeList();

        BipartiteGraph graph = new BipartiteGraphGenerator(interviewers, interviewees).generate();
        String message = "Successfully scheduled!";

        HopCroftKarp algorithm = new HopCroftKarp(graph);
        algorithm.execute();

        boolean isNonEmptyResult = assignSlots(graph);
        if (isNonEmptyResult) {
            try {
                model.updateSchedulesAfterScheduling();
            } catch (ScheduleException e) {
                throw new CommandException("Error occurs!", e);
            }
        } else {
            message = message + "\nNo matching is found!\n";
        }

        logger.info("Finish scheduling interviews");
        model.setScheduled(true);
        return new CommandResult(message);
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
    private boolean assignSlots(BipartiteGraph graph) {
        int numInterviewees = graph.getNumInterviewees();
        boolean isNonEmptyResult = false;

        for (int i = 0; i < numInterviewees; i++) {
            IntervieweeVertex vertex = graph.getIntervieweePair(i).getHead();

            if (vertex.isMatched()) {
                isNonEmptyResult = true;

                Interviewee interviewee = vertex.getItem();
                InterviewerSlot interviewerSlot = vertex.getPartner().getItem();
                Interviewer interviewer = interviewerSlot.getInterviewer();
                Slot slot = interviewerSlot.getSlot();

                interviewee.setAllocatedSlot(slot);
                interviewer.addIntervieweeSlot(new IntervieweeSlot(interviewee, slot));
            }
        }

        return isNonEmptyResult;
    }
}
