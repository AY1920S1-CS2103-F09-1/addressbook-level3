package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertModelHasInterviewee;
import static seedu.scheduler.logic.commands.CommandTestUtil.showIntervieweeWithName;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    @Test
    public void execute_validIntervieweeUnfilteredList_success() {
        Model model = new ModelManager(getTypicalIntervieweeList(), new InterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewee alice = TypicalPersons.ALICE_INTERVIEWEE;
        Role role = new Role("interviewee");

        Interviewee intervieweeToDel = model.getInterviewee(alice.getName().fullName);

        DeleteCommand deleteCommand = new DeleteCommand(alice.getName(), role);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, intervieweeToDel);

        // create duplicate and remove interviewee
        ModelManager expectedModel = new ModelManager(model.getMutableIntervieweeList(),
                model.getMutableInterviewerList(), new UserPrefs(), new LinkedList<>());

        expectedModel.deleteInterviewee(intervieweeToDel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validInterviewerUnfilteredList_success() {
        Model model = new ModelManager(new IntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewer benson = TypicalPersons.BENSON_INTERVIEWER;
        Role role = new Role("interviewer");

        Interviewer interviewerToDel = model.getInterviewer(benson.getName().fullName);

        DeleteCommand deleteCommand = new DeleteCommand(benson.getName(), role);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, interviewerToDel);

        // create duplicate and remove interviewee
        ModelManager expectedModel = new ModelManager(model.getMutableIntervieweeList(),
                model.getMutableInterviewerList(), new UserPrefs(), new LinkedList<>());

        expectedModel.deleteInterviewer(interviewerToDel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        DeleteCommand deleteCommand = new DeleteCommand(new Name("This name doesnt exist in IntervieweeBook"),
                new Role("interviewee"));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        // pre-processing
        Model model = new ModelManager(getTypicalIntervieweeList(), new InterviewerList(),
                new UserPrefs(), new LinkedList<>());
        Interviewee toDelete = ALICE_INTERVIEWEE;

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, toDelete);

        assertModelHasInterviewee(model, toDelete);

        showIntervieweeWithName(model, ALICE_INTERVIEWEE.getName());

        DeleteCommand deleteCommand = new DeleteCommand(toDelete.getName(), new Role("interviewee"));

        Model expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel.deleteInterviewee(toDelete);

        showAllInterviewees(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalIntervieweeList(), new InterviewerList(),
                new UserPrefs(), new LinkedList<>());

        showIntervieweeWithName(model, ALICE_INTERVIEWEE.getName());

        Name invalidName = new Name("This name doesnt exist in the interviewee book");

        DeleteCommand deleteCommand = new DeleteCommand(invalidName, new Role("interviewee"));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(ALICE_INTERVIEWEE.getName(), new Role("interviewee"));
        DeleteCommand deleteSecondCommand = new DeleteCommand(BENSON_INTERVIEWEE.getName(), new Role("interviewee"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(ALICE_INTERVIEWEE.getName(), new Role("interviewee"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered interviewee list to show no one.
     */
    private void showNoInterviewee(Model model) {
        model.updateFilteredIntervieweeList(p -> false);

        assertTrue(model.getFilteredIntervieweeList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered interviewee list to show everyone.
     * @param model
     */
    private void showAllInterviewees(Model model) {
        model.updateFilteredIntervieweeList(p -> true);
    }
}
