package parser;

import structure.Graph;

import java.io.IOException;
import java.util.ArrayList;

public class GraphParser extends Parser {

    /**
     * Initialize the structure.
     *
     * @param path to structure logFile.
     */
    public GraphParser(String path) throws IOException {
        super(path);
    }

    @Override
    public Graph read() {
        // make instance of the graph...
        Graph graph = new Graph();

        // make a variable for vertex id...
        int edgeId = 0;

        // now loop on other string lines...
        // number of iterations must be equal to number of nodes in graph...
        for (Object line : lines) {
            ArrayList<String> edgeNodes;
            edgeNodes = this.parseLine((String) line);

            // check for new nodes and add them to the graph...
            for (String firstNode : edgeNodes) {
                for (String secondNode : edgeNodes) {
                    if (firstNode.equals(secondNode)) {
                        continue;
                    }
                }
                // create new node with random color and add it to the graph...
                // Node node = new Node(nodeIdentifier, this.randomInteger(Main.NUMBER_OF_COLORS));
                // graph.addNode(node);
            }

            // create an hyperedge instance and add nodes to the hyperedge...
            // Edge edge = new Edge(edgeId, edgeNodes);

            // add the edge to the graph...
            // graph.addEdge(edge);

            // make neighbourhood relationship for all nodes in the hyperedge...
            // graph.makeNeighbourhood(hyperedgeNodes);

            // increment the node id...
            edgeId++;
        }

        // return the filled instance of graph...
        return graph;
    }

    @Override
    public ArrayList<String> parseLine(String line) {
        // split the line...
        String[] parts = line.split(" ");

        // make list of ids...
        ArrayList<String> neighbourIds = new ArrayList<>();

        // extract the neighbours...
        for (String part : parts) {
            // skip empty strings...
            if (part.equals("")) {
                continue;
            }

            // ids are decremented to convert into indexes...
            neighbourIds.add(part);
        }

        // return list of indexes...
        return neighbourIds;
    }

}
