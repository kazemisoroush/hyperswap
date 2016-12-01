package database;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;

public class QueryBuilder {

    /**
     * Resulting query string.
     */
    protected String query;

    /**
     * Type of the query.
     */
    protected QueryType queryType;

    /**
     * Tables in which query is being executed.
     */
    protected ArrayList<String> tables;

    /**
     * Array of where conditions. Used in select, update and delete queries.
     */
    protected ArrayList<String> whereConditions;

    /**
     * Array of values being inserted into database.
     */
    protected ArrayList<String> values;

    /**
     * Array of attribute / value pairs in string form for update queries.
     */
    protected ArrayList<String> set;

    /**
     * Columns which are being selected.
     */
    protected ArrayList<String> columns;

    /**
     * Database connector used to execute queries.
     */
    protected DatabaseConnector connector;

    /**
     * Instantiate new instance of query builder.
     */
    public QueryBuilder() {
        // clean up the query builder...
        this.cleanUp();

        // make the database connection...
        this.connector = new DatabaseConnector();

        // connect the database connector...
        this.connector.connect();
    }

    /**
     * Clean up attributes of this query builder and make it ready for another query.
     */
    private void cleanUp() {
        this.query = "";
        this.queryType = QueryType.SELECT;
        this.tables = new ArrayList<String>();
        this.whereConditions = new ArrayList<String>();
        this.values = new ArrayList<String>();
        this.set = new ArrayList<String>();
        this.columns = new ArrayList<String>();
    }

    /**
     * Start a select query.
     *
     * @param columns which are being selected.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder select(String... columns) {
        this.queryType = QueryType.SELECT;

        for (String column : columns) {
            this.columns.add(column);
        }

        return this;
    }

    /**
     * Start an insert query.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder insert() {
        this.queryType = QueryType.INSERT;

        return this;
    }

    /**
     * Start a delete query.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder delete() {
        this.queryType = QueryType.DELETE;

        return this;
    }

    /**
     * Start an update query.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder update(String table) {
        this.tables.add(table);

        this.queryType = QueryType.UPDATE;

        return this;
    }

    /**
     * Define tables which we are using to select, update or delete.
     *
     * @param tables names which are being affected.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder from(String... tables) {
        for (String table : tables) {
            this.tables.add(table);
        }

        return this;
    }

    /**
     * Define table which is used to execute insert.
     *
     * @param table name which is being affected.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder into(String table) {
        this.tables.add(table);

        return this;
    }

    /**
     * Set values for update queries.
     *
     * @param column name to change value.
     * @param value  to be changed.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder set(String column, String value) {
        // make the set statement and add it to set array...
        this.set.add(column + " = " + value);

        return this;
    }

    /**
     * Set values to be inserted inside the database.
     *
     * @param columns values to be inserted.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder values(String... columns) {
        // add the value to the list of values...
        for (String column : columns) {
            this.values.add(column);
        }

        return this;
    }

    /**
     * Add where condition to the query.
     *
     * @param column   of condition.
     * @param equality term of condition.
     * @param value    to be inserted.
     *
     * @return this to continue chaining.
     */
    public QueryBuilder where(String column, String equality, String value) {
        // add the where clause...
        this.whereConditions.add(column + " " + equality + " " + value);

        return this;
    }

    /**
     * Execute select queries.
     *
     * @return two dimensional array of result.
     * @throws IncorrectQueryExecution for incorrect uses.
     */
    public ArrayList<ArrayList<String>> get() throws IncorrectQueryExecution {
        // check the query type...
        if (this.queryType != QueryType.SELECT) {
            throw new IncorrectQueryExecution();
        }

        // make the query string...
        this.query = this.queryType + " " + Helpers.implode(this.columns, ", ") +
                " from " + Helpers.implode(this.tables, ", ") +
                " where " + Helpers.implode(this.whereConditions, " and ");

        // execute the query and return the result...
        ArrayList<ArrayList<String>> result = this.connector.select(this.query, this.columns.size());

        System.out.println(this.query);

        // clean up the query builder...
        this.cleanUp();

        return result;
    }

    /**
     * Execute and commit the insert, update and delete queries.
     *
     * @return result of execution in integer format.
     * @throws IncorrectQueryExecution for incorrect uses.
     */
    public int save() throws IncorrectQueryExecution {
        // check the query type...
        if (this.queryType != QueryType.INSERT && this.queryType != QueryType.UPDATE && this.queryType != QueryType.DELETE) {
            throw new IncorrectQueryExecution();
        }

        int result;

        if (this.queryType == QueryType.INSERT) {
            // make the query string for insert queries...
            this.query = this.queryType + " into " + Helpers.implode(this.tables, ", ") +
                    " values (" + Helpers.implode(this.values, ", ") + ")";

            System.out.println(this.query);

            result = this.connector.insert(this.query);
        } else if (this.queryType == QueryType.UPDATE) {
            // make the query string for update queries...
            this.query = this.queryType + " " + Helpers.implode(this.tables, ", ") +
                    " set " + Helpers.implode(this.set, ", ") +
                    " where " + Helpers.implode(this.whereConditions, ", ");

            result = this.connector.update(this.query);
        } else {
            // make the query string for delete queries...
            this.query = this.queryType + " from " + Helpers.implode(this.tables, ", ") +
                    " where " + Helpers.implode(this.whereConditions, ", ");

            result = this.connector.delete(this.query);
        }

        // clean up the query builder...
        this.cleanUp();

        return result;
    }

}
