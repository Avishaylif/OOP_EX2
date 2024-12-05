package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Implements a collision strategy where the colliding brick is
 * removed from the game, and an extra life is created.
 */
public class ExtraLifeCollisionStrategy implements CollisionStrategy{

    private BrickerGameManager brickerGameManager;
    private CollisionStrategy collisionStrategy;

   /**
     * Constructs a new ExtraLifeCollisionStrategy instance.
     *
     * @param brickerGameManager The game manager responsible for managing game objects and logic.
     * @param collisionStrategy The collision strategy to be wrapped.
     */
    public ExtraLifeCollisionStrategy(BrickerGameManager brickerGameManager,
                                      CollisionStrategy collisionStrategy){
        this.collisionStrategy = collisionStrategy;
        this.brickerGameManager = brickerGameManager;
    }
    /**
     * Handles collision behavior for a game object.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        this.collisionStrategy.onCollision(gameObject1, gameObject2);
        this.brickerGameManager.createHeart(gameObject1.getCenter());
    }
}
