package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    // Constructor for user paddle
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        Vector2 newTopLeft = getTopLeftCorner().add(movementDir.mult(300 * deltaTime));
        if (newTopLeft.x() < 0) {
            newTopLeft = new Vector2(0, newTopLeft.y());
        }
        if (newTopLeft.x() + getDimensions().x() > windowDimensions.x()) {
            newTopLeft = new Vector2(windowDimensions.x() - getDimensions().x(), newTopLeft.y());
        }
        setTopLeftCorner(newTopLeft);
    }
}
