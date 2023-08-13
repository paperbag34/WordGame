package Game;

import java.util.Scanner;

public class Hosts extends Person {
    private String hostName;

    public Hosts(String firstName) {
        super(firstName);
        this.hostName = firstName;
    }

    public void enterPhrase(String phrase) {
        // Set the game phrase based on the provided input
        Phrases.setGamePhrase(phrase);
    }
}
