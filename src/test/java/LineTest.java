import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LineTest {

    @Test
    public void testLengthNormal() {
        Line line = new Line(0, 0, 0, 5);
        assertEquals(5.0, line.length(), "Length of line from (0,0) to (0,5) should be 5");
    }

    @Test
    public void testMiddlePoint() {
        Line line = new Line(0, 0, 10, 10);
        Point mid = line.middle();
        assertEquals(5.0, mid.getX(), "Middle X should be 5");
        assertEquals(5.0, mid.getY(), "Middle Y should be 5");
    }

    @Test
    public void testIsIntersectingTrue() {
        Line line1 = new Line(0, 0, 10, 10);
        Line line2 = new Line(0, 10, 10, 0);
        assertTrue(line1.isIntersecting(line2), "Lines forming an X should intersect");
    }

    @Test
    public void testIsIntersectingFalseParallel() {
        // Test for two parallel lines that do not intersect
        Line line1 = new Line(0, 0, 10, 0);
        Line line2 = new Line(0, 5, 10, 5);
        assertFalse(line1.isIntersecting(line2), "Parallel lines should not intersect");
    }

    @Test
    public void testIntersectionWithPoint() {
        Line line1 = new Line(0, 0, 10, 10);
        Line line2 = new Line(0, 10, 10, 0);
        Point intersection = line1.intersectionWith(line2);
        
        assertNotNull(intersection, "Intersection point should not be null");
        assertEquals(5.0, intersection.getX(), "Intersection X should be exactly in the middle");
        assertEquals(5.0, intersection.getY(), "Intersection Y should be exactly in the middle");
    }

    @Test
    public void testLengthZeroEdgeCase() {
        Line line = new Line(5, 5, 5, 5);
        assertEquals(0.0, line.length(), "Line with same start and end should have length 0");
    }

    @Test
    public void testIntersectionNullNoIntersection() {
        Line line1 = new Line(0, 0, 10, 0);
        Line line2 = new Line(0, 5, 10, 5);
        assertNull(line1.intersectionWith(line2), "Should return null when lines are parallel");
    }

    @Test
    public void testIsIntersectingSegmentsNotTouching() {
        Line line1 = new Line(0, 0, 5, 5);
        Line line2 = new Line(6, 6, 10, 10);
        assertFalse(line1.isIntersecting(line2), "Collinear non-touching segments should not intersect");
    }

    @Test
    public void testEqualsTrueSameLine() {
        Line line1 = new Line(1, 2, 3, 4);
        Line line2 = new Line(1, 2, 3, 4);
        assertTrue(line1.equals(line2), "Lines with exact same coordinates should be equal");
    }

    @Test
    public void testEqualsReversedCoordinates() {
        Line line1 = new Line(0, 0, 10, 10);
        Line line2 = new Line(10, 10, 0, 0);
        assertTrue(line1.equals(line2), "Reversed line should still be logically equal");
    }
}