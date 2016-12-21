package partitioner;

import sampling.Sampler;

public abstract class Partitioner<Structure> {

    protected Sampler sampler;

    /**
     * Total number of swaps for partitioner.
     */
    protected int swaps = 0;

    /**
     * Temperature variable for simulated annealing cooling process.
     */
    protected double temperature = 10;

    /**
     * Data structure to partition.
     */
    protected Structure structure = null;

    /**
     * Initialize the partitioner.
     *
     * @param structure to be partitioned.
     */
    public Partitioner(Structure structure) {
        this.structure = structure;
    }

    /**
     * Run the partitioning algorithm on the input structure.
     */
    protected abstract void partition();

    /**
     * Retrieve a list of sample nodes from nodes in the structure.
     *
     * @param node to find sample for.
     *
     * @return list of sample nodes.
     */
    // protected static abstract ArrayList<Node> getSample(Node node);

}
