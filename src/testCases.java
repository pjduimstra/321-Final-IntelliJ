import org.junit.*;

import static org.junit.Assert.assertEquals;

public class testCases {

    private YahtzeeGame game;

    // -------------------------------------------------------------------
    // Setup/Takedown methods
    // -------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        game = new YahtzeeGame();
    }

    @After
    public void takeDown() throws Exception {
        game = null;
    }


    // -------------------------------------------------------------------
    // Logic  Coverage Tests
    // -------------------------------------------------------------------

    @Test
    public void testIsGameOver() throws Exception {
        assertEquals(game.isGameOver(), false);
        for (int i = 0; i < 13; i++) {
            game.setScoreAt(i, 0);
        }
        assertEquals(game.isGameOver(), true);
    }

    @Test
    public void testScoreOnes() {
        game.setDice(new int[] {4, 2, 2, 4, 4});
        assertEquals(game.calculateScore(1), 0);
        game.setDice(new int[] {1, 2, 2, 1, 1});
        assertEquals(game.calculateScore(1), 3);
    }

    @Test
    public void testScoreTwos() {
        game.setDice(new int[] {1, 4, 3, 1, 1});
        assertEquals(game.calculateScore(2), 0);
        game.setDice(new int[] {1, 2, 2, 1, 1});
        assertEquals(game.calculateScore(2), 4);
    }

    @Test
    public void testScoreThrees() {
        game.setDice(new int[] {1, 2, 2, 1, 1});
        assertEquals(game.calculateScore(3), 0);
        game.setDice(new int[] {1, 3, 2, 3, 1});
        assertEquals(game.calculateScore(3), 6);
    }

    @Test
    public void testScoreFours() {
        game.setDice(new int[] {1, 3, 2, 3, 1});
        assertEquals(game.calculateScore(4), 0);
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(4), 8);
    }

    @Test
    public void testScoreFives() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(5), 0);
        game.setDice(new int[] {1, 5, 2, 5, 1});
        assertEquals(game.calculateScore(5), 10);
    }

    @Test
    public void testScoreSixes() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(6), 0);
        game.setDice(new int[] {1, 6, 2, 6, 6});
        assertEquals(game.calculateScore(6), 18);
    }

    /**
     * Tests not only the scoring method for three of a kind, but also
     * the helper method to determine if the counts of dice indicate
     * a three of a kind is present.
     */

    @Test
    public void testScoreTOK() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(7), 0);
        game.setDice(new int[] {5, 5, 5, 6, 6});
        assertEquals(game.calculateScore(7), 27);
    }

    /**
     * Tests not only the scoring method for four of a kind, but also
     * the helper method to determine if the counts of dice indicate
     * a four of a kind is present.
     */

    @Test
    public void testScoreFOK() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(8), 0);
        game.setDice(new int[] {5, 5, 5, 5, 6});
        assertEquals(game.calculateScore(8), 26);
    }

    /**
     * Tests not only the scoring method for Full House, but also
     * the helper method to determine if the counts of dice indicate
     * a full house is present.
     */
    @Test
    public void testFullHouse() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(9), 0);
        game.setDice(new int[] {2, 2, 3, 3, 3});
        assertEquals(game.calculateScore(9), 25);
    }

    /**
     * Tests not only the scoring method for small straight, but also
     * the helper method to determine if the counts of dice indicate
     * a small straight is present.
     */
    @Test
    public void testSmallStaight() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(10), 0);
        game.setDice(new int[] {1, 2, 3, 4, 3});
        assertEquals(game.calculateScore(10), 30);
    }

    /**
     * Tests not only the scoring method for large straight, but also
     * the helper method to determine if the counts of dice indicate
     * a large straight is present.
     */
    @Test
    public void testLargeStaight() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(11), 0);
        game.setDice(new int[] {1, 2, 3, 4, 5});
        assertEquals(game.calculateScore(11), 40);
    }

    /**
     * Tests not only the scoring method for yahtzee, but also
     * the helper method to determine if the counts of dice indicate
     * a yahtzee is present.
     */
    @Test
    public void testYahtzee() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(12), 0);
        game.setDice(new int[] {1, 1, 1, 1, 1});
        assertEquals(game.calculateScore(12), 50);
    }

    @Test
    public void testChance() {
        game.setDice(new int[] {1, 2, 3, 4, 3});
        assertEquals(game.calculateScore(13), 13);
    }

    // -------------------------------------------------------------------
    // Graph Coverage Tests
    // -------------------------------------------------------------------

}
