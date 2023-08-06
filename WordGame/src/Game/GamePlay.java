package Game;

import java.util.Scanner;

public class GamePlay {
    public static void main(String[] args) {
    	
    	Numbers num = new Numbers();
    	
        Scanner scanner = new Scanner(System.in);

        Players[] currentPlayers = new Players[3];

        for (int i = 0; i < currentPlayers.length; i++) {
            System.out.print("Enter player " + (i + 1) + "'s name: ");
            String playerName = scanner.nextLine();
            currentPlayers[i] = new Players(playerName);
        }

        //get number for game.
        num.generateNumber();

        
        Hosts host = new Hosts("Game Host");
        Turn turn = new Turn();

        boolean keepPlaying = true;

        while (keepPlaying) {
            for (Players currentPlayer : currentPlayers) {
                turn.takeTurn(currentPlayer, host, new Money()); // Use Money class for award
                //System.out.println("Gameplay" + num.randomNum);
                System.out.print("Do you want to keep playing? (yes/no): ");
                String keepPlayingChoice = scanner.nextLine();
                if (keepPlayingChoice.equalsIgnoreCase("no")) {
                	host.randomizeNum();
                    keepPlaying = false;
                }
            }
        }
    }
}