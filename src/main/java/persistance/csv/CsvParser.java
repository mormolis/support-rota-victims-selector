package persistance.csv;

import configuration.Configuration;
import entities.Person;
import entities.Team;
import persistance.TeamRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CsvParser implements TeamRepository<Team> {

    private final Configuration configuration;
    private final DataIntegrityChecker dataIntegrityChecker;
    private final LineParser lineParser;

    public CsvParser(Configuration configuration, DataIntegrityChecker dataIntegrityChecker, LineParser lineParser) {
        this.configuration = configuration;
        this.dataIntegrityChecker = dataIntegrityChecker;
        this.lineParser = lineParser;
    }

    @Override
    public Team getTeam() {
        try (BufferedReader reader = Files.newBufferedReader(pathToCsv(), StandardCharsets.UTF_8)) {
            checkCsvHeadersValidity(reader);
            Set<Person> people = parseCsvLineByLine(reader);
            return new Team(people);
        } catch (IOException ex) {
            throw new RuntimeException("Error while trying to parse the csv", ex);
        }
    }

    @Override
    public void storeTeam(Team team) {
        try (BufferedWriter writer = Files.newBufferedWriter(pathToCsv(), StandardCharsets.UTF_8)) {
            writer.write(CsvHeader.getHeader() + "\n");
            for (Person person : team.getPeople()) {
                writer.write(lineParser.convertToLine(person) + "\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error while trying to write the csv", ex);
        }
    }

    private Set<Person> parseCsvLineByLine(BufferedReader reader) throws IOException {
        Set<Person> people = new HashSet<>(); // we can use treeset and pass the comparator
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            List<String> line = Arrays.asList(currentLine.split(","));
            checkDataIntegrity(line);
            people.add(lineParser.convertToPerson(line));
        }
        return people;
    }

    private void checkCsvHeadersValidity(BufferedReader reader) throws IOException {
        String currentLine;
        if ((currentLine = reader.readLine()) != null) {
            List<String> lineAsAList = Arrays.asList(currentLine.split(","));
            dataIntegrityChecker.checkHeadersAllowed(lineAsAList);
        }
    }


    private void checkDataIntegrity(List<String> line) {
        dataIntegrityChecker.checkLocationAllowed(line.get(2));
        dataIntegrityChecker.checkOutcomeAllowed(line.get(1));
    }


    private Path pathToCsv() {
        return Paths.get(configuration.getPersistenceUri());
    }

}
