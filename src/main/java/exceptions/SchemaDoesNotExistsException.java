package exceptions;

public class SchemaDoesNotExistsException extends Exception {

    public SchemaDoesNotExistsException() {
        super();
    }

    public SchemaDoesNotExistsException(String message) {
        super(message);
    }
}
