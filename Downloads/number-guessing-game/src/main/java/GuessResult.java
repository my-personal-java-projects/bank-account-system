/**
 * Immutable value object representing the outcome of a single guess.
 */
public class GuessResult {

    public enum Status {
        TOO_LOW,
        TOO_HIGH,
        CORRECT,
        GAME_OVER
    }

    private final Status status;
    private final int attemptsUsed;
    private final int maxAttempts;

    public GuessResult(Status status, int attemptsUsed, int maxAttempts) {
        this.status = status;
        this.attemptsUsed = attemptsUsed;
        this.maxAttempts = maxAttempts;
    }

    public Status getStatus() { return status; }
    public int getAttemptsUsed() { return attemptsUsed; }
    public int getAttemptsRemaining() { return maxAttempts - attemptsUsed; }

    @Override
    public String toString() {
        return switch (status) {
            case TOO_LOW  -> "Too low! " + getAttemptsRemaining() + " attempt(s) remaining.";
            case TOO_HIGH -> "Too high! " + getAttemptsRemaining() + " attempt(s) remaining.";
            case CORRECT  -> "Correct! You got it in " + attemptsUsed + " attempt(s).";
            case GAME_OVER -> "Game over! No more attempts left.";
        };
    }
}
