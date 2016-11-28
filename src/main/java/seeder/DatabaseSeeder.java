package seeder;

import database.DatabaseConnector;
import exceptions.SchemaDoesNotExistsException;
import main.Property;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Has the responsibility of filling the database at the first run on benchmark. Checks the database schema existence
 * and also checks whether the tables are filled with benchmark initial data.
 */
public class DatabaseSeeder extends Property {

    /**
     * Database connection instance.
     */
    protected Connection connection;

    /**
     * Database connector instance.
     */
    protected DatabaseConnector connector;

    /**
     * Create a new instance of database seeder.
     */
    public DatabaseSeeder() {
        // initialSeed properties...
        super();

        // retrieve a database connection instance...
        this.connector = new DatabaseConnector();
        this.connection = this.connector.connect();
    }

    /**
     * Seed the database.
     */
    public void seed() throws SchemaDoesNotExistsException {
        // check if the schema exists...
        if (! this.connector.schemaExists()) {
            throw new SchemaDoesNotExistsException("Schema does not exists.");
        }

        // check if the database is not seeded yet...
        //if (this.isSeeded()) {
        //return;
        //}

        // turn on the database checks...
        this.connector.constraintsOff();

        // make instance of loader and seed the database...
        Loader loader = new Loader();

        // start the initial seed...
        loader.initialSeed();

        // turn database checks off...
        this.connector.constraintsOn();
    }

    /**
     * Check if the database is seeded.
     *
     * @return boolean is seeded.
     */
    private boolean isSeeded() {
        Statement statement;

        try {
            // select on customer table...
            statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("select * from `customer`");

            // check if result has next...
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] arguments) {
        DatabaseSeeder seeder = new DatabaseSeeder();

        try {
            seeder.seed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
