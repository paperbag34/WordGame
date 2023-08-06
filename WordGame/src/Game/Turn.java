package Game;

import java.util.Random;
import java.util.Scanner;

public class Turn {
    public void takeTurn(Players player, Hosts host, Award award) {
        Numbers numbers = new Numbers();
        boolean keepPlaying = true;

        Scanner scanner = new Scanner(System.in);

        while (keepPlaying) {
        	//System.out.println("Turn" + numbers.randomNum);
            System.out.print(host.getFirstName() + " says: " + player.getFirstName() + ", guess the number (0-100): ");

            int guess = scanner.nextInt();
            boolean isCorrectGuess = numbers.compareNumber(guess);

            // Decide whether the player wins money or a physical prize
            if (new Random().nextBoolean()) {
                // Player wins money
                Money moneyAward = new Money();
                long winnings = moneyAward.displayWinnings(player, isCorrectGuess);
                player.setMoney(player.getMoney() + winnings);
                numbers.generateNumber();
            } else {
                // Player wins a physical prize
                Physical physicalAward = new Physical();
                physicalAward.displayWinnings(player, isCorrectGuess);
                numbers.generateNumber();
            }

            System.out.println(player);
            keepPlaying = false; // For this example, we stop after one turn, you can change this
        }
    }
}