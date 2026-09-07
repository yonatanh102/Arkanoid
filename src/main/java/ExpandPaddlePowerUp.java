import javafx.scene.paint.Color;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(Point center, Game game, Paddle paddle) {
        super(center, game, paddle, Color.YELLOW); 
    }

    @Override
    public void applyEffect() {
        double currentWidth = this.paddle.getCollisionRectangle().getWidth();
        this.paddle.setWidth(currentWidth + 40); 
    }
}