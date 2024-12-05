package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class represents a graphic counter of player's lives.
 */
public class GraphicCounter extends GameObject {
    //constants
    private static final int HEART_SIZE = 20;
    private static final int HEART_SPACING = 10;
    private static final int HEART_OFFSET_FROM_BOTTOM = 30;
    // Class fields
    private BrickerGameManager brickerGameManager;
    private LivesCounter livesCounter;
    private int prevLivesNum;
    private GameObject[] hearts;

    //methods

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public GraphicCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          LivesCounter livesCounter, Renderable heartImage,
                          BrickerGameManager brickerGameManager) {
        //set game object
        super(topLeftCorner, dimensions, renderable);
        //set the fields
        this.livesCounter = livesCounter;
        this.prevLivesNum = livesCounter.getCurrentLivesNumber();
        this.brickerGameManager = brickerGameManager;
        this.hearts = new GameObject[livesCounter.getMaxLives()];
        this.brickerGameManager.addObject(this, Layer.BACKGROUND);
        for (int i = 0; i < livesCounter.getMaxLives(); i++) {
            Vector2 position = new Vector2(HEART_SPACING + i * (HEART_SIZE + HEART_SPACING),
                    topLeftCorner.y() - HEART_SIZE - HEART_OFFSET_FROM_BOTTOM);
            this.hearts[i] = new GameObject(position, dimensions, heartImage);
            if (i < livesCounter.getCurrentLivesNumber()) {
                brickerGameManager.addObject(this.hearts[i], Layer.BACKGROUND);
            }
        }
    }

    /**
     * This method is updated at every frame.
     * It updates the graphic counter to show the current number of lives.
     * @param deltaTime : The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.getCurrentLivesNumber() != this.prevLivesNum) {
            this.prevLivesNum = this.livesCounter.getCurrentLivesNumber();
            for (int i = 0; i < this.prevLivesNum; i++) {
                this.brickerGameManager.addObject(this.hearts[i], Layer.BACKGROUND);
            }
            for (int j = this.prevLivesNum; j < this.livesCounter.getMaxLives(); j++) {
                this.brickerGameManager.deleteObject(this.hearts[j], Layer.BACKGROUND);
            }

        }

    }
}
