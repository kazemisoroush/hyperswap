package transactions;

public class StockLevelTransaction extends Transaction {

    protected void failTransaction() {
        // ...
    }

    protected void succeedTransaction() {
        // ...
    }

    protected boolean isTransactionFailed() {
        return false;
    }
}
