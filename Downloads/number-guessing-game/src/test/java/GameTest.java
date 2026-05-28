import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Game.
 *
 * Because the secret number is randomly generated, tests use reflection
 * to read it where needed, ensuring deterministic assertions.
 */
class GameTest {

    private Game game;
    private int secretNumber;

    @BeforeEach
    void setUp() throws Exception {
        game = new Game(1, 100, 7);
        // Use reflection to read the private secretNumber for deterministic tests
        Field field = Game.class.getDeclaredField("secretNumber");
        field.setAccessible(true);
        secretNumber = (int) field.get(game);
    }

    // ── Constructor ──────────────────────────────────────────────────────────

    @Test
    void constructor_shouldThrowWhenMaxAttemptsIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Game(1, 100, 0));
    }

    @Test
    void constructor_shouldThrowWhenMaxAttemptsIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Game(1, 100, -3));
    }

    @Test
    void constructor_initialState_attemptsUsedShouldBeZero() {
        assertEquals(0, game.getAttemptsUsed());
    }

    @Test
    void constructor_initialState_shouldNotBeWon() {
        assertFalse(game.isWon());
    }

    @Test
    void constructor_initialState_shouldNotBeGameOver() {
        assertFalse(game.isGameOver());
    }

    // ── Correct guess ────────────────────────────────────────────────────────

    @Test
    void guess_correctGuess_shouldReturnCorrectStatus() {
        GuessResult result = game.guess(secretNumber);
        assertEquals(GuessResult.Status.CORRECT, result.getStatus());
    }

    @Test
    void guess_correctGuess_shouldMarkGameAsWon() {
        game.guess(secretNumber);
        assertTrue(game.isWon());
    }

    @Test
    void guess_correctGuess_shouldMarkGameAsOver() {
        game.guess(secretNumber);
        assertTrue(game.isGameOver());
    }

    // ── Too low ──────────────────────────────────────────────────────────────

    @Test
    void guess_tooLow_shouldReturnTooLowStatus() {
        if (secretNumber > 1) {
            GuessResult result = game.guess(secretNumber - 1);
            assertEquals(GuessResult.Status.TOO_LOW, result.getStatus());
        }
    }

    // ── Too high ─────────────────────────────────────────────────────────────

    @Test
    void guess_tooHigh_shouldReturnTooHighStatus() {
        if (secretNumber < 100) {
            GuessResult result = game.guess(secretNumber + 1);
            assertEquals(GuessResult.Status.TOO_HIGH, result.getStatus());
        }
    }

    // ── Attempts tracking ────────────────────────────────────────────────────

    @Test
    void guess_shouldIncrementAttemptsUsed() {
        int wrongGuess = (secretNumber == 1) ? 2 : 1;
        game.guess(wrongGuess);
        assertEquals(1, game.getAttemptsUsed());
    }

    @Test
    void guess_afterMaxAttempts_shouldBeGameOver() {
        int wrongGuess = (secretNumber == 1) ? 2 : 1;
        for (int i = 0; i < 7; i++) {
            game.guess(wrongGuess);
        }
        assertTrue(game.isGameOver());
    }

    @Test
    void guess_afterGameOver_shouldReturnGameOverStatus() {
        int wrongGuess = (secretNumber == 1) ? 2 : 1;
        for (int i = 0; i < 7; i++) game.guess(wrongGuess);

        GuessResult result = game.guess(wrongGuess);
        assertEquals(GuessResult.Status.GAME_OVER, result.getStatus());
    }

    @Test
    void guess_afterGameOver_shouldNotIncrementAttempts() {
        int wrongGuess = (secretNumber == 1) ? 2 : 1;
        for (int i = 0; i < 7; i++) game.guess(wrongGuess);

        int attemptsBefore = game.getAttemptsUsed();
        game.guess(wrongGuess);
        assertEquals(attemptsBefore, game.getAttemptsUsed());
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    @Test
    void getBounds_shouldMatchConstructorValues() {
        assertEquals(1, game.getLowerBound());
        assertEquals(100, game.getUpperBound());
    }

    @Test
    void getMaxAttempts_shouldMatchConstructorValue() {
        assertEquals(7, game.getMaxAttempts());
    }
}
