package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static danogl.gui.ImageReader.*;

public class BrickerGameManager extends GameManager{

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Renderable ballImage = imageReader.readImage("assets/ball.png", true); // עדכן את הנתיב לתמונה הנכונה
        GameObject ball = new GameObject(Vector2.ZERO, new Vector2(50, 50), ballImage);
    }
    public static void main(String[] args) {

        new GameManager("Bricker", new Vector2(800, 600)).run();
    }


}
// update me that you see this comment!!!