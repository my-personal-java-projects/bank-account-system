# Number Guessing Game

A console-based Java game where the player guesses a randomly generated number within a limited number of attempts. Built to demonstrate clean OOP design, separation of concerns, input validation, and unit testing with JUnit 5.

---

## Project Overview

| Detail | Info |
|---|---|
| Language | Java 17+ |
| Build Tool | Maven |
| Testing | JUnit 5 (Jupiter) |
| Type | Console Application |
| Author | Your Name |

---

## How to Play

1. The program secretly picks a number between **1 and 100**
2. You have **7 attempts** to guess it
3. After each guess you get a hint: **Too High**, **Too Low**, or **Correct!**
4. The number of remaining attempts is shown after every guess
5. If you run out of attempts the secret number is revealed
6. After each round you can choose to play again or exit

---

## Project Structure

```
number-guessing-game/
├── pom.xml                              ← Maven build + JUnit 5 dependency
├── README.md
├── docs/
│   └── uml-diagram.md                  ← UML class diagram
└── src/
    ├── main/
    │   └── java/
    │       ├── Main.java                ← Entry point
    │       ├── Game.java                ← Core game logic & state
    │       ├── GameUI.java              ← Console input/output
    │       ├── GuessResult.java         ← Immutable result value object
    │       └── NumberGenerator.java     ← Random number utility
    └── test/
        └── java/
            ├── GameTest.java            ← 16 tests for game logic
            ├── GuessResultTest.java     ← 7 tests for result object
            └── NumberGeneratorTest.java ← 5 tests for number generation
```

---

## Running the Application in IntelliJ IDEA

### Step 1 — Open the project
1. Open IntelliJ IDEA
2. Click **File → Open**
3. Select the `number-guessing-game` folder (the one containing `pom.xml`)
4. Click **OK** — IntelliJ will detect Maven automatically

### Step 2 — Set the Java SDK
1. Go to **File → Project Structure → Project**
2. Set **SDK** to Java 17 or higher
3. Click **Apply → OK**

### Step 3 — Run the game
- Right-click `Main.java` → **Run 'Main.main()'**
- Or press `Shift + F10`
- The game runs in the terminal panel at the bottom of IntelliJ

---

## Running the Tests

### In IntelliJ
- Right-click the `src/test/java` folder → **Run 'All Tests'**
- Or open any `*Test.java` file and click the green ▶ button next to a test method
- Results appear in the **Run** panel with green/red indicators

### With Maven (terminal)
```bash
# From inside the number-guessing-game folder:
mvn test

# Run a specific test class:
mvn test -Dtest=GameTest

# Run a specific test method:
mvn test -Dtest=GameTest#guess_correctGuess_shouldReturnCorrectStatus
```

### Test Coverage Summary

| Test Class | Tests | What It Covers |
|---|---|---|
| `GameTest` | 16 | Constructor validation, correct/wrong guesses, attempt tracking, game over logic, reflection for determinism |
| `GuessResultTest` | 7 | Status values, attempts remaining calculation, toString messages |
| `NumberGeneratorTest` | 5 | Random bounds, repeated runs, invalid arguments |
| **Total** | **28** | |

---

## Class Responsibilities

### `Main`
The entry point. Creates a `GameUI` and calls `start()`. No logic lives here.

### `Game`
The heart of the application. Stores all game state privately: the secret number, attempt count, bounds, win status, and max attempts. Exposes a single `guess(int)` method that returns a `GuessResult`. Game logic is completely decoupled from I/O — `Game` never prints anything.

### `GameUI`
Owns the game loop. Displays the banner, manages the play-again flow, reads and validates user input, and prints `GuessResult` messages. `GameUI` knows nothing about how numbers are generated — it only talks to `Game`.

### `GuessResult`
An immutable value object returned from `Game.guess()`. Carries a `Status` enum (`TOO_LOW`, `TOO_HIGH`, `CORRECT`, `GAME_OVER`) and the attempt counts. Its `toString()` produces the player-facing feedback string using a switch expression.

### `NumberGenerator`
A stateless utility class. Has one job: `generate(lowerBound, upperBound)` returns a random integer in the inclusive range. Throws `IllegalArgumentException` if bounds are invalid.

---

## Key Java Concepts Demonstrated

| Concept | Where |
|---|---|
| OOP & separation of concerns | All classes have single, clear responsibilities |
| Enums with switch expressions | `GuessResult.Status` + `toString()` |
| Input validation | `GameUI.promptGuess()` — handles non-numeric and out-of-range input |
| Encapsulation | All `Game` fields are private; accessed only via getters |
| Immutable value objects | `GuessResult` — state set once in constructor |
| Unit testing with JUnit 5 | `@Test`, `@RepeatedTest`, `@BeforeEach`, reflection for private fields |
| Java Reflection API | `GameTest` uses `Field.setAccessible()` for deterministic testing |

---

## UML Diagram

See [`docs/uml-diagram.md`](docs/uml-diagram.md)

---

## Possible Extensions

- Add difficulty levels: Easy (10 tries / 1–50), Medium (7 tries / 1–100), Hard (4 tries / 1–200)
- Track a session high score (fewest attempts to win)
- Persist high scores to a `.txt` file using Java I/O
- Add a hint system (odd/even, divisible by 5)
- Rewrite the UI layer in JavaFX for a graphical interface
