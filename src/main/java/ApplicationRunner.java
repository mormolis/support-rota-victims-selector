import configuration.Configuration;
import core.PeopleByLastDateServedAndTimesComparator;
import core.PeopleSelector;
import core.TeamUpdater;
import entities.Person;
import entities.Team;
import persistance.TeamRepository;
import persistance.csv.CsvParser;
import persistance.csv.DataIntegrityChecker;
import persistance.csv.LineParser;
import ui.InputReader;
import ui.UserInterface;

import java.util.Comparator;

public class ApplicationRunner {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        DataIntegrityChecker dataIntegrityChecker = new DataIntegrityChecker(configuration);
        LineParser lineParser = new LineParser();
        TeamRepository<Team> teamRepository = new CsvParser(configuration, dataIntegrityChecker, lineParser);
        InputReader inputReader = new InputReader();
        PeopleSelector peopleSelector = new PeopleSelector(configuration);
        Comparator<Person> comparator = new PeopleByLastDateServedAndTimesComparator();
        TeamUpdater teamUpdater = new TeamUpdater();
        UserInterface userInterface = new UserInterface(teamRepository, inputReader, configuration, peopleSelector, comparator, teamUpdater);

        run(userInterface);
    }

    private static void run(UserInterface ui) {
        try {
            ui.play();
        } catch (RuntimeException e) {
            System.out.println("\n\nan error occurred: " + e.getMessage());
            System.out.println("let's try again...\n\n");
            run(ui);
        }
    }
}
