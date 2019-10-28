package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.util.Pair;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.graph.InterviewSlotVertex;
import seedu.address.logic.graph.IntervieweeVertex;
import seedu.address.model.Model;

/**
 * Schedules the interviews using the interviewer's availability data and interviewee's selected slots.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule the interviews using the interviewer's "
        + "availability and the interviewee's selected time slot.\n"
        + "Parameters: none\n"
        + "Example: " + COMMAND_WORD + " (no other word should follow after it)";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Schedule command not implemented yet";

    private List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ScheduleCommand; // instanceof handles nulls
    }
}
