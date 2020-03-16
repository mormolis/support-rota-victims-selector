package core;

import entities.Location;
import entities.Person;
import entities.SubTeam;
import entities.Team;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TeamUpdaterTest {

    private TeamUpdater teamUpdater;
    private Person p1;
    private Person p2;
    private Person p3;
    private Person p4;
    private Team team;


    @Before
    public void setup(){
        p1 = new Person("a person", new Location("a location"), new SubTeam("a subteam"), 0, LocalDate.parse("2020-10-10"), false);
        p2 = new Person("another person", new Location("a location"), new SubTeam("another subteam"), 2, LocalDate.parse("2020-10-10"), false);
        p3 = new Person("a victim person", new Location("a location"), new SubTeam("a subteam"), 0, LocalDate.parse("2020-10-10"), false);
        p4 = new Person("another victim person", new Location("a location"), new SubTeam("a subteam"), 0, LocalDate.parse("2020-10-10"), false);
        Set<Person> personSet = new HashSet<>();
        personSet.add(p1);
        personSet.add(p2);
        personSet.add(p3);
        personSet.add(p4);
        team = new Team(personSet);
        teamUpdater = new TeamUpdater();
    }

    @Test
    public void teamIsUpdated(){
        List<Person> twoPeople = new ArrayList<>();
        twoPeople.add(p3);
        twoPeople.add(p4);
        teamUpdater.updateSelectedPeople(team, twoPeople, LocalDate.parse("2030-10-10"));

        assertThat(p3.getCounter(), equalTo(1));
        assertThat(p3.getLastServed(), equalTo(LocalDate.parse("2030-10-10")));
        assertThat(p4.getCounter(), equalTo(1));
        assertThat(p4.getLastServed(), equalTo(LocalDate.parse("2030-10-10")));
    }

}