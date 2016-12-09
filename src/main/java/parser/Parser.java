package parser;

import main.Helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class Parser<Structure> {

    /**
     * Number of nodes from parsed files.
     */
    public int numberOfNodes;

    /**
     * Number of edges from parsed files.
     */
    // public int numberOfEdges;

    /**
     * Random integer generator.
     */
    protected Random random = new Random(Main.SEED);

    /**
     * Lines of structure logFile.
     */
    protected ArrayList<String> lines = new ArrayList<>();

    /**
     * Initialize the structure.
     *
     * @param path to structure logFile.
     */
    public Parser(String path) throws IOException {
        // get array of logFile string lines...
        this.lines = Helpers.fileToArray(path);
    }

    /**
     * Read the data structure from logFile and put it inside it's object.
     *
     * @return the graph object.
     */
    public abstract Structure read();

    /**
     * Parse a string line in data structure logFile into list of node identifiers.
     *
     * @param line to be parsed.
     *
     * @return array of neighbour ids.
     */
    public abstract ArrayList<Integer> parseLine(String line);

    /**
     * Generate random integer with input size.
     *
     * @param size of random integer.
     *
     * @return random integer.
     */
    protected int randomInteger(int size) {
        return this.random.nextInt(size);
    }

}
