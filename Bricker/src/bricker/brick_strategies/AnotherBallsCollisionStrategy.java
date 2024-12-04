package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class AnotherBallsCollisionStrategy implements CollisionStrategy {
    //constansts
    private int PUCK_BALL_NUMBER = 2;

    //private members
    private CollisionStrategy collisionStrategy;
    private BrickerGameManager brickerGameManager;

    public AnotherBallsCollisionStrategy(BrickerGameManager brickerGameManager,CollisionStrategy collisionStrategy){
        this.brickerGameManager=brickerGameManager;
        this.collisionStrategy = collisionStrategy;
    }

    @Override
    public void onCollision(GameObject first, GameObject second) {
        this.collisionStrategy.onCollision(first,second);
        for (int i = 0; i<PUCK_BALL_NUMBER; i++){
            this.brickerGameManager.createPuckBall(first.getCenter());
        }
    }
}
