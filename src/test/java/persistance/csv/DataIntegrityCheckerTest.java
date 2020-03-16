package persistance.csv;

import configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataIntegrityCheckerTest {
    private Configuration configurationMock;
    private DataIntegrityChecker dataIntegrityChecker;

    @Before
    public void setup() {
        configurationMock = mock(Configuration.class);
        when(configurationMock.getLocations()).thenReturn(allowedLocations());
        when(configurationMock.getSubTeams()).thenReturn(allowedSubTeams());
        dataIntegrityChecker = new DataIntegrityChecker(configurationMock);
    }

    @Test
    public void csvHeadersAreAccordingToTheEnum() {
        List<String> headers = Arrays.asList("Name,SubTeam,Location,LastServed,ShiftsCounter,IsExcluded".split(","));
        try {
            dataIntegrityChecker.checkHeadersAllowed(headers);
        } catch (Exception e) {
            fail("Should Not throw Exceptions for headers: " + headers);
        }
    }

    @Test
    public void shouldNotThrowExceptionWhenLocationsPassedIsAllowed() {
        try {
            dataIntegrityChecker.checkLocationAllowed("Another location");
        } catch (Exception e) {
            fail("Should Not throw Exceptions for location");
        }
    }

    @Test
    public void shouldNotThrowExceptionWhenOutcomePassedIsAllowed() {
        try {
            dataIntegrityChecker.checkOutcomeAllowed("Another subteam");
        } catch (Exception e) {
            fail("Should Not throw Exceptions for subteam");
        }
    }

    private List<String> allowedLocations() {
        List<String> allowedLocations = new ArrayList<>();
        allowedLocations.add("A location");
        allowedLocations.add("Another location");
        return allowedLocations;
    }

    private List<String> allowedSubTeams() {
        List<String> allowedSubTeams = new ArrayList<>();
        allowedSubTeams.add("A subteam");
        allowedSubTeams.add("Another subteam");
        return allowedSubTeams;
    }

}