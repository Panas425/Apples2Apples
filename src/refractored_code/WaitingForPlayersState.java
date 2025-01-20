package refractored_code;

import java.io.IOException;
import java.net.Socket;

import refractored_code.Interfaces.IGameState;

public class WaitingForPlayersState implements IGameState {
    private final GameController controller;

    public WaitingForPlayersState(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void startGame() throws IOException {
        controller.notifyObservers("INFO", "Starting server...");
        controller.getNetworkHandler().startServer(8080);
        controller.notifyObservers("INFO", "Waiting for clients to connect...");
        
        for (int i = 0; i < controller.getGameService().getPlayers().size(); i++) {
                Socket client = controller.getNetworkHandler().acceptClientWithTimeout(5000);
                if (client != null) {
                    controller.getConnectedClients().add(client);
                    controller.notifyObservers("INFO", "Client connected: " + client.getInetAddress());
                    controller.getNetworkHandler().sendMessage(client, "Welcome to the game!");
                } else {
                    controller.notifyObservers("INFO", "Client connection timeout. Proceeding with offline mode.");
                    controller.setState(new DealingCardsState(controller));
                    return;
                }
            }
            controller.setState(new DealingCardsState(controller));
        
    }

    @Override
    public void playRounds() throws Exception {
        controller.notifyObservers("ERROR", "Cannot play rounds before starting the game.");
    }

    @Override
    public void announceGameWinner() throws IOException {
        controller.notifyObservers("ERROR", "Cannot announce winner before playing the game.");
    }
}
