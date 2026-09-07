import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SpriteCollection} class manages a list of {@link Sprite} objects.
 * It is responsible for updating and drawing all sprites in the game.
 */
public class SpriteCollection {
    private final List<Sprite> sprites = new ArrayList<>();

    /**
     * Adds a sprite to the collection.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            this.sprites.add(s);
        }
    }

    /**
     * removes a sprite from the collection.
     * @param s the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Notifies all sprites that time has passed by calling their {@code timePassed()} method.
     * This is used to update each sprite's state once per frame.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites on the given {@link GraphicsContext} by calling their {@code drawOn()} method.
     * @param gc the graphics context
     */
    public void drawAllOn(GraphicsContext gc) {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.drawOn(gc);
        }
    }
}