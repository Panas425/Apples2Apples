package refractored_code.Interfaces;

import java.util.List;

/**
 * Interface for observable subjects that notify observers of log events.
 */
public interface ILogSubject {
    void registerObserver(ILogObserver observer);
    void notifyObservers(String level, String message);
}
