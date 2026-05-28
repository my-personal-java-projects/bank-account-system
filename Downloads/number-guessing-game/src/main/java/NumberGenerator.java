import java.util.Random;

// Utility class responsible for generating random numbers within a given range.

public class NumberGenerator {

    private static final Random random = new Random();

    private NumberGenerator() {}

    //Generates a random integer between lowerBound (inclusive) and upperBound (inclusive).

    public static int generate(int lowerBound, int upperBound) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("lowerBound must be less than upperBound");
        }
        return random.nextInt((upperBound - lowerBound) + 1) + lowerBound;
    }
}
