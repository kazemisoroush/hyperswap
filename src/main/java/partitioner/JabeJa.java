package partitioner;

import main.Main;
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
        for (int iteration = 1; iteration <= Main.ROUNDS || this.temperature == 1; iteration++) {
            for (Node node : this.structure.getNodes()) {
                // this.sampleAndSwap(node);
            }
        }
    }

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

    protected ArrayList<Node> getNeighbors(Node node) {
        // we have the graph...
        Graph graph = this.structure;

        return graph.getNeighbors(node);
    }

}
