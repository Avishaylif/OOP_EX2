package bricker.brick_strategies;

import danogl.GameObject;
/**
 * Defines the behavior to execute when a collision occurs between game objects.
 */
public interface CollisionStrategy {
    /**
     * Executes a collision handling strategy for the given game objects.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    void onCollision(GameObject gameObject1, GameObject gameObject2);
}
