import javafx.scene.paint.Color;

public class ShrinkPaddlePowerUp extends PowerUp {

    public ShrinkPaddlePowerUp(Point center, Game game, Paddle paddle) {
        super(center, game, paddle, Color.RED); 
    }

    @Override
    public void applyEffect() {
        double currentWidth = this.paddle.getCollisionRectangle().getWidth();
        if (currentWidth > 40) {
            this.paddle.setWidth(currentWidth - 30);
        }
    }
}