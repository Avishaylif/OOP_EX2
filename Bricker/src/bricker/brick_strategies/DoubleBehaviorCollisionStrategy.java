package bricker.brick_strategies;

import danogl.GameObject;

public class DoubleBehaviorCollisionStrategy extends CollisionStrategyDecorator{
    private final CollisionStrategy secondStrategy;


    public DoubleBehaviorCollisionStrategy(CollisionStrategy firstStrategy, CollisionStrategy secondStrategy) {
        super(firstStrategy);
        this.secondStrategy = secondStrategy;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        super.onCollision(gameObject1, gameObject2);

        secondStrategy.onCollision(gameObject1, gameObject2);
    }

}
