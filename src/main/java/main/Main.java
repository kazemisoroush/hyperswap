package main;

import analyzer.PartitioningAnalyzer;
import parser.HypergraphParser;
import partitioner.HyperSwap;
import partitioner.JabeJa;
import structure.Hypergraph;

import java.io.IOException;

public class Main {

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
    public static int NUMBER_OF_COLORS = 4;

    /**
     * Seed value for random number generator function.
     */
    public static int SEED = 654;

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
            // Benchmark train = new Benchmark(10);
            // train.run();

            // TODO: make the modeled graph with the transaction logs...
            // GraphParser graphParser = new GraphParser(Main.GRAPH_PATH);
            // Graph graph = graphParser.read();

            // make the modeled hypergraph with the transaction logs...
            HypergraphParser hypergraphParser = new HypergraphParser(Main.HYPERGRAPH_PATH);
            Hypergraph hypergraph = hypergraphParser.read();

            // System.out.println("...PARSED GRAPH...\n");
            // System.out.println(graph);

            System.out.println("...PARSED HYPERGRAPH...\n");
            System.out.println(hypergraph);

            // TODO: partition the graph with ja-be-ja algorithm...
            JabeJa jabeJa = new JabeJa();
            // partitionedGraph = jabeJa.partition(graph);

            // TODO: partition the hypergraph with hyper-swap algorithm...
            HyperSwap hyperSwap = new HyperSwap();
            // partitionedHyperGraph = hyperSwap.partition(hypergraph);

            // TODO: now run the test benchmark and analyze the results...
            PartitioningAnalyzer analyzer = new PartitioningAnalyzer("performance");
            // analyzer.analyze(partitionedGraph, partitionedHyperGraph);
        } catch (IOException e) {
            e.printStackTrace();
            // } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
