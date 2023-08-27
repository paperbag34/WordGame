package Game;


import javax.sound.sampled.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUI {
	private JFrame frame;
    private JPanel panel;
    private JTextArea gameOutput;
    private JTextField phraseInput;
    private JTextField playerNameInput;
    private JButton guessButton;
    private Hosts host;
    private List<Players> players;
    private int currentPlayerIndex;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu aboutMenu;
    private JCheckBoxMenuItem saveMessagesCheckbox;
    private JTextArea messageArea;
    private JProgressBar loadingBar;
    
    public GUI() {
    	
    	// Initialize the players list
        players = new ArrayList<>();
        
    	frame = new JFrame("Word Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1000);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        gameOutput = new JTextArea(10, 50);
        gameOutput.setEditable(false);

        phraseInput = new JTextField(30);
        playerNameInput = new JTextField(20);
        guessButton = new JButton("Guess");
        
        // Initialize the loading bar
        loadingBar = new JProgressBar(0, 100); // Set the min and max values for the loading progress
        loadingBar.setStringPainted(true); // Display percentage text on the loading bar

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeTurn();
                
            }
        });

        JPanel inputPanel = new JPanel();
        JButton startGameButton = new JButton("Start Game");
        JButton guessButton = new JButton("Guess");
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Enter a phrase for the players to guess:"));
        inputPanel.add(phraseInput);
        inputPanel.add(new JLabel("Enter player names (comma-separated):"));
        inputPanel.add(playerNameInput);
        inputPanel.add(startGameButton);
        inputPanel.add(loadingBar);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(guessButton);

        

        panel.add(gameOutput, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(guessButton, BorderLayout.EAST);
        
        

        // Create menu bar
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // Create game menu and set mnemonic
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(gameMenu);

        // Create about menu and set mnemonic
        aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(aboutMenu);

        // Create menu items under the game menu
        JMenuItem addPlayerMenuItem = new JMenuItem("Add Player");
        JMenuItem addHostMenuItem = new JMenuItem("Add Host");
        gameMenu.add(addPlayerMenuItem);
        gameMenu.add(addHostMenuItem);

        // Create menu item under the about menu
        JMenuItem layoutMenuItem = new JMenuItem("Layout");
        aboutMenu.add(layoutMenuItem);

        // Create message area with scroll pane
        messageArea = new JTextArea(10, 50);
        messageArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        panel.add(messageScrollPane, BorderLayout.SOUTH);

        // Create save messages checkbox
        saveMessagesCheckbox = new JCheckBoxMenuItem("Save Messages");
        gameMenu.addSeparator();
        gameMenu.add(saveMessagesCheckbox);
    
     
        
        
        //Action listener for addplayer
        addPlayerMenuItem.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		addPlayer();
        	}
        });
        // ActionListener for "Add Host" menu item
        addHostMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHost();
            }
        });
     // ActionListener for "Start Game" button
        startGameButton.addActionListener(new ActionListener() {
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
        
       

        frame.add(panel);
        frame.setVisible(true);
        //playMusic();
        playMusicInBackground();
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
//            if (currentPlayerIndex == 0 && allPlayersHaveTakenTurns()) {
//                startNewRound();
//            } else {
//                updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
//            }
            return;
        }

        // Get letter input
        String letter = JOptionPane.showInputDialog("Host says: " + Phrases.getPlayingPhrase() + "\n" +
                currentPlayer.getFirstName() + ", enter a letter: ");

        if (letter != null && letter.length() == 1) {
            try {
                // Update the code to process the letter and check for a win
                Phrases.findLetters(letter); // Assuming this method updates the game state
                updateGameOutput(currentPlayer.toString());

                if (Phrases.winCondition) {
                    // Update the code for winning logic and starting new rounds
                    currentPlayer.setMoney(currentPlayer.getMoney() + 500);
                    updateGameOutput(currentPlayer.getFirstName() + " Won 500 dollars. Their Total is $" + currentPlayer.getMoney());
                    showImageDialog();
                    startNewRound();

                    if (allPlayersHaveTakenTurns()) {
                        startNewRound();
                    } else {
                        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                        updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
                    }
                } else {
                    currentPlayer.setHasTakenTurn(true);
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
                }
            } catch (MultipleLettersException e) {
                updateGameOutput(e.getMessage());
            } catch (Exception e) {
                updateGameOutput("Invalid input. Please enter a letter.");
            }
        } else {
            updateGameOutput("Invalid input. Please enter a single letter.");
        }
    }

    private void playMusic() {
        try {
            File audioFile = new File("C:\\Users\\Xavier\\Downloads\\(FREE) Joji x Rich Brian Type Beat 2286 (prod by Null)  Sad Lofi Type Beat 2020.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            clip.start();  // Start playing the audio
            
            // Keep the program running while the music plays
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            
            clip.stop();   // Stop the audio after it's done
            clip.close();  // Close the clip
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void playMusicInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                File audioFile = new File("C:\\Users\\Xavier\\Downloads\\(FREE) Joji x Rich Brian Type Beat 2286 (prod by Null)  Sad Lofi Type Beat 2020.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Start playing the audio
                clip.start();  

                // Keep the thread running while the music plays
                Thread.sleep(clip.getMicrosecondLength() / 1000);

                clip.stop();   // Stop the audio after it's done
                clip.close();  // Close the clip
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    private void addHost() {
        String hostName = JOptionPane.showInputDialog("Enter the host's name:");
        if (hostName != null && !hostName.trim().isEmpty()) {
            host = new Hosts(hostName);
            updateGameOutput("Host " + hostName + " added.");
        } else {
            updateGameOutput("Invalid host name.");
        }
    }
    
    private void addPlayer() {
        String playerName = JOptionPane.showInputDialog("Enter the player's name:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            players.add(new Players(playerName));
            updateGameOutput("Player " + playerName + " added.");
        } else {
            updateGameOutput("Invalid player name.");
        }
    }

    private void updateLoadingProgress(int progress) {
        loadingBar.setValue(progress);
    }
    
    private void startGame() {
        // Get the phrase from the user
        String phrase = phraseInput.getText();
        phraseInput.setText(""); // Clear the input box

        // Set the game phrase using the enterPhrase method in the Hosts class
        host = new Hosts("Game Host");
        host.enterPhrase(phrase);

        // Create a list of Players
        players = new ArrayList<>();
        String[] playerNames = playerNameInput.getText().split("\\s*,\\s*");
        for (String playerName : playerNames) {
            players.add(new Players(playerName));
        }

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                int totalSteps = 100;
                for (int step = 0; step <= totalSteps; step++) {
                    publish(step); // Publish the progress
                    Thread.sleep(50); // Simulate loading time
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                updateLoadingProgress(progress);
                frame.repaint();
            }

            @Override
            protected void done() {
                // Initialize the game
                currentPlayerIndex = 0;
                guessButton.setEnabled(true);
                updateGameOutput("Game started. It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
            }
        };

        worker.execute(); // Start the SwingWorker

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Update UI components here
                updateGameOutput("UI updated"); // For debugging purposes
                panel.revalidate();
                panel.repaint();
            }
        });
    }
    
    
    private boolean askForPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }

    //C:\\Users\\Xavier\\Desktop\\500dollars.jpg
    
    private void showImageDialog() {
        JDialog dialog = new JDialog(frame, "Congratulations!", true);
        dialog.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Xavier\\Desktop\\500dollars.jpg"); // Replace with the actual path to your image file
        JLabel label = new JLabel(imageIcon);
        dialog.add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    private void startNewRound() {
        
    	 updateGameOutput("All players have taken their turns. Starting a new round.");
         String newPhrase = JOptionPane.showInputDialog("Enter a new phrase for the players to guess:");
         host.enterPhrase(newPhrase);
         for (Players player : players) {
             player.resetTurn(); // Reset the turn state for each player
         }
         currentPlayerIndex = 0; // Reset the current player index
         Phrases.winCondition = false; // Reset the win condition
         updateGameOutput("It's " + players.get(currentPlayerIndex).getFirstName() + "'s turn.");
         
    	
        boolean playAgain = askForPlayAgain();
        if (playAgain) {
            // Start a new game
            resetGame();
            startGame();
        } else {
            // Exit the application
            System.exit(0);
        }
    }
    
    private void resetGame() {
    	currentPlayerIndex = 0;
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
