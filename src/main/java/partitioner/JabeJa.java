package partitioner;

import structure.Graph;
import structure.Structure;

public class JabeJa implements Partitioner {

    public void partition(Structure graph) {
        if (! (graph instanceof Graph)) {
            return;
        }
    }
}
