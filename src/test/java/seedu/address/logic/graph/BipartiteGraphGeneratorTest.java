package seedu.address.logic.graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.Pair;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.testutil.SampleGraph;
import seedu.address.testutil.SampleInterviewee;
import seedu.address.testutil.SampleInterviewer;

class BipartiteGraphGeneratorTest {
    @Test
    public void getGraph_sampleDataFromGraphOne_returnSampleGraphOne() {
        List<Interviewer> interviewers = SampleInterviewer.getSampleInterviewersForGraphOne();
        List<Interviewee> interviewees = SampleInterviewee.getSampleIntervieweesForGraphOne();
        BipartiteGraphGenerator generator = new BipartiteGraphGenerator(interviewers, interviewees);

        List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> expectedGraph = SampleGraph.getSampleGraphOne();
        List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> resultantGraph = generator.getGraph();

        assertEquals(resultantGraph, expectedGraph);
    }
}