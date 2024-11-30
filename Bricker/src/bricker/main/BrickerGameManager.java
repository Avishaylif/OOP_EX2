package bricker.main;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

import static danogl.gui.ImageReader.*;
import static javax.swing.text.html.CSS.Attribute.BORDER_COLOR;

public class BrickerGameManager extends GameManager{
    private UserInputListener inputListener;
//TODO: 1. change Magic numbers to variable
//    2. color of the walls or nulls?
//    3. debug: background.setCoordinatesSpace(CoordinateSpace.CAMERA_COORDINATES);


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Renderable ballImage = imageReader.readImage("assets/BouncingBallBasic/assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/BouncingBallBasic/assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);

        createUserPaddle(imageReader, windowDimensions, inputListener);
        createWalls(windowDimensions);
        createBackground(imageReader, windowDimensions);


    }

    private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable background = new RectangleRenderable(Color.BLACK);
        GameObject bg = new GameObject(Vector2.ZERO, windowDimensions, background);
        //        background.setCoordinatesSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(bg, Layer.BACKGROUND);
    }

    private void createUserPaddle(ImageReader imageReader, Vector2 windowDimensions, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/BouncingBallBasic/assets/paddle.png", true);
        Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, inputListener, windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);
    }

//    private void createAiPaddles(ImageReader imageReader, Vector2 windowDimensions) {
//        Renderable paddleImage = imageReader.readImage("assets/BouncingBallBasic/assets/paddle.png", true);
//           Paddle aiPaddle = new Paddle(Vector2.ZERO, new Vector2(200, 20), paddleImage);
//            aiPaddle.setCenter(new Vector2(windowDimensions.x() / 2, 30));
//            gameObjects().addGameObject(aiPaddle);
//    }



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

    public static void main(String[] args) {

        new BrickerGameManager("Bricker", new Vector2(800, 600)).run();
    }


}
// update me that you see this comment (30/11/2024, 23:10)!!!