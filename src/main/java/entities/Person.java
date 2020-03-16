package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private String name;
    private Location location;
    private SubTeam subTeam;
    private int counter;
    private LocalDate lastServed;
    private boolean isExcluded;

    public Person(String name, Location location, SubTeam subTeam, int counter, LocalDate lastServed, boolean isExcluded) {
        this.name = name;
        this.location = location;
        this.subTeam = subTeam;
        this.counter = counter;
        this.lastServed = lastServed;
        this.isExcluded = isExcluded;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SubTeam getSubTeam() {
        return subTeam;
    }

    public void setSubTeam(SubTeam subTeam) {
        this.subTeam = subTeam;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public LocalDate getLastServed() {
        return lastServed;
    }

    public void setLastServed(LocalDate lastServed) {
        this.lastServed = lastServed;
    }

    public boolean isExcluded() {
        return isExcluded;
    }

    public void setExcluded(boolean excluded) {
        isExcluded = excluded;
    }

    @Override
    public String toString() {
        return name +
                " " + location +
                " " + subTeam +
                " " + counter +
                " " + lastServed +
                " " + isExcluded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return counter == person.counter &&
                isExcluded == person.isExcluded &&
                Objects.equals(name, person.name) &&
                Objects.equals(location, person.location) &&
                Objects.equals(subTeam, person.subTeam) &&
                Objects.equals(lastServed, person.lastServed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, subTeam, counter, lastServed, isExcluded);
    }
}
