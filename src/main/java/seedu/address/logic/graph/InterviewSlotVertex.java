package seedu.address.logic.graph;

import seedu.address.model.person.Interviewee;

public class InterviewSlotVertex extends Vertex<InterviewSlot, Interviewee> implements Comparable<InterviewSlotVertex> {
    public InterviewSlotVertex(InterviewSlot item, int index) {
        super(item, index);
    }

    @Override
    public int compareTo(InterviewSlotVertex other) {
        InterviewSlot thisInterviewSlot = this.getItem();
        InterviewSlot otherInterviewSlot = other.getItem();

        return thisInterviewSlot.compareTo(otherInterviewSlot);
    }
}
