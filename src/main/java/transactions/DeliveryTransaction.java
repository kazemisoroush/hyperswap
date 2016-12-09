package transactions;

/**
 *
 */
public class DeliveryTransaction extends Transaction {

    /**
     * Actions needed to emulate the successful transaction.
     */
    protected void succeedTransaction() {
        // ...
    }

    /**
     * Actions needed to emulate the failed transaction.
     */
    protected void failTransaction() {
        // ...
    }

    /**
     * Method which determines if the running transaction is failing.
     *
     * @return boolean value of failed condition.
     */
    protected boolean isTransactionFailed() {
        return false;
    }
}
