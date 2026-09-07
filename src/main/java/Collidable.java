/**
 * The {@code Collidable} interface represents objects that can be collided with,
 * such as blocks, walls, or paddles in a game.
 */
public interface Collidable {
    /**
     * Returns the shape that defines the collision area of the object.
     * @return the collision {@link Rectangle}
     */
    Rectangle getCollisionRectangle();
    /**
     * Notifies the object that a collision occurred at the given point with the given velocity.
     * The object can calculate and return the new velocity after the hit.
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the velocity before the collision
     * @param hitter - the ball hitting the collidable object
     * @return the new velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
