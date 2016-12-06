package transactions;

public class PaymentTransaction extends Transaction {

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
