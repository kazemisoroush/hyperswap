package parser;

import structure.Edge;
import structure.Node;
import structure.Structure;

import java.util.ArrayList;
import java.util.HashSet;

public class Calculator {

    /**
     * Calculate energy on input instance of structure.
     *
     * @param structure which we need to know it's energy.
     *
     * @return integer value of structure.
     */
    public static int structureEnergy(Structure structure) {
        // get graph nodes and edges...
        ArrayList<Node> nodes = structure.getNodes();
        ArrayList<Edge> edges = structure.getEdges();

        // instantiate energy value...
        int energySum = 0;

        // sum the graph nodes energy...
        for (Edge edge : edges) {
            // get edge nodes...
            HashSet<Integer> edgeNodes = edge.getNodes();

            for (int nodeId : edgeNodes) {
                // find the node instance...
                Node node = nodes.get(nodeId);

                energySum += nodeEnergy(structure, node);
            }
        }

        // return the energy value...
        return energySum / 2;
    }

    /**
     * Get energy of node in the graph.
     *
     * @param structure including all nodes.
     * @param node      that we need energy of.
     *
     * @return integer value of node energy.
     */
    private static int nodeEnergy(Structure structure, Node node) {
        // instantiate node energy...
        int energy = 0;

        // TODO: for each edge containing the node
        // TODO: we must check for the neighbours
        // TODO: and then for each neighbour we must increment the energy value

        // iterate on nodes of graph to see which one is it's neighbour...
        for (Node neighbour : structure.getNodes()) {
            // check the neighbourhood...
            if (! node.hasNeighbour(neighbour.getId())) continue;

            // increment the energy...
            energy++;
        }

        return energy;
    }
}
