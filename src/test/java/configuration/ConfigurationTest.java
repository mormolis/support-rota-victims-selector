package configuration;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConfigurationTest {

    public static final String A_DIR_PASSED_AS_A_SYSTEM_PROPERTY = "/such/a/dir";
    private final Configuration appConfig = new Configuration();

    @Test
    public void directoryToSyncIsReturningASystemPropertyFirst() {
        System.setProperty("persistenceUri", A_DIR_PASSED_AS_A_SYSTEM_PROPERTY);

        String directoryToSync = appConfig.getPersistenceUri();

        assertThat(directoryToSync, equalTo(A_DIR_PASSED_AS_A_SYSTEM_PROPERTY));
    }

    @Test
    public void directoryToSyncIsReturningTheDefaultValueWhenNoSystemPropertyExists() {
        System.clearProperty("persistenceUri");

        String directoryToSync = appConfig.getPersistenceUri();

        assertThat(directoryToSync, equalTo("/home/mormolis/run_rota.csv"));
    }

    @Test
    public void maxAmountOfVictimsPerLocation() {
       int maxAmountOfVictimsPerLocation = appConfig.getMaxAmountOfVictimsPerLocation();
       assertThat(maxAmountOfVictimsPerLocation, equalTo(2));
    }

    @Test
    public void maxAmountOfVictims() {
        int maxAmountOfVictimsPerLocation = appConfig.getMaxAmountOfVictims();
        assertThat(maxAmountOfVictimsPerLocation, equalTo(6));
    }

}