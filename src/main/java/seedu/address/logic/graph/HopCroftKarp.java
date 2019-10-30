package seedu.address.logic.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Runs the Hopcroft-Karp algorithm on the given bipartite graphs of interviewee and available interview slots
 * and returns the set of maximum one-to-one matching between interviewee and available interview slots.
 * Crucial assumption: A bipartite graph is given to the algorithm.
 */
public class HopCroftKarp {
    private BipartiteGraph graph;
    private List<InterviewSlotVertex> intervieweePredecessor;
    private List<List<IntervieweeVertex>> interviewSlotPredecessors;
    private boolean[] usedInterviewees;
    private boolean[] usedSlots;

    public HopCroftKarp(BipartiteGraph graph) {
        this.graph = graph;
    }

    public void execute() {
        initialiseHopCroftKarp();
        List<InterviewSlotVertex> lastLayer = new LinkedList<>();

        do {
            lastLayer = new BfsHopCroftKarp(graph).execute(intervieweePredecessor, interviewSlotPredecessors);
            // If augmenting path(s) is found
            if (!lastLayer.isEmpty()) {
                new DfsHopCroftKarp(graph).execute(lastLayer, intervieweePredecessor, interviewSlotPredecessors,
                    usedInterviewees, usedSlots);
            }
            cleanUp();
        } while (!lastLayer.isEmpty()); // while there exists an augmenting path(s)
    }

    private void initialiseHopCroftKarp() {
        int numInterviewees = graph.getNumInterviewees();
        int numSlots = graph.getNumInterviewSlots();

        intervieweePredecessor = Arrays.asList(new InterviewSlotVertex[numInterviewees]);
        interviewSlotPredecessors = new ArrayList<>(numSlots);
        usedInterviewees = new boolean[numInterviewees];
        usedSlots = new boolean[numSlots];

        IntStream.range(0, numSlots).forEach(i -> interviewSlotPredecessors.add(new LinkedList<>()));
    }

    private void cleanUp() {
        Collections.fill(intervieweePredecessor, null);
        interviewSlotPredecessors.forEach(list -> list.clear());
        Arrays.fill(usedInterviewees, false);
        Arrays.fill(usedSlots, false);
    }
}
