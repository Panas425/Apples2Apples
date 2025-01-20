package refractored_code;

import java.io.IOException;

import refractored_code.Interfaces.IGameState;

public class AnnouncingWinnerState implements IGameState {
    private final GameController controller;

    public AnnouncingWinnerState(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void startGame() {
        controller.notifyObservers("ERROR", "Game already finished.");
    }

    @Override
    public void playRounds() throws Exception {
        controller.notifyObservers("ERROR", "Game already finished.");
    }

    @Override
    public void announceGameWinner() throws IOException {
        Player winner = controller.getGameService().getWinner();
        if (winner != null) {
            controller.notifyObservers("INFO", "Game Over! Winner: Player " + winner.getPlayerID() + " with " + winner.getGreenApples().size() + " green apples!");
        } else {
            controller.notifyObservers("INFO", "Game Over! No winner.");
        }
    }
}
