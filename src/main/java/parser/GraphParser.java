package parser;

import structure.Edge;
import structure.Graph;
import structure.Node;

import java.io.IOException;
import java.util.ArrayList;

public class GraphParser extends Parser {

    /**
     * Initialize the structure.
     *
     * @param path to structure file.
     */
    public GraphParser(String path) throws IOException {
        super(path);
    }

    @Override
    public Graph read() {
        // make instance of the graph...
        Graph graph = new Graph();

        // make a variable for vertex id...
        int nodeId = 0;

        // now loop on other string lines...
        // number of iterations must be equal to number of nodes in graph...
        for (Object line : lines) {
            ArrayList<Integer> neighbourIds = this.parseLine((String) line);

            // make a color for the node...
            int nodeColor = this.randomInteger(Main.NUMBER_OF_COLORS);

            // make a new node with random color...
            Node node = new Node(nodeId, nodeColor);

            // add neighbours to the node...
            node.setNeighbours(neighbourIds);

            // add node to the graph...
            graph.addNode(node);

            // add edges...
            for (int neighbourId : neighbourIds) {
                Edge edge = new Edge(graph.numberOfEdges() + 1, nodeId, neighbourId);
                graph.addEdge(edge);
            }

            // increment the node id...
            nodeId++;
        }

        // return the filled instance of graph...
        return graph;
    }

    @Override
    public ArrayList<Integer> parseLine(String line) {
        // split the line...
        String[] parts = line.split(" ");

        // make list of ids...
        ArrayList<Integer> neighbourIds = new ArrayList<Integer>();

        // extract the neighbours...
        for (String part : parts) {
            // skip empty strings...
            if (part.equals("")) continue;

            // ids are decremented to convert into indexes...
            neighbourIds.add(Integer.parseInt(part) - 1);
        }

        // return list of indexes...
        return neighbourIds;
    }

}
