/** score tracking counter to use in game.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Constructs a new counter.
     * @param scoreCounter - the counter for tracking the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * score increase logic.
     * @param beingHit the ball hitting the object
     * @param hitter the object that being hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}
