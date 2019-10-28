package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.graph.InterviewSlot;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Schedule;
import seedu.address.model.person.Department;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static String[][] sampleFilledTable =
            new String[][]{
                    {"10/9/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
                    {"18:00-18:30", "John", "Steven", "0"},
                    {"18:30-19:00", "Alex", "Clark", "John"},
                    {"19:00-19:30", "Alicia", "0", "charlie"},
                    {"19:30-20:00", "Charlie", "0", "Selina"},
                    {"20:00-20:30", "Selina", "0", "0"},
                    {"20:30-21:00", "Natal", "0", "0"}};

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static List<Schedule> getSampleSchedulesList() {
        LinkedList<Schedule> sampleSchedulesList = new LinkedList<>();
        String date = sampleFilledTable[0][0];
        LinkedList<LinkedList<String>> sampleData = toTwoDimensionalLinkedList(sampleFilledTable);
        sampleSchedulesList.add(new Schedule(date, sampleData));

        return sampleSchedulesList;
    }

    /**
     * Returns the given two dimensional array of strings as a two dimensional LinkedList of strings.
     */
    private static LinkedList<LinkedList<String>> toTwoDimensionalLinkedList(String[][] table) {
        LinkedList<LinkedList<String>> tableCopy = new LinkedList<>();
        for (String[] row : table) {
            LinkedList<String> rowCopy = new LinkedList<>(Arrays.asList(row));
            tableCopy.add(rowCopy);
        }
        return tableCopy;
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
    /**
     * Returns a department list containing the list of strings given.
     */
    public static List<Department> getDepartmentList(String... strings) {
        return Arrays.stream(strings)
                .map(Department::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a time slot list containing the list of strings given.
     */
    public static List<Slot> getTimeslotList(String...timeslots) {
        return Arrays.stream(timeslots)
                .map(Slot::new)
                .collect(Collectors.toList());
    }

    public static List<List<InterviewSlot>> getSampleGraph() {
        List<Interviewer> interviewers = getSampleInterviewers();

        InterviewSlot interviewSlot1 = new InterviewSlot("26/10/2019 18:00-18:30", interviewers.get(0));
        InterviewSlot interviewSlot2 = new InterviewSlot("26/10/2019 18:30-19:00", interviewers.get(0));
        InterviewSlot interviewSlot3 = new InterviewSlot("27/10/2019 20:00-20:30", interviewers.get(1));
        InterviewSlot interviewSlot4 = new InterviewSlot("27/10/2019 20:30-21:00", interviewers.get(1));
        InterviewSlot interviewSlot5 = new InterviewSlot("28/10/2019 19:00-19:30", interviewers.get(2));

        List<InterviewSlot> node1 = new LinkedList<>();
        List<InterviewSlot> node2 = new LinkedList<>();
        List<InterviewSlot> node3 = new LinkedList<>();
        List<InterviewSlot> node4 = new LinkedList<>();
        List<InterviewSlot> node5 = new LinkedList<>();

        node1.add(interviewSlot1);

        node2.add(interviewSlot1);
        node2.add(interviewSlot2);
        node2.add(interviewSlot3);

        node3.add(interviewSlot1);
        node3.add(interviewSlot4);

        node4.add(interviewSlot2);
        node4.add(interviewSlot5);

        node5.add(interviewSlot3);
        node5.add(interviewSlot4);

        List<List<InterviewSlot>> graph = new ArrayList<>();
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);

        return graph;
    }

    public static List<Slot> getSampleSlots() {
        Slot slot1 = new Slot("26/10/2019", "18:00", "18:30");
        Slot slot2 = new Slot("26/10/2019", "18:30", "19:00");
        Slot slot3 = new Slot("27/10/2019", "20:00", "20:30");
        Slot slot4 = new Slot("27/10/2019", "20:30", "21:00");
        Slot slot5 = new Slot("28/10/2019", "19:00", "19:30");

        List<Slot> slots = new LinkedList<>();
        slots.add(slot1);
        slots.add(slot2);
        slots.add(slot3);
        slots.add(slot4);
        slots.add(slot5);

        return slots;
    }

    public static List<Interviewer> getSampleInterviewers() {
        List<Slot> slots = getSampleSlots();

        List<Slot> slots1 = new LinkedList<>();
        slots1.add(slots.get(0));
        slots1.add(slots.get(1));

        List<Slot> slots2 = new LinkedList<>();
        slots1.add(slots.get(2));
        slots1.add(slots.get(3));

        List<Slot> slots3 = new LinkedList<>();
        slots1.add(slots.get(4));

        Interviewer interviewer1 = new Interviewer.InterviewerBuilder(new Name("Chris"), new Phone("12345678"),
            new HashSet<>()).department(new Department("Technical")).availabilities(slots1).build();
        Interviewer interviewer2 = new Interviewer.InterviewerBuilder(new Name("John"), new Phone("12345678"),
            new HashSet<>()).department(new Department("Technical")).availabilities(slots2).build();
        Interviewer interviewer3 = new Interviewer.InterviewerBuilder(new Name("Barry"), new Phone("12345678"),
            new HashSet<>()).department(new Department("Training")).availabilities(slots3).build();

        Interviewer[] interviewersArr = new Interviewer[]{interviewer1, interviewer2, interviewer3};
        return Arrays.asList(interviewersArr);
    }

    public static List<Interviewee> getSampleInterviewees() {
        List<Slot> slots = getSampleSlots();

        List<Slot> slots1 = new LinkedList<>();
        slots1.add(slots.get(0));

        List<Slot> slots2 = new LinkedList<>();
        slots2.add(slots.get(0));
        slots2.add(slots.get(1));
        slots2.add(slots.get(2));

        List<Slot> slots3 = new LinkedList<>();
        slots3.add(slots.get(0));
        slots3.add(slots.get(3));

        List<Slot> slots4 = new LinkedList<>();
        slots4.add(slots.get(1));
        slots4.add(slots.get(4));

        List<Slot> slots5 = new LinkedList<>();
        slots5.add(slots.get(2));
        slots5.add(slots.get(3));

        Interviewee interviewee1 = new Interviewee.IntervieweeBuilder(new Name("Bernard"), new Phone("11112222"),
            new HashSet<>()).availableTimeslots(slots1).build();
        Interviewee interviewee2 = new Interviewee.IntervieweeBuilder(new Name("Bernard"), new Phone("11112222"),
            new HashSet<>()).availableTimeslots(slots2).build();
        Interviewee interviewee3 = new Interviewee.IntervieweeBuilder(new Name("Bernard"), new Phone("11112222"),
            new HashSet<>()).availableTimeslots(slots3).build();
        Interviewee interviewee4 = new Interviewee.IntervieweeBuilder(new Name("Bernard"), new Phone("11112222"),
            new HashSet<>()).availableTimeslots(slots4).build();
        Interviewee interviewee5 = new Interviewee.IntervieweeBuilder(new Name("Bernard"), new Phone("11112222"),
            new HashSet<>()).availableTimeslots(slots5).build();

        List<Interviewee> interviewees = new LinkedList<>();
        interviewees.add(interviewee1);
        interviewees.add(interviewee2);
        interviewees.add(interviewee3);
        interviewees.add(interviewee4);
        interviewees.add(interviewee5);

        return interviewees;
    }

}

