package partitioner;

import parser.Hypergraph;
import parser.Structure;

public class HyperSwap implements Partitioner {

    public void partition(Structure hypergraph) {
        if (! (hypergraph instanceof Hypergraph)) {
            return;
        }
    }
}
