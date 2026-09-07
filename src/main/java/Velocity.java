
// Velocity specifies the change in position on the `x` and the `y` axes.
/** Represents a velocity in 2D space, specified by changes in x and y coordinates. */
public class Velocity {
    private double x;
    private double y;

    // constructor
    /**
     * Constructs a new Velocity with the specified x and y components.
     * @param dx the change in x-coordinate per unit time
     * @param dy the change in y-coordinate per unit time
     */
    public Velocity(double dx, double dy) {
        this.x = dx;
        this.y = dy;
    }
    /** Sets the velocity using a Velocity object.
     * @param v the new velocity to set
     */
    public void setVelocity(Velocity v) {
        this.x = v.getDx();
        this.y = v.getDy();
    }
    /** Sets the velocity using dx and dy components.
     * @param dx the change in x direction
     * @param dy the change in y direction
     */
    public void setVelocity(double dx, double dy) {
        this.x = dx;
        this.y = dy;
    }
    /**
     * Creates a Velocity from angle (in degrees) and speed.
     * @param angle the direction angle in degrees (0-360)
     * @param speed the magnitude of the velocity
     * @return a new Velocity object with the calculated dx and dy components
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.cos(radians);
        double dy = -speed * Math.sin(radians);
        return new Velocity(dx, dy);
    }
    /** @return the change in x-coordinate per unit time */
    public double getDx() {
        return (this.x);
    }
    /** @return the change in y-coordinate per unit time */
    public double getDy() {
        return (this.y);
    }
    /**
     * Applies this velocity to a specified point, returning a new point.
     * The resulting point's coordinates are calculated as (x+dx, y+dy).
     * @param p the original point to apply the velocity to
     * @return a new Point representing the position after movement
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.x, p.getY() + this.y);
    }
}
