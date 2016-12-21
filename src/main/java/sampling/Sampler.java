package sampling;

import structure.Node;
import structure.Structure;

import java.util.ArrayList;

public abstract class Sampler {

    /**
     * Data structure to get sampler from.
     */
    protected Structure structure;

    /**
     * Get uniform random sample from input data structure.
     *
     * @param node to sample for.
     *
     * @return list of sample nodes.
     */
    public abstract ArrayList<Node> getSample(Node node);

}
