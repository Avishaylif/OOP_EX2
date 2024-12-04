package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.*;

public class NumericCounter extends GameObject {
    // Constants
    private final int HIGH = 3;
    private final int MEDIUM = 2;
    private final int LOW = 1;

    // Class fields
    private Renderable text;
    private int previousLivesNumber;
    private LivesCounter livesCounter;

    private String prompt;
    private TextRenderable textRenderable;

    /**
     * Constructor of new GameObject instance that represent numericly the player's lives.
     *
     * @param topLeftCorner The location of the object.
     * @param dimensions Width and height in the window coordinates.
     * @param renderable represent the object.
     * @param livesCounter represent the lives management.
     */
    public NumericCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable,
                          LivesCounter livesCounter) {
        //set game object
        super(topLeftCorner, dimensions, renderable);
        this.livesCounter = livesCounter;
        this.previousLivesNumber = livesCounter.getCurrentLivesNumber();
        this.prompt = String.valueOf(this.previousLivesNumber);
        this.textRenderable = renderable;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        if (this.livesCounter.getCurrentLivesNumber() != this.previousLivesNumber) {
            this.previousLivesNumber = this.livesCounter.getCurrentLivesNumber();
            this.prompt = String.valueOf(this.previousLivesNumber);
            this.textRenderable.setString(this.prompt);
            changeColor();
        }
    }

    /**
     * manage the color of the text.
     */
    private void changeColor() {
        int currentLives  = this.livesCounter.getCurrentLivesNumber();
        if (currentLives == LOW) {
            this.textRenderable.setColor(Color.RED);
        }
        else if (currentLives == MEDIUM) {
            this.textRenderable.setColor(Color.YELLOW);
        }
        else if (currentLives >= HIGH) {
            this.textRenderable.setColor(Color.GREEN);
        }
    }
}
