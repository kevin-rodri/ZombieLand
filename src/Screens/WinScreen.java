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
//    
//    public WinScreen(ScreenCoordinator screenCoordinator) {
//        initialize();
//    }
    
    public WinScreen(PlayLevelScreen playLevelScreen) {
        initialize();
        this.playLevelScreen = playLevelScreen;
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Game Over!", 650, 270, "Comic Sans", 30, Color.white);
        shots = new SpriteFont("Shots Fired: " + PlayLevelScreen.numberShotsFired, 600, 400, "Comic Sans", 30, Color.white);
        kills = new SpriteFont("Number Of Kills: " + Shooting.numberOfKills, 600, 500, "Comic Sans", 30, Color.white);
        numberOfWaves = new SpriteFont("Number Of Waves: " + PlayLevelScreen.numberOfWaves, 600, 600, "Comic Sans", 30, Color.white);
        timeSurvived = new SpriteFont("Time Survived: " + 0, 600, 700, "Comic Sans", 30, Color.white);
        instructions = new SpriteFont("Press Escape to close the game ", 600, 300,"Comic Sans", 20, Color.white);
        keyLocker.unlockKey(Key.SPACE);
        keyLocker.unlockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        if (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.ESC)) {
           System.exit(0);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
//    	initialize();
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
        shots.draw(graphicsHandler);
        kills.draw(graphicsHandler);
        timeSurvived.draw(graphicsHandler);
        numberOfWaves.draw(graphicsHandler);

    }
    
}
