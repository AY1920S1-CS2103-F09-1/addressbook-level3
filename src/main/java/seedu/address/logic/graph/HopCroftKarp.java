package seedu.address.logic.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Runs the Hopcroft-Karp algorithm on the given bipartite graphs of interviewee and available interview slots
 * and returns the set of maximum one-to-one matching between interviewee and available interview slots.
 * Crucial assumption: A bipartite graph is given to the algorithm.
 */
public class HopCroftKarp {
    private List<List<InterviewSlotVertex>> intervieweePredecessors;
    private List<List<IntervieweeVertex>> interviewSlotPredecessors;
    private boolean[] usedInterviewees;
    private boolean[] usedSlots;

    public void execute(BipartiteGraph graph) {
        int numInterviewees = graph.getNumInterviewees();
        int numSlots = graph.getNumInterviewSlots();

        // NEED TO CLEAN UP THESE 4 STUFF after every iteration
        intervieweePredecessors = new ArrayList<>(numInterviewees);
        interviewSlotPredecessors = new ArrayList<>(numSlots);
        usedInterviewees = new boolean[numInterviewees];
        usedSlots = new boolean[numSlots];

        fillWithSubLists(intervieweePredecessors, numInterviewees);
        fillWithSubLists(interviewSlotPredecessors, numSlots);
    }

    private <Z> void fillWithSubLists(List<List<Z>> list, int numInterviewees) {
        for (int i = 0; i < numInterviewees; i++) {
            list.add(new LinkedList<>());
        }
    }
}
