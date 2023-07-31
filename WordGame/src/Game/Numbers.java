package Game;

import java.util.Random;
public class Numbers {
    private int randomNum;

    // Public getter and setter for randomNum field
    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }

    //generate a number 0-100. set it to randomNum.
    public void generateNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(101); 
        this.randomNum = randomNumber;
    }

    // Method to compare the guess with randomNum
    public boolean compareNumber(int guess) {
        if (guess == randomNum) {
            System.out.println("Congratulations, you guessed the number!");
            return true;
        } else if (guess > randomNum) {
            System.out.println("I'm sorry. That guess was too high.");
            return false;
        } else {
            System.out.println("I'm sorry, That guess was too low.");
            return false;
        }
    }
}