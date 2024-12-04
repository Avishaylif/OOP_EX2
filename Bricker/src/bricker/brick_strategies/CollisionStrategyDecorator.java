package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public abstract class CollisionStrategyDecorator implements CollisionStrategy {
    protected final CollisionStrategy wrappedStrategy;
    public CollisionStrategyDecorator(CollisionStrategy wrappedStrategy) {
        this.wrappedStrategy = wrappedStrategy;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        wrappedStrategy.onCollision(gameObject1, gameObject2);
    }
}

