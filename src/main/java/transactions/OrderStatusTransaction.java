package transactions;

public class OrderStatusTransaction extends Transaction {

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
