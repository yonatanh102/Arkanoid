/** The {@code Rectangle} class represents a rectangle in a 2D coordinate system.
 * It is defined by its upper-left corner point, width, and height. The rectangle
 * also provides methods to check for intersections with a line and access its
 * boundaries (ceiling, floor, left and right walls). */
public class Rectangle {
    private final Point point;
    private final double width;
    private final double height;
    private final Line ceiling;
    private final Line floor;
    private final Line rightWall;
    private final Line leftWall;

    // Create a new rectangle with location and width/height.
    /** Constructs a new {@code Rectangle} with the specified upper-left corner point,
     * width, and height.
     * @param upperLeft the point representing the upper-left corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle */
    public Rectangle(Point upperLeft, double width, double height) {
        this.point = upperLeft;
        this.height = height;
        this.width = width;
        double minX = upperLeft.getX();
        double maxX = upperLeft.getX() + width;
        double minY = upperLeft.getY();
        double maxY = upperLeft.getY() + height;
        //edges
        this.ceiling = new Line(minX, minY, maxX, minY);
        this.floor = new Line(minX, maxY, maxX, maxY);
        this.leftWall = new Line(minX, minY, minX, maxY);
        this.rightWall = new Line(maxX, minY, maxX, maxY);
    }
    // Return a (possibly empty) List of intersection points
    // with the specified line.
    /** Returns a list of intersection points between the specified line and the rectangle's
     * four edges (ceiling, floor, left wall, right wall).
     * If there are no intersections, the list will be empty.
     * @param line the line to check for intersections
     * @return a list of intersection points with the rectangle's edges */
    public java.util.List<Point> intersectionPoints(Line line) {

        java.util.List<Point> intersections = new java.util.ArrayList<>();

        Point intersection;
        intersection = line.intersectionWith(this.ceiling);
        if (intersection != null) {
            intersections.add(intersection);
        }
        intersection = line.intersectionWith(this.leftWall);
        if (intersection != null) {
            intersections.add(intersection);
        }
        intersection = line.intersectionWith(this.rightWall);
        if (intersection != null) {
            intersections.add(intersection);
        }
        intersection = line.intersectionWith(this.floor);
        if (intersection != null) {
            intersections.add(intersection);
        }
        return intersections;
    }
    // Return the width and height of the rectangle
    /** Returns the width of the rectangle.
     * @return the width of the rectangle*/
    public double getWidth() {
        return (this.width);
    }
    /** Returns the height of the rectangle.
     * @return the height of the rectangle */
    public double getHeight() {
        return (this.height);
    }
    /** Returns the upper-left point of the rectangle.
     * @return the point representing the upper-left corner of the rectangle */
    public Point getUpperLeft() {
         return (this.point);
    }
    /** Returns the line representing the ceiling (top edge) of the rectangle.
     * @return the ceiling line of the rectangle */
    public Line getCeiling() {
        return (this.ceiling);
    }
    /** Returns the line representing the floor (bottom edge) of the rectangle.
     * @return the floor line of the rectangle */
    public Line getFloor() {
        return (this.floor);
    }
    /** Returns the line representing the right wall (right vertical edge) of the rectangle.
     * @return the right wall line of the rectangle */
    public Line getRightWall() {
        return (this.rightWall);
    }
    /** Returns the line representing the left wall (left vertical edge) of the rectangle.
     * @return the left wall line of the rectangle */
    public Line getLeftWall() {
        return (this.leftWall);
    }
}
