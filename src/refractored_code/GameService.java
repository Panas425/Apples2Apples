package refractored_code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import refractored_code.Interfaces.IDeckRepository;
import refractored_code.Interfaces.IGameRuleStrategy;
import refractored_code.Interfaces.IGameService;
import refractored_code.Interfaces.ILogObserver;
import refractored_code.Interfaces.ILogSubject;

/**
 * GameService manages the core logic of the game, including players, cards, game state, 
 * and game rules. It uses an IDeckRepository for retrieving cards and 
 * an IGameRuleStrategy for game rule enforcement. 
 */
public class GameService implements IGameService, ILogSubject {
    private final IDeckRepository deckRepository;  // Source for card decks
    private final List<String> redApples;         // Red apple cards (played by players)
    private final List<String> greenApples;       // Green apple cards (used for matching)
    private final List<Player> players;           // List of game players
    private int currentJudgeIndex;                // Tracks the current judge
    private IGameRuleStrategy gameRuleStrategy;   // Handles game rule logic
    private final List<ILogObserver> observers = new ArrayList<>();  // Observers for game events

    /**
     * Constructor for GameService.
     * Initializes the card decks and players using provided dependencies.
     */
    public GameService(IDeckRepository deckRepository, IGameRuleStrategy gameRuleStrategy) throws Exception {
        this.deckRepository = deckRepository;
        this.redApples = deckRepository.getRedApples();
        this.greenApples = deckRepository.getGreenApples();
        this.players = new ArrayList<>();
        this.gameRuleStrategy = gameRuleStrategy;
    }

    /**
     * Returns a defensive copy of the player list.
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);  // Prevents external modification
    }

    /**
     * Initializes the game by resetting players and decks.
     */
    public void initializeGame() throws Exception {
        redApples.clear();
        redApples.addAll(deckRepository.getRedApples());  // Refill red apples deck
        greenApples.clear();
        greenApples.addAll(deckRepository.getGreenApples());  // Refill green apples deck
        players.clear();  // Clear existing players
    }

    /**
     * Adds a player to the game.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Deals 7 red apples to each player if available.
     */
    public void dealCards() {
        for (Player player : players) {
            while (player.getHand().size() < 7 && !redApples.isEmpty()) {
                player.receiveCard(redApples.remove(0));
            }
        }
    }

    /**
     * Draws a green apple card from the deck.
     * Throws an exception if the deck is empty.
     */
    public String drawGreenApple() throws Exception {
        if (greenApples.isEmpty()) {
            throw new Exception("No more green apples available.");
        }
        return greenApples.remove(0);  // Draw a card
    }

    /**
     * Selects a random player to be the judge for the round.
     */
    public void selectRandomJudge() {
        currentJudgeIndex = new Random().nextInt(players.size());
    }

    /**
     * Determines the winner of the current round by selecting a random played apple.
     * Awards the corresponding player with a green apple.
     */
    public Player selectRoundWinner(List<PlayedApple> playedApples) {
        if (playedApples == null || playedApples.isEmpty()) {
            notifyObservers("INFO", "No cards were played this round.");
            return null;  // No winner if no cards were played
        }

        Collections.shuffle(playedApples);  // Randomize played cards
        PlayedApple winningCard = playedApples.get(0);  // Select a random winner
        Player winner = players.get(winningCard.getPlayerID());
        winner.getGreenApples().add(winningCard.getRedApple());  // Award a green apple

        notifyObservers("INFO", "Winning Card: " + winningCard.getRedApple());
        return winner;
    }

    /**
     * Assigns the next player as the judge in a round-robin fashion.
     */
    public void assignNextJudge() {
        currentJudgeIndex = (currentJudgeIndex + 1) % players.size();  // Loop through players
    }

    /**
     * Checks if the game is over according to the current game rule strategy.
     */
    public boolean isGameOver() {
        return gameRuleStrategy.isGameOver(players);  // Delegates to game rule strategy
    }

    /**
     * Retrieves the current winner based on the game rule strategy.
     */
    public Player getWinner() {
        return gameRuleStrategy.getWinner(players);
    }

    /**
     * Determines the number of green apples required to win based on player count.
     */
    public int getWinningThreshold() {
        return gameRuleStrategy.getWinningThreshold(players.size());
    }

    /**
     * Returns the current judge's index.
     */
    public int getCurrentJudgeIndex() {
        return currentJudgeIndex;
    }

    /**
     * Checks if the green apple deck is empty.
     */
    public boolean isGreenAppleDeckEmpty() {
        return greenApples.isEmpty();
    }

    /**
     * Refills players' hands to ensure they have 7 red apple cards, except the judge.
     */
    public void refillPlayerHands(int judge) {
        for (int i = 0; i < players.size(); i++) {
            if (i != judge) {  // Skip the judge
                while (players.get(i).getHand().size() < 7 && !redApples.isEmpty()) {
                    String card = redApples.remove(0);
                    notifyObservers("INFO", "Dealt card: " + card + " to Player ID: " + players.get(i).getPlayerID());
                    players.get(i).receiveCard(card);  // Refill player hand
                }
            }
        }
    }

    /**
     * Updates the current game rule strategy.
     */
    public void setGameRuleStrategy(IGameRuleStrategy newStrategy) {
        this.gameRuleStrategy = newStrategy;  // Change game rule logic
    }

    /**
     * Registers an observer to receive game event notifications.
     */
    @Override
    public void registerObserver(ILogObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all registered observers with a log message.
     */
    @Override
    public void notifyObservers(String level, String message) {
        for (ILogObserver observer : observers) {
            observer.update(level, message);  // Send log update
        }
    }
}
