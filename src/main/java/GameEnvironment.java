import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameEnvironment} class holds a collection of objects that can be collided with.
 * It is used by the game to detect and handle collisions between moving objects (like a ball)
 * and static collidable objects (like blocks or the paddle).
 */
public class GameEnvironment {
    private final List<Collidable> collidables = new ArrayList<>();
    /**
     * Adds a {@link Collidable} object to the environment.
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.collidables.add(c);
        }
    }
    /**
     * Removes a {@link Collidable} object from the environment.
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
    /**
     * Determines the closest collision that will occur if an object moves along a given trajectory.
     *
     * @param trajectory the path the object is expected to move along
     * @return the {@link CollisionInfo} of the closest collision, or {@code null} if no collision will occur
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (trajectory == null) {
            return null;
        }
        CollisionInfo closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Collidable collidable : this.collidables) {
            Rectangle rect = collidable.getCollisionRectangle();
            Point intersection = trajectory.closestIntersectionToStartOfLine(rect);
            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = new CollisionInfo(intersection, collidable);
                }
            }
        }
        return closest;
    }
    /**
     * Returns the list of all collidable objects in the environment.
     * @return the list of collidables
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }
}
