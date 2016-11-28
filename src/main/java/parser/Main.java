package parser;

import java.io.IOException;

public class Main {

    /**
     * Path to graph file.
     */
    public static String GRAPH_PATH = "/graphs/test.graph";

    /**
     * Path to hypergraph file.
     */
    public static String HYPERGRAPH_PATH = "/hypergraphs/test.graph";

    /**
     * Total number of colors or partitions in the algorithm.
     */
    public static int NUMBER_OF_COLORS = 4;

    /**
     * Each line starting with these strings assumed as comment line.
     */
    public static String[] COMMENTS = {"%", "//", "#"};

    /**
     * Seed value for random number generator function.
     */
    public static int SEED = 654;

    // public static int ROUNDS = 1000;
    // public static int RND_LIST_SIZE = 5;
    // public static float NOISE = 2;
    // public static float NOISE_DELTA = (float) 0.003;
    // public static int CLOSE_BY_NEIGHBOURS = 3;

    /**
     * parser.Main method that runs the algorithm.
     *
     * @param arguments for main method.
     */
    public static void main(String[] arguments) {
        // try to read the graph...
        try {
            // make instance of main class...
            // parser.GraphParser parser = new parser.GraphParser(GRAPH_PATH);
            HypergraphParser parser = new HypergraphParser(HYPERGRAPH_PATH);

            // make an instance of graph...
            // parser.Graph graph = parser.read();
            Hypergraph hypergraph = parser.read();

            System.out.println(hypergraph);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
