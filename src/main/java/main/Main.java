package main;

import exceptions.SchemaDoesNotExistsException;
import seeder.DatabaseSeeder;

public class Main {

    /**
     * Run the project.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        // seed the database with benchmark's seeder...
        DatabaseSeeder seeder = new DatabaseSeeder();
        try {
            seeder.seed();
        } catch (SchemaDoesNotExistsException e) {
            e.printStackTrace();
        }

        // run the benchmark on the database...

        // retrieve the benchmark log files...

        // extract the transactions and tuple inside them...

        // make hypergraph with transactions...

        // initialSeed and partition the built hypergraph using HyperSwap algorithm...

        // run the test benchmark on partitioned data...
    }
}
