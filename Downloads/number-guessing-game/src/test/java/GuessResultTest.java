import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GuessResult.
 */
class GuessResultTest {

    @Test
    void getAttemptsRemaining_shouldCalculateCorrectly() {
        GuessResult result = new GuessResult(GuessResult.Status.TOO_LOW, 3, 7);
        assertEquals(4, result.getAttemptsRemaining());
    }

    @Test
    void getAttemptsRemaining_shouldBeZeroWhenAllUsed() {
        GuessResult result = new GuessResult(GuessResult.Status.TOO_HIGH, 7, 7);
        assertEquals(0, result.getAttemptsRemaining());
    }

    @Test
    void toString_tooLow_shouldContainHint() {
        GuessResult result = new GuessResult(GuessResult.Status.TOO_LOW, 2, 7);
        assertTrue(result.toString().toLowerCase().contains("low"));
    }

    @Test
    void toString_tooHigh_shouldContainHint() {
        GuessResult result = new GuessResult(GuessResult.Status.TOO_HIGH, 2, 7);
        assertTrue(result.toString().toLowerCase().contains("high"));
    }

    @Test
    void toString_correct_shouldContainAttemptsUsed() {
        GuessResult result = new GuessResult(GuessResult.Status.CORRECT, 4, 7);
        assertTrue(result.toString().contains("4"));
    }

    @Test
    void toString_gameOver_shouldIndicateGameOver() {
        GuessResult result = new GuessResult(GuessResult.Status.GAME_OVER, 7, 7);
        assertTrue(result.toString().toLowerCase().contains("game over")
                || result.toString().toLowerCase().contains("over"));
    }

    @Test
    void getStatus_shouldReturnCorrectStatus() {
        GuessResult result = new GuessResult(GuessResult.Status.CORRECT, 1, 7);
        assertEquals(GuessResult.Status.CORRECT, result.getStatus());
    }
}
