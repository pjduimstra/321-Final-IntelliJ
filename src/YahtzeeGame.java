import java.util.*;

/**
 * @author PJ Duimstra
 * Copyright 2023 PJ Duimstra
 * CSE 321 Final Project
 * Dr. Goodman
 * Logical functions associated with the YahtzeeGame GUI coded for the final project of CSE 321.
 */

public class YahtzeeGame {

    // Define the constants for the game
    private static final int NUM_DICE = 5;
    private static final int MAX_ROLLS = 3;
    private static final int NUM_CATEGORIES = 13;

    // Define the categories for scoring
    private static final int ONES = 1;
    private static final int TWOS = 2;
    private static final int THREES = 3;
    private static final int FOURS = 4;
    private static final int FIVES = 5;
    private static final int SIXES = 6;
    private static final int THREE_OF_KIND = 7;
    private static final int FOUR_OF_KIND = 8;
    private static final int FULL_HOUSE = 9;
    private static final int SMALL_STRAIGHT = 10;
    private static final int LARGE_STRAIGHT = 11;
    private static final int YAHTZEE = 12;
    private static final int CHANCE = 13;
    private static final String[] CATEGORIES = {
            "Ones", "Twos", "Threes", "Fours", "Fives", "Sixes",
            "Three of a Kind", "Four of a Kind", "Full House",
            "Small Straight", "Large Straight", "Yahtzee", "Chance", "Total Score"
    };

    // Define the instance variables
    private int[] dice;
    private int rollsLeft;
    private int[] scoreSheet = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private int totalScore = 0;

    /**
     * Constructor class for the YahtzeeGame object
     */
    public YahtzeeGame() {
        // Initialize the game
        dice = new int[NUM_DICE];
        rollsLeft = MAX_ROLLS;
    }

    /**
     * Function used to roll the dice for the game
     *
     * @param i the index to be rolled
     */
    public void rollDie(int i) {
        // Roll the die for a selected index i
        dice[i] = (int) (Math.random() * 6 + 1);
        // dice[i] = 4;
    }

