package refractored_code;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import refractored_code.Interfaces.ILogObserver;
import refractored_code.Interfaces.ILogSubject;
import refractored_code.Interfaces.INetworkHandler;

/**
 * NetworkHandler implements server and client communication
 * while notifying registered log observers of important events.
 */
public class NetworkHandler implements INetworkHandler, ILogSubject {
    private ServerSocket serverSocket;
    private final List<ILogObserver> observers = new ArrayList<>();

    /**
     * Starts the server on the specified port.
     */
    @Override

    public void startServer(int port) throws IOException {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();  // Close previous instance if active
            }
            serverSocket = new ServerSocket(port);  // Start server on given port
            notifyObservers("INFO", "Server started on port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            notifyObservers("ERROR", "Failed to start server: " + e.getMessage());
            throw e;  // Re-throw exception after logging
        }
    }



    /**
     * Accepts a client connection with a specified timeout.
     * 
     * @param timeoutMillis Timeout in milliseconds to wait for a client.
     * @return The connected client socket or null if timeout occurs.
     */
    @Override
    public Socket acceptClientWithTimeout(int timeoutMillis) throws IOException {
        serverSocket.setSoTimeout(timeoutMillis);  // Set timeout for incoming connections
        try {
            Socket client = serverSocket.accept();  // Wait for incoming connection
            notifyObservers("INFO", "Client connected: " + client.getInetAddress());
            return client;
        } catch (SocketTimeoutException e) {
            notifyObservers("WARNING", "Client connection timed out after " + timeoutMillis + " ms.");
            return null;  // Return null if no connection is made
        } catch (IOException e) {
            notifyObservers("ERROR", "Client connection failed: " + e.getMessage());
            throw e;  // Re-throw exception after logging
        }
    }

    /**
     * Sends a message to a specific client.
     */
    @Override
    public void sendMessage(Socket socket, String message) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()))) {
            writer.write(message);
            writer.newLine();  // Ensure message ends with a newline
            writer.flush();  // Ensure message is sent
            notifyObservers("INFO", "Sent message to client: " + message);
        } catch (IOException e) {
            notifyObservers("ERROR", "Failed to send message: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     */
    @Override
    public void broadcastMessage(Socket[] clients, String message) throws IOException {
        for (Socket client : clients) {
            sendMessage(client, message);
        }
        notifyObservers("INFO", "Broadcast message to all clients: " + message);
    }

    /**
     * Registers an observer to receive log updates.
     */
    @Override
    public void registerObserver(ILogObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Notifies all registered observers of a log event.
     */
    @Override
    public void notifyObservers(String level, String message) {
        for (ILogObserver observer : observers) {
            observer.update(level, message);
        }
    }
    
    @Override
    public void stopServer() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
            notifyObservers("INFO", "Server stopped.");
        }
    }
    
    /**
     * Getter for current ServerSocket.
     */
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
}
