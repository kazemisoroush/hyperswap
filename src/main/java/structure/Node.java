package structure;

import main.Main;
import sampling.HypergraphSampler;

import java.util.ArrayList;

public class Node {

    /**
     * Node identifier.
     */
    private String id;

    /**
     * Node current color.
     */
    private int color;

    /**
     * Node initial color.
     */
    private int initColor;

    /**
     * Node's edges.
     */
    public ArrayList<Hyperedge> hyperedges;

    /**
     * Initialize node object.
     *
     * @param id    node's identifier.
     * @param color initial color of node.
     */
    public Node(String id, int color) {
        this.id = id;
        this.color = color;
        this.initColor = color;
        this.hyperedges = new ArrayList<>();
    }

    /**
     * Getter for node's identifier.
     *
     * @return node's identifier.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Change the node's color to input parameter.
     *
     * @param color color to set.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Get node's color.
     *
     * @return node's color.
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Get node initialized color.
     *
     * @return initial color.
     */
    public int getInitColor() {
        return this.initColor;
    }

    /**
     * Energy of node within each edge of it.
     *
     * @return double value of energy.
     */
    public double energy() {
        return this.energy(this.color);
    }

    /**
     * Energy of this node if it's color is equal to input color. This is equal to dissimilarity of node in each of it's
     * hyperedges. For each hyperedge just count the dissimilarity and divide it by size of neighbors inside it.
     *
     * @param color which we need energy of node with it.
     *
     * @return double value of energy.
     */
    public double energy(int color) {

        double energy = 0;

        // we need summation of dissimilarities in node within each edge of the node...
        for (Hyperedge edge : this.hyperedges) {
            // edge with one node has no dissimilarity...
            if (edge.getNodes().size() == 1) {
                continue;
            }

            double dissimilarity = 0;

            // search for each neighbor...
            for (Node neighbor : edge.getNodes()) {
                if (neighbor.getColor() != color) {
                    dissimilarity++;
                }
            }

            energy += dissimilarity / (edge.getNodes().size() - 1);
        }

        return energy;
    }

    /**
     * Add single hyperedge to the node.
     *
     * @param hyperedge to add.
     */
    public void addHyperedge(Hyperedge hyperedge) {
        this.hyperedges.add(hyperedge);
    }

    /**
     * Swap color of this node with input node.
     *
     * @param node to swap colors.
     *
     * @return boolean value of success or failure.
     */
    public boolean swap(Node node) {
        // check if nodes are equal...
        if (node.getId().equals(this.getId())) {
            return false;
        }

        int tempColor = this.getColor();

        this.setColor(node.getColor());

        node.setColor(tempColor);

        return true;
    }

    /**
     * Return list of neighbors.
     *
     * @return list of neighbors.
     */
    public ArrayList<Node> getNeighbors() {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (Hyperedge hyperedges : this.hyperedges) {
            for (Node neighbor : hyperedges.nodes) {
                // avoid self add or duplicate...
                if (neighbor.getId().equals(this.getId()) || neighbors.contains(neighbor)) {
                    continue;
                }
                // add the neighbor to the list...
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    /**
     * Return list of neighbors.
     *
     * @param color to match neighbors.
     *
     * @return list of neighbors.
     */
    public ArrayList<Node> getNeighbors(int color) {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (Hyperedge hyperedges : this.hyperedges) {
            for (Node neighbor : hyperedges.nodes) {
                // avoid self add or duplicate...
                if (neighbor.getId().equals(this.getId()) || neighbors.contains(neighbor) || neighbor.getColor() != color) {
                    continue;
                }

                // add the neighbor to the list...
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    /**
     * Get degree of hypergraph.
     *
     * @return number of hyperedges.
     */
    public int getDegree() {
        return this.getNeighbors().size();
    }

    /**
     * Get degree of hypergraph.
     *
     * @param color to search in neighbors.
     *
     * @return number of hyperedges.
     */
    public int getDegree(int color) {
        return this.getNeighbors(color).size();
    }

    /**
     * Sample and Swap function from ja-be-ja algorithm. This method uses hybrid partner selection policy. In this
     * policy first the immediate neighbor nodes are selected (i.e., the local policy). If this selection fails to
     * improve the pair-wise utility, the node is given another chance for improvement, by letting it to select nodes
     * from its random sample.
     *
     * @param temperature is simulated annealing cooling factor.
     * @param sampler     object which samples nodes from structure.
     *
     * @return true if any swapping happened.
     */
    public boolean sampleAndSwap(double temperature, HypergraphSampler sampler) {
        // find partner from neighbors...
        Node partner = this.findPartner(this.getNeighbors(), temperature);

        boolean swapHappened = false;

        // find partner from sample...
        if (partner == null) {
            partner = this.findPartner(sampler.getSample(this), temperature);
        }

        // do the swap if you found any partners...
        if (partner != null) {
            // swap node with it's partner and don't forget to increase the swaps counter...
            this.swap(partner);
            swapHappened = true;
        }

        return swapHappened;
    }

    /**
     * Find best partner to swap from input candidate nodes for node p.
     *
     * @param candidates from which we need to extract best swap partner.
     *
     * @return best swap partner for the node or null.
     */
    private Node findPartner(ArrayList<Node> candidates, Double temperature) {
        double highest = 0;

        Node p = this;
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
            if (after * temperature > before && after > highest) {
                bestPartner = q;

                highest = after;
            }
        }

        return bestPartner;
    }

    /**
     * Print out the node.
     *
     * @return node to string.
     */
    @Override
    public String toString() {
        return String.format("[%s c=%s e=%s]", this.getId(), this.getColor(), this.energy());
    }
}
