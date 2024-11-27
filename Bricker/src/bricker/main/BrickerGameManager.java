package bricker.main;

import danogl.GameManager;
import danogl.gui.WindowController;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {

    // Constructor
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

    }

    public static void main(String[] args) {

        // Create a new instance of the BrickerGameManager class
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}
