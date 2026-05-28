import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;


class NumberGeneratorTest {

    @RepeatedTest(50)
    void generate_shouldAlwaysReturnNumberWithinBounds() {
        int result = NumberGenerator.generate(1, 10);
        assertTrue(result >= 1 && result <= 10,
                "Expected number between 1 and 10 but got: " + result);
    }

    @RepeatedTest(20)
    void generate_shouldWorkWithLargeRange() {
        int result = NumberGenerator.generate(1, 1000);
        assertTrue(result >= 1 && result <= 1000);
    }

    @Test
    void generate_shouldReturnOnlyPossibleValueWhenRangeIsOne() {
        // Range of 2 consecutive numbers
        int result = NumberGenerator.generate(5, 6);
        assertTrue(result == 5 || result == 6);
    }

    @Test
    void generate_shouldThrowWhenLowerBoundEqualsUpperBound() {
        assertThrows(IllegalArgumentException.class,
                () -> NumberGenerator.generate(5, 5));
    }

    @Test
    void generate_shouldThrowWhenLowerBoundGreaterThanUpperBound() {
        assertThrows(IllegalArgumentException.class,
                () -> NumberGenerator.generate(10, 5));
    }
}
