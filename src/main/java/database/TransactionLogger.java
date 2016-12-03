package database;

import java.util.ArrayList;

/**
 * Make the log for the transactions. By log I mean put the transaction name and it's modified rows afterwards.
 */
public class TransactionLogger {

    /**
     * Make new instance of transaction logger.
     */
    public TransactionLogger() {
        // check if the file exists...
        // make it if it does not exists...
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
        // ...MAYBE YOU DON'T NEED ANY IDENTIFIERS FOR YOUR TRANSACTIONS...

        // just add modified rows for each transaction in a new line of the log file...
        // don't forget to put a line-break at the end of the file...

        // append all modified rows after the transaction identifier...
    }

    /**
     * Truncate the log file.
     */
    public void truncateLogFile() {
        // make the log file empty...
    }
}
