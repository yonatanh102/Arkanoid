import java.util.List;

import javafx.scene.paint.Color;

public class ChangeBallColorPowerUp extends PowerUp {
    private final List<Ball> activeBalls;

    public ChangeBallColorPowerUp(Point center, Game game, Paddle paddle, List<Ball> activeBalls) {
        super(center, game, paddle, Color.ORANGE);
        this.activeBalls = activeBalls;
    }

    @Override
    public void applyEffect() {
        for (Ball ball : this.activeBalls) {
            ball.setColor(Color.BLACK);
        }
    }
}
