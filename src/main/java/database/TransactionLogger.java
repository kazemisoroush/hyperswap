package database;

import main.Helpers;

import java.io.File;
import java.util.ArrayList;

/**
 * Make the log for the transactions. By log I mean put the transaction name and it's modified rows afterwards.
 */
public class TransactionLogger {

    /**
     * Path to the log file.
     */
    protected String pathToLogFile = "logs/transaction.log";

    protected File file;

    /**
     * Make new instance of transaction logger.
     */
    public TransactionLogger() {
        // check if the file exists...
        try {
            this.file = new File(getClass().getClassLoader().getResource(this.pathToLogFile).getFile());
        } catch (NullPointerException e) {
            // file does not exists...
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
        // append the transaction identifier to the file in one line...
        String appendThis = Helpers.implode(modifiedRows, " ");

        // append all modified rows after the transaction identifier...
        // just add modified rows for each transaction in a new line of the log file...
        Helpers.appendStringToFile(this.file, appendThis, true);
    }

    /**
     * Truncate the log file.
     */
    public void truncateLogFile() {
        // make the log file empty...
    }

    public static void main(String[] arguments) {
        TransactionLogger logger = new TransactionLogger();

        logger.truncateLogFile();
    }

}
