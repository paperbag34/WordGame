package Game;
import java.util.Scanner;

public class GamePlay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Do you want to enter a last name? (yes/no): ");
        String choice = scanner.nextLine();
        Person player;

        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();
            player = new Person(firstName, lastName);
        } else {
            player = new Person(firstName);
        }

        Numbers numberGame = new Numbers();
        numberGame.generateNumber();

        while (true) {
            System.out.print(player.getFirstName() + ", guess the number (0-100): ");
            int guess = scanner.nextInt();
            if (numberGame.compareNumber(guess)) {
                break;
            }
        }

        scanner.close();
    }
}