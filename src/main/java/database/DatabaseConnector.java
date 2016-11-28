package database;

import main.Property;

import java.sql.*;

public class DatabaseConnector extends Property {

    /**
     * Database url string.
     */
    public String url;

    /**
     * Database username string.
     */
    public String username;

    /**
     * Database password string.
     */
    public String password;

    /**
     * Database name string.
     */
    public String databaseName;

    /**
     * Database connection.
     */
    public Connection connection;

    /**
     * Make new instance of database connector.
     */
    public DatabaseConnector() {
        // initialSeed properties...
        super();
    }

    /**
     * Try to connect to the database by parameters in properties file.
     *
     * @return connection to the database.
     */
    public Connection connect() {
        // try to find mysql jdbc connector driver...
        try {
            Class.forName(this.properties.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // get database variables from properties file...
        this.url = this.properties.getProperty("JDBC_URL");
        this.username = this.properties.getProperty("DB_USERNAME");
        this.password = this.properties.getProperty("DB_PASSWORD");
        this.databaseName = this.properties.getProperty("DB_NAME");

        // try to connect to the mysql database...
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);

            return this.connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Check if schema exists.
     *
     * @return boolean result of this check.
     */
    public boolean schemaExists() {
        // check database exists...
        return this.databaseExists() && this.tablesExists();
    }

    /**
     * Check if the database schema exists...
     *
     * @return boolean of check result.
     */
    public boolean databaseExists() {
        try {
            ResultSet result = this.connection.getMetaData().getCatalogs();

            // check if database name is in return result...
            while (result.next()) {
                String databaseName = result.getString(1);
                if (databaseName.equals(this.databaseName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }

        return false;
    }

    /**
     * Check if all tables in our schema exists.
     *
     * @return boolean result of this check.
     */
    public boolean tablesExists() {
        try {
            DatabaseMetaData meta = this.connection.getMetaData();
            ResultSet tables = meta.getTables(null, null, "customer", null);

            // check if it exists...
            if (tables.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }

        return false;
    }

    /**
     * Turn database checks on.
     */
    public void constraintsOn() {
        this.turnChecks(1);
    }

    /**
     * Turn database checks off.
     */
    public void constraintsOff() {
        this.turnChecks(0);
    }

    /**
     * Turn database checks on.
     *
     * @param status on or off.
     */
    private void turnChecks(int status) {
        // set some database default constraints on...
        try {
            Statement statement = this.connection.createStatement();
            statement.execute("SET UNIQUE_CHECKS=" + status);
            statement.execute("SET FOREIGN_KEY_CHECKS=" + status);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param query to be executed.
     *
     * @return result of the query.
     */
    public int insert(String query) {
        int insertedId = - 1;

        try {
            // instantiate an executable statement...
            Statement statement = this.connection.createStatement();

            // execute the insert query...
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            // get the returning result from the statement...
            ResultSet result = statement.getGeneratedKeys();

            // fill the inserted id variable...
            if (result.next()) {
                insertedId = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            insertedId = - 1;
        }

        // return the last inserted id...
        return insertedId;
    }

}
