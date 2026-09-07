import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CounterTest {

    @Test
    public void testInitialValue() {
        Counter counter = new Counter();
        assertEquals(0, counter.getValue(), "Initial counter value should be 0");
    }

    @Test
    public void testIncreaseNormal() {
        Counter counter = new Counter();
        counter.increase(10);
        assertEquals(10, counter.getValue(), "Counter should be 10 after normal increase");
    }

    @Test
    public void testDecreaseNormal() {
        Counter counter = new Counter();
        counter.increase(5);
        counter.decrease(2);
        assertEquals(3, counter.getValue(), "Counter should be 3 after decrease");
    }

    @Test
    public void testIncreaseNegativeEdgeCase() {
        Counter counter = new Counter();
        counter.increase(10);
        // edge case: increasing by a negative number should decrease the counter
        counter.increase(-5); 
        assertEquals(5, counter.getValue(), "Increasing by negative should act like decrease");
    }

    @Test
    public void testDecreaseBelowZeroEdgeCase() {
        Counter counter = new Counter();
        counter.decrease(5);
        // edge case: decreasing below zero should allow negative values
        assertEquals(-5, counter.getValue(), "Counter should allow negative values if not restricted");
    }

    @Test
    public void testMultipleIncreases() {
        Counter counter = new Counter();
        for (int i = 0; i < 100; i++) {
            counter.increase(1);
        }
        assertEquals(100, counter.getValue(), "Counter should handle loop increases accurately");
    }

    @Test
    public void testIncreaseAndDecreaseToZero() {
        Counter counter = new Counter();
        counter.increase(50);
        counter.decrease(50);
        assertEquals(0, counter.getValue(), "Counter should return to exactly 0");
    }

    @Test
    public void testLargeNumbers() {
        Counter counter = new Counter();
        counter.increase(1000000);
        counter.increase(2000000);
        assertEquals(3000000, counter.getValue(), "Counter should handle large integers correctly");
    }

    @Test
    public void testDecreaseNegativeEdgeCase() {
        Counter counter = new Counter();
        counter.increase(10);
        counter.decrease(-5);
        assertEquals(15, counter.getValue(), "Decreasing by negative should increase the value");
    }

    @Test
    public void testSequentialOperations() {
        Counter counter = new Counter();
        counter.increase(10);
        counter.decrease(2);
        counter.increase(5);
        counter.decrease(20);
        assertEquals(-7, counter.getValue(), "Should handle complex sequence of mixed operations");
    }
}