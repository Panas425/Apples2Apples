package refractored_code;

import java.util.Scanner;

import refractored_code.Interfaces.IDeckRepository;
import refractored_code.Interfaces.IGameRuleStrategy;
import refractored_code.Interfaces.IGameService;
import refractored_code.Interfaces.ILogObserver;
import refractored_code.Interfaces.ILogger;
import refractored_code.Interfaces.INetworkHandler;

public class Main {
    public static void main(String[] args) {
        ILogger logger = Logger.getInstance();

        try {
            logger.logInfo("Initializing Dependencies...");

            // Register services in the Service Locator
            ServiceLocator.registerService(IDeckRepository.class, new DeckRepository());
            ServiceLocator.registerService(ILogger.class, logger);
            ServiceLocator.registerService(IGameRuleStrategy.class, new GameRuleStrategy());
            
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.registerObserver((ILogObserver)logger);
            ServiceLocator.registerService(INetworkHandler.class, networkHandler);

            // Initialize Game Service using Dependency Injection
            IGameService gameService = new GameService(
                ServiceLocator.getService(IDeckRepository.class),
                ServiceLocator.getService(IGameRuleStrategy.class)
            );
            gameService.registerObserver((ILogObserver)logger);
            ServiceLocator.registerService(IGameService.class, gameService);

            // Get number of players
            Scanner scanner = new Scanner(System.in);
            logger.logInfo("Enter the number of players (minimum 4): ");
            int numberOfPlayers = scanner.nextInt();

            // Validate player input
            while (numberOfPlayers < 4) {
                logger.logInfo("The game requires at least 4 players. Enter a valid number: ");
                numberOfPlayers = scanner.nextInt();
            }

            // Automatically set up players
            logger.logInfo("Setting up " + numberOfPlayers + " players...");
            for (int i = 0; i < numberOfPlayers; i++) {
                gameService.addPlayer(new Player(i));
                logger.logInfo("Player " + i + " has been added.");
            }

            // Assign a random judge
            gameService.selectRandomJudge();

            // Create and start GameController using Service Locator
            GameController gameController = new GameController(
                gameService,
                ServiceLocator.getService(INetworkHandler.class)
            );
            gameController.registerObserver((ILogObserver)logger);
            logger.logInfo("Starting Apples2Apples Game...");
            
            gameController.startGame();
            while (!gameService.isGameOver()) {
                gameController.playRounds();
            }
            gameController.announceGameWinner();

        } catch (Exception e) {
            logger.logError("Fatal Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
