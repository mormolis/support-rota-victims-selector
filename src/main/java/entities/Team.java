package entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Team {
    private final Set<Person> people;

    public Team(Set<Person> people) {
        this.people = people;
    }


    public Set<Person> getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return people.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(people, team.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(people);
    }
}
