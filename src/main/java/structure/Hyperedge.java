package structure;

import main.Helpers;

import java.util.ArrayList;

public class Hyperedge {

    /**
     * Edge's identifier
     */
    public int id;

    /**
     * List of nodes which this edge is relating them together.
     */
    protected ArrayList<Node> nodes = new ArrayList<>();

    /**
     * No args constructor needed.
     */
    public Hyperedge() {
    }

    /**
     * Make new instance of this edge.
     *
     * @param id edge's identifier.
     */
    public Hyperedge(int id) {
        this.id = id;
    }

    /**
     * Get edge id.
     *
     * @return edge identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Add node to this edge.
     *
     * @param node which this edge is relating.
     *
     * @return real node instance.
     */
    public int addNode(Node node) {
        // add the node to the node list...
        // if and only if it's a new node...
        if (! this.nodes.contains(node)) {
            this.nodes.add(node);
        }

        return this.nodes.size();
    }

    /**
     * Get nodes in this hyperedge.
     *
     * @return set of nodes.
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        String nodesString = Helpers.implode(this.nodes, ", ");

        return String.format("{%s}", nodesString);
    }
}
