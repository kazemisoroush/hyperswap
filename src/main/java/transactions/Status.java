package transactions;

/**
 * Different statuses for transactions.
 */
public enum Status {
    QUEUED(0), PROCESSING(1), FAILED(2), SUCCESSFUL(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
