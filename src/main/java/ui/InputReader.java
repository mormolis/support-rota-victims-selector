package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {

    BufferedReader reader;

    public InputReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readUsersInput() {
        return readUsersInputHelper(reader);
    }

    private String readUsersInputHelper(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return readUsersInputHelper(reader);
        }
    }
}
