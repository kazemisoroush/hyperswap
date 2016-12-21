package main;

import logger.Logger;
import parser.HypergraphParser;
import partitioner.HyperSwap;
import structure.Hypergraph;

public class Main {

    /**
     * Size of uniform sample nodes gathered from the structure.
     */
    public static final int SAMPLE_SIZE = 5;

    /**
     * Each line starting with these strings assumed as comment line.
     */
    public static String[] COMMENTS = {"%", "//", "#"};

    /**
     * Path to graph logFile.
     */
    public static String GRAPH_PATH = "/logs/transaction.log";

    /**
     * Path to hypergraph logFile.
     */
    public static String HYPERGRAPH_PATH = "/hypergraphs/test.graph"; // "/logs/transaction.log";

    /**
     * Total number of colors or partitions in the algorithm.
     */
    public static int NUMBER_OF_COLORS = 2;

    /**
     * Seed value for random number generator function.
     */
    public static int SEED = 654;

    /**
     * Maximum number of rounds in which iterative partitioning algorithms will run.
     */
    public static int ROUNDS = 10000;

    /**
     * Variable which determines speed of cooling process.
     */
    public static double DELTA = 0.003;

    /**
     * Parameter of energy in ja-be-ja algorithm.
     */
    public static double ALPHA = 1;

    /**
     * Run the project.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        try {
            // occupy the database with benchmark's seeder...
            // if the database has already been seeded then no action will be taken...
            // DatabaseSeeder seeder = new DatabaseSeeder();
            // seeder.occupy();

            // run the train benchmark and generate the transaction logs...
            // Benchmark train = new Benchmark(11);
            // train.run();

            // make the modeled graph with the transaction logs...
            // GraphParser graphParser = new GraphParser(Main.GRAPH_PATH);
            // Graph graph = graphParser.read();

            // make the modeled hypergraph with the transaction logs...
            HypergraphParser hypergraphParser = new HypergraphParser(Main.HYPERGRAPH_PATH);
            Hypergraph hypergraph = hypergraphParser.read();

            // System.out.println("...PARSED GRAPH...\n");
            // System.out.println(graph);

            Logger.log("...PARSED HYPERGRAPH...\n");
            Logger.log(hypergraph);

            // partition the graph with ja-be-ja algorithm...
            // JabeJa jabeJa = new JabeJa(graph);
            // jabeJa.partition();

            // partition the hypergraph with hyper-swap algorithm...
            HyperSwap hyperSwap = new HyperSwap(hypergraph);
            hyperSwap.partition();

            Logger.log("...PARTITIONED HYPERGRAPH...\n");
            Logger.log(hypergraph);

            // TODO: now run the test benchmark and analyze the results...
            // PartitioningAnalyzer analyzer = new PartitioningAnalyzer("performance");
            // analyzer.analyze(partitionedGraph, partitionedHyperGraph);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
