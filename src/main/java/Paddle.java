import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Represents a paddle in the game, which the player can move left and right using the keyboard.
 * The paddle interacts with the ball to bounce it off when they collide. */
public class Paddle implements Sprite, Collidable {
    private final Set<String> activeKeys;
    private Rectangle paddleRect;
    private final int speed = 8;
    private final Color color = Color.DARKGRAY;
    private final String leftKey;
    private final String rightKey;
    private final int screenWidth = 800;

    /** Constructs a new {@code Paddle} with the specified keyboard sensor, rectangle for the paddle's position,
     * and the left and right key bindings for controlling the paddle.
     * @param activeKeys the set of currently active keys
     * @param paddleRect the {@code Rectangle} representing the paddle's position and size
     * @param leftKey the key used to move the paddle left
     * @param rightKey the key used to move the paddle right */
    public Paddle(Set<String> activeKeys, Rectangle paddleRect, String leftKey, String rightKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.paddleRect = paddleRect;
        this.activeKeys = activeKeys;
    }

    //moving part:
    /** Moves the paddle left by the speed value, wrapping around the screen if it goes out of bounds. */
    public void moveLeft() {
        double newX = paddleRect.getUpperLeft().getX() - speed;
        if (newX < 0) {
            newX = screenWidth - paddleRect.getWidth();
        }
        Point newPoint = new Point(newX, paddleRect.getUpperLeft().getY());
        this.paddleRect = new Rectangle(newPoint, paddleRect.getWidth(), paddleRect.getHeight());
    }

    /** Moves the paddle right by the speed value, wrapping around the screen if it goes out of bounds. */
    public void moveRight() {
        double newX = paddleRect.getUpperLeft().getX() + speed;
        if (newX > screenWidth - paddleRect.getWidth()) {
            newX = 0;
        }
        Point newPoint = new Point(newX, paddleRect.getUpperLeft().getY());
        this.paddleRect = new Rectangle(newPoint, paddleRect.getWidth(), paddleRect.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return (this.paddleRect);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        SoundManager.playPaddleHit(); 

        double speed = Math.sqrt(
            Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2)
        );

        double paddleWidth = this.paddleRect.getWidth();
        double regionWidth = paddleWidth / 5.0;
        
        double hitX = collisionPoint.getX() - this.paddleRect.getUpperLeft().getX();
        
        int region = (int) (hitX / regionWidth) + 1;
        if (region < 1) region = 1;
        if (region > 5) region = 5;

        switch (region) {
            case 1: 
                return Velocity.fromAngleAndSpeed(150, speed);
            case 2: 
                return Velocity.fromAngleAndSpeed(120, speed);
            case 3: 
                return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
            case 4: 
                return Velocity.fromAngleAndSpeed(60, speed);
            case 5: 
                return Velocity.fromAngleAndSpeed(30, speed);
            default:
                return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        }
    }

    @Override
    public void drawOn(GraphicsContext gc) {
        double x = this.paddleRect.getUpperLeft().getX();
        double y = this.paddleRect.getUpperLeft().getY();
        double width = this.paddleRect.getWidth();
        double height = this.paddleRect.getHeight();
        
        gc.setFill(this.color);
        gc.fillRect(x, y, width, height);
        
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);
    }

    @Override
    public void timePassed() {
        if (this.activeKeys.contains(leftKey)) {
            this.moveLeft();
        }
        if (this.activeKeys.contains(rightKey)) {
            this.moveRight();
        }
    }
    /** Adds this paddle to the game, making it both a collidable object and a drawable sprite.
     * @param g the {@code Game} to which this paddle should be added */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    public void setWidth(double newWidth) {
        this.paddleRect = new Rectangle(this.paddleRect.getUpperLeft(), newWidth, this.paddleRect.getHeight());
    }

    public void resetWidth(double originalWidth) {
        Point currentPos = this.paddleRect.getUpperLeft(); 
        this.paddleRect = new Rectangle(currentPos, originalWidth, this.paddleRect.getHeight());
    }
}
