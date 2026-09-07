import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/** Represents a 2D point with x and y coordinates. */
public class Point {

    private static final double EPSILON = 0.00001;
    private final double x;
    private final double y;
    // constructor
    /**
     * Constructs a new Point with specified coordinates.
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point
    /**
     * Calculates the Euclidean distance between this point and another point.
     * @param other the other point to measure distance to
     * @return the distance between the points
     */
    public double distance(Point other) {
        double dis = (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
        dis = Math.sqrt(dis);
        return (dis);
    }

    // equals -- return true is the points are equal, false otherwise
    /** @return true if points are equal within epsilon tolerance
     * @param other the other point to compare with
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Point)) {
            return false;
        }
        Point otherPoint = (Point) other;
        
        return Double.compare(this.x, otherPoint.getX()) == 0 &&
               Double.compare(this.y, otherPoint.getY()) == 0;
    }

    // Return the x and y values of this point
    /** @return x coordinate */
    public double getX() {
        return (this.x);
    }
    /** @return y coordinate */
    public double getY() {
        return (this.y);
    }

    /**
     *
     * @param d - the DrawSurface file
     * @param color - the selected color for the point
     */
    public void drawOn(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillOval(x - 3, y - 3, 6, 6); 
    }
}
