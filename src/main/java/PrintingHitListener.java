/**
 * The PrintingHitListener class implements the HitListener interface and
 * prints a message to the console whenever a block is hit.
 * This is mainly used for debugging purposes.
 */
public class PrintingHitListener implements HitListener {
    /**
     * This method is called whenever the beingHit block is hit.
     * It prints a simple message to the console.
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}
