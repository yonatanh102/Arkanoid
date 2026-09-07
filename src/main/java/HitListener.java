/**
 * The {@code HitListener} interface should be implemented by any class
 * that wants to be notified of hit events in the game.
 *
 * <p>This is part of the Observer pattern used for reacting to events such as
 * a block being hit by a ball.</p>
 */
public interface HitListener {
    /**
     * This method is called whenever the {@code beingHit} object is hit.
     * @param beingHit the {@link Block} that was hit
     * @param hitter   the {@link Ball} that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
