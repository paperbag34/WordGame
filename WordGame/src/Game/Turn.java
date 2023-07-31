package Game;

import java.util.Scanner;

public class Turn {
	private static int winAmount = 500; // Chosen amount for correct guesses
	private static int lossAmount = 200; // Chosen amount for incorrect guesses
	

	public void takeTurn(Players player, Hosts host) {
        Numbers numbers = new Numbers();
        numbers.generateNumber();
        boolean keepPlaying = true;

        Scanner scanner = new Scanner(System.in);
        
        while(keepPlaying) {
        System.out.print(host.getFirstName() + " says: " + player.getFirstName() + ", guess the number (0-100): ");
        
        //This is for testing
        //System.out.println("The correct number is " + numbers.randomNum);
        
        int guess = scanner.nextInt();
        
        	if (numbers.compareNumber(guess)) {
        		player.setMoney(player.getMoney() + winAmount);
        		System.out.println("Congratulations, " + player.getFirstName() + " guessed the number!");
        		System.out.println(player);
        		keepPlaying = false;
		
        	} else {
        		player.setMoney(player.getMoney() - lossAmount);
        		System.out.println(player);
        	}	
        }
   	}
}

//Numbers numberGame = new Numbers();
//numberGame.generateNumber();
//
//while (true) {
//    System.out.print(player.getFirstName() + ", guess the number (0-100): ");
//    int guess = scanner.nextInt();
//    if (numberGame.compareNumber(guess)) {
//        break;
//    }
//}