package structure;

import java.util.ArrayList;

public class Edge extends Hyperedge {

    /**
     * Make new instance of this edge.
     *
     * @param id           edge's identifier.
     * @param firstNodeId  integer value of first node identifier.
     * @param secondNodeId integer value of second node identifier.
     */
    public Edge(int id, Node firstNodeId, Node secondNodeId) {
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
    public int addNodes(ArrayList<Node> nodes) {
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
    public ArrayList<Node> getNodes() {
        return nodes;
    }

}
