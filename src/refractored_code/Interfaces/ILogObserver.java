package refractored_code.Interfaces;

/**
 * Interface for log observers that listen for log events.
 */
public interface ILogObserver {
    void update(String level, String message);
}
