package structure;

import java.util.ArrayList;

public abstract class Structure {

    /**
     * List of nodes.
     */
    protected ArrayList<Node> nodes = new ArrayList<>();

    /**
     * List of edges.
     */
    protected ArrayList<Edge> edges = new ArrayList<>();

    /**
     * Initialize the graph with empty set of nodes.
     */
    public Structure() {
    }

    /**
     * Get number of vertices in graph.
     *
     * @return number of vertices.
     */
    public int numberOfNodes() {
        return this.nodes.size();
    }

    /**
     * Add a node to the graph.
     *
     * @param node to be added.
     *
     * @return size of nodes.
     */
    public int addNode(Node node) {
        this.nodes.add(node);

        return this.nodes.size();
    }

    /**
     * Add a nodes to the graph.
     *
     * @param nodes to be added.
     *
     * @return size of nodes.
     */
    public int addNodes(ArrayList<Node> nodes) {
        this.nodes.addAll(nodes);

        return this.nodes.size();
    }

    /**
     * Get nodes of this structure.
     *
     * @return list of nodes.
     */
    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    /**
     * Add a edge to the graph.
     *
     * @param edge to be added.
     *
     * @return size of nodes.
     */
    public int addEdge(Edge edge) {
        // do not allow duplicate edges...
        for (Edge duplicateEdge : this.edges) {
            if (duplicateEdge.equals(edge))
                return this.nodes.size();
        }

        this.edges.add(edge);

        return this.nodes.size();
    }

    /**
     * Get edges of this structure.
     *
     * @return list of nodes.
     */
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Get size of the structure edges.
     *
     * @return integer value of edges size.
     */
    public int numberOfEdges() {
        // check if edges is not null...
        if (this.edges == null) {
            return 0;
        }

        return this.edges.size();
    }

    /**
     * Make a neighbourhood for list of nodes.
     *
     * @param indexes of nodes.
     */
    //public void makeNeighbourhood(ArrayList<String> indexes) {
    //    // iterate on node indexes...
    //    for (String index : indexes) {
    //        // find the node...
    //        Node node = this.nodes.get(index);
    //
    //        // make other indexes neighbour of current iteration's node...
    //        node.setNeighbours(indexes);
    //    }
    //}

    /**
     * Check if structure has node with this identifier.
     *
     * @param nodeIdentifier which we are checking.
     *
     * @return boolean value of check result.
     */
    public boolean hasNode(String nodeIdentifier) {
        // check if structure has node with this identifier...
        for (Node node : this.nodes) {
            if (node.getId().equals(nodeIdentifier)) {
                return true;
            }
        }

        // node not found...
        return false;
    }

}
