package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a secondary paddle that disappears after 4 collisions.
 */
public class SecondPaddle extends Paddle {

    private static final int MAX_COLLISIONS = 4; // Deafult number of collisions before disappearance
    private final String EXTRA_PADDLE_TAG = "Extra Paddle";

    private int maxCollisions = MAX_COLLISIONS;
    private int collisionCounter = 0; // Tracks the number of collisions
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs a new SecondaryPaddle object.
     *
     * @param topLeftCorner The top-left corner of the paddle's position in the game world.
     * @param dimensions The width and height of the paddle.
     * @param renderable The visual representation of the paddle.
     * @param inputListener Listens to user input to control the paddle.
     * @param windowDimensions The dimensions of the game window for boundary constraints.
     * @param maxCollisions the maximum collisions before remove.
     * @param brickerGameManager Reference to the brickerGameManager.
     */
    public SecondPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                           UserInputListener inputListener, Vector2 windowDimensions, int maxCollisions,
                        BrickerGameManager brickerGameManager ) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.brickerGameManager = brickerGameManager;
        this.maxCollisions = maxCollisions;
        this.setTag(EXTRA_PADDLE_TAG);
    }




    /**
     * Updates the paddle's position and handles collision-specific logic.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * Handles collision behavior. Increments the collision counter and removes the paddle
     * after the specified number of collisions.
     *
     * @param other The other object involved in the collision.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("Ball") || other.getTag().equals("Puck Ball")) {
            collisionCounter++;
        }

        // Remove paddle if max collisions reached
        if (collisionCounter >= maxCollisions) {
            brickerGameManager.deleteObject(this, Layer.DEFAULT);
        }
    }
}

