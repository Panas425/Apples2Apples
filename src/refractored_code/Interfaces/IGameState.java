package refractored_code.Interfaces;

import java.io.IOException;

public interface IGameState {
    void startGame() throws IOException;
    void playRounds() throws Exception;
    void announceGameWinner() throws IOException;
}
