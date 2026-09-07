import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Represents a line segment between two points in 2D space. */
public class Line {

    private static final double EPSILON = 0.000001;
    private final Point start;
    private final Point end;
    private final double slope;
    private final double b;
    private final boolean isVertical;
    private final boolean isHorizontal;

    // constructors
    /** Creates line from two points.
     * @param start represents first point on the line
     * @param end represents last point on the line
     * */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.isHorizontal = (Math.abs(start.getY() - end.getY()) < EPSILON);
        this.isVertical = (Math.abs(start.getX() - end.getX()) < EPSILON);
        this.slope = isVertical ? Double.NaN : ((end.getY() - start.getY()) / (end.getX() - start.getX()));
        this.b = isVertical ? Double.NaN : (start.getY() - slope * start.getX());
    }
    /** Creates line from coordinates.
     * @param x1 - the x-coordinate of the first point
     * @param y1 - the y-coordinate of the first point
     * @param x2 - the x-coordinate of the second point
     * @param y2 - the y-coordinate of the second point
     * */
    public Line(double x1, double y1, double x2, double y2) {

        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        this.isHorizontal = (Math.abs(y1 - y2) < EPSILON);
        this.isVertical = (Math.abs(x1 - x2) < EPSILON);
        this.slope = isVertical ? Double.NaN : ((y2 - y1) / (x2 - x1));
        this.b = isVertical ? Double.NaN : (y1 - slope * x1);
    }
    // Return the length of the line
    /** @return length of the line segment */
    public double length() {
        return (start.distance(end));
    }
    // Returns the middle point of the line
    /** @return midpoint of the line */
    public Point middle() {
        double xMid = (start.getX() + end.getX()) / 2.0;
        double yMid = (start.getY() + end.getY()) / 2.0;
        return (new Point(xMid, yMid));
    }
    // Returns the start point of the line
    /** @return starting point */
    public Point start() {
        return start;
    }
    // Returns the end point of the line
    /** @return ending point */
    public Point end() {
        return end;
    }
    /** @return true if Vertical */
    public boolean isVertical() {
        return isVertical;
    }
    /** @return true if Horizontal */
    public boolean isHorizontal() {
        return isHorizontal;
    }
    // Returns true if the lines intersect, false otherwise
    /** @return true if point lies on this line
     * @param other the second line
     * */
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null || this.overlapsWith(other);
    }
    // Returns true if this 2 lines intersect with this line, false otherwise
    /** @return true if both lines intersect with our line
     * @param other1 - second line
     * @param other2 - third line
     * */
    public boolean isIntersecting(Line other1, Line other2) {
        return (this.isIntersecting(other1) && this.isIntersecting(other2));
    }
    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    /** @return intersection point or null
     * @param other - the second line
     * */
    public Point intersectionWith(Line other) {

        if (this.isParallelTo(other)) {
            if (this.overlapsWith(other)) {
                return null;
            }
            return findSingleTouchPoint(other);
        }

        double x, y;
        if (this.isVertical) {
            x = this.start.getX();
            y = other.slope * x + other.b;
        } else if (other.isVertical) {
            x = other.start.getX();
            y = this.slope * x + this.b;
        } else {
            x = (other.b - this.b) / (this.slope - other.slope);
            y = this.slope * x + this.b;
        }

        Point intersection = new Point(x, y);
        return (this.containsPoint(intersection) && other.containsPoint(intersection)) ? intersection : null;
    }

    private boolean isParallelTo(Line other) {
        if (this.isVertical && other.isVertical) {
            return (true);
        }
        if (this.isVertical || other.isVertical) {
            return (false);
        }
        return Math.abs(this.slope - other.slope) < EPSILON;
    }
    // checks for a single touch point
    private Point findSingleTouchPoint(Line other) {
        if (this.start.equals(other.start) || this.start.equals(other.end)) {
            return this.start;
        }
        if (this.end.equals(other.start) || this.end.equals(other.end)) {
            return this.end;
        }
        return null;
    }
    // Check if any endpoint of other lies on this.line
    private boolean overlapsWith(Line other) {
        return this.containsPoint(other.start) || this.containsPoint(other.end)
                || other.containsPoint(this.start) || other.containsPoint(this.end);
    }
    /** @return true if p fall within the computed range of the line
     * @param p - the point we want to check
     * */
    public boolean containsPoint(Point p) {
        double minX = Math.min(start.getX(), end.getX()) - EPSILON;
        double maxX = Math.max(start.getX(), end.getX()) + EPSILON;
        double minY = Math.min(start.getY(), end.getY()) - EPSILON;
        double maxY = Math.max(start.getY(), end.getY()) + EPSILON;
        return p.getX() >= minX && p.getX() <= maxX && p.getY() >= minY && p.getY() <= maxY;
    }
    // equals -- return true if the lines are equal, false otherwise
    /** @return true if lines are equal
     * @param other - the second line
     * */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start()) && this.end.equals(other.end()))
                || (this.start.equals(other.end()) && this.end.equals(other.start())));
    }

    /**
     * @param d - the DrawSurface file
     * @param color - the selected color for the line
     */
    public void drawOn(GraphicsContext gc, Color color) {
        gc.setStroke(color);
        gc.strokeLine(start().getX(), start().getY(), end().getX(), end().getY());
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    /**
     * Returns the closest intersection point between this line and the given rectangle.
     * @param rect the rectangle to check for intersection with
     * @return the closest intersection point to the start of this line, or {@code null} if none exist
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersections = rect.intersectionPoints(this);
        // Return null if no intersections
        if (intersections.isEmpty()) {
            return null;
        }
        return (this.closestIntersectionToStartOfLineWithList(intersections));
    }
    /**
     * Finds and returns the closest point to the start of this line from a list of points.
     * @param intersections the list of intersection points to consider
     * @return the point in the list that is closest to the start of the line
     */
    public Point closestIntersectionToStartOfLineWithList(List<Point> intersections) {
        Point closestPoint = intersections.get(0);
        for (int i = 1; i < intersections.size(); i++) {
            if (closestPoint.distance(this.start) > intersections.get(i).distance(this.start)) {
                closestPoint = intersections.get(i);
            }
        }
        return (closestPoint);
    }
}


