package core;

import configuration.Configuration;
import entities.Team;
import entities.Location;
import entities.Person;
import entities.SubTeam;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: write more test covering different scenarios
public class PeopleSelectorTest {
    Location l1 = new Location("l1");
    Location l2 = new Location("l2");
    Location l3 = new Location("l3");
    Location l4 = new Location("l4");

    SubTeam s1 = new SubTeam("o1");
    SubTeam s2 = new SubTeam("o2");
    SubTeam s3 = new SubTeam("o3");
    SubTeam s4 = new SubTeam("o4");
    SubTeam s5 = new SubTeam("o5");


    Person p1 = new Person("p1", l1, s1, 0, LocalDate.parse("2020-10-10").minus(20, ChronoUnit.DAYS), false);
    Person p2 = new Person("p2", l1, s1, 0, LocalDate.parse("2020-10-10").minus(10, ChronoUnit.DAYS), false);
    Person p3 = new Person("p3", l2, s1, 0, LocalDate.parse("2020-10-10").minus(10, ChronoUnit.DAYS), false);
    Person p4 = new Person("p4", l2, s2, 0, LocalDate.parse("2020-10-10").minus(20, ChronoUnit.DAYS), false);
    Person p5 = new Person("p5", l2, s2, 0, LocalDate.parse("2020-10-10").minus(30, ChronoUnit.DAYS), false);
    Person p6 = new Person("p6", l3, s2, 0, LocalDate.parse("2020-10-10").minus(30, ChronoUnit.DAYS), false);
    Person p7 = new Person("p7", l3, s3, 0, LocalDate.parse("2020-10-10").minus(30, ChronoUnit.DAYS), false);
    Person p8 = new Person("p8", l3, s3, 0, LocalDate.parse("2020-10-10").minus(20, ChronoUnit.DAYS), false);
    Person p9 = new Person("p9", l3, s3, 0, LocalDate.parse("2020-10-10").minus(1, ChronoUnit.DAYS), false);
    Person p10 = new Person("p10", l3, s3, 0, LocalDate.parse("2020-10-10").minus(10, ChronoUnit.DAYS), false);
    Person p11 = new Person("p11", l4, s3, 0, LocalDate.parse("2020-10-10").minus(15, ChronoUnit.DAYS), false);
    Person p12 = new Person("p12", l4, s4, 0, LocalDate.parse("2020-10-10").minus(40, ChronoUnit.DAYS), false);
    Person p13 = new Person("p13", l4, s4, 0, LocalDate.parse("2020-10-10").minus(10, ChronoUnit.DAYS), false);
    Person p14 = new Person("p14", l4, s4, 0, LocalDate.parse("2020-10-10").minus(40, ChronoUnit.DAYS), false);
    Person p15 = new Person("p15", l4, s5, 0, LocalDate.parse("2020-10-10").minus(10, ChronoUnit.DAYS), true);

    @Test
    public void victimsAreBeingPickedBySearchCriteriaPassed() {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getMaxAmountOfVictims()).thenReturn(6);
        when(configuration.getMaxAmountOfVictimsPerLocation()).thenReturn(2);
        PeopleSelector peopleSelector = new PeopleSelector(configuration);

        List<Person> victims = peopleSelector.selectVictimsForSprint(getTeam(), new PeopleByLastDateServedAndTimesComparator());

        assertThat(victims, equalTo(expectedVictims()));
    }

    private List<Person> expectedVictims() {

        List<Person> expected = new ArrayList<>();
        expected.add(p12);
        expected.add(p5);
        expected.add(p7);
        expected.add(p1);
        return expected;
    }

    private Team getTeam() {
        Set<Person> peopleInIdentity = new LinkedHashSet<>();
        Team team = new Team(peopleInIdentity);
        peopleInIdentity.add(p1);
        peopleInIdentity.add(p2);
        peopleInIdentity.add(p3);
        peopleInIdentity.add(p4);
        peopleInIdentity.add(p5);
        peopleInIdentity.add(p6);
        peopleInIdentity.add(p7);
        peopleInIdentity.add(p8);
        peopleInIdentity.add(p9);
        peopleInIdentity.add(p10);
        peopleInIdentity.add(p11);
        peopleInIdentity.add(p12);
        peopleInIdentity.add(p13);
        peopleInIdentity.add(p14);
        peopleInIdentity.add(p15);

        return team;
    }

}