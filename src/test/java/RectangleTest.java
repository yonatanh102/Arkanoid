import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class RectangleTest {

    @Test
    public void testIntersectionPointsNormal() {
        Rectangle rect = new Rectangle(new Point(0, 0), 10, 10);
        // line passing through the center of the rectangle (intersecting the top and bottom edges)
        Line line = new Line(5, -5, 5, 15);
        List<Point> intersections = rect.intersectionPoints(line);
        
        assertEquals(2, intersections.size(), "Line passing through rectangle should have 2 intersection points");
    }

    @Test
    public void testIntersectionPointsNoIntersection() {
        Rectangle rect = new Rectangle(new Point(0, 0), 10, 10);
        // line completely outside the rectangle
        Line line = new Line(20, 20, 30, 30);
        List<Point> intersections = rect.intersectionPoints(line);
        
        assertTrue(intersections.isEmpty(), "Line outside rectangle should have 0 intersection points");
    }

    @Test
    public void testIntersectionPointsEdgeCaseCorner() {
        Rectangle rect = new Rectangle(new Point(0, 0), 10, 10);
        // Edge case: line that touches the corner of the rectangle
        Line line = new Line(10, 10, 20, 20);
        List<Point> intersections = rect.intersectionPoints(line);
        assertFalse(intersections.isEmpty(), "Line touching a corner should register an intersection");
    }

    @Test
    public void testGetWidthAndHeight() {
        Rectangle rect = new Rectangle(new Point(5, 5), 20, 15);
        assertEquals(20.0, rect.getWidth(), "Width should be 20");
        assertEquals(15.0, rect.getHeight(), "Height should be 15");
    }

    @Test
    public void testGetUpperLeftEdgeCaseNegative() {
        // Edge case: rectangle with negative coordinates
        Rectangle rect = new Rectangle(new Point(-10, -20), 50, 50);
        assertEquals(-10.0, rect.getUpperLeft().getX(), "Upper left X should handle negative values");
        assertEquals(-20.0, rect.getUpperLeft().getY(), "Upper left Y should handle negative values");
    }

    @Test
    public void testIntersectionPointsLineInside() {
        Rectangle rect = new Rectangle(new Point(0, 0), 20, 20);
        Line line = new Line(5, 5, 10, 10);
        List<Point> intersections = rect.intersectionPoints(line);
        assertTrue(intersections.isEmpty(), "Line fully inside rectangle should have 0 intersections with edges");
    }

    @Test
    public void testIntersectionPointsStartsInsideExits() {
        Rectangle rect = new Rectangle(new Point(0, 0), 20, 20);
        Line line = new Line(10, 10, 30, 10);
        List<Point> intersections = rect.intersectionPoints(line);
        assertEquals(1, intersections.size(), "Line starting inside and exiting should have exactly 1 intersection");
    }

    @Test
    public void testIntersectionPointsTangentEdge() {
        Rectangle rect = new Rectangle(new Point(0, 0), 20, 20);
        Line line = new Line(-10, 0, 30, 0);
        List<Point> intersections = rect.intersectionPoints(line);
        assertFalse(intersections.isEmpty(), "Tangent line touching the edge should register intersections");
    }

    @Test
    public void testRectangleZeroWidthEdgeCase() {
        Rectangle rect = new Rectangle(new Point(0, 0), 0, 10);
        assertEquals(0.0, rect.getWidth(), "Should allow 0 width");
    }

    @Test
    public void testRectangleNegativeDimensions() {
        Rectangle rect = new Rectangle(new Point(0, 0), -10, -5);
        assertEquals(-10.0, rect.getWidth(), "Should return assigned width (or throw exception depending on implementation)");
    }
}