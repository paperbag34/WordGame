package Game;

public class Players extends Person {
    private long money;

    public Players(String firstName) {
        super(firstName);
        this.money = 1000;
    }

    // Getter and setter for money
    public long getMoney() {
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
}
