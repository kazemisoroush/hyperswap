package sampling;

import main.Helpers;
import main.Main;
import structure.Hypergraph;
import structure.Node;

import java.util.ArrayList;

public class HypergraphSampler extends Sampler {

    /**
     * Instantiate a hypergraph sampler object.
     *
     * @param hypergraph to sample on.
     */
    public HypergraphSampler(Hypergraph hypergraph) {
        this.structure = hypergraph;
    }

    @Override
    public ArrayList<Node> getSample(Node node) {
        // we have the graph...
        Hypergraph hypergraph = (Hypergraph) this.structure;
        ArrayList<Node> samples = new ArrayList<>();

        // size of random sample is set inside main class...
        ArrayList<Integer> indexes = Helpers.getRandomIntegers(0, hypergraph.getNodes().size() - 1, Main.SAMPLE_SIZE);

        // add the samples...
        for (int index : indexes) {
            samples.add(hypergraph.getNodes().get(index));
        }

        // return the sample nodes...
        return samples;
    }
}
