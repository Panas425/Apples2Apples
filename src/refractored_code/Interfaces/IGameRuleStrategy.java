package refractored_code.Interfaces;

import java.util.List;

import refractored_code.Player;

/**
 * IGameRuleStrategy defines the contract for implementing 
 * different game rule strategies in the Apples2Apples game.
 * This interface allows for flexibility in adjusting game rules 
 * such as determining the winner, checking if the game is over, 
 * and calculating the winning threshold.
 */
public interface IGameRuleStrategy {

    /**
     * Determines if the game is over based on the current players' states.
     * 
     * @param players A list of players currently in the game.
     * @return true if the game has ended, otherwise false.
     */
    boolean isGameOver(List<Player> players);

    /**
     * Retrieves the player who has won the game based on game rules.
     * 
     * @param players A list of players to evaluate for the winner.
     * @return The Player who has met the winning criteria or null if no winner exists.
     */
    Player getWinner(List<Player> players);

    /**
     * Calculates the number of green apples required to win the game.
     * 
     * @param playerCount The number of players currently in the game.
     * @return The minimum number of green apples needed to declare a winner.
     */
    int getWinningThreshold(int playerCount);
}
