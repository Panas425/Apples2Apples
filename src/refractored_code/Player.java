package refractored_code;

import java.util.ArrayList;
import java.util.List;

import refractored_code.Interfaces.IPlayer;

/**
 * Represents a player in the game, managing their hand of red apples
 * and collection of green apples.
 */
public class Player implements IPlayer {

    // Unique player ID
    private final int playerID;

    // The player's current hand of red apple cards
    private final List<String> hand;

    // The player's collection of green apples
    private final List<String> greenApples;

    /**
     * Constructs a Player instance with a unique ID.
     * 
     * @param playerID The unique identifier for this player
     */
    public Player(int playerID) {
        this.playerID = playerID;
        this.hand = new ArrayList<>();
        this.greenApples = new ArrayList<>();
    }

    /**
     * Plays a red apple card from the player's hand.
     * 
     * @param cardIndex The index of the card to play
     * @return The played card wrapped in a PlayedApple object
     * @throws IndexOutOfBoundsException If the hand is empty or the index is invalid
     */
    public PlayedApple playCard(int cardIndex) throws IndexOutOfBoundsException {
        if (hand.isEmpty()) {
            throw new IndexOutOfBoundsException("No cards left to play.");
        }
        String card = hand.remove(cardIndex);
        return new PlayedApple(playerID, card);
    }

    /**
     * Adds a red apple card to the player's hand.
     * 
     * @param card The red apple card to be added
     */
    public void receiveCard(String card) {
        hand.add(card);
    }

    /**
     * Retrieves the player's current hand of red apple cards.
     * 
     * @return A list representing the player's hand
     */
    public List<String> getHand() {
        return hand;
    }

    /**
     * Retrieves the player's unique ID.
     * 
     * @return The player's ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Retrieves the player's collection of green apples.
     * 
     * @return A list of green apples collected by the player
     */
    public List<String> getGreenApples() {
        return greenApples;
    }
}
