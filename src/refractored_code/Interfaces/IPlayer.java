package refractored_code.Interfaces;

import java.util.List;

/**
 * IPlayer defines essential player operations
 * for the Apples2Apples game.
 */
public interface IPlayer {

    /**
     * Retrieves the player's unique ID.
     *
     * @return The player's unique identifier.
     */
    int getPlayerID();

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to be received.
     */
    void receiveCard(String card);

    /**
     * Retrieves the list of cards currently in the player's hand.
     *
     * @return A list of cards in the player's hand.
     */
    List<String> getHand();

    /**
     * Retrieves the list of green apples the player has won.
     *
     * @return A list of green apples collected by the player.
     */
    List<String> getGreenApples();
}
