import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class VelocityTest {

    @Test
    public void testApplyToPointNormal() {
        Velocity v = new Velocity(2, 3);
        Point p = new Point(10, 10);
        Point result = v.applyToPoint(p);
        assertEquals(12, result.getX(), "X should move by dx");
        assertEquals(13, result.getY(), "Y should move by dy");
    }

    @Test
    public void testApplyToPointNegativeCoords() {
        Velocity v = new Velocity(-5, -5);
        Point p = new Point(0, 0);
        Point result = v.applyToPoint(p);
        assertEquals(-5, result.getX(), "X should handle negative velocity");
        assertEquals(-5, result.getY(), "Y should handle negative velocity");
    }

    @Test
    public void testFromAngleAndSpeedUp() {
        Velocity v = Velocity.fromAngleAndSpeed(90, 5);
        // edge case: angle of 90 degrees (upwards) should result in negative dy
        assertEquals(-5.0, v.getDy(), 0.001, "Dy should be negative for upwards");
        assertEquals(0.0, v.getDx(), 0.001, "Dx should be 0");
    }

    @Test
    public void testFromAngleZeroSpeedEdgeCase() {
        Velocity v = Velocity.fromAngleAndSpeed(45, 0);
        // edge case: speed of 0 should result in both dx and dy being 0
        assertEquals(0.0, v.getDx(), 0.0001, "Speed 0 means 0 dx");
        assertEquals(0.0, v.getDy(), 0.0001, "Speed 0 means 0 dy");
    }

    @Test
    public void testApplyToPointNullEdgeCase() {
        Velocity v = new Velocity(1, 1);
        // edge case: applying velocity to a null point should throw an exception
        assertThrows(NullPointerException.class, () -> {
            v.applyToPoint(null);
        }, "Should throw NullPointerException when point is null");
    }

    @Test
    public void testFromAngleRight() {
        Velocity v = Velocity.fromAngleAndSpeed(0, 5);
        assertEquals(0.0, v.getDy(), 0.001); 
    }

    @Test
    public void testNegativeSpeed() {
        Velocity v = Velocity.fromAngleAndSpeed(90, -5);
        assertEquals(5.0, v.getDy(), 0.001, "Negative speed should reverse direction");
    }

    @Test
    public void testGettersNormal() {
        Velocity v = new Velocity(3.5, -4.2);
        assertEquals(3.5, v.getDx(), "Getter for dx should return exact value");
        assertEquals(-4.2, v.getDy(), "Getter for dy should return exact value");
    }

    @Test
    public void testVelocityZero() {
        Velocity v = new Velocity(0, 0);
        Point p = new Point(10, 10);
        Point p2 = v.applyToPoint(p);
        assertEquals(10, p2.getX(), "Point should not move if velocity is 0");
        assertEquals(10, p2.getY(), "Point should not move if velocity is 0");
    }

    @Test
    public void testSpeedCalculation() {
        Velocity v = new Velocity(3, 4);
        double speed = Math.sqrt(v.getDx() * v.getDx() + v.getDy() * v.getDy());
        assertEquals(5.0, speed, "Speed derived from dx 3 and dy 4 should be 5");
    }
}