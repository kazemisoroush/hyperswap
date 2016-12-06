package structure;

import main.Helpers;

import java.util.ArrayList;
import java.util.HashSet;

public class Edge {

    /**
     * structure.Edge's identifier
     */
    public int id;

    /**
     * List of nodes which this edge is relating them together.
     */
    protected HashSet<Integer> nodes = new HashSet<Integer>();

    /**
     * Make new instance of this edge.
     *
     * @param id edge's identifier.
     */
    public Edge(int id) {
        this.id = id;
    }

    /**
     * Make new instance of this edge.
     *
     * @param id    edge's identifier.
     * @param nodes which this edge is relating.
     */
    public Edge(int id, ArrayList<Integer> nodes) {
        this.id = id;
        this.nodes.addAll(nodes);
    }

    /**
     * Make new instance of this edge.
     *
     * @param id           edge's identifier.
     * @param firstNodeId  integer value of first node identifier.
     * @param secondNodeId integer value of second node identifier.
     */
    public Edge(int id, int firstNodeId, int secondNodeId) {
        this.id = id;
        this.nodes.add(firstNodeId);
        this.nodes.add(secondNodeId);
    }

    /**
     * Add nodes to this edge.
     *
     * @param nodes which this edge is relating.
     *
     * @return size of nodes.
     */
    public int addNodes(ArrayList<Integer> nodes) {
        this.nodes.addAll(nodes);

        return this.nodes.size();
    }

    /**
     * Check if edge has node this node id.
     *
     * @param nodeId to check.
     *
     * @return result of check.
     */
    public boolean hasNode(int nodeId) {
        return this.nodes.contains(nodeId);
    }

    /**
     * Get node list for this edge.
     *
     * @return node list.
     */
    public HashSet<Integer> getNodes() {
        return nodes;
    }

    /**
     * Check if two edges are equal.
     *
     * @param edge possible duplicate edge.
     *
     * @return boolean result of check.
     */
    public boolean equals(Edge edge) {
        return this.nodes.equals(edge.nodes);
    }

    @Override
    public String toString() {
        String nodesString = Helpers.implode(this.nodes, ", ");

        return String.format("{%s}", nodesString);
    }
}
