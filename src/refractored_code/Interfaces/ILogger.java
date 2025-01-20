package refractored_code.Interfaces;

/**
 * Interface for the Logger component.
 * Defines methods for logging messages with different severity levels.
 */
public interface ILogger {

    /**
     * Logs an informational message.
     * 
     * @param message The message to log.
     */
    void logInfo(String message);

    /**
     * Logs a warning message.
     * 
     * @param message The message to log.
     */
    void logWarning(String message);

    /**
     * Logs an error message.
     * 
     * @param message The message to log.
     */
    void logError(String message);
}
