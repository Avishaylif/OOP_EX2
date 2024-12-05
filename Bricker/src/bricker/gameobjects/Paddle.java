package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * Represents a paddle controlled by the player in the game.
 */
public class Paddle extends GameObject {
    private final String PADDLE_TAG = "Paddle";

    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private static final int MULT_DELTA = 300;

    /**
     * Constructs a new Paddle object.
     *
     * @param topLeftCorner The top-left corner of the paddle's position in the game world.
     * @param dimensions The width and height of the paddle.
     * @param renderable The visual representation of the paddle.
     * @param inputListener Listens to user input to control the paddle.
     * @param windowDimensions The dimensions of the game window for boundary constraints.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.setTag(PADDLE_TAG);
    }

    /**
     * Updates the paddle's position based on user input.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = calculateMovementDirection();
        updatePosition(movementDir, deltaTime);
    }

    /**
     * Calculates the movement direction based on user input.
     *
     * @return A Vector2 representing the movement direction.
     */
    private Vector2 calculateMovementDirection() {
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        return movementDir;
    }

    /**
     * Updates the paddle's position based on the movement direction and delta time.
     *
     * @param movementDir The direction of movement.
     * @param deltaTime The time elapsed since the last frame.
     */
    private void updatePosition(Vector2 movementDir, float deltaTime) {
        Vector2 newTopLeft = getTopLeftCorner().add(movementDir.mult(MULT_DELTA * deltaTime));
        if (newTopLeft.x() < 0) {
            newTopLeft = new Vector2(0, newTopLeft.y());
        }
        if (newTopLeft.x() + getDimensions().x() > windowDimensions.x()) {
            newTopLeft = new Vector2(windowDimensions.x() - getDimensions().x(), newTopLeft.y());
        }
        setTopLeftCorner(newTopLeft);
    }

}
