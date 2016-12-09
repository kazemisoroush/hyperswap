package structure;

import main.Helpers;

import java.util.ArrayList;
import java.util.HashSet;

public class Hyperedge {

    /**
     * structure.Edge's identifier
     */
    public int id;

    /**
     * List of nodes which this edge is relating them together.
     */
    protected HashSet<String> nodes = new HashSet<>();

    /**
     * No args constructor needed.
     */
    public Hyperedge() {
    }

    /**
     * Make new instance of this edge.
     *
     * @param id    edge's identifier.
     * @param nodes which this edge is relating.
     */
    public Hyperedge(int id, ArrayList<String> nodes) {
        this.id = id;
        this.nodes.addAll(nodes);
    }

    /**
     * Add nodes to this edge.
     *
     * @param nodes which this edge is relating.
     *
     * @return size of nodes.
     */
    public int addNodes(ArrayList<String> nodes) {
        this.nodes.addAll(nodes);

        return this.nodes.size();
    }

    /**
     * Get edge id.
     *
     * @return edge identifier.
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String nodesString = Helpers.implode(this.nodes, ", ");

        return String.format("{%s}", nodesString);
    }
}
