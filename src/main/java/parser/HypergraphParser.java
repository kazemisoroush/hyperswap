package parser;

import main.Helpers;
import structure.Hyperedge;
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

        // make a variable for edge id...
        int edgeId = 1;

        // now loop on other string lines...
        // number of iterations must be equal to number of edges...
        for (Object line : this.lines) {
            // get hyperedge's nodes in single line of input logFile...
            ArrayList<String> hyperedgeNodes = this.parseLine((String) line);

            // nodes per hyperedge foreach loop...
            // check for new nodes and add them to the hypergraph...
            for (String nodeIdentifier : hyperedgeNodes) {
                // create new node with random color and add it to the hypergraph...
                Node node = new Node(nodeIdentifier, this.randomInteger(Main.NUMBER_OF_COLORS));
                hypergraph.addNode(node);
            }

            // create an hyperedge instance and add nodes to the hyperedge...
            Hyperedge hyperedge = new Hyperedge(edgeId, hyperedgeNodes);

            // add the hyperedge to the hypergraph...
            hypergraph.addEdge(hyperedge);

            // make neighbourhood relationship for all nodes in the hyperedge...
            // hypergraph.makeNeighbourhood(hyperedgeNodes);

            // increment the node id...
            edgeId++;
        }

        return hypergraph;
    }

    @Override
    public ArrayList<String> parseLine(String line) {
        // split the line...
        String[] parts = Helpers.explode(line, " ");

        // make list of node ids...
        ArrayList<String> hyperedgeNodes = new ArrayList<>();

        // extract the neighbours...
        for (String part : parts) {
            if (part.equals("")) {
                continue;
            }

            hyperedgeNodes.add(part);
        }

        return hyperedgeNodes;
    }
}
