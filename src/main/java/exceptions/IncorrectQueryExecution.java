package exceptions;

public class IncorrectQueryExecution extends Exception {

    public IncorrectQueryExecution() {
        super();
    }

    public IncorrectQueryExecution(String message) {
        super(message);
    }
}

