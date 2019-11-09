package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.DeleteCommand.MESSAGE_USAGE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.stream.Stream;

import seedu.scheduler.logic.commands.DeleteCommand;
import seedu.scheduler.logic.commands.DeleteIntervieweeCommand;
import seedu.scheduler.logic.commands.DeleteInterviewerCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.model.person.RoleType;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ROLE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getPreamble());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

        return role.getRole().equals(RoleType.INTERVIEWEE)
                ? new DeleteIntervieweeCommand(name)
                : new DeleteInterviewerCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
