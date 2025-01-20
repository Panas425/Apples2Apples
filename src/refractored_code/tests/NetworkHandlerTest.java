package refractored_code.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import refractored_code.NetworkHandler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class NetworkHandlerTest {

    private NetworkHandler networkHandler;
    private ServerSocket serverSocket;
    private int assignedPort;

    /**
     * Sets up a new server with a dynamic port before each test.
     */
    @BeforeEach
    void setUp() throws IOException {
        serverSocket = new ServerSocket(0);  // OS will assign a free port
        assignedPort = serverSocket.getLocalPort();
        serverSocket.close();  // Close it so NetworkHandler can reuse it
        networkHandler = new NetworkHandler();
        networkHandler.startServer(assignedPort);
    }


    /**
     * Closes the server after each test.
     */
    @AfterEach
    void tearDown() throws IOException {
        if (networkHandler != null) {
            networkHandler.stopServer();  // Properly stop the server
        }
    }
    
    /**
     * Tests if the server starts correctly.
     */
    @Test
    void testStartServer() {
        assertNotNull(networkHandler.getServerSocket(), "Server socket should not be null.");
        assertFalse(networkHandler.getServerSocket().isClosed(), "Server should be running.");
        assertEquals(assignedPort, networkHandler.getServerSocket().getLocalPort(), "Assigned port should match.");
    }
    
    @Test
    void testStopServer() throws IOException {
        // Ensure the server is running
        assertNotNull(networkHandler.getServerSocket(), "Server socket should be initialized.");
        assertFalse(networkHandler.getServerSocket().isClosed(), "Server should be running before closure.");

        // Stop the server
        networkHandler.stopServer();

        // Verify the server is closed
        assertTrue(networkHandler.getServerSocket().isClosed(), "Server should be closed after stopServer().");
    }
   

    /**
     * Tests successful client connection with a dynamic port.
     */
    @Test
    void testAcceptClientWithTimeout_Success() throws IOException {
        new Thread(() -> {
            try (Socket clientSocket = new Socket("localhost", assignedPort)) {
                assertNotNull(clientSocket, "Client should successfully connect.");
            } catch (IOException e) {
                fail("Client connection failed.");
            }
        }).start();

        Socket acceptedSocket = networkHandler.acceptClientWithTimeout(5000);
        assertNotNull(acceptedSocket, "Client should be accepted.");
    }

    /**
     * Tests client connection failure due to timeout.
     */
    @Test
    void testAcceptClientWithTimeout_Fail() throws IOException {
        Socket client = networkHandler.acceptClientWithTimeout(1000);
        assertNull(client, "No client should be connected due to timeout.");
    }



}
