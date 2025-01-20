package refractored_code;

/**
 * Represents a played red apple card in the game.
 * This class stores the player ID who played the card and the corresponding red apple card text.
 */
public class PlayedApple {

    // The ID of the player who played the card
    private final int playerID;

    // The text description of the red apple card
    private final String redApple;

    /**
     * Constructs a PlayedApple instance with a specified player ID and red apple card.
     * 
     * @param playerID  The unique ID of the player who played the card
     * @param redApple  The description of the played red apple card
     */
    public PlayedApple(int playerID, String redApple) {
        this.playerID = playerID;
        this.redApple = redApple;
    }

    /**
     * Retrieves the ID of the player who played this card.
     * 
     * @return The player's unique ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Retrieves the description of the red apple card played by the player.
     * 
     * @return The text of the red apple card
     */
    public String getRedApple() {
        return redApple;
    }
}
