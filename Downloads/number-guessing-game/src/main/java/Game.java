// Core game logic. Holds state for one round of the number guessing game.

public class Game {

    private final int secretNumber;
    private final int maxAttempts;
    private final int lowerBound;
    private final int upperBound;
    private int attemptsUsed;
    private boolean isWon;

    public Game(int lowerBound, int upperBound, int maxAttempts) {
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts must be greater than 0");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.maxAttempts = maxAttempts;
        this.attemptsUsed = 0;
        this.isWon = false;
        this.secretNumber = NumberGenerator.generate(lowerBound, upperBound);
    }

    // Processes a player's guess and returns a GuessResult.

    public GuessResult guess(int number) {
        if (isGameOver()) {
            return new GuessResult(GuessResult.Status.GAME_OVER, attemptsUsed, maxAttempts);
        }
        attemptsUsed++;
        if (number < secretNumber) {
            return new GuessResult(GuessResult.Status.TOO_LOW, attemptsUsed, maxAttempts);
        } else if (number > secretNumber) {
            return new GuessResult(GuessResult.Status.TOO_HIGH, attemptsUsed, maxAttempts);
        } else {
            isWon = true;
            return new GuessResult(GuessResult.Status.CORRECT, attemptsUsed, maxAttempts);
        }
    }

    public boolean isGameOver()    { return isWon || attemptsUsed >= maxAttempts; }
    public boolean isWon()         { return isWon; }
    public int getAttemptsUsed()   { return attemptsUsed; }
    public int getMaxAttempts()    { return maxAttempts; }
    public int getLowerBound()     { return lowerBound; }
    public int getUpperBound()     { return upperBound; }
    public int getSecretNumber()   { return secretNumber; }
}
