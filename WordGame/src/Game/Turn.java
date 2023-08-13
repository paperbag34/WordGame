package Game;

import java.util.Scanner;

public class Turn {
    public void takeTurn(Players player, Hosts host) {
        Scanner scanner = new Scanner(System.in);

        boolean keepPlaying = true;

        while (keepPlaying) {
            System.out.println("Host says: " + Phrases.getPlayingPhrase());

            System.out.print(player.getFirstName() + ", enter a letter: ");
            String letter = scanner.nextLine();

            try {
                Phrases.findLetters(letter);
            } catch (MultipleLettersException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a letter.");
            }

            System.out.println(player);
            keepPlaying = false;
        }
    }
}
