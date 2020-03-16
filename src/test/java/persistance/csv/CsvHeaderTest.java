package persistance.csv;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class CsvHeaderTest {

    @Test
    public void getHeader(){
        String expectedHeader = "Name,SubTeam,Location,LastServed,ShiftsCounter,IsExcluded";
        assertThat(CsvHeader.getHeader(), equalTo(expectedHeader));
    }

}