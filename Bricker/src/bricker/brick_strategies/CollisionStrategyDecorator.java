package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Defines the behavior to execute when a collision occurs between game objects.
 */
public abstract class CollisionStrategyDecorator implements CollisionStrategy {
    protected final CollisionStrategy wrappedStrategy;
    public CollisionStrategyDecorator(CollisionStrategy wrappedStrategy) {
        this.wrappedStrategy = wrappedStrategy;
    }

    /**
     * Executes a collision handling strategy for the given game objects.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        wrappedStrategy.onCollision(gameObject1, gameObject2);
    }
}

