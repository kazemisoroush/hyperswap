package partitioner;

import logger.Logger;
import main.Main;
import sampling.HypergraphSampler;
import structure.Hypergraph;
import structure.Node;

public class HyperSwap extends Partitioner<Hypergraph> {

    /**
     * Create new instance of partitioner.
     *
     * @param hypergraph to be partitioned.
     */
    public HyperSwap(Hypergraph hypergraph) {
        super(hypergraph);

        this.sampler = new HypergraphSampler(hypergraph);
    }

    @Override
    public void partition() {
        // finish the algorithm in two conditions...
        // 1. limited algorithm rounds...
        // 2. temperature cool enough...
        for (int iteration = 1; iteration <= Main.ROUNDS; iteration++) {
            Logger.log("Start Round #%s, Temp = %s", iteration, this.temperature);

            for (Node node : this.structure.getNodes()) {
                if (node.sampleAndSwap(this.temperature, (HypergraphSampler) this.sampler)) {
                    this.swaps++;
                }
            }

            // update the temperature...
            temperature -= Main.DELTA;

            // limit the temperature...
            if (temperature < 1) {
                temperature = 1.0;
            }

            // hypergraph is cool enough...
            if (this.temperature == 1) {
                break;
            }
        }

        Logger.log("Number of Swaps: %s", this.swaps);
    }

}
