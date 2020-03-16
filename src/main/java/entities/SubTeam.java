package entities;

import java.util.Objects;

public class SubTeam {

    private String name;

    public SubTeam(String name) {
        this.name = name;
    }

    public SubTeam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTeam subTeam = (SubTeam) o;
        return Objects.equals(name, subTeam.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
