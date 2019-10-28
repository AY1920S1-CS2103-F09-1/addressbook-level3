package seedu.address.logic.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs the Hopcroft-Karp algorithm on the given bipartite graphs of interviewee and available interview slots
 * and returns the set of maximum one-to-one matching between interviewee and available interview slots.
 * Crucial assumption: A bipartite graph is given to the algorithm.
 */
public class HopCroftKarp {
    public void execute(BipartiteGraph graph) {
        int numInterviewees = graph.getNumInterviewees();
        int numSlots = graph.getNumInterviewSlots();

        ArrayList<List<InterviewSlotVertex>> intervieweesPredecessor = new ArrayList<>(numInterviewees);
        ArrayList<List<InterviewSlotVertex>> interviewSlotsPredecessor = new ArrayList<>(numSlots);
    }
}
