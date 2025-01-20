package refractored_code;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import refractored_code.Interfaces.IGameService;
import refractored_code.Interfaces.IGameState;
import refractored_code.Interfaces.ILogObserver;
import refractored_code.Interfaces.ILogSubject;
import refractored_code.Interfaces.INetworkHandler;

public class GameController implements ILogSubject {
    private final IGameService gameService;
    private final INetworkHandler networkHandler;
    private final List<Socket> connectedClients;
    private final List<ILogObserver> observers;

    private IGameState currentState;

    public GameController(IGameService gameService, INetworkHandler networkHandler) {
        this.gameService = gameService;
        this.networkHandler = networkHandler;
        this.connectedClients = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.currentState = new WaitingForPlayersState(this);
    }

    public void setState(IGameState state) {
        currentState = state;
    }

    public void startGame() throws IOException {
        currentState.startGame();
    }

    public void playRounds() throws Exception {
        currentState.playRounds();
    }

    public void announceGameWinner() throws IOException {
        currentState.announceGameWinner();
    }

    @Override
    public void registerObserver(ILogObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String level, String message) {
        for (ILogObserver observer : observers) {
            observer.update(level, message);
        }
    }

    public List<Socket> getConnectedClients() {
        return connectedClients;
    }

    public IGameService getGameService() {
        return gameService;
    }

    public INetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}