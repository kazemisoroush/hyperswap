package transactions;

public class DeliveryTransaction extends Transaction {

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
