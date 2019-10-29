package seedu.address.logic.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SampleGraph;
import seedu.address.testutil.SampleInterviewSlotVertex;

public class BfsHopCroftKarpTest {

    @Test
    public void bfs_sampleGraphOneAfterOneIteration_success() {
        BipartiteGraph subjectGraph = SampleGraph.getSampleGraphOne();
        int numInterviewees = subjectGraph.getNumInterviewees();
        int numSlots = subjectGraph.getNumInterviewSlots();

        List<List<InterviewSlotVertex>> intervieweePredecessors = new ArrayList<>(numInterviewees);
        List<List<IntervieweeVertex>> interviewSlotPredecessors = new ArrayList<>(numSlots);
        fillWithSubLists(intervieweePredecessors, numInterviewees);
        fillWithSubLists(interviewSlotPredecessors, numSlots);

        List<InterviewSlotVertex> expectedVertices = SampleInterviewSlotVertex.getSampleInterviewSlotVerticesGraph1();
        List<InterviewSlotVertex> resultVertices = new BfsHopCroftKarp(subjectGraph).bfs(intervieweePredecessors,
            interviewSlotPredecessors);

        // resultVertices.forEach(System.out::println);
        assertEquals(resultVertices, expectedVertices);
    }

    private <Z> void fillWithSubLists(List<List<Z>> list, int numInterviewees) {
        for (int i = 0; i < numInterviewees; i++) {
            list.add(new LinkedList<>());
        }
    }
}