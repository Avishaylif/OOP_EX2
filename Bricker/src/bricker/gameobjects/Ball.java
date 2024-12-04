package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a ball in the game with collision behavior and sound.
 */
public class Ball extends GameObject {
    private final String BALL_TAG = "Ball";
    private int collisionCounter = 0;
    private Sound collisionSound;


    /**
     * Constructs a new Ball object.
     *
     * @param topLeftCorner The top-left corner of the ball's position in the game world.
     * @param dimensions The width and height of the ball.
     * @param renderable The visual representation of the ball.
     * @param collisionSound The sound played when the ball collides with another object.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.setTag(BALL_TAG);
    }

    /**
     * Handles logic when the ball collides with another game object.
     *
     * @param other The other game object involved in the collision.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter++;
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }


    /**
     * Returns the number of collisions the ball has had.
     *
     * @return The collision count.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
//תוהה לעצמי אם כדאי להוסיף כאן את פונקציית הבדיקה של הכדור ביחס לחלון