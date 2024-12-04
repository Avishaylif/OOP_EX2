package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static java.lang.Math.max;

public class TurboModeCollisionStrategy implements CollisionStrategy {
    private static final String REGULAR_BALL_TAG = "Ball";
    private final float VELOCITY_FACTOR = 1.4f;
    private final int NUM_OF_COLLISONS = 6;



    private BrickerGameManager brickerGameManager;
    private Renderable newBallImage;

    private Renderable originalBallImage;
    private Vector2 originalVelocity;
    private int initialCollisionCount;
    private CollisionStrategy collisionStrategy;
    private boolean turboModeActive = false;


    public TurboModeCollisionStrategy(BrickerGameManager brickerGameManager,
                                      CollisionStrategy collisionStrategy) {
        this.brickerGameManager = brickerGameManager;
        Ball ball = brickerGameManager.getBall();
        this.originalBallImage = ball.renderer().getRenderable();
        this.originalVelocity = ball.getVelocity();
        this.collisionStrategy = collisionStrategy;
    }
    
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        this.collisionStrategy.onCollision(gameObject1, gameObject2);
        // Check if the collided object is a regular ball. Skip if the ball is already in TurboMode or another state,
        //thanks to the upcoming TAG changing.
        if (!(gameObject1.getTag().equals(REGULAR_BALL_TAG))) {
            return;
        }
        Ball ball = (Ball) gameObject1;
        if(turboModeActive){
            //note the max function, in case were in the start of the game
            int relativeCollisionCount = max(ball.getCollisionCounter() - initialCollisionCount,0);
            // If the relative collision count exceeds the threshold, reset the ball
            if (relativeCollisionCount >= NUM_OF_COLLISONS) {
                resetBall(ball);
            }
        } else {
            activateTurboMode(ball);
            }
        
        }

    private void activateTurboMode(Ball ball) {
        //the initialization here helps to avoid double-activate of the mode.
        this.newBallImage = brickerGameManager.getTurboBallImage();
        this.initialCollisionCount = ball.getCollisionCounter();
        turboModeActive = true;
        ball.setVelocity(ball.getVelocity().mult(VELOCITY_FACTOR));
        ball.renderer().setRenderable(newBallImage);
    }


    private void resetBall(Ball ball) {
        ball.setVelocity(originalVelocity);
        ball.renderer().setRenderable(originalBallImage);

    }
}
