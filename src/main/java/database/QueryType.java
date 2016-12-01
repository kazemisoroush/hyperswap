package database;

public enum QueryType {

    SELECT("select"), INSERT("insert"), UPDATE("update"), DELETE("delete");

    private final String value;

    QueryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
