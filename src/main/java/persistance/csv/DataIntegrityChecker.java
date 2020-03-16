package persistance.csv;

import configuration.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataIntegrityChecker {
    private final List<String> allowedHeaders;
    private final List<String> allowedLocations;
    private final List<String> allowedSubTeams;

    public DataIntegrityChecker(Configuration configuration) {
        allowedHeaders = getHeaders();
        allowedLocations = configuration.getLocations();
        allowedSubTeams = configuration.getSubTeams();
    }

    public void checkHeadersAllowed(List<String> headersExtractedFromCsv) {
        if (headersExtractedFromCsv.size() != allowedHeaders.size()) {
            throw new IllegalArgumentException("Headers are different size from the one expected -> " + allowedHeaders);
        }

        for (int i = 0; i < headersExtractedFromCsv.size(); i++) {
            if (!headersExtractedFromCsv.get(i).equals(allowedHeaders.get(i))) {
                throw new IllegalArgumentException("Header missmatch, expected " + allowedHeaders.get(i)
                        + " got: " + headersExtractedFromCsv.get(i));
            }
        }
    }

    public void checkLocationAllowed(String location) {
        if (!allowedLocations.contains(location)) {
            throw new IllegalArgumentException("Location: " + location + " is not supported. " +
                    "Check the location Values on the csv to match with the following:\n" +
                    allowedLocations);
        }
    }

    public void checkOutcomeAllowed(String subTeam) {
        if (!allowedSubTeams.contains(subTeam)) {
            throw new IllegalArgumentException("SubTeam: " + subTeam + " is not supported. " +
                    "Check the subteam values on the csv to match with the following:\n" +
                    allowedSubTeams);
        }
    }

    private List<String> getHeaders() {
        return Arrays.stream(CsvHeader.values())
                .map(CsvHeader::getOnCsvValue)
                .collect(Collectors.toList());
    }


}
