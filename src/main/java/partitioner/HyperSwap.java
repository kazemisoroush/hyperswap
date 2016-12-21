package partitioner;

import logger.Logger;
import main.Main;
import sampling.HypergraphSampler;
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

        this.sampler = new HypergraphSampler(hypergraph);
    }

    @Override
    public void partition() {
        // make an active list of nodes which allowed to swap colors...
        ArrayList<Node> active = new ArrayList<>();
        active.addAll(this.structure.getNodes());

        // finish the algorithm in two conditions...
        // 1. limited algorithm rounds...
        // 2. temperature cool enough...
        for (int iteration = 1; iteration <= Main.ROUNDS; iteration++) {
            // Logger.log("Start Round #%s, Temp = %.2f", iteration, this.temperature);

            // list of next active node list...
            ArrayList<Node> next = new ArrayList<>();

            for (Node node : active) {
                // execute the sample and swap...
                Node partner = node.sampleAndSwap(this.temperature, (HypergraphSampler) this.sampler);

                // no swapping done...
                if (partner == null) {
                    // stable node must be removed from active list...
                    // continue the loop...
                    continue;
                }

                // increment the swaps counter...
                this.swaps++;

                // add the node to the list if it's not inside it...
                if (! next.contains(node)) {
                    next.add(node);
                }

                // add the partner to the list if it's not inside it...
                if (! next.contains(partner)) {
                    next.add(partner);
                }
            }

            // build the active list for next iteration...
            active.clear();
            active.addAll(next);

            // update the temperature...
            this.temperature -= Main.DELTA;

            // limit the temperature...
            // hypergraph is cool enough...
            if (this.temperature <= 1) {
                break;
            }
        }

        Logger.err("Number of Swaps: %s", this.swaps);
    }

}
