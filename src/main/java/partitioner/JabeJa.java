package partitioner;

import main.Helpers;
import structure.Graph;
import structure.Node;

import java.util.ArrayList;

public class JabeJa extends Partitioner<Graph> {

    /**
     * Create new instance of partitioner.
     *
     * @param graph to be partitioned.
     */
    public JabeJa(Graph graph) {
        super(graph);
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
        // we have the graph...
        Graph graph = this.structure;
        int energy = 0;

        for (Node node : graph.getNodes()) {
            ArrayList<Node> neighbors = this.getNeighbors(node);

            for (Node neighbor : neighbors) {
                if (node.getColor() != neighbor.getColor()) {
                    energy++;
                }
            }
        }

        // return the energy value divided by two...
        // because we calculated each twice...
        return energy / 2;
    }

    @Override
    protected ArrayList<Node> getNeighbors(Node node) {
        // we have the graph...
        Graph graph = this.structure;

        return graph.getNeighbors(node);
    }

    @Override
    protected ArrayList<Node> getSample(Node node) {
        // we have the graph...
        Graph graph = this.structure;
        ArrayList<Node> samples = new ArrayList<>();

        // size of random sample is always 10...
        ArrayList<Integer> indexes = Helpers.getRandomIntegers(0, graph.getNodes().size() - 1, 10);

        // add the samples...
        for (int index : indexes) {
            samples.add(graph.getNodes().get(index));
        }

        // return the sample nodes...
        return samples;
    }

}
