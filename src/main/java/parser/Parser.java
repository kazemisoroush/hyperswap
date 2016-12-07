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
     * Other parameters extracted from first line of structure logFile.
     */
    // public ArrayList<String> otherParameters = new ArrayList<>();

    /**
     * Initialize the structure.
     *
     * @param path to structure logFile.
     */
    public Parser(String path) throws IOException {
        // get array of logFile string lines...
        this.lines = Helpers.fileToArray(path);

        // first line is the graph vertices and edges number...
        // String firstLine = this.lines.get(0);

        // remove analyzed line...
        // this.lines.remove(0);

        // split the first line and extract vertices and edges number...
        // List<String> parts = new LinkedList<>(Helpers.explode(firstLine, " "));

        // extract global information about the structure...
        // this.numberOfNodes = Integer.parseInt(parts.get(0));
        // this.numberOfEdges = Integer.parseInt(parts.get(1));

        // remove extracted indexes...
        // parts.remove(0);
        // parts.remove(0);

        // extract other parameters from first line...
        // this.otherParameters.addAll(parts);
    }

    /**
     * Read the data structure from logFile and put it inside it's object.
     *
     * @return the graph object.
     */
    public abstract Structure read();

    /**
     * Parse a string line in data structure logFile.
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
