package refractored_code;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import refractored_code.Interfaces.IGameState;

public class PlayingRoundsState implements IGameState {
    private final GameController controller;

    public PlayingRoundsState(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void startGame() {
        controller.notifyObservers("ERROR", "Game already in progress.");
    }

    @Override
    public void playRounds() throws Exception {
        while (!controller.getGameService().isGameOver() && !controller.getGameService().isGreenAppleDeckEmpty()) {
            String greenApple = controller.getGameService().drawGreenApple();
            controller.getNetworkHandler().broadcastMessage(
                controller.getConnectedClients().toArray(new Socket[0]),
                "Green Apple: " + greenApple
            );
            controller.notifyObservers("INFO", "Green Apple: " + greenApple);

            List<PlayedApple> playedApples = new ArrayList<>();

            for (Player player : controller.getGameService().getPlayers()) {
                if (player.getPlayerID() != controller.getGameService().getCurrentJudgeIndex() && !player.getHand().isEmpty()) {
                    PlayedApple playedCard = player.playCard(0);
                    playedApples.add(playedCard);
                    controller.getNetworkHandler().broadcastMessage(
                        controller.getConnectedClients().toArray(new Socket[0]),
                        "Player " + player.getPlayerID() + " played: " + playedCard.getRedApple()
                    );
                }
            }

            Player roundWinner = controller.getGameService().selectRoundWinner(playedApples);
            if (roundWinner != null) {
                controller.notifyObservers("INFO", "Round Winner: Player " + roundWinner.getPlayerID());
            }

            controller.getGameService().refillPlayerHands(controller.getGameService().getCurrentJudgeIndex());
            controller.getGameService().assignNextJudge();
        }
        controller.setState(new AnnouncingWinnerState(controller));
    }

    @Override
    public void announceGameWinner() throws IOException {
        controller.notifyObservers("ERROR", "Cannot announce winner during game play.");
    }
}
