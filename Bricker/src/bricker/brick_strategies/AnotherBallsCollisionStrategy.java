package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Implements a collision strategy where the colliding brick is removed from the game,
 * and create another ball.
 */
public class AnotherBallsCollisionStrategy implements CollisionStrategy {
    //constansts
    private int PUCK_BALL_NUMBER = 2;

    //private members
    private CollisionStrategy collisionStrategy;
    private BrickerGameManager brickerGameManager;

    /**
     * Constructs a new ExtraLifeCollisionStrategy instance.
     *
     * @param brickerGameManager The game manager responsible for managing game objects and logic.
     * @param collisionStrategy The collision strategy to be wrapped.
     */
    public AnotherBallsCollisionStrategy(BrickerGameManager brickerGameManager,
                                         CollisionStrategy collisionStrategy){
        this.brickerGameManager=brickerGameManager;
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handles collision behavior for a game object.
     *
     * @param first The first game object involved in the collision.
     * @param second The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject first, GameObject second) {
        this.collisionStrategy.onCollision(first,second);
        for (int i = 0; i<PUCK_BALL_NUMBER; i++){
            this.brickerGameManager.createPuckBall(first.getCenter());
        }
    }
}
