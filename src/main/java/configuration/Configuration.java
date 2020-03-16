package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration {

    public static final String PERSISTENCE_URI = "persistenceUri";
    private final Properties properties;

    public Configuration() {
        properties = new Properties();
        loadProperties();
    }

    public String getPersistenceUri() {
        if (System.getProperty(PERSISTENCE_URI) != null) {
            return System.getProperty(PERSISTENCE_URI);
        }
        return properties.getProperty(PERSISTENCE_URI);
    }

    public int getMaxAmountOfVictimsPerLocation() {
        return Integer.parseInt(properties.getProperty("maxAmountOfVictimsPerLocation"));
    }

    public int getMaxAmountOfVictims() {
        return Integer.parseInt(properties.getProperty("maxAmountOfVictims"));
    }

    public List<String> getSubTeams() {
        String subTeams = properties.getProperty("subTeams");
        return Arrays.asList(subTeams.split(","));
    }

    public List<String> getLocations() {
        String locations = properties.getProperty("locations");
        return Arrays.asList(locations.split(","));
    }

    private void loadProperties() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("could not load application properties", e);
        }
    }
}
