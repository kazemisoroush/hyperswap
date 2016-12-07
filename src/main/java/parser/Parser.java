package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public abstract class Parser<Structure> {

    /**
     * Number of nodes from parsed files.
     */
    public int numberOfNodes;

    /**
     * Number of edges from parsed files.
     */
    public int numberOfEdges;

    /**
     * Random integer generator.
     */
    protected Random random = new Random(Main.SEED);

    /**
     * Lines of structure file.
     */
    protected ArrayList<String> lines = new ArrayList<String>();

    /**
     * Other parameters extracted from first line of structure file.
     */
    public ArrayList<String> otherParameters = new ArrayList<String>();

    /**
     * Initialize the structure.
     *
     * @param path to structure file.
     */
    public Parser(String path) throws IOException {
        // get array of file string lines...
        this.lines = this.fileToArray(path);

        // first line is the graph vertices and edges number...
        String firstLine = this.lines.get(0);

        // remove analyzed line...
        this.lines.remove(0);

        // split the first line and extract vertices and edges number...
        List<String> parts = new LinkedList<String>(Arrays.asList(firstLine.split(" ")));

        // extract global information about the structure...
        this.numberOfNodes = Integer.parseInt(parts.get(0));
        this.numberOfEdges = Integer.parseInt(parts.get(1));

        // remove extracted indexes...
        parts.remove(0);
        parts.remove(0);

        // extract other parameters from first line...
        this.otherParameters.addAll(parts);
    }

    /**
     * Read the data structure from file and put it inside it's object.
     *
     * @return the graph object.
     */
    public abstract Structure read();

    /**
     * Parse a string line in data structure file.
     *
     * @param line to be parsed.
     *
     * @return array of neighbour ids.
     */
    public abstract ArrayList<Integer> parseLine(String line);

    /**
     * Fill an array of string lines from input file path.
     *
     * @param path to the file.
     *
     * @return array of lines.
     * @throws IOException of file not found.
     */
    protected ArrayList<String> fileToArray(String path) throws IOException {
        // make a variable for lines...
        String line;

        // initialize array of lines...
        ArrayList<String> arrayOfLines = new ArrayList<String>();

        // make a buffered reader to read the stream line by line...
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));

        // the first non-comment line of file is number of nodes and number of edges of the graph...
        // so we need to extract the first non-comment line...
        while ((line = reader.readLine()) != null) {
            // define comment for first of lines...
            if (this.isComment(line))
                continue;

            // remove comment part of the line...
            line = this.cutCommentPart(line);

            // add the line string to the array of lines...
            arrayOfLines.add(line);
        }

        return arrayOfLines;
    }

    /**
     * Check if the line is comment.
     *
     * @param line to check.
     *
     * @return boolean which specifies the line is comment or not.
     */
    private boolean isComment(String line) {
        // check each comment starting...
        for (String comment : Main.COMMENTS) {
            if (line.startsWith(comment))
                return true;
        }

        // otherwise...
        return false;
    }

    /**
     * Remove the comment part from string line.
     *
     * @param line which we need to be comment free.
     *
     * @return comment free line.
     */
    private String cutCommentPart(String line) {
        for (String comment : Main.COMMENTS) {
            // if line does not have this comment then just skip it...
            if (! line.contains(comment))
                continue;

            // cut the line from beginning to the comment character...
            line = line.substring(0, line.lastIndexOf(comment) - 1);
        }

        return line;
    }

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