    /**
     * Function to check if the game is complete (ie all scoreboard items have
     * values stored)
     *
     * @return boolean value indicating if the game is over
     */
    public boolean isGameOver() {
        // Check if the game is over
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            if (scoreSheet[i] == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Function to give a score based on a preset category
     *
     * @param category the category that the dice fall into at the current moment
     * @return the score of the current dice
     */
    public int calculateScore(int category) {
        // Calculate the score for a given category
        int score = 0;
        int[] counts = getCounts();

        switch (category) {
            case ONES:
            case TWOS:
            case THREES:
            case FOURS:
            case FIVES:
            case SIXES:
                for (int i = 0; i < NUM_DICE; i++) {
                    if (dice[i] == category) {
                        score += category;
                    }
                }
                break;
            case THREE_OF_KIND:
                for (int i = 1; i <= 6; i++) {
                    if (counts[i] > 2) {
                        for (int j = 0; j < NUM_DICE; j++) {
                            score += dice[j];
                        }
                    }
                }
                break;
            case FOUR_OF_KIND:
                for (int i = 1; i <= 6; i++) {
                    if (counts[i] > 3) {
                        for (int j = 0; j < NUM_DICE; j++) {
                            score += dice[j];
                        }
                    }
                }
                break;
            case CHANCE:
                for (int i = 0; i < NUM_DICE; i++) {
                    score += dice[i];
                }
                break;
            case FULL_HOUSE:
                if (isFullHouse()) {
                    score = 25;
                }
                break;
            case SMALL_STRAIGHT:
                if (isSmallStraight()) {
                    score = 30;
                }
                break;
            case LARGE_STRAIGHT:
                if (isLargeStraight()) {
                    score = 40;
                }
                break;
            case YAHTZEE:
                if (isYahtzee()) {
                    score = 50;
                }
                break;
        }
        return score;
    }

    /**
     * Checks to see if the current dice are in the full house category
     *
     * @return the boolean value indicating whether the dice are in the full house
     *         category
     */
    public boolean isFullHouse() {
        // Check if the dice have a full house
        int[] counts = getCounts();
        boolean twoCount = false;
        boolean threeCount = false;
        for (int count : counts) {
            if (count == 2) {
                twoCount = true;
            }
            if (count == 3)
                threeCount = true;
        }
        return twoCount && threeCount;
    }

    /**
     * Function used to check if the current dice have a small straight present
     *
     * @return the boolean value indicating if there is a small straight
     */
    public boolean isSmallStraight() {
        // Check if the dice have a small straight
        int[] counts = getCounts();
        return (counts[1] >= 1 && counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1)
                || (counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1 && counts[5] >= 1)
                || (counts[3] >= 1 && counts[4] >= 1 && counts[5] >= 1 && counts[6] >= 1);
    }

    /**
     * Function used to check if the current dice have a large straight present
     *
     * @return the boolean value indicating if there is a large straight
     */
    public boolean isLargeStraight() {
        // Check if the dice have a large straight
        int[] counts = getCounts();
        return (counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1)
                || (counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1 && counts[6] == 1);
    }

    /**
     * Function used to check if the current dice have a yahtzee present
     *
     * @return the boolean value indicating if there is a yahtzee
     */
    public boolean isYahtzee() {
        // Check if the dice have a Yahtzee
        int[] counts = getCounts();
        for (int count : counts) {
            if (count == 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function used to keep track of the number of repeated values in a given roll
     *
     * @return an integer array showing the number of each value of dice
     */
    private int[] getCounts() {
        // Get the counts of each dice value
        int[] counts = new int[7];
        for (int i = 0; i < NUM_DICE; i++) {
            counts[dice[i]]++;
        }
        return counts;
    }

    /**
     * Function to return the number of rolls left so that it can be accessed by the
     * GUI class
     *
     * @return the number of rolls left
     */
    public int getRollsLeft() {
        return rollsLeft;
    }

    /**
     * Function to fetch the current values of the dice
     *
     * @return an integer array with the values of the current roll
     */
    public int[] getDice() {
        // Get the current dice values
        return dice;
    }

    /**
     * Function to fetch the current scoresheet for the YahtzeeGame
     *
     * @return the integer array for the current scores of the game
     */
    public int[] getScoreSheet() {
        // Get the current scoresheet
        return scoreSheet;
    }

    /**
     * Helper method to return the number of categories so that they can be accessed
     * by the GUI class
     *
     * @return the number of categories in the score sheet
     */
    public int getNumCategories() {
        return NUM_CATEGORIES;
    }

    /**
     * Helper method to return the number of dice being used so that it can be
     * access by the GUI class
     *
     * @return the number of dice
     */
    public int getNumDice() {
        return NUM_DICE;
    }

    /**
     * Helper method to access a given category in the categories array so that it
     * can be access by the GUI class
     *
     * @param i the index to be accessed
     * @return the string value of index i of the array
     */
    public String getCategory(int i) {
        return CATEGORIES[i];
    }

    /**
     * Function to subtract a roll from the game instance after being used via the
     * GUI
     */
    public void useRoll() {
        rollsLeft--;
    }

    /**
     * Function used to reset the number of rolls left following the completion of a
     * round
     *
     * @param rolls the number of rolls to be reset to.
     */
    public void setRollsLeft(int rolls) {
        rollsLeft = rolls;
    }

    /**
     * Function to return an index given a category string
     * @param input the string to be searched for in CATEGORIES
     * @return the index of input in CATEGORIES
     */
    public int returnCategoryIndex(String input) {
        List<String> stringList = new ArrayList<String>(Arrays.asList(CATEGORIES));
        return stringList.indexOf(input);
    }

    /**
     * Function to set the score at a given index in the scoresheet
     * @param i the index to be updated
     * @param num the number to be placed at index i
     * @throws Exception if the index provided is invalid
     */
    public void setScoreAt(int i, int num) throws Exception {
        if (i < 0 || i > scoreSheet.length - 1) throw new Exception("Invalid index provided: " + i);
        else scoreSheet[i] = num;
    }

    /**
     * Function to find and return the score at a given index in the scoresheet
     * @param i the index to be retrieved
     * @return the score at index i
     * @throws Exception if the index provided is invalid
     */
    public int getScoreAt(int i) throws Exception {
        if (i < 0 || i > scoreSheet.length - 1) throw new Exception("Invalid index provided: " + i);
        else return scoreSheet[i];
    }

    /**
     * Helper method to update the total score
     * @param num the amount to be added to the total score
     */
    public void addTotalScore(int num) {
        totalScore += num;
    }

    /**
     * Helper method to return the current total score
     * @return the current total score of the Yahtzee game
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Testing function to set dice to predetermined values
     * @param newDice the new array to be used.
     */
    public void setDice(int[] newDice) {
        for (int i = 0; i < 5; i++) {
            dice[i] = newDice[i];
        }
    }
}