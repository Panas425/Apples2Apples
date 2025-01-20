package refractored_code.Interfaces;

import java.util.List;

import refractored_code.PlayedApple;
import refractored_code.Player;

/**
 * IGameService defines the essential operations 
 * required to manage the Apples2Apples game.
 */
public interface IGameService {

    // Core Game Management

    /**
     * Initializes the game by setting up decks and players.
     * 
     * @throws Exception If initialization fails.
     */
    void initializeGame() throws Exception;

    /**
     * Adds a player to the game.
     * 
     * @param player The player to be added.
     */
    void addPlayer(Player player);

    /**
     * Deals cards to all players.
     */
    void dealCards();

    /**
     * Draws the next green apple from the deck.
     * 
     * @return The next green apple card.
     * @throws Exception If the green apple deck is empty.
     */
    String drawGreenApple() throws Exception;

    /**
     * Selects a random judge for the current round.
     */
    void selectRandomJudge();

    /**
     * Determines the winner of the current round.
     * 
     * @param playedApples The list of cards played by players.
     * @return The player who won the round.
     */
    Player selectRoundWinner(List<PlayedApple> playedApples);

    /**
     * Assigns the next judge in sequence.
     */
    void assignNextJudge();


    // Game State Checkers

    /**
     * Checks if the game has reached its conclusion based on game rules.
     * 
     * @return True if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * Checks if the green apple deck is empty.
     * 
     * @return True if the deck is empty, false otherwise.
     */
    boolean isGreenAppleDeckEmpty();

    /**
     * Retrieves the winning player based on game criteria.
     * 
     * @return The winning player or null if no winner exists.
     */
    Player getWinner();

    /**
     * Determines the winning threshold based on player count.
     * 
     * @return The number of green apples needed to win.
     */
    int getWinningThreshold();

    /**
     * Retrieves the current judge's index.
     * 
     * @return The index of the current judge.
     */
    int getCurrentJudgeIndex();


    // Player Management

    /**
     * Retrieves all players currently in the game.
     * 
     * @return A list of players.
     */
    List<Player> getPlayers();

    /**
     * Refills players' hands with red apples, skipping the judge.
     * 
     * @param judge The index of the current judge.
     */
    void refillPlayerHands(int judge);
    
    void registerObserver(ILogObserver observer);
}
