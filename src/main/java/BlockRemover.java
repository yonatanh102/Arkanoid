/**
 * The BlockRemover class is a HitListener that removes blocks from the game
 * when they are hit by a ball of the same color, and keeps track of the number of remaining blocks.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;
    /**
     * Constructs a BlockRemover.
     * @param game           the game from which blocks will be removed
     * @param remainingBlocks a counter for the number of blocks left in the game
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }
    /**
     * Called whenever a block is hit.
     * If the block and ball have the same color, the block is removed from the game,
     * the counter is decremented, and the listener is removed from the block.
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.ballColorMatch(hitter)) {
            beingHit.removeFromGame(this.game);
            remainingBlocks.decrease(1);
            beingHit.removeHitListener(this);
            SoundManager.playBlockHit();
        }
    }
}
