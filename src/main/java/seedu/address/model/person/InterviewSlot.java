package seedu.address.model.person;

public class InterviewSlot extends Slot {
    private Interviewer interviewer;

    public InterviewSlot(String date, String start, String end, Interviewer interviewer) {
        super(date, start, end);
        this.interviewer = interviewer;
    }

    public String getDateTime() {
        return super.toString();
    }

    public Name getInterviewerName() {
        return interviewer.getName();
    }

    public Department getDepartment() {
        return interviewer.getDepartment();
    }

    @Override
    public String toString() {
        String dateTime = super.toString();
        return String.format("%s, %s", dateTime, interviewer.getName());
    }
}
