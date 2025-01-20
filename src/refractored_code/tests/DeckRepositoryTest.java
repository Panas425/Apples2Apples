package refractored_code.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import refractored_code.DeckRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DeckRepository class.
 * Verifies that red and green apple decks are properly loaded and not empty.
 */
class DeckRepositoryTest {

    private DeckRepository deckRepository;

    /**
     * Initializes the DeckRepository instance before each test.
     */
    @BeforeEach
    void setUp() {
        deckRepository = new DeckRepository();
    }

    /**
     * Tests if the red apple deck is loaded correctly.
     * Ensures the list is not null and not empty after loading.
     * @throws Exception If file loading fails.
     */
    @Test
    void testGetRedApples() throws Exception {
        List<String> redApples = deckRepository.getRedApples();

        // Assert that the deck is not null
        assertNotNull(redApples, "Red apples deck should not be null.");

        // Assert that the deck is not empty
        assertFalse(redApples.isEmpty(), "Red apples deck should not be empty.");
    }

    /**
     * Tests if the green apple deck is loaded correctly.
     * Ensures the list is not null and not empty after loading.
     * @throws Exception If file loading fails.
     */
    @Test
    void testGetGreenApples() throws Exception {
        List<String> greenApples = deckRepository.getGreenApples();

        // Assert that the deck is not null
        assertNotNull(greenApples, "Green apples deck should not be null.");

        // Assert that the deck is not empty
        assertFalse(greenApples.isEmpty(), "Green apples deck should not be empty.");
    }
}
