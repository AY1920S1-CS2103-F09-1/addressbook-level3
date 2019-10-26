package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setSchedulesList(List<Schedule> schedulesList);

    /**
     * Sets interviewee's data.
     */
    void setIntervieweesList(List<Interviewee> list);

    /** Returns the schedulesList **/
    List<Schedule> getSchedulesList();

    /** Returns the intervieweesList **/
    List<Interviewee> getIntervieweesList();

    /**
     * Returns a list of observable list of the schedules.
     */
    List<ObservableList<ObservableList<String>>> getObservableLists();

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    List<List<String>> getTitlesLists();

    /**
     * Returns an Interviewee given the intervieweeName.
     * The Interviewee must exist in the database.
     * @throws NoSuchElementException If the Interviewee does not exist in the database.
     */
    Interviewee getInterviewee(String intervieweeName) throws NoSuchElementException;

    /**
     * Emails the given Interviewee.
     * The Interviewee must exist in the database.
     */
    void emailInterviewee(Interviewee interviewee) throws IOException;

    /**
     * Returns the interview slot assigned to the interviewee with the {@code intervieweeName}.
     */
    List<Slot> getInterviewSlots(String intervieweeName);

    /**
     * Returns the date of the schedule in which the interviewer exists in, otherwise return empty string.
     */
    String hasInterviewer(Interviewer interviewer);

    /**
     * Adds an interviewer to one of the schedules if the interviewer's availability fall within those schedules
     * and returns true. Otherwise, the method will not add the interviewer and return false.
     */
    void addInterviewer(Interviewer interviewer);

    /**
     * Adds an interviewee to the interviewee-equivalent of addressbook. Must be unique.
     */
    void addInterviewee(Interviewee interviewee);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Returns the Person object given the name.
     * The person must exist in the address book.
     * @param name The name of the Person
     * @throws NoSuchElementException If the person does not exist in the address book.
     */
    Person getPerson(String name) throws NoSuchElementException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
