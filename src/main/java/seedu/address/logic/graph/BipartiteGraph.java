package seedu.address.logic.graph;

import java.util.List;
import seedu.address.commons.util.Pair;
import seedu.address.model.person.Department;

/**
 * Encapsulates a bipartite graph of interviewees vertices and interview slot vertices. Serves as a container
 * for the underlying list to store some additional information.
 */
public class BipartiteGraph {
    private List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph;
    private int numInterviewees;
    private int numInterviewSlots;

    public BipartiteGraph(List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> graph,
                          int numInterviewees, int numInterviewSlots) {
        this.graph = graph;
        this.numInterviewees = numInterviewees;
        this.numInterviewSlots = numInterviewSlots;
    }

    public int getNumInterviewees() {
        return numInterviewees;
    }

    public int getNumInterviewSlots() {
        return numInterviewSlots;
    }

    public List<Pair<IntervieweeVertex, List<InterviewSlotVertex>>> getGraph() {
        return graph;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || other instanceof BipartiteGraph && graph.equals(((BipartiteGraph) other).graph)
                && numInterviewees == ((BipartiteGraph) other).numInterviewees
                && numInterviewSlots == ((BipartiteGraph) other).numInterviewSlots;
    }
}
