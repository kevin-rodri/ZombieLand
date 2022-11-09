package Game;

import java.awt.GridLayout;

import Engine.GameWindow;
import Engine.ScreenManager;

/* 
 * The game starts here
 * This class just starts up a GameWindow and attaches the ScreenCoordinator to the ScreenManager instance in the GameWindow
 * From this point on the ScreenCoordinator class will dictate what the game does
 */
public class Game {
    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        GameWindow gameWindow = new GameWindow();
        ScreenCoordinator sc = new ScreenCoordinator();
    //    ScreenCoordinator sc2 = new ScreenCoordinator();
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
       // ScreenManager screenManager2 = gameWindow.getScreenManager2();
        screenManager.setCurrentScreen(sc);
       // screenManager2.setCurrentScreen(sc2);
       // sc.setWindow(gameWindow);  
     //   sc2.setWindow(gameWindow);

    }
}