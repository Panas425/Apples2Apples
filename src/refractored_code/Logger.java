package refractored_code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import refractored_code.Interfaces.ILogObserver;
import refractored_code.Interfaces.ILogger;

/**
 * Logger that listens for log events using the Observer Pattern.
 * Implements the ILogObserver interface for decoupled logging.
 */
public class Logger implements ILogObserver, ILogger {

    // Singleton instance
    private static Logger instance;

    // Log file location
    private static final String LOG_FILE = "application.log";

    // Writer for logging
    private BufferedWriter writer;

    /**
     * Private constructor for Singleton pattern.
     */
    private Logger() {
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize Logger: " + e.getMessage());
        }
    }

    /**
     * Retrieves the Singleton instance of Logger.
     * Thread-safe double-checked locking.
     */
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    /**
     * Central logging method triggered by observers.
     */
    @Override
    public synchronized void update(String level, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("[%s] [%s] %s", timestamp, level, message);

        // Print to console
        System.out.println(logEntry);

        // Write to file
        try {
            writer.write(logEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write log entry: " + e.getMessage());
        }
    }
    @Override
    public void logInfo(String message) {
        update("INFO", message);
    }

    @Override
    public void logWarning(String message) {
        update("WARNING", message);
    }

    @Override
    public void logError(String message) {
        update("ERROR", message);
    }
}
