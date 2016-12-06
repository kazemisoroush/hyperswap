package main;

import benchmark.TrainBenchmark;
import exceptions.SchemaDoesNotExistsException;
import parser.GraphParser;
import parser.Hypergraph;
import parser.HypergraphParser;
import partitioner.HyperSwap;
import partitioner.JabeJa;
import seeder.DatabaseSeeder;

import java.io.IOException;

public class Main {

    /**
     * Run the project.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        try {
            // seed the database with benchmark's seeder...
            DatabaseSeeder seeder = new DatabaseSeeder();
            seeder.seed();

            // run the train benchmark and generate the transaction logs...
            TrainBenchmark train = new TrainBenchmark();
            train.run();

            // make the graph with the transaction logs...
            GraphParser graphParser = new GraphParser("");
            parser.Graph graph = graphParser.read();

            // make the hypergraph with the transaction logs...
            HypergraphParser hypergraphParser = new HypergraphParser("");
            Hypergraph hypergraph = hypergraphParser.read();

            // partition the graph with ja-be-ja algorithm...
            JabeJa jabeJa = new JabeJa();
            jabeJa.partition(graph);

            // partition the hypergraph with hyper-swap algorithm...
            HyperSwap hyperGraphPartitioner = new HyperSwap();
            hyperGraphPartitioner.partition(hypergraph);
        } catch (SchemaDoesNotExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
