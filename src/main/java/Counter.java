/**
 * The Counter class is used to keep track of a numeric count.
 * It provides methods to increase, decrease, and retrieve the current value.
 */
public class Counter {
    private int count;
    /**
     * Constructs a new Counter initialized to zero.
     */
    public Counter() {
        this.count = 0;
    }
    /**
     * Increases the counter by the specified number.
     *
     * @param number the amount to add to the current count
     */
    public void increase(int number) {
        this.count += number;
    }
    /**
     * Decreases the counter by the specified number.
     *
     * @param number the amount to subtract from the current count
     */
    public void decrease(int number) {
        this.count -= number;
    }
    /**
     * Returns the current value of the counter.
     *
     * @return the current count
     */
    public int getValue() {
        return this.count;
    }
}