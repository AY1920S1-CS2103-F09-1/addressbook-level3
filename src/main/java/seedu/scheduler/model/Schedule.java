package seedu.scheduler.model;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;

/**
 * Represents the interview schedule.
 * The first row of the Schedule is the column titles, with the first cell as the date of the interview schedule.
 * Subsequent rows are time slots, with the first cell of each row as the timing of all the time slots in the row.
 */
public class Schedule {
    private String date;
    private ObservableList<String> titles;
    private ObservableList<ObservableList<String>> data; // EXCLUDE the first row which is the column titles

    public Schedule(String date, LinkedList<LinkedList<String>> list) {
        this.date = date;

        ObservableList<ObservableList<String>> table = toTwoDimensionalObservableList(list);
        if (table.isEmpty()) {
            this.titles = FXCollections.observableList(new LinkedList<>());
        } else {
            this.titles = table.remove(0);
        }

        this.data = table;
    }

    private Schedule() {
    }

    public ObservableList<String> getTitles() {
        return titles;
    }

    public ObservableList<ObservableList<String>> getObservableList() {
        return data;
    }

    public List<Slot> getInterviewSlots(String intervieweeName) {
        List<Slot> slots = new LinkedList<>();
        int tableSize = data.size();

        // Need to search the first row as well because now the first row of data(table) is not the titles,
        // it is data.
        for (int i = 0; i < tableSize; i++) {
            ObservableList<String> row = data.get(i);
            int rowSize = row.size();

            // Exclude search in the first cell as the first cell is the time slot
            for (int j = 1; j < rowSize; j++) {
                String value = row.get(j);
                if ("NA".equals(value)) {
                    continue;
                } else if (intervieweeName.equals(value)) {
                    String timeSlot = row.get(0);
                    String[] times = timeSlot.split("-");
                    String start = times[0].trim();
                    String end = times[1].trim();
                    slots.add(new Slot(date, start, end));
                }
            }
        }

        return slots;
    }

    /**
     * Returns true if an interviewer exists in the Schedule.
     */
    public boolean hasInterviewer(Interviewer interviewer) {
        String columnTitle = generateColumnTitle(interviewer);

        boolean found = false;
        for (String title : titles) {
            if (title.equals(columnTitle)) {
                found = true;
                break;
            }
        }

        return found;
    }

    /**
     * Returns the corresponding column title of the given interviewer.
     */
    public String generateColumnTitle(Interviewer interviewer) {
        return String.format("%s - %s", interviewer.getDepartment().toString(),
            interviewer.getName().toString());
    }

    @Override
    public boolean equals(Object s) {
        if (!(s instanceof Schedule)) {
            return false;
        }
        Schedule sCasted = (Schedule) s;
        return date.equals(sCasted.date)
            && titles.equals(sCasted.titles)
            && data.equals(sCasted.data);
    }

    /**
     * Returns a copy of the @code{Schedule} object given.
     */
    public static Schedule cloneSchedule(Schedule schedule) {
        Schedule clone = new Schedule();
        clone.date = String.valueOf(schedule.date);
        clone.titles = cloneRow(schedule.titles);
        clone.data = cloneTable(schedule.data);
        return clone;
    }

    /**
     * Returns an independent deep copy of the table given in observable list form.
     */
    private static ObservableList<ObservableList<String>> cloneTable(ObservableList<ObservableList<String>> table) {
        ObservableList<ObservableList<String>> tableClone = FXCollections.observableList(new LinkedList<>());

        for (ObservableList<String> row : table) {
            ObservableList<String> rowClone = cloneRow(row);
            tableClone.add(rowClone);
        }

        return tableClone;
    }

    /**
     * Returns an independent deep copy of the row given in observable list form.
     */
    private static ObservableList<String> cloneRow(ObservableList<String> row) {
        ObservableList<String> rowClone = FXCollections.observableList(new LinkedList<>());
        for (String string : row) {
            rowClone.add(String.valueOf(string));
        }
        return rowClone;
    }

    /**
     * Convert a two-dimensional LinkedList into a two-dimensional Observable list.
     */
    public static ObservableList<ObservableList<String>> toTwoDimensionalObservableList(
        LinkedList<LinkedList<String>> list) {
        LinkedList<ObservableList<String>> clone = new LinkedList<>();

        // Shallow copy can be used here as String is immutable.
        list.forEach(row -> {
            LinkedList<String> rowCopy = (LinkedList<String>) row.clone();
            clone.add(FXCollections.observableList(rowCopy));
        });

        return FXCollections.observableList(clone);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(450);

        // Append the title rows
        String titleRep = rowToString(titles);
        builder.append(titleRep);

        // Append the other rows
        for (ObservableList<String> row : data) {
            String rowRep = rowToString(row);
            builder.append(rowRep);
        }

        return builder.toString();
    }

    /**
     * Convert a row to its string representation (each value separated by a comma, then the row ends with
     * a newline character.
     */
    private String rowToString(List<String> row) {
        StringBuilder builder = new StringBuilder(110);

        for (String value : row) {
            builder.append(value);
            builder.append(",");
        }

        builder.append("\n");
        return builder.toString();
    }
}
