package partitioner;

import logger.Logger;
import main.Helpers;
import main.Main;
import structure.Hypergraph;
import structure.Node;

import java.util.ArrayList;

public class HyperSwap extends Partitioner<Hypergraph> {

    /**
     * Create new instance of partitioner.
     *
     * @param hypergraph to be partitioned.
     */
    public HyperSwap(Hypergraph hypergraph) {
        super(hypergraph);

        this.temperature = structure.energy();
    }

    @Override
    public void partition() {
        // finish the algorithm in two conditions...
        // 1. limited algorithm rounds...
        // 2. temperature cool enough...
        for (int iteration = 1; iteration <= Main.ROUNDS; iteration++) {
            Logger.log("Start Round #%s", iteration);

            for (Node node : this.structure.getNodes()) {
                this.sampleAndSwap(node);
            }

            // hypergraph is cool enough...
            if (this.temperature == 1) {
                break;
            }
        }

        Logger.log("Number of Swaps: %s", this.swaps);
    }

    @Override
    protected ArrayList<Node> getSample(Node node) {
        // we have the graph...
        Hypergraph hypergraph = this.structure;
        ArrayList<Node> samples = new ArrayList<>();

        // size of random sample is always 10...
        ArrayList<Integer> indexes = Helpers.getRandomIntegers(0, hypergraph.getNodes().size() - 1, 10);

        // add the samples...
        for (int index : indexes) {
            samples.add(hypergraph.getNodes().get(index));
        }

        // return the sample nodes...
        return samples;
    }

}
