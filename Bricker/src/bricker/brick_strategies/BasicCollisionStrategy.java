package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * Implements a basic collision strategy where the colliding brick is removed from the game.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;
    /**
     * Constructs a new BasicCollisionStrategy instance.
     *
     * @param gameManager The game manager responsible for managing game objects and logic.
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Handles collision behavior for a game object.
     *
     * @param gameObject1 The first game object involved in the collision (typically a brick).
     * @param gameObject2 The second game object involved in the collision.
     */
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
            if(gameManager.deleteObject(gameObject1,Layer.DEFAULT)){
                gameManager.getBricksCounter().decrement();
            }
        System.out.println("Collision with brick detected");
    }




}

