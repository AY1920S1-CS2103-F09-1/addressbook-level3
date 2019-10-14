package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class ScheduleTest {
    /**
     * Compare the resultant Schedule object given a sample interviewer's availability table
     * with the expected Schedule object.
     */
    @Test
    public void schedule_nonEmptyTable_success() {
        LinkedList<LinkedList<String>> table = getSampleTable();
        ObservableList<ObservableList<String>> tableObservable = Schedule.copy(table);
        ObservableList<String> columnTitles = tableObservable.remove(0);
        String date = columnTitles.get(0);

        Schedule schedule = new Schedule(date, table);
        assertEquals(date, schedule.getDate());
        assertEquals(columnTitles, schedule.getColumnNames());
        assertEquals(tableObservable, schedule.getData());
    }

    @Test
    public void clone_twoDimensionalLinkedList_success() {
        LinkedList<LinkedList<String>> list = new LinkedList<>();
        IntStream.range(0, 3).forEach(i -> list.add(getRandomList(5)));
        ObservableList<ObservableList<String>> clone = Schedule.copy(list);
        assertEquals(list, clone);
    }

    private LinkedList<String> getRandomList(int n) {
        LinkedList<String> list = new LinkedList<>();
        Random rand = new Random();
        IntStream.range(0, n).forEach(i -> list.add(String.valueOf(rand.nextInt(1000))));
        return list;
    }

    private LinkedList<LinkedList<String>> getSampleTable() {
        LinkedList<LinkedList<String>> table = new LinkedList<>();

        LinkedList<String> columnTitles = new LinkedList<>();
        columnTitles.add("9/10/2019(Wed)");
        columnTitles.add("Technical-Jason");
        columnTitles.add("Marketing-John");
        columnTitles.add("Marketing-Walter");
        table.add(columnTitles);

        LinkedList<String> timeSlots = getSampleTimeSlots();
        Random random = new Random();
        IntStream.range(0, timeSlots.size()).forEach(i -> {
            LinkedList<String> row = new LinkedList<>();
            row.add(timeSlots.get(i));
            row.add(String.valueOf(random.nextInt(2)));
            row.add(String.valueOf(random.nextInt(2)));
            row.add(String.valueOf(random.nextInt(2)));
            table.add(row);
        });

        return table;
    }

    private LinkedList<String> getSampleTimeSlots() {
        LinkedList<String> timeSlots = new LinkedList<>();
        timeSlots.add("6:00pm-6:30pm");
        timeSlots.add("6:30pm-7:00pm");
        timeSlots.add("7:00pm-7:30pm");
        timeSlots.add("7:30pm-8:00pm");
        timeSlots.add("8:00pm-8:30pm");
        timeSlots.add("8:30pm-9:00pm");
        return timeSlots;
    }
}
