package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;

import java.awt.*;

import Enemies.Shooting;

// This class is for the win level screen
public class WinScreen extends Screen {
    protected SpriteFont winMessage;
    protected SpriteFont instructions;
    protected SpriteFont shots;
    protected SpriteFont kills;
    protected SpriteFont numberOfWaves;
    protected SpriteFont timeSurvived;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;
    public WinScreen(ScreenCoordinator screenCoordinator) {
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Game Over!", 650, 270, "Comic Sans", 30, Color.white);
        shots = new SpriteFont("Shots Fired: " + PlayLevelScreen.numberShotsFired, 600, 400, "Comic Sans", 30, Color.white);
        kills = new SpriteFont("Number Of Kills: " + Shooting.numberOfKills, 600, 500, "Comic Sans", 30, Color.white);
        numberOfWaves = new SpriteFont("Number Of Waves: " + PlayLevelScreen.numberOfWaves, 600, 600, "Comic Sans", 30, Color.white);
        timeSurvived = new SpriteFont("Time Survived: " + 0, 600, 700, "Comic Sans", 30, Color.white);
        instructions = new SpriteFont("Press Space to play again or Escape to go back to the main menu", 450, 300,"Comic Sans", 20, Color.white);
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);


        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        // if space is pressed, reset level. if escape is pressed, go back to main menu
        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.resetLevel();
//            screenCoordinator.setGameState(GameState.LEVEL);

        } else if (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.ESC)) {
            playLevelScreen.goBackToMenu();
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
        shots.draw(graphicsHandler);
        kills.draw(graphicsHandler);
        timeSurvived.draw(graphicsHandler);
        numberOfWaves.draw(graphicsHandler);

    }
    
}
