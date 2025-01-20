package refractored_code.Interfaces;

import java.io.IOException;

public interface IGameController {

    /**
     * Starts the game, initializing server and accepting clients.
     * 
     * @throws IOException If an I/O error occurs during game initialization or server setup.
     */
    void startGame() throws IOException;

    /**
     * Plays the game rounds until a winner is determined or the game ends.
     * 
     * @throws IOException If an I/O error occurs during game rounds.
     */
    void playRounds() throws IOException;
}
