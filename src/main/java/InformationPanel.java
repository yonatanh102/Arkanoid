import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InformationPanel implements Sprite {
    private final Counter score;
    private final Counter lives;
    private final String levelName;

    public InformationPanel(Counter score, Counter lives, String levelName) {
        this.score = score;
        this.lives = lives;
        this.levelName = levelName;
    }

    @Override
    public void drawOn(GraphicsContext gc) {
        gc.setFill(Color.web("#1A1A1A"));
        gc.fillRect(0, 0, 800, 30);

        gc.setFill(Color.web("#00E676")); 
        gc.fillRect(0, 30, 800, 2);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 15));
        
        String shortLevelName = this.levelName;
        if (shortLevelName != null && shortLevelName.contains(":")) {
            shortLevelName = shortLevelName.split(":")[0].toUpperCase();
        }
        
        gc.fillText("LIVES: " + lives.getValue(), 50, 20);
        gc.fillText("SCORE: " + score.getValue(), 350, 20);
        gc.fillText(shortLevelName, 650, 20); 
    }

    @Override
    public void timePassed() {
        // No action needed for the information panel on time passed
    }

    public void addToGame(Game game) {
        game.addSprite(this);
    }
}