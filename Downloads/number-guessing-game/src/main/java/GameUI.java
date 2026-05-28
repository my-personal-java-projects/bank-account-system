import java.util.Scanner;

// Handles all input/output and game loop control.

public class GameUI {

    private static final int LOWER_BOUND  = 1;
    private static final int UPPER_BOUND  = 100;
    private static final int MAX_ATTEMPTS = 7;

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        printBanner();
        boolean playAgain = true;
        while (playAgain) {
            playRound();
            playAgain = askPlayAgain();
        }
        System.out.println("\nThanks for playing! Goodbye.");
        scanner.close();
    }

    private void playRound() {
        Game game = new Game(LOWER_BOUND, UPPER_BOUND, MAX_ATTEMPTS);
        System.out.printf("%nI'm thinking of a number between %d and %d.%n", LOWER_BOUND, UPPER_BOUND);
        System.out.printf("You have %d attempts. Good luck!%n%n", MAX_ATTEMPTS);

        while (!game.isGameOver()) {
            int guess = promptGuess(game);
            GuessResult result = game.guess(guess);
            System.out.println("  >> " + result);
        }

        if (!game.isWon()) {
            System.out.println("\nBetter luck next time! The number was: " + game.getSecretNumber());
        }
    }

    private int promptGuess(Game game) {
        while (true) {
            System.out.printf("Attempt %d/%d — Enter your guess: ",
                    game.getAttemptsUsed() + 1, game.getMaxAttempts());
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < game.getLowerBound() || value > game.getUpperBound()) {
                    System.out.printf("  Please enter a number between %d and %d.%n",
                            game.getLowerBound(), game.getUpperBound());
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    private boolean askPlayAgain() {
        System.out.print("\nPlay again? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("yes") || answer.equals("y");
    }

    private void printBanner() {
        System.out.println("=========================================");
        System.out.println("       NUMBER GUESSING GAME v1.0        ");
        System.out.println("=========================================");
    }
}
