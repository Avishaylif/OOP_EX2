package bricker.brick_strategies;


import bricker.main.BrickerGameManager;
import danogl.GameObject;


/**
 * Implements a collision strategy where the colliding brick is removed from the game, and create another paddle.
 */
public class SecondPaddleColissionStrategy implements CollisionStrategy{
    private BrickerGameManager brickerGameManager;
    private CollisionStrategy collisionStrategy;

    public SecondPaddleColissionStrategy(BrickerGameManager brickerGameManager, CollisionStrategy collisionStrategy){
        this.brickerGameManager = brickerGameManager;
        this.collisionStrategy  = collisionStrategy;
    }




    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        this.collisionStrategy.onCollision(gameObject1, gameObject2);
        this.brickerGameManager.createSecondPaddle();
    }
}
