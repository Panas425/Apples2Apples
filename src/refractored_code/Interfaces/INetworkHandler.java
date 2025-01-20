package refractored_code.Interfaces;

import java.io.IOException;
import java.net.Socket;

public interface INetworkHandler {

    /**
     * Starts the server on the specified port.
     * 
     * @param port The port number on which the server should run.
     * @throws IOException If an I/O error occurs while starting the server.
     */
    void startServer(int port) throws IOException;

    /**
     * Accepts a client connection with a specified timeout.
     * 
     * @param timeoutMillis The maximum time to wait for a client connection in milliseconds.
     * @return The connected client socket or null if the timeout occurs.
     * @throws IOException If an I/O error occurs while waiting for a client.
     */
    Socket acceptClientWithTimeout(int timeoutMillis) throws IOException;

    /**
     * Sends a message to a specific client.
     * 
     * @param socket  The client socket to send the message to.
     * @param message The message to be sent.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    void sendMessage(Socket socket, String message) throws IOException;

    /**
     * Broadcasts a message to all connected clients.
     * 
     * @param clients An array of connected client sockets.
     * @param message The message to be broadcasted.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    void broadcastMessage(Socket[] clients, String message) throws IOException;

	void stopServer() throws IOException;
}
