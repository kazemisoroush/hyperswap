package database;

import main.Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Make the log for the transactions. By log I mean put the transaction name and it's modified rows afterwards.
 */
public class TransactionLogger {

    /**
     * Path to the log logFile.
     */
    protected String pathToLogFile = "src/main/resources/logs/transaction.log";

    /**
     * Make new instance of transaction logger.
     */
    public TransactionLogger() {
    }

    /**
     * Finish transaction logging.
     *
     * @param modifiedRows from transaction.
     */
    public void logTransaction(ArrayList<String> modifiedRows) {
        // append the transaction identifier to the logFile in one line...
        String appendThis = Helpers.implode(modifiedRows, " ");

        // check if any row is modified...
        if (modifiedRows.isEmpty()) {
            return;
        }

        // append all modified rows after the transaction identifier...
        // just add modified rows for each transaction in a new line of the log logFile...
        Helpers.appendStringToFile(this.pathToLogFile, appendThis);
    }

    /**
     * Truncate the log logFile.
     */
    public void truncateLogFile() {
        // make the log logFile empty...
        FileWriter writer = null;

        try {
            File file = new File(this.pathToLogFile);
            writer = new FileWriter(file);
            writer.write("");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * For testing purposes.
     *
     * @param arguments passed by command line interface.
     */
    public static void main(String[] arguments) {
        TransactionLogger logger = new TransactionLogger();

        // System.out.println(logger.absolutePathToLogFile);
        ArrayList<String> list = new ArrayList<String>();

        list.add("1. Soroush");
        list.add("2. Mohammad");
        list.add("3. Pouyan");
        list.add("4. Sara");

        logger.truncateLogFile();
        System.out.println("Truncate please...");
        logger.logTransaction(list);
    }

}
