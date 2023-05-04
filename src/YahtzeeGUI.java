import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * @author PJ Duimstra
 *         Copyright 2023 PJ Duimstra
 *         CSE 321 Final Project
 *         Dr. Goodman
 *
 *         GUI coded as part of the final project for
 *         CSE 321.
 */

public class YahtzeeGUI {
    public static YahtzeeGUI ui;
    public int clicks = 0;
    private JFrame frame;
    private YahtzeeGame game;
    private JLabel[] diceLabels;
    private JLabel[] scoreLabels;
    private JButton rollButton;

    // ArrayList used for 'locking' dice while round is in play
    private ArrayList selectedDice = new ArrayList<>();

    // ArrayList used to select the score category prior to scoring the round
    private ArrayList selectedCategory = new ArrayList<>();

    // ArrayList to track the scored categories
    private ArrayList scored = new ArrayList<>();

    /**
     * Constructor for the game GUI
     */
    public YahtzeeGUI() {
        // Create the game and initialize the frame and components
        game = new YahtzeeGame();
        // Add title
        frame = new JFrame("Yahtzee");
        // Update close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set frame size
        frame.setSize(800, 450);
        frame.setLayout(new BorderLayout());

        // Rules panel
        JPanel rules = new JPanel();
        rules.setLayout(new GridLayout(1, 2));
        JLabel rule = new JLabel("Click on the dice to save their value", JLabel.CENTER);
        JLabel filler = new JLabel("Blue categories have been scored", JLabel.CENTER);
        rules.add(rule);
        rules.add(filler);
        rules.setBorder(new EmptyBorder(25, 15, 0, 15));
        frame.add(rules, BorderLayout.NORTH);

        // Dice panel
        JPanel dicePanel = new JPanel();
        dicePanel.setLayout(new GridLayout(1, 5));
        diceLabels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            // Initialize with emtpy.png so that the panel is sized correctly but dice are
            // not visible.
            diceLabels[i] = new JLabel(new ImageIcon("dice/empty.png"), JLabel.CENTER);
            diceLabels[i].setOpaque(true);
            diceLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    if (selectedDice.contains(label)) {
                        label.setBackground(null); // Reset the backgroun to nothing
                        selectedDice.remove(label);
                    } else {
                        label.setBackground(Color.green); // Set the background to green to show it is selected.
                        selectedDice.add(label);
                    }
                }
            });
            dicePanel.add(diceLabels[i]);
        }
        frame.add(dicePanel, BorderLayout.WEST);

        // Score panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(14, 2));
        scoreLabels = new JLabel[14];
        for (int i = 0; i < 14; i++) {
            scoreLabels[i] = new JLabel("0");
            JLabel currCategory = new JLabel(game.getCategory(i));
            currCategory.setOpaque(true);
            if (i < 13) {
                currCategory.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel) e.getSource();
                        if (selectedCategory.contains(label)) {
                            label.setBackground(null);
                            selectedCategory.clear();
                        } else {
                            if (selectedCategory.size() > 0) {
                                JLabel curr = (JLabel) selectedCategory.get(0);
                                curr.setBackground(null);
                                selectedCategory.clear();
                            }
                            label.setBackground(Color.green);
                            selectedCategory.add(label);
                            if (game.getRollsLeft() == 0) {
                                JLabel curr = (JLabel) selectedCategory.get(0);
                                if (game.returnCategoryIndex(curr.getText()) != 11
                                        && scored.contains(scoreLabels[game.returnCategoryIndex(curr.getText())])) {
                                    rollButton.setText("Please select an empty category");
                                    rollButton.setBackground(Color.orange);
                                    curr.setBackground(null);
                                    selectedCategory.clear();
                                } else if (scored.contains(scoreLabels[game.returnCategoryIndex(curr.getText())])) {
                                    rollButton.setText("Please select a empty category");
                                    rollButton.setBackground(Color.orange);
                                    curr.setBackground(null);
                                    selectedCategory.clear();
                                } else {
                                    rollButton.setText("Score and Reset Dice");
                                }
                            }
                        }
                    }
                });
            }
            scorePanel.add(currCategory);
            scorePanel.add(scoreLabels[i]);
        }
        scorePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        scorePanel.setMinimumSize(new Dimension(100, -1));
        frame.add(scorePanel, BorderLayout.CENTER);

        // Roll button
        rollButton = new JButton("Roll");
        rollButton.setOpaque(true);
        rollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!game.isGameOver()) {
                    rollDice();
                } else {
                    if (clicks == 0) {
                        addBonus();
                        clicks++;
                        rollButton.setText("Game Over! Please click again to start a new game");
                    } else {
                        resetGame(false);
                    }
                }
            }
        });
        frame.add(rollButton, BorderLayout.SOUTH);
    }

    /**
     * Function used to roll dice and control scoring in the game.
     */

    public void rollDice() {
        // Check if game is over
        if (!game.isGameOver()) {
            // Checks if there are rolls available
            if (game.getRollsLeft() > 0) {
                // There are rolls available, so the dice are rolled.
                int[] dice = game.getDice();
                game.useRoll();

                // Checks if there are dice selected, and if so, skips rolling them
                for (int i = 0; i < 5; i++) {
                    if (!selectedDice.contains(diceLabels[i])) {
                        game.rollDie(i);
                        diceLabels[i].setIcon(new ImageIcon("dice/dice" + dice[i] + ".png"));
                    }
                }
                // Updates text on roll button
                if (game.getRollsLeft() == 0) {
                    rollButton.setText("Click to Select Score Category");
                } else {
                    rollButton.setText("Roll (" + game.getRollsLeft() + ")");
                }
            } else {
                if (selectedCategory.size() > 0) {
                    // There are no rolls available, so calculate round score

                    // Reset roll button background
                    rollButton.setBackground(null);
                    // Get current dice from board
                    game.getDice();
                    // Get score label selection
                    JLabel selection = (JLabel) selectedCategory.get(0);
                    // Reset the score label after necessary information is grabbed.
                    selection.setBackground(null);
                    selectedCategory.clear();

                    int categoryIndex = game.returnCategoryIndex(selection.getText());
                    if (categoryIndex == 11) {
                        scoreLabels[categoryIndex]
                                .setText(Integer.toString(Integer.parseInt(scoreLabels[categoryIndex].getText())
                                        + game.calculateScore(categoryIndex + 1)));
                        game.setScoreAt(categoryIndex,
                                game.getScoreAt(categoryIndex) + game.calculateScore(categoryIndex + 1));
                    } else {
                        scoreLabels[categoryIndex].setText(Integer.toString(game.calculateScore(categoryIndex + 1)));
                        game.setScoreAt(categoryIndex, game.calculateScore(categoryIndex + 1));
                    }

                    // Update total score label and instance variable
                    scoreLabels[13].setText(Integer.toString(
                            Integer.parseInt(scoreLabels[13].getText()) + game.calculateScore(categoryIndex + 1)));
                    game.addTotalScore(game.calculateScore(categoryIndex + 1));

                    // Add score to scored to prevent scoring twice
                    if (categoryIndex != 11) {
                        scored.add(scoreLabels[categoryIndex]);
                    }
                    game.setScoreAt(categoryIndex, game.calculateScore(categoryIndex + 1));
                    selection.setBackground(Color.blue);
                    selection.setForeground(Color.white);

                    // Reset dice backgrounds and values to "clear" game board
                    for (int i = 0; i < diceLabels.length; i++) {
                        diceLabels[i].setBackground(null);
                    }
                    hideDice();

                    // Reset the selectedDice ArrayList since the round is over
                    selectedDice.clear();

                    // Reset the rolls left for the new round
                    game.setRollsLeft(3);

                    // Update the roll button text
                    rollButton.setText("Roll (" + game.getRollsLeft() + ")");
                } else {
                    // Highlight roll button with score instructions
                    rollButton.setBackground(Color.orange);
                }
            }

        } else {
            // Game is over, calculate bonuses and reset the tables when user clicks the
            // roll button again.
            rollButton.setText("GAME OVER! Click to reset");
        }
    }

    public void addBonus() {
        int topSection = 0;
        int numBonusYahtzee = (game.getScoreAt(11) % 5) - 1;

        for (int i = 0; i < 6; i++) {
            topSection += game.getScoreAt(i);
        }

        if (topSection >= 63) {
            game.addTotalScore(35);
        }

        if (numBonusYahtzee > 0) {
            game.addTotalScore(numBonusYahtzee * 100);
        }
        scoreLabels[13].setText(Integer.toString(game.getTotalScore()));
    }

    /**
     * Function used to "hide" the dice.
     * Sets the ImageIcon to an empty PNG file
     */
    public void hideDice() {
        for (int i = 0; i < 5; i++) {
            diceLabels[i].setIcon(new ImageIcon("dice/empty.png"));
        }
    }

    /**
     * Function to return the currently stored value in the scoresheet
     *
     * @param i the index of the score value being queried
     * @return the value held in scoreLabels[i]
     */
    public int getScoreLabelValue(int i) {
        return Integer.parseInt(scoreLabels[i].getText());
    }

    /**
     * Function to "start" the Yahtzee GUI
     * Sets the frame visibility to true
     */
    public void start() {
        // Show the frame and start the game
        frame.setVisible(true);
    }

    public void end() {
        frame.setVisible(false);
    }

    public static void resetGame(boolean start) {
        if (!start) { ui.end(); }
        ui = new YahtzeeGUI();
        ui.start();
        ui.hideDice();
    }

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        // Create the UI and start the game
        resetGame(true);
    }
}
