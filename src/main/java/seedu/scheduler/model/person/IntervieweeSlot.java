package seedu.scheduler.model.person;

/**
 * Represents a container for a slot and the interviewee which will be interviewing at the timing of the slot.
 */
public class IntervieweeSlot extends Slot {
    private Interviewee interviewee;

    public IntervieweeSlot(String date, String start, String end, Interviewee interviewee) {
        super(date, start, end);
        this.interviewee = interviewee;
    }

    public Interviewee getInterviewee() {
        return interviewee;
    }
}
