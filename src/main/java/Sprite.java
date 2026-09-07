import javafx.scene.canvas.GraphicsContext;

/**
 * The {@code Sprite} interface represents a game object that can be drawn
 * to the screen and updated as time progresses.
 */
public interface Sprite {
    /**
     * Draws the sprite on the given graphics context.
     * @param gc the graphics context to draw the sprite on
     */
    void drawOn(GraphicsContext gc);

    /**
     * Notifies the sprite that time has passed, so it can update its state.
     */
    void timePassed();
}