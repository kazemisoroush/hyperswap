package partitioner;

import parser.Graph;
import parser.Structure;

public class JabeJa implements Partitioner {

    public void partition(Structure graph) {
        if (! (graph instanceof Graph)) {
            return;
        }
    }
}
