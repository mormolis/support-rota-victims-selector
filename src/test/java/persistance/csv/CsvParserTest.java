package persistance.csv;

import configuration.Configuration;
import entities.Location;
import entities.Person;
import entities.SubTeam;
import entities.Team;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CsvParserTest {

    private Configuration configurationMock;
    private DataIntegrityChecker dataIntegrityCheckerMock;
    private LineParser lineParserMock;
    private Person aPerson;


    private CsvParser csvParser;
    private File csv;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setup() throws IOException {
        configurationMock = mock(Configuration.class);
        dataIntegrityCheckerMock = mock(DataIntegrityChecker.class);
        lineParserMock = mock(LineParser.class);

        aPerson = new Person("Foufoutos", new Location("London"), new SubTeam("outcome1"), 0, LocalDate.parse("2020-10-16"), true);

        csv = temporaryFolder.newFile("testCsv.csv");
        when(configurationMock.getPersistenceUri()).thenReturn(csv.toString());
        csvParser = new CsvParser(configurationMock, dataIntegrityCheckerMock, lineParserMock);
    }

    @Test
    public void getTeam() throws IOException {
        List<String> firstLine = Arrays.asList("Name,SubTeam,Location,LastServed,ShiftsCounter,IsExcluded".split(","));
        List<String> secondLine = Arrays.asList("Foufoutos,outome1,London,2020-10-16,0,true".split(","));
        when(lineParserMock.convertToPerson(secondLine)).thenReturn(aPerson);
        String fileContent = "Name,SubTeam,Location,LastServed,ShiftsCounter,IsExcluded\nFoufoutos,outome1,London,2020-10-16,0,true\n";
        Files.write(Paths.get(csv.getAbsolutePath()), fileContent.getBytes());
        Team team = csvParser.getTeam();

        assertThat(team, equalTo(aTeam()));
        verify(dataIntegrityCheckerMock).checkHeadersAllowed(firstLine);
    }

    @Test
    public void storeTeam() {
        List<String> secondLine = Arrays.asList("Foufoutos,outome1,London,2020-10-16,0,true".split(","));
        when(lineParserMock.convertToPerson(secondLine)).thenReturn(aPerson);
        when(lineParserMock.convertToLine(aPerson)).thenReturn("Foufoutos,outome1,London,2020-10-16,0,true");

        csvParser.storeTeam(aTeam());
        //TODO: use non prod code for this test (CsvParser::getTeam)
        assertThat(csvParser.getTeam(), equalTo(aTeam()));
    }

    private Team aTeam() {
        Set<Person> people = new HashSet<>();
        people.add(aPerson);
        return new Team(people);
    }


}

