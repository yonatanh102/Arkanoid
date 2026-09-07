import java.util.List;
import java.util.Random;

public class PowerUpSpawner implements HitListener {
    private final Game game;
    private final Paddle paddle;
    private final GameEnvironment environment;
    private final Counter remainingBalls;
    private final List<Ball> activeBalls;
    private final Random random;

    public PowerUpSpawner(Game game, Paddle paddle, GameEnvironment env, Counter remainingBalls, List<Ball> activeBalls) {
        this.game = game;
        this.paddle = paddle;
        this.environment = env;
        this.remainingBalls = remainingBalls;
        this.activeBalls = activeBalls;
        this.random = new Random();
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // generate a random number between 0 and 9 (i.e., 10% chance for each type of power-up, or 50% chance that something will drop)
        int chance = random.nextInt(10); 
        double x = beingHit.getCollisionRectangle().getUpperLeft().getX() + (beingHit.getCollisionRectangle().getWidth() / 2);
        double y = beingHit.getCollisionRectangle().getUpperLeft().getY() + (beingHit.getCollisionRectangle().getHeight() / 2);
        Point dropPoint = new Point(x, y);

        PowerUp powerUp = null;

        // switch case to determine which power-up to spawn based on the random chance
        switch (chance) {
            case 0:
                powerUp = new ExpandPaddlePowerUp(dropPoint, game, paddle);
                break;
            case 1:
                powerUp = new ShrinkPaddlePowerUp(dropPoint, game, paddle);
                break;
            case 2:
                powerUp = new MultiBallPowerUp(dropPoint, game, paddle, environment, remainingBalls);
                break;
            case 3:
                powerUp = new ExpandBallPowerUp(dropPoint, game, paddle, activeBalls);
                break;
            case 4:
                powerUp = new ShrinkBallPowerUp(dropPoint, game, paddle, activeBalls);
                break;
            case 5:
                powerUp = new ChangeBallColorPowerUp(dropPoint, game, paddle, activeBalls);
                break;
        }

        // if a power-up was created, add it to the game
        if (powerUp != null) {
            powerUp.addToGame();
        }
    }
}