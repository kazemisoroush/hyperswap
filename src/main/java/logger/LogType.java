package logger;

public enum LogType {

    CONSOLE("console"), FILE("file");

    private final String value;

    LogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
