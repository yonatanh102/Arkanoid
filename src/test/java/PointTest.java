import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PointTest {

    @Test
    public void testDistanceNormal() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        assertEquals(5.0, p1.distance(p2), "Distance should be exactly 5.0");
    }

    @Test
    public void testDistanceToSelfEdgeCase() {
        Point p = new Point(10.5, 20.5);
        assertEquals(0.0, p.distance(p), "Distance to the same point must be 0");
    }

    @Test
    public void testDistanceWithNegativeCoordinates() {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(-4, -5);
        assertEquals(5.0, p1.distance(p2), "Distance formula should handle negative coordinates");
    }

    @Test
    public void testEqualsTrue() {
        Point p1 = new Point(5.5, 10.1);
        Point p2 = new Point(5.5, 10.1);
        assertTrue(p1.equals(p2), "Points with same coordinates should be equal");
    }

    @Test
    public void testEqualsNullOrDifferentTypeEdgeCase() {
        Point p1 = new Point(5, 5);
        assertFalse(p1.equals(null), "Equals with null should return false");
        assertFalse(p1.equals("A String"), "Equals with different object type should return false");
    }

    @Test
    public void testDistanceZeroDifferentInstances() {
        Point p1 = new Point(100.5, 100.5);
        Point p2 = new Point(100.5, 100.5);
        assertEquals(0.0, p1.distance(p2), "Distance between identical points should be 0");
    }

    @Test
    public void testEqualsFalseDifferentX() {
        Point p1 = new Point(1, 5);
        Point p2 = new Point(2, 5);
        assertFalse(p1.equals(p2), "Points with different X should not be equal");
    }

    @Test
    public void testEqualsFalseDifferentY() {
        Point p1 = new Point(5, 1);
        Point p2 = new Point(5, 2);
        assertFalse(p1.equals(p2), "Points with different Y should not be equal");
    }

    @Test
    public void testGetters() {
        Point p = new Point(3.14, -2.71);
        assertEquals(3.14, p.getX(), "Getter for X must return exact constructor value");
        assertEquals(-2.71, p.getY(), "Getter for Y must return exact constructor value");
    }

    @Test
    public void testFloatingPointPrecisionEquals() {
        Point p1 = new Point(1.0 / 3.0, 10);
        Point p2 = new Point(0.3333333333333333, 10);
        assertTrue(p1.equals(p2), "Equals should handle floating point precision correctly");
    }
}