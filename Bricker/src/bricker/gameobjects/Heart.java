package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    // Class fields
    private BrickerGameManager brickerGameManager;
    private LivesCounter livesCounter;
    private String collisionTag;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager brickerGameManager,
                 LivesCounter livesCounter,
                 String collisionTag) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.livesCounter = livesCounter;
        this.collisionTag = collisionTag;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision){
        super.onCollisionEnter(other, collision);
        this.livesCounter.addLife();
        this.brickerGameManager.deleteObject(this, Layer.DEFAULT);
    }

    /**
     * This method checks if the object should collide with the other object.
     * @param other the other game object.
     * @return true if the object should collide with the other object.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(this.collisionTag);
    }



    /**
     * This method is updated at every frame.
     * It deletes the object if it is out of bounds.
     * @param deltaTime : The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        boardersCheckAndDeleting();
    }

    /**
     * This method check if the heart is in the boarders, and if not - delete it.
     */
    private void boardersCheckAndDeleting() {
        double heartHeight = this.getCenter().y();
        if (heartHeight > this.brickerGameManager.getWindowDimensions().y()){
            brickerGameManager.deleteObject(this, Layer.DEFAULT);
        }
    }
}

