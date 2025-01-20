package refractored_code.tests;

import org.junit.jupiter.api.Test;

import refractored_code.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Logger class.
 * Tests the Singleton behavior, logging functionality, and ensures
 * no exceptions are thrown during typical usage.
 */
class LoggerTest {

    /**
     * Tests if the Logger class follows the Singleton design pattern.
     * This ensures only one instance of Logger is created.
     */
    @Test
    void testLoggerSingleton() {
        Logger logger1 = Logger.getInstance();  // First instance
        Logger logger2 = Logger.getInstance();  // Second reference

        // Ensure both instances are the same object
        assertSame(logger1, logger2, "Logger should be a singleton.");
    }

    /**
     * Tests the logInfo method of Logger.
     * Ensures that no exception is thrown while logging an informational message.
     */
    @Test
    void testLogInfo() {
        Logger logger = Logger.getInstance();

        // Assert that logging an info message does not throw an exception
        assertDoesNotThrow(() -> logger.logInfo("Testing Logger"), 
            "Logging an informational message should not throw an exception.");
    }

    /**
     * Tests the logError method of Logger.
     * Ensures that no exception is thrown while logging an error message.
     */
    @Test
    void testLogError() {
        Logger logger = Logger.getInstance();

        // Assert that logging an error message does not throw an exception
        assertDoesNotThrow(() -> logger.logError("Testing Error"), 
            "Logging an error message should not throw an exception.");
    }
}
