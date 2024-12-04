package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.gui.rendering.Renderable;

import java.util.Random;

/**
 * This class is a factory for creating collision strategies.
 * It creates a random collision strategy with the next probabilities:
 * 50% - BasicCollisionStrategy, 10% - AnotheBallCollisionStrategy,
 * 10% - TurboMoodeCollisionsStrategy, 10% - AnothePaddleCollisionStrategy,
 * 10% - ExtraLifeCollisionStrategy, 10% - Double strategy (two of the previous ones).
 *
 */
public class CollisionsStrategiesFactory {
    private final int RANDOM_NUMBER = 10;
    private final int MAX_NUMBER_OF_STRATEGIES = 3;
    private final int BASIC_STRATEGY_CHOICE = 4;
    private final int DOUBLE_STRATEGY_CHOICE = 5;
    private final int EXTRA_LIFE_STRATEGY_CHOICE = 6;
    private final int SECOND_PADDLE_STRATEGY_CHOICE = 7;
    private final int TURBO_STRATEGY_CHOICE = 8;
    private final int PUCK_BALLS_STRATEGY_CHOICE = 9;
    private int strategiesCounter;
    private Random rand;
    private BrickerGameManager brickerGameManager;

    public CollisionsStrategiesFactory(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }

    public CollisionStrategy initializeStrategy(){
        strategiesCounter=0;
        this.rand = new Random();
        int randChoice = this.rand.nextInt(RANDOM_NUMBER);
        return chooseStrategy(randChoice,new BasicCollisionStrategy(this.brickerGameManager));
    }

    private CollisionStrategy chooseStrategy(int randChoice, CollisionStrategy collisionStrategy){

        switch (randChoice){
            case BASIC_STRATEGY_CHOICE:
                return new BasicCollisionStrategy(brickerGameManager);
            case DOUBLE_STRATEGY_CHOICE:{
                return createDoubleStrategy(collisionStrategy);
            }
            case EXTRA_LIFE_STRATEGY_CHOICE:{
                return new ExtraLifeCollisionStrategy(brickerGameManager, collisionStrategy);
            }
            case SECOND_PADDLE_STRATEGY_CHOICE:{
                return new SecondPaddleColissionStrategy(brickerGameManager, collisionStrategy);
            }
            case TURBO_STRATEGY_CHOICE:{
                return new TurboModeCollisionStrategy(brickerGameManager, collisionStrategy);
            }
            case PUCK_BALLS_STRATEGY_CHOICE:{
                return new AnotherBallsCollisionStrategy(brickerGameManager, collisionStrategy);
            }
            default:
                return new BasicCollisionStrategy(brickerGameManager);
        }
    }

    private CollisionStrategy createDoubleStrategy(CollisionStrategy collisionStrategy) {
        int randChoice;
        CollisionStrategy firstCollisionStrategy;
        CollisionStrategy secondCollisionStrategy;

        if(this.strategiesCounter ==0){
            strategiesCounter++;
            randChoice = this.rand.nextInt(BASIC_STRATEGY_CHOICE)+BASIC_STRATEGY_CHOICE;
            firstCollisionStrategy = chooseStrategy(randChoice,collisionStrategy);
            randChoice = this.rand.nextInt(BASIC_STRATEGY_CHOICE)+BASIC_STRATEGY_CHOICE;
            secondCollisionStrategy =chooseStrategy(randChoice,collisionStrategy);
        }else{
            randChoice = this.rand.nextInt(DOUBLE_STRATEGY_CHOICE)+DOUBLE_STRATEGY_CHOICE;
            firstCollisionStrategy = chooseStrategy(randChoice,collisionStrategy);
            randChoice = this.rand.nextInt(DOUBLE_STRATEGY_CHOICE)+DOUBLE_STRATEGY_CHOICE;
            secondCollisionStrategy =chooseStrategy(randChoice,collisionStrategy);
            strategiesCounter++;
        }
        return new DoubleBehaviorCollisionStrategy(firstCollisionStrategy,secondCollisionStrategy);
    }


}
