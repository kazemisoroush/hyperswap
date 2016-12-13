package partitioner;

import structure.Node;

import java.util.ArrayList;

public abstract class Partitioner<S> {

    protected int swaps = 0;

    protected int rounds = 0;

    protected int temperature = 0;

    protected double delta = 0.003;

    protected double alpha = 1;

    protected S structure = null;

    public Partitioner(S structure) {
        this.structure = structure;

        temperature = this.energy();
    }

    /**
     * Run the partitioning algorithm on the input structure.
     */
    protected abstract void partition();

    protected void sampleAndSwap(Node node) {
        // find partner from neighbors...
        Node partner = this.findPartner(node, this.getNeighbors(node), this.temperature);

        // find partner from sample...
        if (partner == null) {
            partner = this.findPartner(node, this.getSample(node), this.temperature);
        }

        // do the swap if you found any partners...
        if (partner != null) {
            this.swap(node, partner);
        }

        // update the temperature...
        this.temperature -= this.delta;

        // limit the temperature...
        if (this.temperature < 1) {
            this.temperature = 1;
        }
    }

    protected Node findPartner(Node p, ArrayList<Node> nodes, int temperature) {
        double highest = 0;

        Node bestPartner = null;

        for (Node q : nodes) {
            int dpp = this.getDegree(p, p.getColor());
            int dqq = this.getDegree(q, q.getColor());

            double before = Math.pow(dpp, this.alpha) + Math.pow(dqq, this.alpha);

            int dpq = this.getDegree(p, q.getColor());
            int dqp = this.getDegree(q, p.getColor());

            double after = Math.pow(dpq, this.alpha) + Math.pow(dqp, this.alpha);

            if (after * this.temperature > before && after > highest) {
                bestPartner = q;

                highest = after;
            }
        }

        return bestPartner;
    }

    protected abstract int energy();

    protected abstract ArrayList<Node> getNeighbors(Node node);

    protected abstract ArrayList<Node> getSample(Node node);

    protected void swap(Node first, Node second) {
        int tempColor = first.getColor();

        first.setColor(second.getColor());

        second.setColor(tempColor);
    }

    /**
     * get number of neighbors of this node with this input colors.
     *
     * @param node  to determine the degree.
     * @param color desired.
     *
     * @return the degree.
     */
    protected int getDegree(Node node, int color) {
        int degree = 0;

        for (Node neighbor : this.getNeighbors(node)) {
            if (neighbor.getColor() == color) {
                degree++;
            }
        }

        return degree;
    }
}
