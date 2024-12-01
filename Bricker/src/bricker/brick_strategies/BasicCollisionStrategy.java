package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;

    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.removeGameObject(gameObject1);
        System.out.println("Collision with brick detected");
    }




}

