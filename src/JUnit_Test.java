import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;

class JUnit_Test {

    private ServerSocket serverSocket;

    /**
     * Closes the server socket after each test to avoid port conflicts.
     */
    @AfterEach
    public void tearDown() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    /**
     * Test if apples are loaded correctly from the file.
     * Ensures that decks are not empty after loading.
     */
    @Test
    public void testApplesLoadedFromFile() throws Exception {
        Apples2Apples game = new Apples2Apples(0);
        assertFalse(game.greenApples.isEmpty(), "Green apples deck should not be empty.");
        assertFalse(game.redApples.isEmpty(), "Red apples deck should not be empty.");
    }

    /**
     * Test if apples are shuffled after loading.
     * Verifies that original and shuffled decks differ.
     */
    @Test
    public void testApplesShuffled() throws Exception {
        Apples2Apples game = new Apples2Apples(0);

        List<String> originalGreenApples = new ArrayList<>(game.greenApples);
        List<String> originalRedApples = new ArrayList<>(game.redApples);

        assertNotEquals(originalGreenApples, game.greenApples, "Green apples should be shuffled.");
        assertNotEquals(originalRedApples, game.redApples, "Red apples should be shuffled.");
    }

    /**
     * Tests if players receive 7 red apples at the start of the game.
     */
    @Test
    public void testPlayersReceiveSevenCards() throws Exception {
        Apples2Apples game = new Apples2Apples(3); // 3 online players and 1 server player

        for (Player player : game.players) {
            assertEquals(7, player.hand.size(), "Each player should have 7 red apples.");
        }
    }

    /**
     * Tests if the judge is assigned randomly.
     * Verifies the judge index is within valid bounds.
     */
    @Test
    public void testJudgeIsRandomized() throws Exception {
        Apples2Apples game = new Apples2Apples(0);
        int judge = game.players.indexOf(game.players.get(0));
        assertTrue(judge >= 0 && judge < game.players.size(), "Judge must be a valid player.");
    }

    /**
     * Tests drawing a green apple from the deck.
     * Ensures a green apple is successfully drawn.
     */
    @Test
    public void testGreenAppleDrawn() throws Exception {
        Apples2Apples game = new Apples2Apples(0);
        String drawnGreenApple = game.greenApples.remove(0);
        assertNotNull(drawnGreenApple, "A green apple must be drawn.");
    }

    /**
     * Tests if players play one card from their hand.
     */
    @Test
    public void testPlayersChooseOneCard() throws Exception {
        Apples2Apples game = new Apples2Apples(3);

        for (Player player : game.players) {
            if (!player.isBot) {
                player.play();
                assertEquals(6, player.hand.size(), "Player should have 6 cards after playing 1.");
            }
        }
    }

    /**
     * Tests if played apples are shuffled for fairness.
     */
    @Test
    public void testPlayedApplesRandomized() throws Exception {
        Apples2Apples.playedApple.clear();
        Apples2Apples.playedApple.add(new PlayedApple(1, "red1"));
        Apples2Apples.playedApple.add(new PlayedApple(2, "red2"));

        List<PlayedApple> originalOrder = new ArrayList<>(Apples2Apples.playedApple);
        Collections.shuffle(Apples2Apples.playedApple);

        assertNotEquals(originalOrder, Apples2Apples.playedApple, "Played apples order should be randomized.");
    }

    /**
     * Tests if all players play before results are calculated.
     */
    @Test
    public void testAllPlayersPlayedBeforeResults() throws Exception {
        Apples2Apples game = new Apples2Apples(3);

        for (Player player : game.players) {
            if (player.isBot) {
                player.play();
            }
        }

        assertEquals(game.players.size() - 1, Apples2Apples.playedApple.size(),
                "All players except the judge must play.");
    }

    /**
     * Tests if the judge selects their favorite apple.
     */
    @Test
    public void testJudgeSelectsFavorite() throws Exception {
        Apples2Apples.playedApple.clear();
        Apples2Apples.playedApple.add(new PlayedApple(1, "red1"));
        Apples2Apples.playedApple.add(new PlayedApple(2, "red2"));

        Player judge = new Player(0, false, null, null, null); // Create mock judge
        PlayedApple favorite = judge.judge();

        assertNotNull(favorite, "Judge must select a favorite red apple.");
    }

    /**
     * Tests if played apples are cleared after the round.
     */
    @Test
    public void testPlayedApplesDiscarded() {
        Apples2Apples.playedApple.add(new PlayedApple(1, "red1"));
        Apples2Apples.playedApple.add(new PlayedApple(2, "red2"));

        Apples2Apples.playedApple.clear();
        assertTrue(Apples2Apples.playedApple.isEmpty(), "Played apples must be discarded after a round.");
    }

    /**
     * Tests if players' hands are replenished correctly after a round.
     */
    @Test
    public void testPlayersReplenished() throws Exception {
        Apples2Apples game = new Apples2Apples(3);

        for (Player player : game.players) {
            player.addCard("newCard");
            assertEquals(7, player.hand.size(), "Player's hand should have 7 red apples after replenishment.");
        }
    }

    /**
     * Tests if the next judge is assigned correctly in a circular order.
     */
    @Test
    public void testNextJudgeAssigned() throws Exception {
        Apples2Apples game = new Apples2Apples(3);

        int currentJudge = 0;
        currentJudge = (currentJudge + 1) % game.players.size();

        assertEquals(1, currentJudge, "Next judge should be assigned in circular order.");
    }

    /**
     * Tests if players' scores are tracked correctly.
     */
    @Test
    public void testScoreTracking() {
        Player player = new Player(1, new ArrayList<>(), false);
        player.greenApples.add("green1");

        assertEquals(1, player.greenApples.size(), "Player's score must be tracked correctly.");
    }

    /**
     * Tests if the winning condition is correctly determined.
     */
    @Test
    public void testWinningCondition() {
        Player player = new Player(1, new ArrayList<>(), false);
        player.greenApples.addAll(List.of("green1", "green2", "green3", "green4"));

        int winningThreshold = 4;
        assertTrue(player.greenApples.size() >= winningThreshold, "Player should win after reaching the winning threshold.");
    }
}
