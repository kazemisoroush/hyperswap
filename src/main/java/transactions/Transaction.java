package transactions;

import database.Model;
import database.TransactionLogger;

public abstract class Transaction {

    /**
     * Transaction model here it is.
     */
    protected Model model = new Model();

    /**
     * Some logger to log the transaction as we need.
     */
    protected TransactionLogger logger = new TransactionLogger();

    /**
     * Status of this transaction.
     */
    protected Status status = Status.QUEUED;

    /**
     * Instantiate new transaction instance.
     */
    public Transaction() {
        // ...
    }

    /**
     * Start transaction process.
     */
    public void process() {
        // ...START THE TRANSACTION...
        this.status = Status.PROCESSING;

        this.logger.beginTransactionLogging();

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

        this.logger.finishTransactionLogging(this.model.modifiedRows);
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

}
