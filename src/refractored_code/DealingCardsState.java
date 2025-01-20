package refractored_code;

import java.io.IOException;
import java.net.Socket;

import refractored_code.Interfaces.IGameState;

public class DealingCardsState implements IGameState {
    private final GameController controller;

    public DealingCardsState(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void startGame() {
        controller.notifyObservers("ERROR", "Game already started.");
    }

    @Override
    public void playRounds() throws Exception {
        controller.notifyObservers("INFO", "Dealing cards to players...");
        controller.getNetworkHandler().broadcastMessage(
            controller.getConnectedClients().toArray(new Socket[0]),
            "Dealing cards to players..."
        );
        controller.getGameService().dealCards();
        controller.setState(new PlayingRoundsState(controller));
    }

    @Override
    public void announceGameWinner() throws IOException {
        controller.notifyObservers("ERROR", "Cannot announce winner before playing rounds.");
    }
}
