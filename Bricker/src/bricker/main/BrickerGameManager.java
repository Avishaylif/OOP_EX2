package bricker.main;
import bricker.brick_strategies.*;
import bricker.gameobjects.*;

import danogl.util.Counter;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

import java.awt.*;
import java.util.Random;

/**
 * Manages the main game logic for the Bricker game, including initialization,
 * object creation, collision handling, and user interaction.
 */
public class BrickerGameManager extends GameManager {
    // Constants
    private static final int DEFAULT_NUM_COLUMNS = 10;
    private static final int DEFAULT_NUM_ROWS = 7;
    private static final int DEFAULT_NUM_LIVES = 3;
    private static final int DEFAULT_MAX_LIVES = 4;

    private static final int WALL_THICKNESS = 10;
    private static final int BALL_SIZE = 50;
    private static final int BALL_SPEED = 300;
    private static final int PADDLE_WIDTH = 200;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_OFFSET_FROM_BOTTOM = 30;
    private static final int BRICK_HEIGHT = 15;
    private static final int BRICK_SPACING = 2;
    private static final int HEART_SIZE = 20;
    private static final int HEART_SPACING = 10;
    private static final int HEART_OFFSET_FROM_BOTTOM = 10;
    private static final float HEART_VELOCITY = 100;
    private static final String TURBO_BALL_IMAGE = "assets/assets/redball.png";
    private static final String BRICK_IMAGE_PATH = "assets/assets/brick.png";
    private final int COUNTER_BUFFER = 5;
    private final int NUMERIC_COUNTER_SIZE = 20;
    private final String HEART_IMAGE_PATH = "assets/assets/heart.png";
    private final String WINNING_PROMPT = "You win! Play again?";
    private final String LOSING_PROMPT = "You lose! Play again?";
    private final float PUCK_BALL_SHRINK_FACTOR = 0.75f;
    private final float VELOCITY_FACTOR = 300;


    // Class fields
    private UserInputListener inputListener;
    private int numColumns = DEFAULT_NUM_COLUMNS;
    private int numRows = DEFAULT_NUM_ROWS;
    private Ball ball;
    private Counter bricksCounter;
    private Paddle paddle;
    private SecondPaddle secondPaddle;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private LivesCounter livesCounter;



//TODO: 1. change Magic numbers to variable
//    2. color of the walls or nulls?
//    3. debug: background.setCoordinatesSpace(CoordinateSpace.CAMERA_COORDINATES);
//    4. Readme: why choose the second approach for remove game object, (1.7) ;

    /**
     * Initializes the game manager with the specified window title and dimensions.
     *
     * @param windowTitle The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param numColumns Number of columns in the brick layout.
     * @param numRows Number of rows in the brick layout.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numColumns, int numRows) {
        super(windowTitle, windowDimensions);
        this.numColumns = numColumns;
        this.numRows = numRows;
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.paddle = createUserPaddle();
        this.livesCounter = new LivesCounter(DEFAULT_NUM_LIVES,DEFAULT_MAX_LIVES);

        createBall();
        createWalls();
        createBackground();
        //delete this
        BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(this);
        createBricks();
        //createHearts();
        createGraphicCounter();
        createNumericCounter();
    }




    public void createBall() {
        Renderable ballImage = imageReader.readImage("assets/assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/assets/blop.wav");
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, collisionSound);
       resetBall(ball);
        gameObjects().addGameObject(this.ball);
        ball.setVelocity(RandomizeBallVelocity(ball));
        this.gameObjects().addGameObject(this.ball);
    }



    /**
     * Reset the ball to the center of the window, with a random direction.
     * @param ball the ball that created.
     */
    private void resetBall(Ball ball) {
        this.ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED));
        this.ball.setCenter(this.windowDimensions.mult(0.5F));
    }

    public void createPuckBall(Vector2 location) {
        Renderable ballImage = imageReader.readImage("assets/assets/mockBall.png", true);
        Sound collisionSound = soundReader.readSound("assets/assets/blop.wav");
        PuckBall puckBall = new PuckBall(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE).mult(PUCK_BALL_SHRINK_FACTOR),
                ballImage, collisionSound, this);
        puckBall.setVelocity(RandomizeBallVelocity(puckBall));
        puckBall.setCenter(location);
        this.gameObjects().addGameObject(puckBall);
    }

    private Vector2 RandomizeBallVelocity(Ball ball) {
        float ballVelX = VELOCITY_FACTOR;
        float ballVelY = VELOCITY_FACTOR;
        Random random = new Random();
        if(ball.getTag().equals("Ball")) {
            if (random.nextBoolean()) {
                ballVelX = -ballVelX;
            }
            if (random.nextBoolean()) {
                ballVelY = -ballVelY;
            }
        }else{
            double angle = random.nextDouble() * Math.PI;
            ballVelX = (float)Math.cos(angle) * VELOCITY_FACTOR;
            ballVelY = (float)Math.sin(angle) * VELOCITY_FACTOR;
        }
        return (new Vector2(ballVelX, ballVelY));
    }








    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage("assets/assets/DARK_BG2_small.jpeg",
                false);
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    private Paddle createUserPaddle() {
        Renderable paddleImage = imageReader.readImage("assets/assets/paddle.png", true);
        Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, inputListener, windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);
        return userPaddle;
    }

    public void createSecondPaddle(){
        if (this.secondPaddle==null) {
            Renderable paddleImage = imageReader.readImage("assets/assets/paddle.png", true);
            this.secondPaddle = new SecondPaddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, inputListener, windowDimensions, 4, this);
            this.secondPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
            gameObjects().addGameObject(secondPaddle);
        }
    }


    private void createWalls() {
        Renderable wallImage = new RectangleRenderable(Color.GRAY);

        // Left wall
        GameObject leftWall = new GameObject(new Vector2(0, 0), new Vector2(10, windowDimensions.y()), wallImage);
        this.gameObjects().addGameObject(leftWall);

        // Right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - 10, 0), new Vector2(10, windowDimensions.y()), wallImage);
        this.gameObjects().addGameObject(rightWall);

        // Top wall
        GameObject topWall = new GameObject(new Vector2(0, 0), new Vector2(windowDimensions.x(), 10), wallImage);
        this.gameObjects().addGameObject(topWall);
    }

    private void createBricks() {
        //initialize the BrickerGameManager field "bricksCounter"
        this.bricksCounter = new Counter((int) (numColumns*numRows));

        // calculate the brick width
        float brickWidth = (windowDimensions.x() - 2 * WALL_THICKNESS - (numColumns - 1) * BRICK_SPACING) / numColumns;
        // create a collision strategy factory
        CollisionsStrategiesFactory collisionStrategyFactory = new CollisionsStrategiesFactory(this);
        // create the bricks
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                float x = WALL_THICKNESS + col * (brickWidth + BRICK_SPACING);
                float y = 100 + row * (BRICK_HEIGHT + BRICK_SPACING);
                Renderable brickImage = imageReader.readImage(BRICK_IMAGE_PATH, true);
                    Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, BRICK_HEIGHT),
                            brickImage, collisionStrategyFactory.initializeStrategy());
                    gameObjects().addGameObject(brick);
                }
            }
        }

