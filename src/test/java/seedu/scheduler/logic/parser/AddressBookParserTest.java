package seedu.scheduler.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BOB_INTERVIEWEE_MANUAL;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.AddIntervieweeCommand;
import seedu.scheduler.logic.commands.ClearCommand;
import seedu.scheduler.logic.commands.DeleteCommand;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EditIntervieweeCommand;
import seedu.scheduler.logic.commands.EmailCommand;
import seedu.scheduler.logic.commands.ExitCommand;
import seedu.scheduler.logic.commands.FindCommand;
import seedu.scheduler.logic.commands.HelpCommand;
import seedu.scheduler.logic.commands.ListCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.PersonNameHasKeywordsPredicate;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.testutil.IntervieweeBuilder;
import seedu.scheduler.testutil.IntervieweeUtil;
import seedu.scheduler.testutil.PersonUtil;
import seedu.scheduler.testutil.TestUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        // add interviewee
        Interviewee interviewee = new IntervieweeBuilder(BOB_INTERVIEWEE_MANUAL).build();
        AddCommand command = (AddCommand) parser.parseCommand(IntervieweeUtil.getAddCommand(interviewee));
        assertEquals(new AddIntervieweeCommand(interviewee), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " "
                        + ALICE_INTERVIEWEE.getName() + " "
                        + PREFIX_ROLE + "interviewee");
        assertEquals(new DeleteCommand(ALICE_INTERVIEWEE.getName(), new Role("interviewee")), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        EditIntervieweeCommand.EditIntervieweeDescriptor descriptor =
                TestUtil.getDescriptorFromInterviewee(ALICE_INTERVIEWEE);
        EditIntervieweeCommand command =
                (EditIntervieweeCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + ALICE_INTERVIEWEE.getName() + " " + PREFIX_ROLE + "interviewee" + " "
                + PersonUtil.getEditIntervieweeDescriptorDetails(descriptor));

        assertEquals(new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonNameHasKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_email() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(EmailCommand.COMMAND_WORD));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(EmailCommand.COMMAND_WORD + " timeslot"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(EmailCommand.COMMAND_WORD + " invalidcommand"));

        assertTrue(parser.parseCommand(
                EmailCommand.COMMAND_WORD + " timeslot Alice") instanceof EmailCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
