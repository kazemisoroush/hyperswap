package transactions;

/**
 * The Stock-Level business transaction determines the number of recently sold items that have a stock level below a
 * specified threshold. It represents a heavy read-only database transaction with a low frequency of execution, a
 * relaxed response time requirement, and relaxed consistency requirements.
 */
public class StockLevelTransaction extends Transaction {

    /**
     * Warehouse number for this order. Equal to 1.
     */
    protected int w_id = 1;

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
        // ...THIS TYPE OF TRANSACTION NEVER FAILS...
    }

    /**
     * Method which determines if the running transaction is failing.
     *
     * @return boolean value of failed condition.
     */
    protected boolean isTransactionFailed() {
        return false;
    }

    /**
     * Execute this transaction for testing purposes.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        DeliveryTransaction transaction = new DeliveryTransaction();

        transaction.process();
    }
}
