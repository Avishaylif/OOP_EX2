package bricker.main;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.util.Random;

import static java.lang.System.exit;

public class BrickerGameManager extends GameManager {
    private UserInputListener inputListener;
    private int numColumns = 10; // Default value
    private int numRows = 7; // Default value
    private int numLives = 3; // Default value
    private List<GameObject> hearts = new ArrayList<>();
    private Ball ball;
    private Paddle paddle;
    public void removeGameObject(GameObject object) {
        gameObjects().removeGameObject(object);
    }

//TODO: 1. change Magic numbers to variable
//    2. color of the walls or nulls?
//    3. debug: background.setCoordinatesSpace(CoordinateSpace.CAMERA_COORDINATES);
//    4. Readme: why choose the second approach for remove game object, (1.7) ;


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numColumns, int numRows) {
        super(windowTitle, windowDimensions);
        this.numColumns = numColumns;
        this.numRows = numRows;
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Renderable ballImage = imageReader.readImage("assets/assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/assets/blop.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);
        float ballVelX = 300;
        float ballVelY = 300;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelX = -ballVelX;
        }
        if (random.nextBoolean()) {
            ballVelY = -ballVelY;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        this.ball = ball;
        this.paddle = createUserPaddle(imageReader, windowDimensions, inputListener);
        createWalls(windowDimensions);
        createBackground(imageReader, windowController);
        CollisionStrategy collisionStrategy = new BasicCollisionStrategy(this);
        float totalBrickWidth = 8 * 15;
        float totalBrickHeight = 7 * 15;

        float xStart = (windowDimensions.x() - totalBrickWidth) / 2; // Center bricks horizontally
        float yStart = 50; // Start drawing bricks below the top wall
        Renderable brickImage = imageReader.readImage("assets/assets/brick.png", true);
// Get the width of the area between the walls
        float availableWidth = windowDimensions.x() - 20;  // 10 units for each wall (left + right)

// Set the height of each brick to 15 pixels
        float brickHeight = 15;
        int bricksPerRow = 8;  // Number of bricks in each row, can be adjusted as needed

// Calculate the width of each brick based on the number of bricks in the row
        float brickWidth = availableWidth / bricksPerRow;

// Create the bricks (assuming 2 rows)
        int numRows = 7;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < bricksPerRow; col++) {
                // Calculate the position of the brick
                float x = 10 + col * (brickWidth + 2);  // 10 for the left wall
                float y = row * (brickHeight + 2);  // Space bricks vertically

                // Create the brick and add it to the game
                Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, brickHeight),
                        imageReader.readImage("assets/assets/brick.png", true), collisionStrategy);
                gameObjects().addGameObject(brick);
            }
        }
        int screenWidth = 800;
        int screenHeight = 600;
        int heartSize = 20;

        for (int i = 0; i < numLives; i++) {
            Vector2 position = new Vector2(10 + i * (heartSize + 10), screenHeight - heartSize - 10);
            Renderable heartImage = imageReader.readImage("assets/assets/heart.png", true);
            GameObject heart = new GameObject(position, new Vector2(heartSize, heartSize), heartImage);
            hearts.add(heart);
            gameObjects().addGameObject(heart, Layer.UI);
        }





    }









    private void createBackground(ImageReader imageReader, WindowController windowController) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",
                false);
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    private Paddle createUserPaddle(ImageReader imageReader, Vector2 windowDimensions, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/assets/paddle.png", true);
        Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, inputListener, windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);
        return userPaddle;
    }


    private void createWalls(Vector2 windowDimensions) {
        Renderable wallImage = new RectangleRenderable(Color.GRAY);

        // Left wall
        GameObject leftWall = new GameObject(new Vector2(0, 0), new Vector2(10, windowDimensions.y()), wallImage);
        gameObjects().addGameObject(leftWall);

        // Right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - 10, 0), new Vector2(10, windowDimensions.y()), wallImage);
        gameObjects().addGameObject(rightWall);

        // Top wall
        GameObject topWall = new GameObject(new Vector2(0, 0), new Vector2(windowDimensions.x(), 10), wallImage);
        gameObjects().addGameObject(topWall);
    }


    public static void main(String[] args)
    {
        int columns = 8;
        int rows = 7;
        if (args.length == 2) {
                columns = Integer.parseInt(args[0]);
                rows = Integer.parseInt(args[1]);
            }
        new BrickerGameManager("Bricker", new Vector2(800, 600),columns,rows).run();

    }


}
// update me that you see this comment (1/12/2024, 20:30)!!!