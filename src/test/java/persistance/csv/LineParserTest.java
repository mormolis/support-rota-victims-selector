package persistance.csv;

import entities.Location;
import entities.Person;
import entities.SubTeam;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class LineParserTest {

    LineParser lineParser = new LineParser();

    @Test
    public void convertToPersonTest() {
        Person expected = aPerson();
        List<String> list = aLine();

        Person actual = lineParser.convertToPerson(list);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void convertFromPersonToLine() {
        String expected = "Emily,A-team,London,2020-10-10,10,false";

        String actual = lineParser.convertToLine(aPerson());

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void personWithNoValuesReturnsComas(){
        String expected = "Emily,A-Team,London,,0,false";

        String actual = lineParser.convertToLine(anEmptyPerson());

        assertThat(actual, equalTo(expected));
    }

    private List<String> aLine() {
        List<String> list = new ArrayList<>();
        list.add("Emily");
        list.add("A-team");
        list.add("London");
        list.add("2020-10-10");
        list.add("10");
        list.add("false");
        return list;
    }

    private Person aPerson() {
        return new Person("Emily",
                new Location("London"),
                new SubTeam("A-team"),
                10,
                LocalDate.parse("2020-10-10"),
                false);
    }

    private Person anEmptyPerson() {
        Person person = new Person();
        person.setName("Emily");
        person.setLocation(new Location("London"));
        person.setSubTeam(new SubTeam("A-Team"));
        return person;
    }

}