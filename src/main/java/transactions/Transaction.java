package transactions;

import database.Model;
import database.TransactionLogger;

public abstract class Transaction {

    /**
     * Start time of transaction.
     */
    protected long start;

    /**
     * End time of transaction.
     */
    protected long end;

    /**
     * Transaction model here it is.
     */
    protected Model model = new Model();

    /**
     * Some logger to log the transaction as we need.
     */
    protected TransactionLogger logger;

    /**
     * Status of this transaction.
     */
    protected Status status = Status.QUEUED;

    /**
     * Instantiate new transaction instance.
     */
    public Transaction() {
        // make new instance of logger...
        this.logger = new TransactionLogger();
    }

    /**
     * Start transaction process.
     */
    public void process() {
        this.start();

        if (this.isTransactionFailed()) {
            // change the transaction status to proper state...
            this.status = Status.FAILED;

            // do failed jobs...
            this.failTransaction();
        } else {
            // change the transaction status to proper state...
            this.status = Status.FAILED;

            // do success jobs...
            this.succeedTransaction();
        }

        this.end();
    }

    /**
     * Fail the transaction.
     */
    protected abstract void failTransaction();

    /**
     * Succeed the transaction.
     */
    protected abstract void succeedTransaction();

    /**
     * Check if the transaction is failed.
     *
     * @return boolean condition of failure.
     */
    protected abstract boolean isTransactionFailed();

    /**
     * Actions required to start transaction.
     */
    protected void start() {
        this.start = System.nanoTime();

        this.status = Status.PROCESSING;

        this.logger.beginTransactionLogging();
    }

    /**
     * Actions required to finish transaction.
     */
    protected void end() {
        this.end = System.nanoTime();

        this.logger.finishTransactionLogging(this.model.modifiedRows);
    }

    /**
     * Get time elapsed for this transaction.
     *
     * @return time elapsed.
     */
    public long elapsed() {
        return this.end - this.start;
    }
}
