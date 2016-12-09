package structure;

import main.Helpers;

import java.util.ArrayList;

public class Hypergraph extends Structure<Node, Hyperedge> {

    /**
     * Instantiate new hypergraph.
     */
    public Hypergraph() {
        super();

        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Add a node to the graph.
     *
     * @param node to be added.
     *
     * @return size of nodes.
     */
    public int addNode(Node node) {
        if (this.hasNode(node)) {
            return this.numberOfNodes();
        }

        // add node...
        this.nodes.add(node);
        this.nodeIndices.add(node.getId());

        return this.numberOfNodes();
    }

    /**
     * Add a edge to the graph.
     *
     * @param edge to be added.
     *
     * @return size of nodes.
     */
    public int addEdge(Hyperedge edge) {
        // add new edge names to the edge list...
        if (! this.hasEdge(edge)) {
            this.edges.add(edge);
            this.edgeIndices.add(edge.getId());
        }

        // now we must update the incident matrix...
        for (String nodeIdentifier : edge.nodes) {
            int nodeIndex = this.nodeIndices.indexOf(nodeIdentifier);
            int edgeIndex = this.edgeIndices.indexOf(edge.getId());

            // if we don't have a row in incident matrix for this node then make one...
            if (this.matrix.size() <= nodeIndex) {
                this.matrix.add(new ArrayList<>());
            }

            // set the node-edge relationship...
            while (this.matrix.get(nodeIndex).size() <= edgeIndex) {
                this.matrix.get(nodeIndex).add(false);
            }
            this.matrix.get(nodeIndex).set(edgeIndex, true);
        }

        // don't forget to push false in place of nulls...
        for (int i = 0; i < this.nodes.size(); i++) {
            for (int j = 0; j < this.edges.size(); j++) {
                if (this.matrix.get(i).size() <= j) {
                    this.matrix.get(i).add(false);
                }
            }
        }

        return this.numberOfEdges();
    }

    /**
     * Check if structure has node with this identifier.
     *
     * @param toCheck which we are checking.
     *
     * @return boolean value of check result.
     */
    public boolean hasNode(Node toCheck) {
        // check if structure has node with this identifier...
        for (Node node : this.nodes) {
            if (node.getId().equals(toCheck.getId())) {
                return true;
            }
        }

        // node not found...
        return false;
    }

    /**
     * Check if structure has edge with this identifier.
     *
     * @param toCheck which we are checking.
     *
     * @return boolean value of check result.
     */
    public boolean hasEdge(Hyperedge toCheck) {
        // check if structure has edge with this identifier...
        for (Hyperedge edge : this.edges) {
            if (edge.getId() == toCheck.getId()) {
                return true;
            }
        }

        // edge not found...
        return false;
    }

    @Override
    public String toString() {
        String result = Helpers.implode(this.edges, " - ") + "\n";

        for (int i = 0; i < this.nodes.size(); i++) {
            result += this.nodes.get(i) + ": " + Helpers.implode(this.matrix.get(i), ", ");

            result += "\n";
        }

        return result;

        // list of nodes...
        // String nodesString = Helpers.implode(this.nodes, ", ");

        // list of edges...
        // String edgesString = Helpers.implode(this.edges, ", ");

        // format the object to string...
        // return String.format("Hypergraph: |V| = %s, |E| = %s \n Nodes: [%s] \n Edges: [%s] \n", this.numberOfNodes(), this.numberOfEdges(), nodesString, edgesString);
    }
}
