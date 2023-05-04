import org.junit.*;

import static org.junit.Assert.*;

public class testCases {

    private YahtzeeGame game;

    /**
     * -------------------------------------------------------------------
     * Setup/Takedown methods
     * -------------------------------------------------------------------
     */

    @Before
    public void setUp() throws Exception {
        game = new YahtzeeGame();
    }

    @After
    public void takeDown() throws Exception {
        game = null;
    }

    /**
     * -------------------------------------------------------------------
     * Logic and Graph Coverage Tests
     * -------------------------------------------------------------------
     */

    @Test
    public void testIsGameOver() throws Exception {
        // Graph Path 1
        assertEquals(game.isGameOver(), false);
        for (int i = 0; i < 6; i++) {
            game.setScoreAt(i, 0);
        }

        // Graph Path 2
        assertEquals(game.isGameOver(), false);
        for (int i = 6; i < 13; i++) {
            game.setScoreAt(i, 0);
        }

        // Graph Path 3
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

    @Test
    public void testScoreTOK() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(7), 0);
        game.setDice(new int[] {5, 5, 5, 6, 6});
        assertEquals(game.calculateScore(7), 27);
    }

    @Test
    public void testScoreFOK() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(8), 0);
        game.setDice(new int[] {5, 5, 5, 5, 6});
        assertEquals(game.calculateScore(8), 26);
    }

    @Test
    public void testIsFullHouse() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertFalse(game.isFullHouse());
        game.setDice(new int[] {2, 2, 3, 3, 3});
        assertTrue(game.isFullHouse());
    }

    @Test
    public void testFullHouse() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(9), 0);
        game.setDice(new int[] {2, 2, 3, 3, 3});
        assertEquals(game.calculateScore(9), 25);
    }

    @Test
    public void testIsSmallStaight() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertFalse(game.isSmallStraight());
        game.setDice(new int[] {1, 2, 3, 4, 3});
        assertTrue(game.isSmallStraight());
    }

    @Test
    public void testSmallStaight() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(10), 0);
        game.setDice(new int[] {1, 2, 3, 4, 3});
        assertEquals(game.calculateScore(10), 30);
    }

    @Test
    public void testIsLargeStaight() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertFalse(game.isLargeStraight());
        game.setDice(new int[] {1, 2, 3, 4, 5});
        assertTrue(game.isLargeStraight());
    }

    @Test
    public void testLargeStaight() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertEquals(game.calculateScore(11), 0);
        game.setDice(new int[] {1, 2, 3, 4, 5});
        assertEquals(game.calculateScore(11), 40);
    }

    @Test
    public void testIsYahtzee() {
        game.setDice(new int[] {4, 3, 2, 3, 4});
        assertFalse(game.isYahtzee());
        game.setDice(new int[] {1, 1, 1, 1, 1});
        assertTrue(game.isYahtzee());
    }

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
}
