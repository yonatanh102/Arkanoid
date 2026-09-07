/**
 * The {@code CollisionInfo} class holds information about a collision:
 * the point where the collision occurred and the object that was hit.
 * It is typically used by the game environment or moving objects like balls
 * to determine how to respond to a collision.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;
    /**
     * Constructs a {@code CollisionInfo} instance.
     * @param collisionPoint  the point at which the collision occurred
     * @param collisionObject the object that was collided with
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }
    /**
     * Returns the point at which the collision occurred.
     * @return the collision point
     */
    public Point collisionPoint() {
        return (this.collisionPoint);
    }
    /**
     * Returns the collidable object that was involved in the collision.
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return (this.collisionObject);
    }
}
