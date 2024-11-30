package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    public Ball(Vector2 tpoLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(tpoLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter++;
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    private int collisionCounter = 0;
    public int getCollisionCounter() {
        return collisionCounter;
    }
    private Sound collisionSound;
}
