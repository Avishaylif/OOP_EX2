package bricker.gameobjects;

import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;


public class PuckBall extends Ball{
    //constants
    private final String PUCK_BALL_TAG = "Puck ball";

    private BrickerGameManager brickerGameManager;



    /**
     * Constructs a new PuckBall object.
     *
     * @param topLeftCorner  The top-left corner of the ball's position in the game world.
     * @param dimensions     The width and height of the ball.
     * @param renderable     The visual representation of the ball.
     * @param collisionSound The sound played when the ball collides with another object.
     * @param brickerGameManager is the current game manager.
     */
    public PuckBall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                    BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.brickerGameManager = brickerGameManager;
        this.setTag(PUCK_BALL_TAG);
    }

    /**
     * This method updates the puckball. If the puckball is out of bounds, it deletes it.
     *
     * @param deltaTime the time passed since the last update.
     */
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        double ballHeight = this.getCenter().y();
        if (ballHeight > this.brickerGameManager.getWindowDimensions().y()){
            brickerGameManager.deleteObject(this, Layer.DEFAULT);
        }
    }
}
