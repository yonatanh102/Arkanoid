import javafx.scene.paint.Color;

public class MultiBallPowerUp extends PowerUp {
    private final GameEnvironment environment;
    private final Counter remainingBalls;

    public MultiBallPowerUp(Point center, Game game, Paddle paddle, GameEnvironment env, Counter remainingBalls) {
        super(center, game, paddle, Color.CYAN);
        this.environment = env;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void applyEffect() {
        // Calculate the center of the paddle to spawn the balls
        double x = this.paddle.getCollisionRectangle().getUpperLeft().getX() + (this.paddle.getCollisionRectangle().getWidth() / 2);
        double y = this.paddle.getCollisionRectangle().getUpperLeft().getY() - 15;

        double currentSpeed = 5.0;
        if (remainingBalls.getValue() > 0) {
            LevelConfig config = this.game.getLevelConfig();
            if (config != null) {
                currentSpeed = config.ballSpeed;
            }
        }

        // Create the first ball (going left)
        Ball b1 = new Ball(x, y, 6, Color.BLACK, this.environment);
        b1.setVelocity(Velocity.fromAngleAndSpeed(330, currentSpeed));
        b1.addToGame(this.game);

        // Create the second ball (going right)
        Ball b2 = new Ball(x, y, 6, Color.BLACK, this.environment);
        b2.setVelocity(Velocity.fromAngleAndSpeed(30, currentSpeed));
        b2.addToGame(this.game);

        // Update the ball counter so the game doesn't end by mistake
        this.remainingBalls.increase(2);
    }
}