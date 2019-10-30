package seedu.address.logic.graph;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SampleGraph;

class HopCroftKarpTest {
    @Test
    public void execute_sampleGraph1_allMatched() {
        BipartiteGraph subjectGraph = SampleGraph.getSampleGraphOne();
        new HopCroftKarp(subjectGraph).execute();
        int numInterviewees = subjectGraph.getNumInterviewees();

        IntStream.range(0, numInterviewees).forEach(i -> {
            IntervieweeVertex intervieweeVertex = subjectGraph.getIntervieweePair(i).getHead();
            assert intervieweeVertex.isMatched() : fail("Something is wrong!");
            // System.out.println(intervieweeVertex.getPartner().getItem()); // TODO: comment this out!
        });
    }
}
