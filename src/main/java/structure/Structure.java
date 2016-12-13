package structure;

import java.util.ArrayList;

public abstract class Structure<N, E> {

    protected ArrayList<ArrayList<Boolean>> matrix = new ArrayList<>();

    protected ArrayList<N> nodes;

    protected ArrayList<E> edges;

    protected ArrayList<String> nodeIndices = new ArrayList<>();

    protected ArrayList<Integer> edgeIndices = new ArrayList<>();

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
     * Get size of the structure edges.
     *
     * @return integer value of edges size.
     */
    public int numberOfEdges() {
        // check if edges is not null...
        if (this.numberOfNodes() == 0) {
            return 0;
        }

        return this.edges.size();
    }

    /**
     * Add a node to the graph.
     *
     * @param node to be added.
     *
     * @return size of nodes.
     */
    public abstract int addNode(N node);

    /**
     * Getter for nodes of structure.
     *
     * @return list of nodes.
     */
    public ArrayList<N> getNodes() {
        return this.nodes;
    }

    /**
     * Add a edge to the graph.
     *
     * @param edge to be added.
     *
     * @return size of nodes.
     */
    public abstract int addEdge(E edge);

    /**
     * Check if the structure contains this node type. Each structure must have this checker method.
     *
     * @param node to check.
     *
     * @return boolean value of the check.
     */
    public abstract boolean hasNode(N node);

    /**
     * Check if the structure contains this edge type. Each structure must have this checker method.
     *
     * @param edge to check.
     *
     * @return boolean value of the check.
     */
    public abstract boolean hasEdge(E edge);
}
