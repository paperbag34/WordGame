package Game;

import java.util.Scanner;

public class GamePlay {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		//gather hosts name
		System.out.print("Enter the host's name: ");
		String hostName = scanner.nextLine();
		Hosts host = new Hosts(hostName);
		host.randomizeNum();

		//gather players name
		System.out.print("Enter your name: ");
		String playerName = scanner.nextLine();
		Players player = new Players(playerName);

		Turn turn = new Turn();

		boolean keepPlaying = true;

		//loops while player wants to keep playing.
		while (keepPlaying) {
			// Keep taking turns until the player guesses correctly
			turn.takeTurn(player, host);
			System.out.print("Do you want to keep playing? (yes/no): ");
			String keepPlayingChoice = scanner.nextLine();
			if (keepPlayingChoice.equalsIgnoreCase("no")) {
				host.randomizeNum(); // Change the random number for the next game
				keepPlaying = false;
			}

		}
	}
}
