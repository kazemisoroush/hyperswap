package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads properties of project from `/hyperswap.properties` file in project root.
 */
public class Property {

    /**
     * Properties for this project.
     */
    public Properties properties = null;

    /**
     * Create a new instance of property.
     */
    public Property() {
        // initialSeed the properties file of the project at the project root...
        String propertiesFile = "hyperswap.properties";

        properties = new Properties();

        // try to initialSeed properties file...
        try {
            this.properties.load(new FileInputStream(propertiesFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
