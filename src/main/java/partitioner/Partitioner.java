package partitioner;

import main.Main;
import structure.Node;

import java.util.ArrayList;

public abstract class Partitioner<Structure> {

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
     * Sample and Swap function from ja-be-ja algorithm. This method uses hybrid partner selection policy. In this
     * policy first the immediate neighbor nodes are selected (i.e., the local policy). If this selection fails to
     * improve the pair-wise utility, the node is given another chance for improvement, by letting it to select nodes
     * from its random sample.
     *
     * @param node to execute sample and swap on.
     */
    protected void sampleAndSwap(Node node) {
        // find partner from neighbors...
        Node partner = this.findPartner(node, node.getNeighbors());

        // find partner from sample...
        if (partner == null) {
            partner = this.findPartner(node, this.getSample(node));
        }

        // do the swap if you found any partners...
        if (partner != null) {
            // swap node with it's partner and don't forget to increase the swaps counter...
            node.swap(partner);
            this.swaps++;
        }

        // update the temperature...
        this.temperature -= Main.DELTA;

        // limit the temperature...
        if (this.temperature < 1) {
            this.temperature = 1;
        }
    }

    /**
     * Find best partner to swap from input candidate nodes for node p.
     *
     * @param p          to find partners for.
     * @param candidates from which we need to extract best swap partner.
     *
     * @return best swap partner for the node.
     */
    private Node findPartner(Node p, ArrayList<Node> candidates) {
        double highest = 0;

        Node bestPartner = null;

        // test all candidates...
        for (Node q : candidates) {
            double dpp = p.energy();
            double dqq = q.energy();

            double before = Math.pow(dpp, Main.ALPHA) + Math.pow(dqq, Main.ALPHA);

            double dpq = p.energy(q.getColor());
            double dqp = q.energy(p.getColor());

            double after = Math.pow(dpq, Main.ALPHA) + Math.pow(dqp, Main.ALPHA);

            // the decision criterion...
            if (after * this.temperature > before && after > highest) {
                bestPartner = q;

                highest = after;
            }
        }

        return bestPartner;
    }

    /**
     * Retrieve a list of sample nodes from nodes in the structure.
     *
     * @param node to find sample for.
     *
     * @return list of sample nodes.
     */
    protected abstract ArrayList<Node> getSample(Node node);

}
