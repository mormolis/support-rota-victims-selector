package persistance.csv;

import entities.Location;
import entities.Person;
import entities.SubTeam;

import java.time.LocalDate;
import java.util.List;

public class LineParser {


    public static final String SPLITTER = ",";

    public Person convertToPerson(List<String> line) {
        return new Person(
                name(line),
                location(line),
                subteam(line),
                counter(line),
                lastServed(line),
                isExcluded(line)
        );
    }

    public String convertToLine(Person person) {
        StringBuilder line = new StringBuilder();
        line.append(person.getName())
                .append(SPLITTER)
                .append(person.getSubTeam().getName())
                .append(SPLITTER)
                .append(person.getLocation().getName())
                .append(SPLITTER);

        if (person.getLastServed() != null) {
            line.append(person.getLastServed());
        }

        line.append(SPLITTER)
                .append(person.getCounter())
                .append(SPLITTER)
                .append(person.isExcluded());
        return line.toString();
    }

    private String name(List<String> line) {
        return line.get(CsvHeader.NAME.ordinal());
    }

    private Location location(List<String> line) {
        String locationName = line.get(CsvHeader.LOCATION.ordinal());
        return new Location(locationName);
    }

    private SubTeam subteam(List<String> line) {
        String subTeam = line.get(CsvHeader.SUB_TEAM.ordinal());
        return new SubTeam(subTeam);
    }

    private int counter(List<String> line) {
        int ordinal = CsvHeader.SHIFTS_COUNTER.ordinal();
        return indexIsNotOutOfBound(line, ordinal) ? Integer.parseInt(line.get(ordinal)) : 0;
    }

    private LocalDate lastServed(List<String> line) {
        int ordinal = CsvHeader.LAST_SERVED.ordinal();
        //YYYY-MM-DD
        return indexOutOfBoundOrEmptyString(line, ordinal) ? LocalDate.parse(line.get(ordinal)) : LocalDate.now();
    }

    private boolean isExcluded(List<String> line) {
        int index = CsvHeader.IS_EXCLUDED.ordinal();
        return indexIsNotOutOfBound(line, index) && Boolean.parseBoolean(line.get(index));
    }

    private boolean indexOutOfBoundOrEmptyString(List<String> line, int ordinal) {
        return indexIsNotOutOfBound(line, ordinal) && !line.get(ordinal).equals("");
    }

    private boolean indexIsNotOutOfBound(List<String> line, int ordinal) {
        return line.size() >= ordinal;
    }
}
