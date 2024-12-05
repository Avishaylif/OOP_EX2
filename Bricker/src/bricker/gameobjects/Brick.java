package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick in the game with collision behavior.
 */
public class Brick extends GameObject {
    private static final String BRICK_TAG = "Brick";

    /**
     * The collision strategy applied when this brick collides with another object.
     */
    public final CollisionStrategy collisionStrategy;

    /**
     * Constructs a new Brick object.
     *
     * @param topLeftCorner The top-left corner of the brick's position in the game world.
     * @param dimensions The width and height of the brick.
     * @param renderable The visual representation of the brick.
     * @param collisionStrategy The strategy to execute when the brick collides with another object.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.setTag(BRICK_TAG);
    }

    /**
     * Handles logic when the brick collides with another game object.
     *
     * @param other The other game object involved in the collision.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, danogl.collisions.Collision collision) {
        if ((other instanceof Ball)) {
            collisionStrategy.onCollision(this, other);
        }
    }
}




