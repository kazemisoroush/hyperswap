package parser;

import structure.Edge;
import structure.Hypergraph;
import structure.Node;

import java.io.IOException;
import java.util.ArrayList;

public class HypergraphParser extends Parser {

    /**
     * Initialize the structure.
     *
     * @param path to structure logFile.
     */
    public HypergraphParser(String path) throws IOException {
        super(path);
    }

    @Override
    public Hypergraph read() {
        // make instance of the hypergraph...
        Hypergraph hypergraph = new Hypergraph();

        // make the node instances...
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < this.numberOfNodes; i++) {
            nodes.add(new Node(i, this.randomInteger(Main.NUMBER_OF_COLORS)));
        }

        // add these nodes to the hypergraph...
        hypergraph.addNodes(nodes);

        // make a variable for edge id...
        int edgeId = 0;

        // now loop on other string lines...
        // number of iterations must be equal to number of edges...
        for (Object line : this.lines) {
            // get hyperedge's nodes in single line of input logFile...
            ArrayList<Integer> hyperedgeNodes = this.parseLine((String) line);

            // create an hyperedge instance and add nodes to the hyperedge...
            Edge hyperedge = new Edge(edgeId, hyperedgeNodes);

            // add the hyperedge to the hypergraph...
            hypergraph.addEdge(hyperedge);

            // make neighbourhood relationship for all nodes in the hyperedge...
            hypergraph.makeNeighbourhood(hyperedgeNodes);

            // increment the node id...
            edgeId++;
        }

        return hypergraph;
    }

    @Override
    public ArrayList<Integer> parseLine(String line) {
        // split the line...
        String[] parts = line.split(" ");

        // make list of node ids...
        ArrayList<Integer> hyperedgeNodes = new ArrayList<Integer>();

        // extract the neighbours...
        for (String part : parts) {
            if (part.equals("")) continue;

            hyperedgeNodes.add(Integer.parseInt(part) - 1);
        }

        return hyperedgeNodes;
    }
}
