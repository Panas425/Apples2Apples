package refractored_code.Interfaces;

import java.util.List;

/**
 * IDeckRepository defines the contract for retrieving card decks 
 * in the game, such as red and green apples.
 */
public interface IDeckRepository {

    /**
     * Retrieves the list of red apple cards from the repository.
     * 
     * @return A list of red apple cards.
     * @throws Exception If the deck cannot be loaded.
     */
    List<String> getRedApples() throws Exception;

    /**
     * Retrieves the list of green apple cards from the repository.
     * 
     * @return A list of green apple cards.
     * @throws Exception If the deck cannot be loaded.
     */
    List<String> getGreenApples() throws Exception;
}
