import java.util.List;

import javafx.scene.paint.Color;

public class ShrinkBallPowerUp extends PowerUp {
    private final List<Ball> activeBalls;

    public ShrinkBallPowerUp(Point center, Game game, Paddle paddle, List<Ball> activeBalls) {
        super(center, game, paddle, Color.PURPLE);
        this.activeBalls = activeBalls;
    }

    @Override
    public void applyEffect() {
        for (Ball ball : this.activeBalls) {
            ball.setSize(ball.getSize() - 4);
        }
    }
}