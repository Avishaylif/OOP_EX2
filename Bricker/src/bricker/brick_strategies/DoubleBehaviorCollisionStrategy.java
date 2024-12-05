package bricker.brick_strategies;
import danogl.GameObject;

/**
 * Defines the behavior to execute when a collision occurs between game objects.
 */
public class DoubleBehaviorCollisionStrategy extends CollisionStrategyDecorator{
    private final CollisionStrategy secondStrategy;
    /**
     * Constructs a new DoubleBehaviorCollisionStrategy instance.
     *
     * @param firstStrategy The first collision strategy to be wrapped.
     * @param secondStrategy The second collision strategy to be wrapped.
     */
    public DoubleBehaviorCollisionStrategy(CollisionStrategy firstStrategy, CollisionStrategy secondStrategy)
    {
        super(firstStrategy);
        this.secondStrategy = secondStrategy;
    }

    /**
     * Executes a collision handling strategy for the given game objects.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        super.onCollision(gameObject1, gameObject2);

        secondStrategy.onCollision(gameObject1, gameObject2);
    }

}
