package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the schedule table data.
 */
public class ModelManager implements Model {
    public static final Schedule EMPTY_SCHEDULE = new Schedule("", new LinkedList<>());
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final List<Schedule> schedulesList;
    private List<Interviewee> intervieweesList;

    private final IntervieweeList intervieweeList; // functionality not stable, refrain from using
    private final InterviewerList interviewerList;
    private final FilteredList<Interviewee> filteredInterviewees; // if we want to display all interviewees on UI
    private final FilteredList<Interviewer> filteredInterviewers; // if we want to display all inteviewers on UI

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        List<Schedule> schedulesList,
                        ReadOnlyList<Interviewee> intervieweeBook, ReadOnlyList<Interviewer> interviewerBook) {
        super();
        requireAllNonNull(addressBook, userPrefs, schedulesList);

        logger.fine("Initializing with list of schedules: " + schedulesList + " and user prefs " + userPrefs);

        // TODO: Delete these later
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getObservableList());

        this.schedulesList = cloneSchedulesList(schedulesList);
        this.userPrefs = new UserPrefs(userPrefs);

        this.intervieweeList = new IntervieweeList(intervieweeBook);
        this.interviewerList = new InterviewerList(interviewerBook);
        filteredInterviewees = new FilteredList<>(this.intervieweeList.getObservableList());
        filteredInterviewers = new FilteredList<>(this.interviewerList.getObservableList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new LinkedList<>(), new IntervieweeList(), new InterviewerList());
    }

    //============ IntervieweeList/InterviewerList =========================================================

    @Override
    public void addInterviewee(Interviewee interviewee) {
        intervieweeList.addEntity(interviewee);
    }

    @Override
    public void addInterviewer(Interviewer interviewer) {
        interviewerList.addEntity(interviewer);
    }

    @Override
    public ReadAndWriteList<Interviewee> getIntervieweeList() {
        return intervieweeList;
    }

    @Override
    public ReadAndWriteList<Interviewer> getInterviewerList() {
        return interviewerList;
    }

    @Override
    public ObservableList<Interviewee> getFilteredIntervieweeList() {
        return filteredInterviewees;
    }

    @Override
    public ObservableList<Interviewer> getFilteredInterviewerList() {
        return filteredInterviewers;
    }

    @Override
    public void updateFilteredIntervieweeList(Predicate<Interviewee> predicate) {
        requireNonNull(predicate);
        filteredInterviewees.setPredicate(predicate);
    }

    @Override
    public void updateFilteredInterviewerList(Predicate<Interviewer> predicate) {
        requireNonNull(predicate);
        filteredInterviewers.setPredicate(predicate);
    }

    @Override
    public Interviewee getInterviewee(String name) throws NoSuchElementException {
        return intervieweeList.getEntity(new Name(name));
    }

    @Override
    public Interviewer getInterviewer(String name) throws NoSuchElementException {
        return interviewerList.getEntity(new Name(name));
    }

    @Override
    public void deleteInterviewee(Interviewee target) throws PersonNotFoundException {
        intervieweeList.removeEntity(target);
    }

    @Override
    public void deleteInterviewer(Interviewer target) throws PersonNotFoundException {
        interviewerList.removeEntity(target);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== Schedule ================================================================================
    /**
     * Replaces schedule data with the data in {@code schedule}.
     * @param list
     */
    @Override
    public void setSchedulesList(List<Schedule> list) {
        schedulesList.clear();
        schedulesList.addAll(cloneSchedulesList(list));
        logger.fine("Schedules list is reset");
    }

    /** Returns the schedulesList **/
    @Override
    public List<Schedule> getSchedulesList() {
        return schedulesList;
    }


    /**
     * Returns a list of observable list of the schedules.
     */
    @Override
    public List<ObservableList<ObservableList<String>>> getObservableLists() {
        List<ObservableList<ObservableList<String>>> observableLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            observableLists.add(schedule.getObservableList());
        }
        return observableLists;
    }

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    @Override
    public List<List<String>> getTitlesLists() {
        List<List<String>> titlesLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            titlesLists.add(schedule.getTitles());
        }
        return titlesLists;
    }

    /**
     * Sets interviewee's data.
     * @param list list of interviewees
     */
    public void setIntervieweesList(List<Interviewee> list) {
        intervieweesList = cloneIntervieweesList(list);
        logger.fine("interviewee's list is updated");
    }

    /**
     * Adds the given interviewer to schedule(s) in which the interviewer's availability fall.
     * If the interviewer's availability does not fall within any of the schedule, then the interviewer will not
     * be added into any of the schedule.
     */
    @Override
    public void addInterviewerToSchedule(Interviewer interviewer) {
        interviewerList.addEntity(interviewer);
        for (Schedule schedule : schedulesList) {
            schedule.addInterviewer(interviewer);
        }
    }

    /** Returns the intervieweesList **/
    public List<Interviewee> getIntervieweesList() {
        return intervieweesList;
    }

    /**
     * Emails the given Interviewee.
     * The Interviewee must exist in the database.
     */
    @Override
    public void emailInterviewee(Interviewee interviewee) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String intervieweeEmails = interviewee.getEmails().getAllEmails().values().stream()
                .map((x) -> {
                    StringBuilder output = new StringBuilder();
                    for (int i = 0; i < x.size(); i++) {
                        output.append(x.get(i));
                        output.append("; ");
                    }

                    if (output.length() != 0) {
                        output.delete(output.length() - 2, output.length());
                    }

                    return output.toString();
                })
                .reduce((x, y) -> x + "; " + y).get();

        String sb = "mailto:"
                + URLEncoder.encode(intervieweeEmails,
                        java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20")
                + "?cc=" + "copied@example.com" + "&subject="
                + URLEncoder.encode("This is a test subject",
                        java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20")
                + "&body="
                + URLEncoder.encode(intervieweeEmails,
                        java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");
        URI uri = URI.create(sb);
        desktop.mail(uri);
    }

    /**
     * Returns a list of interview slots assigned to the interviewee with the {@code intervieweeName}.
     */
    @Override
    public List<Slot> getInterviewSlots(String intervieweeName) {
        List<Slot> slots = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            slots.addAll(schedule.getInterviewSlots(intervieweeName));
        }
        return slots;
    }

    /**
     * Returns the date of the first schedule in which the interviewer exists in, otherwise return empty string.
     */
    @Override
    public String hasInterviewer(Interviewer interviewer) {
        String date = "";
        for (Schedule schedule : schedulesList) {
            if (schedule.hasInterviewer(interviewer)) {
                date = schedule.getDate();
                break;
            }
        }
        return date;
    }

    /**
     * Returns the deep copy of the schedules list given.
     *
     * @param list the list of schedules to be copied.
     * @return the deep copy of the schedules list given.
     */
    private static List<Schedule> cloneSchedulesList(List<Schedule> list) {
        List<Schedule> listClone = new LinkedList<>();
        for (Schedule schedule : list) {
            listClone.add(Schedule.cloneSchedule(schedule));
        }
        return listClone;
    }

    /**
     * Returns the deep copy of the interviewee's list given.
     *
     * @param list the list of interviewees to be copied.
     * @return a deep copy of interviewee's list.
     */
    private static List<Interviewee> cloneIntervieweesList(List<Interviewee> list) {
        List<Interviewee> listClone = new LinkedList<>();
        for (Interviewee interviewee : list) {
            listClone.add(interviewee);
        }
        return listClone;
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        if (person instanceof Interviewer) {
            addInterviewerToSchedule((Interviewer) person);
        }
        if (person instanceof Interviewee) {
            addInterviewee((Interviewee) person);
        }
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public Person getPerson(String name) throws NoSuchElementException {
        return addressBook.getPerson(new Name(name));
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons)
            && intervieweeList.equals(other.intervieweeList)
            && interviewerList.equals(other.interviewerList)
            && filteredInterviewees.equals(other.filteredInterviewees)
            && filteredInterviewers.equals(other.filteredInterviewers);
    }
}
