package seedu.address.logic.graph;

import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;

public class InterviewSlot extends Slot {
    private Interviewer interviewer;

    public InterviewSlot(String date, String start, String end, Interviewer interviewer) {
        super(date, start, end);
        this.interviewer = interviewer;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }
}
