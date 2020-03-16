package ui;

import configuration.Configuration;
import core.PeopleSelector;
import core.TeamUpdater;
import entities.Person;
import entities.Team;
import persistance.TeamRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

// !!BEWARE!!! Ugly code ahead!
public class UserInterface {

    private TeamRepository<Team> teamRepository;
    private InputReader inputReader;
    private Configuration configuration;
    private PeopleSelector peopleSelector;
    private Comparator<Person> selectionCriteria;
    private TeamUpdater teamUpdater;

    public UserInterface(TeamRepository<Team> teamRepository, InputReader inputReader, Configuration configuration, PeopleSelector peopleSelector, Comparator<Person> selectionCriteria, TeamUpdater teamUpdater) {
        this.teamRepository = teamRepository;
        this.inputReader = inputReader;
        this.configuration = configuration;
        this.peopleSelector = peopleSelector;
        this.selectionCriteria = selectionCriteria;
        this.teamUpdater = teamUpdater;
    }

    public void play() {
        Team team = null;
        String input;
        displayMenu();
        do {
            System.out.print("Please input your choice from the menu: ");
            input = inputReader.readUsersInput();
            switch (input) {
                case "1":
                    team = loadFile();
                    break;
                case "2":
                    if (team == null) {
                        System.out.println("Team is not loaded please try to load the team first.");
                        break;
                    }
                    List<Person> victims = doTheSelection(team);
                    LocalDate date = getSprintsStartingDate();
                    teamUpdater.updateSelectedPeople(team, victims, date);
                    promptForSavingChanges(team, date);
                    break;
                case "3":
                    displayMenu();
                    break;
                case "4":
                    System.out.println("Good bye!");
                    break;
            }
        } while (!input.equals("4"));
    }

    private void displayMenu() {
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Welcome to a fairer victim selector for the run rota  |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. load a new team from a file                        |");
        System.out.println("| 2. select victims                                     |");
        System.out.println("| 3. display menu                                       |");
        System.out.println("| 4. exit                                               |");
        System.out.println("|-------------------------------------------------------|");
    }

    private void promptForSavingChanges(Team team, LocalDate date) {
        String case2Input;
        do {
            System.out.print("would you like to save file? (y/n): ");
            case2Input = inputReader.readUsersInput();
            if (case2Input.equals("y")) {
                System.out.println("saving file...");
                System.setProperty(Configuration.PERSISTENCE_URI, configuration.getPersistenceUri() + date.toString() + ".csv");
                teamRepository.storeTeam(team);
            }
            if (!case2Input.equals("y") && !case2Input.equals("n")) {
                System.out.println("wrong input please try again");
            }
        } while ((!case2Input.equals("y") && !case2Input.equals("n")));
    }

    private List<Person> doTheSelection(Team team) {
        System.out.println("Currently the max amount of people per location is set to: " + configuration.getMaxAmountOfVictimsPerLocation());
        System.out.println("Currently the max amount of people selected is set to: " + configuration.getMaxAmountOfVictims());
        System.out.println("The above values are stored in the config file");

        List<Person> victims = peopleSelector.selectVictimsForSprint(team, selectionCriteria);
        System.out.println("Success!");
        System.out.println("People selected: ");
        victims.forEach(System.out::println);
        return victims;
    }

    private Team loadFile() {
        System.clearProperty(Configuration.PERSISTENCE_URI);
        Team team;
        System.out.print("Would you like to specify path? The default is: " + configuration.getPersistenceUri() + " (y/n): ");
        String case1Input = inputReader.readUsersInput();
        if (case1Input.equals("y")) {
            System.out.print("Please give absolute file path: ");
            case1Input = inputReader.readUsersInput();
            System.setProperty(Configuration.PERSISTENCE_URI, case1Input);
        }
        System.out.println("parsing from " + configuration.getPersistenceUri());
        team = teamRepository.getTeam();
        System.out.println("Success!");
        return team;
    }

    private LocalDate getSprintsStartingDate() {
        System.out.println("\nWhen is the starting date of the sprint? (YYYY-MM-DD): ");
        try {
            return LocalDate.parse(inputReader.readUsersInput());
        } catch (DateTimeParseException e) {
            System.out.print("Date string could not be parsed, please follow the format YYYY-MM-DD and try again: ");
            return getSprintsStartingDate();
        }
    }
}
