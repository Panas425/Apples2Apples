package refractored_code;

import java.util.List;

import refractored_code.Interfaces.IGameRuleStrategy;

/**
 * GameRuleStrategy implements game-ending rules, winner determination, 
 * and winning threshold logic based on the number of players in the game.
 */
public class GameRuleStrategy implements IGameRuleStrategy {

    /**
     * Checks if the game is over based on the number of green apples 
     * collected by any player. If a player has reached the winning 
     * threshold, the game ends.
     * 
     * @param players The list of players in the game.
     * @return true if the game is over, otherwise false.
     */
    @Override
    public boolean isGameOver(List<Player> players) {
        for (Player player : players) {
            // Check if any player has reached the winning threshold
            if (player.getGreenApples().size() >= getWinningThreshold(players.size())) {
                return true;  // Game is over
            }
        }
        return false;  // Game is still ongoing
    }

    /**
     * Determines the winner of the game by finding the player with 
     * the most green apples. In case of a tie, the first encountered 
     * player with the maximum number of apples is returned.
     * 
     * @param players The list of players in the game.
     * @return The player with the most green apples or null if no players exist.
     */
    @Override
    public Player getWinner(List<Player> players) {
        return players.stream()
            // Compare players based on the number of green apples they have
            .max((p1, p2) -> Integer.compare(p1.getGreenApples().size(), p2.getGreenApples().size()))
            .orElse(null);  // Return null if the list is empty
    }

    /**
     * Determines the winning threshold based on the number of players.
     * This method defines how many green apples a player must collect 
     * to win the game, based on common game balancing rules.
     * 
     * @param playerCount The number of players in the game.
     * @return The number of green apples needed to win.
     */
    @Override
    public int getWinningThreshold(int playerCount) {
        if (playerCount >= 8) return 4;  // Larger games have a lower threshold
        if (playerCount == 7) return 5;
        if (playerCount == 6) return 6;
        if (playerCount == 5) return 7;
        return 8;  // Default for 4 players or fewer
    }
}
