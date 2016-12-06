package partitioner;

import structure.Hypergraph;
import structure.Structure;

public class HyperSwap implements Partitioner {

    public void partition(Structure hypergraph) {
        if (! (hypergraph instanceof Hypergraph)) {
            return;
        }
    }
}
