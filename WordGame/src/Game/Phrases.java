package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Phrases {
    private static String gamePhrase;
    private static String playingPhrase;
    static Boolean winCondition = false;
    
    public static void setGamePhrase(String phrase) {
        gamePhrase = phrase;
        initializePlayingPhrase();
    }

    public static String getPlayingPhrase() {
        return playingPhrase;
    }

    private static void initializePlayingPhrase() {
        StringBuilder sb = new StringBuilder();
        for (char ch : gamePhrase.toCharArray()) {
            if (Character.isLetter(ch)) {
                sb.append('_');
            } else {
                sb.append(ch);
            }
        }
        playingPhrase = sb.toString();
    }

    public static void findLetters(String letter) throws MultipleLettersException {
        if (letter.length() != 1) {
            throw new MultipleLettersException();
        }

        char target = letter.charAt(0);
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < gamePhrase.length(); i++) {
            if (Character.toLowerCase(gamePhrase.charAt(i)) == Character.toLowerCase(target)) {
                indices.add(i);
            }
        }

        if (!indices.isEmpty()) {
            StringBuilder sb = new StringBuilder(playingPhrase);
            for (int index : indices) {
                sb.setCharAt(index, gamePhrase.charAt(index));
            }
            playingPhrase = sb.toString();
        } else {
            System.out.println("The letter is not in the phrase.");
        }

        //check win condition
        if (!playingPhrase.contains("_")) {
            System.out.println("Congratulations, you won!");
            winCondition = true;  
            initializePlayingPhrase();
        }
    }
}