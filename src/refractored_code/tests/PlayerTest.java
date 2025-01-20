package refractored_code.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import refractored_code.PlayedApple;
import refractored_code.Player;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player class.
 */
class PlayerTest {

    // Player instance used for testing
    private Player player;

    /**
     * Initializes the player instance before each test case.
     */
    @BeforeEach
    void setUp() {
        player = new Player(1);  // Create a new player with ID 1
    }

    /**
     * Tests if a card is correctly received and added to the player's hand.
     */
    @Test
    void testReceiveCard() {
        // Act: Add a card to the player's hand
        player.receiveCard("Apple 1");

        // Assert: Check that the hand size is 1
        assertEquals(1, player.getHand().size(), 
                "Player's hand size should be 1 after receiving a card.");
    }

    /**
     * Tests if the player can successfully play a card from their hand.
     */
    @Test
    void testPlayCard() {
        // Arrange: Add a card to the player's hand
        player.receiveCard("Apple 1");

        // Act: Play the card
        PlayedApple playedCard = player.playCard(0);

        // Assert: Check that the correct card was played
        assertEquals("Apple 1", playedCard.getRedApple(),
                "The played card should be 'Apple 1'.");

        // Assert: Check that the player's hand is empty after playing
        assertEquals(0, player.getHand().size(),
                "Player's hand should be empty after playing a card.");
    }

    /**
     * Tests if playing a card from an empty hand throws an exception.
     */
    @Test
    void testPlayCardThrowsException() {
        // Act & Assert: Expect an IndexOutOfBoundsException when playing with an empty hand
        assertThrows(IndexOutOfBoundsException.class, 
                () -> player.playCard(0), 
                "Playing a card from an empty hand should throw an exception.");
    }
}
