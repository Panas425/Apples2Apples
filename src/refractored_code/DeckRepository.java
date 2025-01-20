package refractored_code;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import refractored_code.Interfaces.IDeckRepository;

/**
 * Manages loading and shuffling of game card decks.
 * Implements the IDeckRepository interface.
 */
public class DeckRepository implements IDeckRepository {

    /**
     * Shuffles a list of cards using Fisher-Yates algorithm.
     * @param cards The list of cards to be shuffled.
     */
    protected void shuffleList(List<String> cards) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Fisher-Yates shuffle implementation
        for (int i = cards.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);  // Random index
            String temp = cards.get(index);  // Swap elements
            cards.set(index, cards.get(i));
            cards.set(i, temp);
        }
    }

    /**
     * Loads and returns the list of red apple cards from a file.
     * @return A shuffled list of red apples.
     * @throws Exception If file loading fails.
     */
    @Override
    public List<String> getRedApples() throws Exception {
        List<String> redApples = new ArrayList<>(Files.readAllLines(
            Paths.get("resources/redApples.txt"), StandardCharsets.ISO_8859_1));

        shuffleList(redApples);  // Shuffle after loading
        return redApples;
    }

    /**
     * Loads and returns the list of green apple cards from a file.
     * @return A shuffled list of green apples.
     * @throws Exception If file loading fails.
     */
    @Override
    public List<String> getGreenApples() throws Exception {
        List<String> greenApples = new ArrayList<>(Files.readAllLines(
            Paths.get("resources/greenApples.txt"), StandardCharsets.ISO_8859_1));

        shuffleList(greenApples);  // Shuffle after loading
        return greenApples;
    }
}
