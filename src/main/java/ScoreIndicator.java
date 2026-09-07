import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * displaying the score.
 */
public class ScoreIndicator implements Sprite {
    private final Counter score;

    /**
     * Constructs a new counter for the score.
     * @param score - the counter
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 15)); 
        gc.fillText("Score: " + this.score.getValue(), 350, 15);
    }

    @Override
    public void timePassed() {
    }
}