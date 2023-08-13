package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Players extends Person {
    private static long money;
    private boolean hasTakenTurn;

    public Players(String firstName) {
        super(firstName);
        this.money = 1000;
        this.hasTakenTurn = false;
    }

    // Getter and setter for money
    public static long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
    
    // Override toString for name and money
    @Override
    public String toString() {
        return getFirstName() + " - Money: $" + money;
    }

    public boolean hasTakenTurn() {
        return hasTakenTurn;
    }

    public void setHasTakenTurn(boolean hasTakenTurn) {
        this.hasTakenTurn = hasTakenTurn;
    }

    public void resetTurn() {
        // Reset any turn-specific state for the player
        this.hasTakenTurn = false;
    }
}
