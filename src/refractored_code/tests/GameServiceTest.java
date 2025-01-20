package refractored_code.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import refractored_code.DeckRepository;
import refractored_code.GameRuleStrategy;
import refractored_code.GameService;
import refractored_code.Logger;
import refractored_code.PlayedApple;
import refractored_code.Player;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameService class, ensuring that core game logic functions as expected.
 */
class GameServiceTest {

    private GameService gameService;
    private DeckRepository deckRepository;
    private GameRuleStrategy gameRuleStrategy;
    private Logger logger;

    /**
     * Initializes dependencies before each test.
     * Uses an actual file-based deck repository.
     */
    @BeforeEach
    void setUp() throws Exception {
        deckRepository = new DeckRepository() {
            @Override
            public List<String> getRedApples() throws Exception {
                List<String> redApples = Files.readAllLines(
                    Paths.get("resources/redApples.txt"), StandardCharsets.ISO_8859_1);
                shuffleList(redApples);
                return redApples;
            }

            @Override
            public List<String> getGreenApples() throws Exception {
                List<String> greenApples = Files.readAllLines(
                    Paths.get("resources/greenApples.txt"), StandardCharsets.ISO_8859_1);
                shuffleList(greenApples);
                return greenApples;
            }
        };
        gameRuleStrategy = new GameRuleStrategy();
        gameService = new GameService(deckRepository, gameRuleStrategy);

        // Add 4 players for initial testing
        gameService.addPlayer(new Player(0));
        gameService.addPlayer(new Player(1));
        gameService.addPlayer(new Player(2));
        gameService.addPlayer(new Player(3));
    }

    /**
     * Tests if adding players works correctly.
     */
    @Test
    void testAddPlayer() {
        assertEquals(4, gameService.getPlayers().size(), "Player count should be 4.");
    }

    /**
     * Tests if cards are correctly dealt to all players.
     */
    @Test
    void testDealCards() {
        gameService.dealCards();

        for (Player player : gameService.getPlayers()) {
            assertEquals(7, player.getHand().size(), "Each player should have 7 red apples.");
        }
    }

    /**
     * Tests if a green apple can be drawn from the deck.
     */
    @Test
    void testDrawGreenApple() throws Exception {
        String greenApple = gameService.drawGreenApple();
        assertNotNull(greenApple, "Green apple should be drawn.");
    }

    /**
     * Tests if players' hands are correctly refilled after a round, skipping the judge.
     */
    @Test
    void testRefillPlayerHands() {
        int judgeIndex = 2;  // Player 2 is the judge

        gameService.refillPlayerHands(judgeIndex);

        for (int i = 0; i < gameService.getPlayers().size(); i++) {
            Player player = gameService.getPlayers().get(i);

            if (i == judgeIndex) {
                assertEquals(0, player.getHand().size(), "Judge should not receive cards.");
            } else {
                assertEquals(7, player.getHand().size(), "Non-judge players should have 7 cards.");
            }
        }
    }

    /**
     * Tests if all players receive exactly 7 cards when refilling hands without a judge.
     */
    @Test
    void testRefillPlayerHands_AllPlayersGet7Cards() throws Exception {
        gameService.refillPlayerHands(-1);  // No judge specified

        for (Player player : gameService.getPlayers()) {
            assertEquals(7, player.getHand().size(), "Each player should have 7 cards after refill.");
        }
    }

    /**
     * Tests selecting a round winner when players have played cards.
     */
    @Test
    void testSelectRoundWinner() {
        List<PlayedApple> playedApples = new ArrayList<>();
        playedApples.add(new PlayedApple(0, "Apple 1"));
        playedApples.add(new PlayedApple(1, "Apple 2"));

        Player winner = gameService.selectRoundWinner(playedApples);

        assertNotNull(winner, "There should be a winner.");
        assertTrue(winner.getGreenApples().size() > 0, "The winner should have at least one green apple.");
    }

    /**
     * Tests if the game ends correctly when a player collects enough green apples.
     */
    @Test
    void testIsGameOver() {
        Player player1 = new Player(1);
        gameService.addPlayer(player1);

        for (int i = 0; i < 8; i++) {
            player1.getGreenApples().add("Green Apple " + i);
        }

        assertTrue(gameService.isGameOver(), "The game should be over when a player wins.");
    }

    /**
     * Tests if the judge assignment rotates correctly.
     */
    @Test
    void testAssignNextJudge() {
        gameService.selectRandomJudge();
        int initialJudgeIndex = gameService.getCurrentJudgeIndex();

        gameService.assignNextJudge();
        int newJudgeIndex = gameService.getCurrentJudgeIndex();

        assertNotEquals(initialJudgeIndex, newJudgeIndex, "The judge should change after assigning the next judge.");
    }

    /**
     * Tests if the game correctly ends when the required number of green apples is reached.
     */
    @Test
    void testGameEndsWithCorrectGreenApples() throws Exception {
        int[] playerCounts = {4, 5, 6, 7, 8};

        for (int playerCount : playerCounts) {
            gameService = new GameService(deckRepository, gameRuleStrategy);

            for (int i = 0; i < playerCount; i++) {
                gameService.addPlayer(new Player(i));
            }

            int winningThreshold = gameService.getWinningThreshold();
            Player winner = gameService.getPlayers().get(0);

            for (int i = 0; i < winningThreshold; i++) {
                winner.getGreenApples().add("Green Apple " + i);
            }

            assertTrue(gameService.isGameOver(), 
                "The game should end when a player has " + winningThreshold + " green apples.");
            assertEquals(winner, gameService.getWinner(), 
                "Player " + winner.getPlayerID() + " should be the winner.");
            assertEquals(winningThreshold, winner.getGreenApples().size(), 
                "The winner should have " + winningThreshold + " green apples.");
        }
    }
}
