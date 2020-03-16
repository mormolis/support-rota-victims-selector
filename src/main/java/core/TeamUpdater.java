package core;

import entities.Person;
import entities.Team;

import java.time.LocalDate;
import java.util.List;

public class TeamUpdater {

    public void updateSelectedPeople(Team team, List<Person> victims, LocalDate date) {
        for (Person personInTeams : team.getPeople()) {
            for (Person personInVictims : victims) {
                if (personInTeams.equals(personInVictims)) {
                    personInTeams.setLastServed(date);
                    personInTeams.setCounter(personInTeams.getCounter() + 1);
                }
            }
        }
    }
}
