package transactions;

public abstract class Transaction extends TransactionalFunctions {

    /**
     * One percent of these transactions must fail.
     */
    protected double failureProbability;

    /**
     * Status of this transaction.
     */
    protected Status status = Status.QUEUED;

    /**
     * Instantiate new transaction instance.
     */
    public Transaction() {
        // ...START THE TRANSACTION...
        this.startTransaction();

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

        return this.status;
    }

    /**
     * Start the transaction.
     */
    protected void startTransaction() {
        this.status = Status.PROCESSING;
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
