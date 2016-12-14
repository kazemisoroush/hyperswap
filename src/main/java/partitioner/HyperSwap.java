package partitioner;

import main.Helpers;
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
    }

    @Override
    public void partition() {
        // finish the algorithm in two conditions...
        // 1. limited algorithm rounds...
        // 2. temperature cool enough...
        for (int iteration = 1; iteration <= this.rounds || this.temperature == 1; iteration++) {
            for (Node node : this.structure.getNodes()) {
                this.sampleAndSwap(node);
            }
        }
    }

    @Override
    protected int energy() {
        // TODO...
        return 0;
    }

    @Override
    protected ArrayList<Node> getNeighbors(Node node) {
        // we have the hypergraph...
        Hypergraph hypergraph = this.structure;

        return hypergraph.getNeighbors(node);
    }

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