/*
    private void createHearts() {
        Renderable heartImage = imageReader.readImage("assets/assets/heart.png", true);
        for (int i = 0; i < numLives; i++) {
            Vector2 position = new Vector2(10 + i * (HEART_SIZE + HEART_SPACING), windowDimensions.y() - HEART_SIZE - HEART_OFFSET_FROM_BOTTOM);
            GameObject heart = new GameObject(position, new Vector2(HEART_SIZE, HEART_SIZE), heartImage);
            hearts.add(heart);
            gameObjects().addGameObject(heart, Layer.UI);
        }
    }*/


    /**
     * Add a game object to the game.
     *
     * @param object The object to add.
     */
    public void addObject(GameObject object, int layer) {
        this.gameObjects().addGameObject(object, layer);
    }

    /**
     * Delete a game object from the game.
     *
     * @param object The object to delete.
     */
    public boolean deleteObject(GameObject object, int layer) {
        return this.gameObjects().removeGameObject(object, layer);
    }

    //TODO: remove this
    public void removeGameObject(GameObject object) {
        gameObjects().removeGameObject(object);
    }

    /**
     * Create the graphic lives counter.
     */
    private void createGraphicCounter() {
        Renderable heartImage = this.imageReader.readImage(HEART_IMAGE_PATH, false);
        GraphicCounter graphicCounter = new GraphicCounter(new Vector2(COUNTER_BUFFER,
                (this.windowDimensions.y()) + NUMERIC_COUNTER_SIZE + COUNTER_BUFFER),
                new Vector2(HEART_SIZE,HEART_SIZE), null,
                this.livesCounter,heartImage, this);
    }

    /**
     * Create the numeric lives counter.
     */
    private void createNumericCounter() {
        TextRenderable textNumeric = new TextRenderable(String.valueOf(DEFAULT_NUM_LIVES));
        textNumeric.setColor(Color.GREEN);
        NumericCounter numericCounter = new NumericCounter(
                new Vector2(this.windowDimensions.x()/5, (this.windowDimensions.y())-NUMERIC_COUNTER_SIZE),
                new Vector2(NUMERIC_COUNTER_SIZE,NUMERIC_COUNTER_SIZE),
                textNumeric,
                this.livesCounter);
        this.gameObjects().addGameObject(numericCounter, Layer.BACKGROUND);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        doesPlayerLoose();
        doesPlayerWon();

    }






    private void doesPlayerLoose(){
        double ballHeight = this.ball.getCenter().y();
        if (ballHeight > this.windowDimensions.y()) {//why >?
            this.livesCounter.removeLive();
            if(this.livesCounter.isPlayerAlive()){
                resetBall(ball);
            }else{
                if(this.windowController.openYesNoDialog(LOSING_PROMPT)){
                    this.windowController.resetGame();
                } else {
                this.windowController.closeWindow();
                }
            }
        }
    }

    public void doesPlayerWon(){
        boolean playerWon = bricksCounter.value()<=0;
        boolean playerPressOnW = inputListener.isKeyPressed(KeyEvent.VK_W);
        if(playerWon || playerPressOnW){
            if (this.windowController.openYesNoDialog(WINNING_PROMPT)) {
                this.windowController.resetGame();
            } else {
                this.windowController.closeWindow();
            }
        }
    }

    public Counter getBricksCounter(){
        return bricksCounter;
    }

    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    public static void main(String[] args){
        int columns = 8;
        int rows = 7;
        if (args.length == 2) {
                columns = Integer.parseInt(args[0]);
                rows = Integer.parseInt(args[1]);
            }
        new BrickerGameManager("Bricker", new Vector2(800, 600),columns,rows).run();

    }


    public void createHeart(Vector2 location) {
        Renderable heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        Heart heart = new Heart(Vector2.ZERO, new Vector2(HEART_SIZE, HEART_SIZE),heartImage,this,
                this.livesCounter,this.paddle.getTag());
        heart.setVelocity(Vector2.DOWN.mult(HEART_VELOCITY));
        heart.setCenter(location);
        this.gameObjects().addGameObject(heart);
    }

    public Ball getBall() {
        return ball;
    }

    public Renderable getTurboBallImage() {
        Renderable turboBallImage = imageReader.readImage(TURBO_BALL_IMAGE, true);
        return turboBallImage;
    }
}
