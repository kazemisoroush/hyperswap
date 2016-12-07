package structure;

import java.util.ArrayList;

public class Node {

    /**
     * structure.Node identifier.
     */
    private String id;

    /**
     * structure.Node current color.
     */
    private int color;

    /**
     * structure.Node initial color.
     */
    private int initColor;

    /**
     * List of node's neighbours.
     */
    private ArrayList<String> neighbours;

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
        this.neighbours = new ArrayList<>();
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
     * Set neighbours for this node. Automatically skips duplicate or self neighbour relations.
     *
     * @param neighbours list of node neighbours.
     */
    public void setNeighbours(ArrayList<String> neighbours) {
        for (String id : neighbours) {
            // do not add duplicate neighbours...
            if (this.id.equals(id) || this.neighbours.contains(id))
                continue;

            // make the neighbour relation...
            this.neighbours.add(id);
        }
    }

    /**
     * Get list of neighbours for this vertex.
     *
     * @return nodes neighbours list.
     */
    public ArrayList<String> getNeighbours() {
        return this.neighbours;
    }

    /**
     * Get degree of vertex.
     *
     * @return node degree.
     */
    public int getDegree() {
        return this.neighbours.size();
    }

    /**
     * Check if this node has this id in it's neighbours.
     *
     * @param id of neighbour.
     *
     * @return boolean result of check.
     */
    public boolean hasNeighbour(String id) {
        // iterate on neighbours...
        for (String neighbourId : this.neighbours) {
            if (neighbourId.equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Print out the node.
     *
     * @return node to string.
     */
    @Override
    public String toString() {
        return String.format("{%s [%s]}", this.id, this.color);
    }
}
