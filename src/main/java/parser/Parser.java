package parser;

import main.Helpers;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Parser<Structure> {

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

}
