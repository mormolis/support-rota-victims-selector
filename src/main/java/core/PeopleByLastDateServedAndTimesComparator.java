package core;

import entities.Person;

import java.util.Comparator;

public class PeopleByLastDateServedAndTimesComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {

        if (person1.getLastServed().isAfter(person2.getLastServed())) {
            return 1;
        }

        if (person2.getLastServed().isAfter(person1.getLastServed())) {
            return -1;
        }

        if (person1.getLastServed().equals(person2.getLastServed())) {

            if (person1.getCounter() > person2.getCounter()) {
                return 1;
            }

            if (person1.getCounter() < person2.getCounter()) {
                return -1;
            }
        }
        return 0;
    }
}
