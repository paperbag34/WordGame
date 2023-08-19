package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JTextArea gameOutput;
    private JTextField phraseInput;
    private JTextField playerNameInput;
    private JButton startButton;
    private JButton guessButton;
    private Hosts host;
    private List<Players> players;
    private int currentPlayerIndex;

    public GUI() {
        frame = new JFrame("Word Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        gameOutput = new JTextArea(10, 50);
        gameOutput.setEditable(false);

        phraseInput = new JTextField(30);
        playerNameInput = new JTextField(20);
        startButton = new JButton("Start Game");
        guessButton = new JButton("Guess");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeTurn();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Enter a phrase for the players to guess:"));
        inputPanel.add(phraseInput);
        inputPanel.add(new JLabel("Enter player names (comma-separated):"));
        inputPanel.add(playerNameInput);

        panel.add(gameOutput, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(startButton, BorderLayout.WEST);
        panel.add(guessButton, BorderLayout.EAST);

        // Disable the "Guess" button initially
        guessButton.setEnabled(false);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void startGame() {
        // Get the phrase from the user
        String phrase = phraseInput.getText();

        // Set the game phrase using the enterPhrase method in the Hosts class
        host = new Hosts("Game Host");
        host.enterPhrase(phrase);

        // Create a list of Players
        players = new ArrayList<>();
        String[] playerNames = playerNameInput.getText().split("\\s*,\\s*");
        for (String playerName : playerNames) {
            players.add(new Players(playerName));
        }

        // Initialize the game
        currentPlayerIndex = 0;
        guessButton.setEnabled(true);
        updateGameOutput("Game started. It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
    }
    
    private boolean allPlayersHaveTakenTurns() {
        for (Players player : players) {
            if (!player.hasTakenTurn()) {
                return false;
            }
        }
        return true;
    }

    private void takeTurn() {
        Players currentPlayer = players.get(currentPlayerIndex);

        // Check if the current player has already taken their turn in this round
        if (currentPlayer.hasTakenTurn()) {
            // Move to the next player's turn
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            // If all players have taken their turns in this round, start a new round
            if (currentPlayerIndex == 0 && allPlayersHaveTakenTurns()) {
                startNewRound();
            } else {
                updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
            }
            return;
        }

        // Get letter input
        String letter = JOptionPane.showInputDialog("Host says: " + Phrases.getPlayingPhrase() + "\n" +
                currentPlayer.getFirstName() + ", enter a letter: ");

        if (letter != null && letter.length() == 1) {
            try {
                Phrases.findLetters(letter);
            } catch (MultipleLettersException e) {
                updateGameOutput(e.getMessage());
            } catch (Exception e) {
                updateGameOutput("Invalid input. Please enter a letter.");
            }

            // Display player info
            updateGameOutput(currentPlayer.toString());

            // Check for win condition
            if (Phrases.winCondition) {
                currentPlayer.setMoney(currentPlayer.getMoney() + 500);
                updateGameOutput(currentPlayer.getFirstName() + " Won 500 dollars. Their Total is $" + currentPlayer.getMoney());
                
                // Check if all players have taken their turns in this round
                if (allPlayersHaveTakenTurns()) {
                    // Start a new round
                    startNewRound();
                } else {
                    // Move to the next player's turn
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
                }
            } else {
                // Mark the current player as having taken their turn
                currentPlayer.setHasTakenTurn(true);

                // Move to the next player's turn
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
            }
        } else {
            updateGameOutput("Invalid input. Please enter a single letter.");
        }
    }



    private void startNewRound() {
        // Start a new round with a new phrase
        updateGameOutput("All players have taken their turns. Starting a new round.");
        String newPhrase = JOptionPane.showInputDialog("Enter a new phrase for the players to guess:");
        host.enterPhrase(newPhrase);
        for (Players player : players) {
            player.resetTurn(); // Reset the turn state for each player
        }
        currentPlayerIndex = 0; // Reset the current player index
        Phrases.winCondition = false; // Reset the win condition
        updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
    }

    private void updateGameOutput(String message) {
        gameOutput.append(message + "\n");
        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
