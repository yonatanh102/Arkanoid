/**
 * the class responsible for removing a ball the exits its borders.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter remainingBalls;

    /**
     * Constructs a new BallRemover.
     * @param game - the game
     * @param counter - counter for the remaining balls in the game
     */
    public BallRemover(Game game, Counter counter) {
        this.game = game;
        this.remainingBalls = counter;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
