package Game;

import java.util.Random;

public class Physical implements Award {
    private String[] physicalPrizes = {
        "Television",
        "Vacation Package",
        "Gaming Console",
        "Smartphone",
        "Gift Voucher"
    };

    public int getRandomPrize() {
        Random random = new Random();
        return random.nextInt(physicalPrizes.length);
    }

    @Override
    public int displayWinnings(Players player, boolean isCorrectGuess) {
        int prizeIndex = getRandomPrize();
        String prize = physicalPrizes[prizeIndex];

        if (isCorrectGuess) {
            System.out.println(player.getFirstName() + " won a " + prize + "!");
        } else {
            System.out.println(player.getFirstName() + " could have won a " + prize + "!");
        }

        return 0;
    }
}