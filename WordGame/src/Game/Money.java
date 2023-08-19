package Game;

public class Money implements Award {
    private int winAmount = 500;
    private int lossAmount = 200;

    @Override
    public int displayWinnings(Players player, boolean isCorrectGuess) {
        if (isCorrectGuess) {
            System.out.println(player.getFirstName() + " won money!");
            return winAmount;
        } else {
            System.out.println(player.getFirstName() + " lost money!");
            return -lossAmount;
        }
    }
}