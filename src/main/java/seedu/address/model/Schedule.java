package seedu.address.model;

import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;

/**
 * Represents the interview schedule.
 */
public class Schedule {
    private String date;
    private ObservableList<ObservableList<String>> data; // Exclude the first row which is the column titles
    private ObservableList<String> columnTitles; // Including the date, which is the first item

    public Schedule(String date, LinkedList<LinkedList<String>> list) {
        this.date = date;
        ObservableList<ObservableList<String>> clone = copy(list);
        this.columnTitles = clone.remove(0);
        this.data = clone;
    }

    public String getDate() {
        return date;
    }

    public ObservableList<ObservableList<String>> getData() {
        return data;
    }

    public ObservableList<String> getColumnNames() {
        return columnTitles;
    }

    public Slot getInterviewSlot(String intervieweeName) {
        return null;
    }

    public boolean addInterviewer(Interviewer interviewer) {
        return true;
    }

    @Override
    public boolean equals(Object s) {
        if (!(s instanceof Schedule)) {
            return false;
        }
        Schedule sCasted = (Schedule) s;
        return date.equals(sCasted.date)
            && columnTitles.equals(sCasted.columnTitles)
            && data.equals(sCasted.data);
    }

    @SuppressWarnings("unchecked")
    public static ObservableList<ObservableList<String>> copy(LinkedList<LinkedList<String>> list) {
        LinkedList<ObservableList<String>> clone = new LinkedList<>();

        // Shallow copy can be used here as String is immutable.
        list.forEach(row -> {
            LinkedList<String> rowCopy = (LinkedList<String>) row.clone();
            clone.add(FXCollections.observableList(rowCopy));
        });

        return FXCollections.observableList(clone);
    }
}
