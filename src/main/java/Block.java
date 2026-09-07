import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** The Block class represents a rectangular object in the game that can be collided with and drawn.
 * It implements both the {@code Sprite} and {@code Collidable} interfaces. */
public class Block implements Sprite, Collidable, HitNotifier {
    private final Rectangle rectangle;
    private final Color color;
    private boolean isBorder = false;
    private final List<HitListener> hitListeners;

    /** Constructs a new {@code Block} with the specified rectangle and color.
     * @param rect the {@code Rectangle} that defines the position and size of the block
     * @param color the {@code Color} to fill the block with */
    public Block(Rectangle rect, Color color) {
        this.rectangle = rect;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * setting the value to ture if the block acts as a border.
     */
    public void setBorder() {
        this.isBorder = true;
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return (this.rectangle);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Rectangle edges
        double left = rectangle.getUpperLeft().getX();
        double right = left + rectangle.getWidth();
        double top = rectangle.getUpperLeft().getY();
        double bottom = top + rectangle.getHeight();

        boolean onLeftEdge = Math.abs(collisionPoint.getX() - left) <= 1e-6;
        boolean onRightEdge = Math.abs(collisionPoint.getX() - right) <= 1e-6;
        boolean onTopEdge = Math.abs(collisionPoint.getY() - top) <= 1e-6;
        boolean onBottomEdge = Math.abs(collisionPoint.getY() - bottom) <= 1e-6;

        // Change direction depending on side of collision
        if ((onLeftEdge || onRightEdge) && (onTopEdge || onBottomEdge)) {
            currentVelocity = new Velocity(-dx, -dy); // corner
        } else if (onLeftEdge || onRightEdge) {
            currentVelocity = new Velocity(-dx, dy);
        } else if (onTopEdge || onBottomEdge) {
            currentVelocity = new Velocity(dx, -dy);
        }

        if (!this.isBorder && this.ballColorMatch(hitter)) {
            this.notifyHit(hitter);
            hitter.setColor(this.color);
        } else if (!this.isBorder && !this.hitListeners.isEmpty()) {
            this.notifyHit(hitter);
        }

        return currentVelocity;
    }

    @Override
    public void drawOn(GraphicsContext gc) {
        double x = this.rectangle.getUpperLeft().getX();
        double y = this.rectangle.getUpperLeft().getY();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();

        gc.setFill(this.color);
        gc.fillRect(x, y, width, height);

        // Draw the black border
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);
    }
    
    /** Adds this block to the game as both a sprite and a collidable.
     * @param g the game to add this block to */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    @Override
    public void timePassed() { }

    /**
     * checks if the color of this block match with the color of a ball.
     * @param ball - the ball we want to check
     * @return - true\ false
     */
    public boolean ballColorMatch(Ball ball) {
        return !this.color.equals(ball.getColor());
    }

    /**
     * removing this block from the game.
     * @param game - the game
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}
