package Game;

public class Players extends Person {
    private int money;

    public Players(String firstName) {
        super(firstName);
        this.money = 1000;
    }

    // Getter and setter for money
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // Override toString for name and money
    @Override
    public String toString() {
        return getFirstName() + " - Money: $" + money;
    }
}