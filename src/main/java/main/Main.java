package main;

import analyzer.PartitioningAnalyzer;
import benchmark.Benchmark;
import exceptions.SchemaDoesNotExistsException;
import partitioner.HyperSwap;
import partitioner.JabeJa;
import seeder.DatabaseSeeder;

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
            Benchmark train = new Benchmark(11);
            train.run();

            // TODO: make the modeled graph with the transaction logs...
            // GraphParser graphParser = new GraphParser("logs/transaction.log");
            // Graph graph = graphParser.read();

            // TODO: make the modeled hypergraph with the transaction logs...
            // HypergraphParser hypergraphParser = new HypergraphParser("logs/transaction.log");
            // Hypergraph hypergraph = hypergraphParser.read();

            // TODO: partition the graph with ja-be-ja algorithm...
            JabeJa jabeJa = new JabeJa();
            // partitionedGraph = jabeJa.partition(graph);

            // TODO: partition the hypergraph with hyper-swap algorithm...
            HyperSwap hyperSwap = new HyperSwap();
            // partitionedHyperGraph = hyperSwap.partition(hypergraph);

            // TODO: now run the test benchmark and analyze the results...
            PartitioningAnalyzer analyzer = new PartitioningAnalyzer("performance");
            // analyzer.analyze(partitionedGraph, partitionedHyperGraph);
        } catch (SchemaDoesNotExistsException e) {
            e.printStackTrace();
            // } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
