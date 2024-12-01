package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {
    public final CollisionStrategy collisionStrategy;

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    @Override
    public void onCollisionEnter(GameObject other, danogl.collisions.Collision collision) {
        // Example: Check if the colliding object is of a specific type (e.g., Ball)
//        if (other instanceof Ball) {
//            System.out.println("Collision detected with a ball!");
            // Call the collision strategy
        if ((other instanceof Ball)) {
            collisionStrategy.onCollision(this, other);
        }

    }
}




