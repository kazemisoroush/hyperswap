package partitioner;

import structure.Hypergraph;
import structure.Node;
import structure.Structure;

public class HyperSwap extends Partitioner {

    @Override
    public void partition(Structure hypergraph) {
        if (! (hypergraph instanceof Hypergraph)) {
            return;
        }
    }

    @Override
    public void swap(Node firstNode, Node secondNode) {
        // ...
    }

    @Override
    public int energy() {
        // ...
        return 0;
    }
}
