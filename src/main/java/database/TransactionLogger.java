package database;

import main.Helpers;

import java.io.File;
import java.util.ArrayList;

/**
 * Make the log for the transactions. By log I mean put the transaction name and it's modified rows afterwards.
 */
public class TransactionLogger {

    /**
     * Path to the log logFile.
     */
    protected String pathToLogFile = "logs/transaction.log";

    /**
     * Instance of log file.
     */
    protected File logFile;

    /**
     * Make new instance of transaction logger.
     */
    public TransactionLogger() {
        // check if the logFile exists...
        try {
            this.logFile = new File(getClass().getClassLoader().getResource(this.pathToLogFile).getFile());
        } catch (NullPointerException e) {
            // logFile does not exists...
        }
    }

    /**
     * Begin transaction logging.
     */
    public void beginTransactionLogging() {
        // no need to do something...
    }

    /**
     * Finish transaction logging.
     *
     * @param modifiedRows from transaction.
     */
    public void finishTransactionLogging(ArrayList<String> modifiedRows) {
        // append the transaction identifier to the logFile in one line...
        String appendThis = Helpers.implode(modifiedRows, " ");

        // append all modified rows after the transaction identifier...
        // just add modified rows for each transaction in a new line of the log logFile...
        Helpers.appendStringToFile(this.logFile, appendThis, true);
    }

    /**
     * Truncate the log logFile.
     */
    public void truncateLogFile() {
        // make the log logFile empty...
    }

    public static void main(String[] arguments) {
        TransactionLogger logger = new TransactionLogger();

        logger.truncateLogFile();
    }

}
