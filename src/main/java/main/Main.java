package main;

import analyzer.PartitioningAnalyzer;
import benchmark.TrainBenchmark;
import exceptions.SchemaDoesNotExistsException;
import parser.GraphParser;
import parser.HypergraphParser;
import partitioner.HyperSwap;
import partitioner.JabeJa;
import seeder.DatabaseSeeder;
import structure.Graph;
import structure.Hypergraph;

import java.io.IOException;

public class Main {

    /**
     * Run the project.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        try {
            // occupy the database with benchmark's seeder...
            // if the database has already been seeded then no action will be taken...
            DatabaseSeeder seeder = new DatabaseSeeder();
            seeder.occupy();

            // run the train benchmark and generate the transaction logs...
            TrainBenchmark train = new TrainBenchmark();
            String logPath = train.run();

            // make the modeled graph with the transaction logs...
            GraphParser graphParser = new GraphParser(logPath);
            Graph graph = graphParser.read();

            // make the modeled hypergraph with the transaction logs...
            HypergraphParser hypergraphParser = new HypergraphParser(logPath);
            Hypergraph hypergraph = hypergraphParser.read();

            // partition the graph with ja-be-ja algorithm...
            JabeJa jabeJa = new JabeJa();
            // partitionedGraph = jabeJa.partition(graph);

            // partition the hypergraph with hyper-swap algorithm...
            HyperSwap hyperSwap = new HyperSwap();
            // partitionedHyperGraph = hyperSwap.partition(hypergraph);

            // now run the test benchmark and analyze the results...
            PartitioningAnalyzer analyzer = new PartitioningAnalyzer("performance");
            // analyzer.analyze(partitionedGraph, partitionedHyperGraph);
        } catch (SchemaDoesNotExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
