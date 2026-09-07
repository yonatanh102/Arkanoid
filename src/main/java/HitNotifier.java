/**
 * The {@code HitNotifier} interface should be implemented by objects that can notify
 * {@link HitListener}s about hit events.
 *
 * <p>This interface is part of the Observer design pattern, where the notifier
 * (e.g., a block) updates listeners (e.g., score tracker, ball remover)
 * when a hit occurs.</p>
 */
public interface HitNotifier {
    /**
     * Adds a {@link HitListener} to the list of listeners that will be notified when a hit event occurs.
     * @param hl the listener to be added
     */
    void addHitListener(HitListener hl);
    /**
     * Removes a {@link HitListener} from the list of listeners that are notified of hit events.
     * @param hl the listener to be removed
     */
    void removeHitListener(HitListener hl);
}
