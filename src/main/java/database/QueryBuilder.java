package database;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;

public class QueryBuilder {

    protected String query;

    protected QueryType queryType;

    protected ArrayList<String> tables;

    protected ArrayList<String> whereConditions;

    protected ArrayList<String> values;

    protected ArrayList<String> set;

    protected ArrayList<String> columns;

    protected DatabaseConnector connector;

    public QueryBuilder() {
        // clean up the query builder...
        this.cleanUp();

        // make the database connection...
        this.connector = new DatabaseConnector();

        // connect the database connector...
        this.connector.connect();
    }

    private void cleanUp() {
        this.query = "";
        this.queryType = QueryType.SELECT;
        this.tables = new ArrayList<String>();
        this.whereConditions = new ArrayList<String>();
        this.values = new ArrayList<String>();
        this.set = new ArrayList<String>();
        this.columns = new ArrayList<String>();
    }

    public QueryBuilder select(String... columns) {
        this.queryType = QueryType.SELECT;

        for (String column : columns) {
            this.columns.add(column);
        }

        return this;
    }

    public QueryBuilder insert() {
        this.queryType = QueryType.INSERT;

        return this;
    }

    public QueryBuilder delete() {
        this.queryType = QueryType.DELETE;

        return this;
    }

    public QueryBuilder update() {
        this.queryType = QueryType.UPDATE;

        return this;
    }

    public QueryBuilder from(String... tables) {
        for (String table : tables) {
            this.tables.add(table);
        }

        return this;
    }

    public QueryBuilder into(String table) {
        this.tables.add(table);

        return this;
    }

    public QueryBuilder set(String column, String value) {
        // make the set statement and add it to set array...
        this.set.add(column + " = " + value);

        return this;
    }

    public QueryBuilder values(String... columns) {
        // add the value to the list of values...
        for (String column : columns) {
            this.values.add(column);
        }

        return this;
    }

    public QueryBuilder where(String column, String equality, String value) {
        // add the where clause...
        this.whereConditions.add(column + " " + equality + " " + value);

        return this;
    }

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
