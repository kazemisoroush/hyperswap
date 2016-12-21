package structure;

import main.Helpers;

import java.util.ArrayList;

public class Hypergraph extends Structure<Hyperedge> {

    /**
     * Instantiate new hypergraph.
     */
    public Hypergraph() {
        super();

        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Find node with node identifier.
     *
     * @param id to find.
     *
     * @return node pointer.
     */
    protected Node getNodeById(String id) {
        for (Node node : this.nodes) {
            if (id.equals(node.getId())) {
                return node;
            }
        }

        return null;
    }

    /**
     * Find node with edge identifier.
     *
     * @param id to find.
     *
     * @return edge pointer.
     */
    protected Hyperedge getEdgeById(int id) {
        for (Hyperedge edge : this.edges) {
            if (id == edge.getId()) {
                return edge;
            }
        }

        return null;
    }

    @Override
    public Node addNode(Node node) {
        if (this.hasNode(node)) {
            // return the node inside nodes array...
            return this.getNodeById(node.getId());
        }

        // add node...
        this.nodes.add(node);
        // this.nodeIndices.add(node.getId());

        return node;
    }

    @Override
    public Hyperedge addEdge(Hyperedge edge) {
        // search edges list for this edge...
        if (this.hasEdge(edge)) {
            return this.getEdgeById(edge.getId());
        }

        // we have a new edge...
        this.edges.add(edge);
        return edge;
    }

    @Override
    public ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();

        // for each edge of this node...
        for (Hyperedge edge : this.edges) {
            // find all unique nodes...
            // for (Node neighbor : edge.getNodes()) {
            //     if (neighbors.contains(neighbor)) {
            //         continue;
            //     }
            // }
        }

        return neighbors;
    }

    @Override
    public boolean hasNode(Node node) {
        // check if structure has node with this identifier...
        return this.getNodeById(node.getId()) != null;
    }

    @Override
    public boolean hasEdge(Hyperedge edge) {
        // check if structure has edge with this identifier...
        return this.getEdgeById(edge.getId()) != null;
    }

    /**
     * Energy of graph is summation of each node's energy.
     *
     * @return double value of energy.
     */
    public double energy() {
        double energy = 0;

        for (Node node : this.nodes) {
            energy += node.energy();
        }

        return energy;
    }

    @Override
    public String toString() {
        String nodeString = Helpers.implode(this.nodes, ", ");
        String edgeString = Helpers.implode(this.edges, ", ");

        return String.format("Hypergraph: |V| = %s, |E| = %s, Energy = %s \n     Nodes: %s \nHyperedges: %s", this.nodes.size(), this.edges.size(), this.energy(), nodeString, edgeString);
    }
}
