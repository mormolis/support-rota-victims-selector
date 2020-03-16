package core;

import configuration.Configuration;
import entities.Location;
import entities.Person;
import entities.SubTeam;
import entities.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PeopleSelector {

    public final int MAX_AMOUNT_OF_VICTIMS;
    public final int MAX_AMOUNT_OF_VICTIMS_PER_LOCATION;

    public PeopleSelector(Configuration configuration) {
        this.MAX_AMOUNT_OF_VICTIMS = configuration.getMaxAmountOfVictims();
        this.MAX_AMOUNT_OF_VICTIMS_PER_LOCATION = configuration.getMaxAmountOfVictimsPerLocation();
    }

    public List<Person> selectVictimsForSprint(Team teamTeam, Comparator<Person> criteriaOfSelection) {

        List<Person> people = new ArrayList<>(teamTeam.getPeople());
        people = excludePeopleFromVictims(people);
        people.sort(criteriaOfSelection);
        List<Person> potentialVictims = searchForPotentialVictims(people);
        return getUpToTheMaximumAmountOfVictimsWanted(potentialVictims);
    }

    private List<Person> searchForPotentialVictims(List<Person> people) {
        List<Person> potentialVictims = new ArrayList<>();
        for (Person person : people) {
            if (anotherPersonOfTheSameSubTeamIsInVictims(potentialVictims, person.getSubTeam())) {
                continue;
            }
            if (moreThanTheMaximumAmountOfPeopleFromTheSameLocationIsInVictims(potentialVictims, person.getLocation())) {
                continue;
            }
            potentialVictims.add(person);
        }
        return potentialVictims;
    }

    private List<Person> excludePeopleFromVictims(List<Person> people) {
        return people.stream()
                .filter(person -> !person.isExcluded())
                .collect(Collectors.toList());
    }

    private boolean moreThanTheMaximumAmountOfPeopleFromTheSameLocationIsInVictims(List<Person> victims, Location location) {
        return victims.stream()
                .filter(person -> person.getLocation().equals(location))
                .count() >= MAX_AMOUNT_OF_VICTIMS_PER_LOCATION;
    }

    private boolean anotherPersonOfTheSameSubTeamIsInVictims(List<Person> victims, SubTeam subTeam) {
        return victims.stream().anyMatch(person -> person.getSubTeam().equals(subTeam));
    }

    private List<Person> getUpToTheMaximumAmountOfVictimsWanted(List<Person> potentialVictims) {
        return potentialVictims.size() >= MAX_AMOUNT_OF_VICTIMS ? potentialVictims.subList(0, MAX_AMOUNT_OF_VICTIMS) : potentialVictims;
    }

}
