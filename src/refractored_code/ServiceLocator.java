package refractored_code;

import java.util.HashMap;
import java.util.Map;

/**
 * ServiceLocator is a simple service container that supports 
 * registering and retrieving service instances by type.
 */
public class ServiceLocator {

    // Stores registered services mapped by their class types
    private static final Map<Class<?>, Object> services = new HashMap<>();

    /**
     * Registers a service implementation with its corresponding class type.
     * This allows services to be retrieved later using the class type key.
     * 
     * @param <T> The type of the service.
     * @param serviceClass The class type of the service to register.
     * @param serviceImplementation The implementation instance of the service.
     */
    public static <T> void registerService(Class<T> serviceClass, T serviceImplementation) {
        services.put(serviceClass, serviceImplementation);
    }

    /**
     * Retrieves a registered service implementation by its class type.
     * Throws an exception if the requested service is not registered.
     * 
     * @param <T> The type of the requested service.
     * @param serviceClass The class type of the service to retrieve.
     * @return The service implementation instance.
     * @throws IllegalArgumentException If no service is registered for the requested type.
     */
    public static <T> T getService(Class<T> serviceClass) {
        if (!services.containsKey(serviceClass)) {
            throw new IllegalArgumentException("No service registered for: " + serviceClass.getName());
        }
        // Cast the service to the requested type before returning
        return serviceClass.cast(services.get(serviceClass));
    }
}
