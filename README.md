Apples2Apples - Refactored Architecture
Overview
This project is a reverse-engineered and refactored implementation of the popular party game Apples to Apples. The original codebase suffered from tight coupling, hardcoded dependencies (fixed port 2048, file I/O), and poor testability. This refactoring addresses those issues by applying SOLID principles, design patterns, and a layered architecture to improve maintainability, scalability, and testability.

Key Improvements
Testability
Before: Constructor dependencies on network (port 2048) and file I/O made unit testing impossible without code modification

After: Clean separation of concerns allows mocking of dependencies; core game logic is now fully testable with JUnit

Architecture
Presentation Layer: GameController coordinates game flow

Service Layer: GameService implements core logic with pluggable rule strategies

Data Access Layer: DeckRepository abstracts file/DB operations

Networking Module: NetworkHandler supports real-time multiplayer

Utility Module: Thread-safe Logger (Singleton) for consistent logging

SOLID Principles Applied
Principle	Implementation
SRP	Player, GameService, DeckRepository each have single responsibilities
OCP	New card types or rules can be added via IGameRuleStrategy without modifying existing code
LSP	IDeckRepository and INetworkHandler allow mock implementations for testing
ISP	Focused interfaces (ILogger, INetworkHandler) – classes only implement what they need
DIP	High-level modules depend on abstractions, not concrete classes
Design Patterns Used
Pattern	Purpose
Strategy	Encapsulates game rules for easy modification/extensibility
Observer	Real-time event notifications in client-server communication
Singleton	Centralized, thread-safe logging (Logger)
Factory	Dynamic creation of Player and PlayedApple instances
Dependency Injection	ServiceLocator pattern for loose coupling between modules
Booch's Metrics Achieved

✅ Low Coupling – Modules connected through interfaces only

✅ High Cohesion – Each module has a clear, focused purpose

✅ Sufficiency – Core gameplay + extensible networking

✅ Completeness – All original game rules supported

✅ Primitiveness – Basic, reusable operations (shuffle, draw, judge assignment)

Example: Testable Requirements
The refactored design enables testing previously impossible scenarios:

java
// Test that played apples are randomized
@Test
public void testPlayedApplesRandomized() {
    Apples2Apples.playedApple.add(new PlayedApple(1, "red1"));
    Apples2Apples.playedApple.add(new PlayedApple(2, "red2"));
    List<PlayedApple> originalOrder = new ArrayList<>(Apples2Apples.playedApple);
    Collections.shuffle(Apples2Apples.playedApple);
    assertNotEquals(originalOrder, Apples2Apples.playedApple);
}

Technology Stack
Java (version)

JUnit (for unit testing)


Running the Game
bash
# Clone the repository
git clone https://github.com/Panas425/Apples2Apples.git

# Build the project
javac src/*.java

# Run the game
java src/Main
Running Tests
bash
# Run JUnit tests
java -cp junit.jar:src org.junit.runner.JUnitCore Apples2ApplesTest
Future Enhancements
GUI implementation (extendable via current architecture)

Database persistence for game history

Additional game rule variants via IGameRuleStrategy

Acknowledgments
Original game concept by Matt Kirby / Out of the Box Publishing. This is an educational refactoring exercise.
