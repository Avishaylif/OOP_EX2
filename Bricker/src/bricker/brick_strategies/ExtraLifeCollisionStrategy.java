package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class ExtraLifeCollisionStrategy implements CollisionStrategy{

    private BrickerGameManager brickerGameManager;
    private CollisionStrategy collisionStrategy;

    public ExtraLifeCollisionStrategy(BrickerGameManager brickerGameManager, CollisionStrategy collisionStrategy){
        this.collisionStrategy = collisionStrategy;
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        this.collisionStrategy.onCollision(gameObject1, gameObject2);
        this.brickerGameManager.createHeart(gameObject1.getCenter());
    }
}
