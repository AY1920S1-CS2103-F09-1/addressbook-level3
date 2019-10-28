package seedu.address.logic.graph;

import seedu.address.model.person.Interviewee;

public class IntervieweeVertex extends Vertex<Interviewee, InterviewSlot> {
    public IntervieweeVertex(Interviewee interviewee, int index) {
        super(interviewee, index);
    }
}
