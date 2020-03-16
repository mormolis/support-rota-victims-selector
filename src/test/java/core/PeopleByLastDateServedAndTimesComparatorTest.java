package core;

import entities.Person;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeopleByLastDateServedAndTimesComparatorTest {

    Person person1;
    Person person2;
    Person person3;
    Person person4;
    List<Person> people;

    @Before
    public void setup() {
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();
        person4 = new Person();
        people = new ArrayList<>();
    }

    @Test
    public void sortByLastServedDate() {

        givenAPersonHas10DaysDifferenceFromTheOther(person1, person2, people);

        people.sort(new PeopleByLastDateServedAndTimesComparator());

        assertThat(people, equalTo(thenPeopleWithOlderDateComeFirst(person1, person2)));
    }

    @Test
    public void sortByCounterWhenDatesAreTheSame() {

        givenAPersonHas10TimesMoreShiftsButSameDates(person1, person2, people);

        people.sort(new PeopleByLastDateServedAndTimesComparator());

        assertThat(people, equalTo(thenPeopleWithLessCountedShiftsComeFirst(person1, person2)));
    }

    @Test
    public void dateIsMoreImportantThanCounterButCounterWillDetermineWhenSomeoneGoesOnShift() {

        givenAnArrayOfPeople(person1, person2, person3, person4, people);

        people.sort(new PeopleByLastDateServedAndTimesComparator());

        assertThat(people, equalTo(expectedTheFareResultsAccordingToTheAlgorithm(person1, person2, person3, person4)));
    }

    private List<Person> thenPeopleWithOlderDateComeFirst(Person person1, Person person2) {
        List<Person> expected = new ArrayList<>();
        expected.add(person2);
        expected.add(person1);
        return expected;
    }

    private List<Person> thenPeopleWithLessCountedShiftsComeFirst(Person person1, Person person2) {
        List<Person> expected = new ArrayList<>();
        expected.add(person2);
        expected.add(person1);
        return expected;
    }

    private void givenAPersonHas10DaysDifferenceFromTheOther(Person person1, Person person2, List<Person> people) {
        person1.setCounter(0);
        person1.setLastServed(LocalDate.now().minus(10, ChronoUnit.DAYS));

        person2.setCounter(0);
        person2.setLastServed(LocalDate.now().minus(20, ChronoUnit.DAYS));

        people.add(person1);
        people.add(person2);
    }

    private void givenAnArrayOfPeople(Person person1, Person person2, Person person3, Person person4, List<Person> people) {
        person1.setCounter(1);
        person1.setName("1");
        person1.setLastServed(LocalDate.now().minus(10, ChronoUnit.DAYS));

        person2.setCounter(30);
        person2.setName("2");
        person2.setLastServed(LocalDate.now().minus(20, ChronoUnit.DAYS));

        person3.setName("3");
        person3.setCounter(0);
        person3.setLastServed(LocalDate.now().minus(10, ChronoUnit.DAYS));

        person4.setName("4");
        person4.setCounter(0);
        person4.setLastServed(LocalDate.now().minus(30, ChronoUnit.DAYS));

        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);
    }

    private List<Person> expectedTheFareResultsAccordingToTheAlgorithm(Person person1, Person person2, Person person3, Person person4) {
        List<Person> expected = new ArrayList<>();
        expected.add(person4);
        expected.add(person2);
        expected.add(person3);
        expected.add(person1);
        return expected;
    }

    private void givenAPersonHas10TimesMoreShiftsButSameDates(Person person1, Person person2, List<Person> people) {
        person1.setCounter(10);
        person1.setLastServed(LocalDate.now().minus(10, ChronoUnit.DAYS));

        person2.setCounter(0);
        person2.setLastServed(LocalDate.now().minus(10, ChronoUnit.DAYS));

        people.add(person1);
        people.add(person2);
    }

}