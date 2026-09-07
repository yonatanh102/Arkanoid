import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** A Ball class represents a circle with position, size, color, and velocity.*/
public class Ball implements Sprite {
    private Point center;
    private double radius;
    private Color color;
    private Velocity velocity;
    private final GameEnvironment environment;

    // constructor
    /**
     * Constructs a new Ball with the specified position, radius, and color.
     * @param x     the x-coordinate of the ball's center
     * @param y     the y-coordinate of the ball's center
     * @param r     the radius of the ball
     * @param color the color of the ball
     * @param environment the game environment
     */
    public Ball(double x, double y, int r, Color color, GameEnvironment environment) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.environment = environment;
    }

    /**
     * Constructs a new Ball with the specified position, radius, and color.
     * @param point the center of the ball
     * @param r     the radius of the ball
     * @param color the color of the ball
     * @param environment the game environment
     */
    public Ball(Point point, int r, Color color, GameEnvironment environment) {
        this.center = point;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.environment = environment;
    }

    /**
     * Sets the velocity of the ball using a Velocity object.
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy components.
     * @param dx the change in x-coordinate per step
     * @param dy the change in y-coordinate per step
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /** @param color the new color to be assigned to this ball */
    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(double newSize) {
        if (newSize >= 3 && newSize <= 25) { 
            this.radius = newSize;
        }
    }

    // accessors
    
    /** @return the x-coordinate as an integer */
    public int getX() {
        return ((int) this.center.getX());
    }
    /** @return the y-coordinate as an integer */
    public int getY() {
        return ((int) this.center.getY());
    }
    /** @return the radius as an integer */
    public int getSize() {
        return ((int) this.radius);
    }
    /** @return the color of the ball */
    public Color getColor() {
        return this.color;
    }
    /** @return the velocity of the ball */
    public Velocity getVelocity() {
        return (this.velocity);
    }
    /** @param surface the DrawSurface to draw the ball on */
    @Override
    public void drawOn(GraphicsContext gc) {
        gc.setFill(this.color);
        // JavaFX draws ovals from the top-left corner of their bounding box
        double x = getX() - getSize();
        double y = getY() - getSize();
        double size = getSize() * 2;
        gc.fillOval(x, y, size, size);
        
        // add an outline for a cleaner look
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x, y, size, size);
    }
    /**
     * Moves the ball one step according to its current velocity.
     * Checks for potential collisions along the trajectory using the game environment.
     * @param gameEnvironment the current game environment containing all collidable objects.
     */
    public void moveOneStep() {
        double speed = Math.sqrt(Math.pow(this.velocity.getDx(), 2) + Math.pow(this.velocity.getDy(), 2));
        if (speed == 0) return;
        
        double dirX = this.velocity.getDx() / speed;
        double dirY = this.velocity.getDy() / speed;
        
        Point nextCenter = this.velocity.applyToPoint(this.center);
        Point extendedEnd = new Point(nextCenter.getX() + (dirX * this.radius), nextCenter.getY() + (dirY * this.radius));
        Line trajectory = new Line(this.center, extendedEnd);
        
        CollisionInfo info = this.environment.getClosestCollision(trajectory);
        
        if (info != null) {
            Point hitPoint = info.collisionPoint();
            
            double newX = hitPoint.getX() - (dirX * (this.radius + 0.1));
            double newY = hitPoint.getY() - (dirY * (this.radius + 0.1));
            this.center = new Point(newX, newY);
            
            this.velocity = info.collisionObject().hit(this, hitPoint, this.velocity);
        } else {
            this.center = nextCenter;
        }
    }
    
    /** removing this ball from the game.
     * @param g - the game
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /** Adds this ball to the specified game as a sprite.
     * @param game the game to which this ball should be added
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
