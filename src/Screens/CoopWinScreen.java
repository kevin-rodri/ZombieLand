package Screens;

import java.awt.Color;

import Enemies.Shooting;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.ScreenManager;
import SpriteFont.SpriteFont;

public class CoopWinScreen {
	protected SpriteFont winMessage;
	protected SpriteFont instructions;
	protected SpriteFont shots;
	protected SpriteFont kills;
	protected SpriteFont numberOfWaves;
    protected SpriteFont weaponsPicked;

	//protected SpriteFont timeSurvived;
	protected KeyLocker keyLocker = new KeyLocker();
	protected CoopScreen coopScreen;
	protected final int waves = CoopScreen.numberOfWaves++;

	public CoopWinScreen(CoopScreen coopScreen) {
		initialize();
		this.coopScreen = coopScreen;
	}

	public void initialize() {
		winMessage = new SpriteFont("Game Over!", 650, 270, "Comic Sans", 30, Color.white);
		shots = new SpriteFont("Shots Fired: " + CoopScreen.shotsFired, 600, 400, "Comic Sans", 30,
				Color.white);
		kills = new SpriteFont("Number Of Kills: " + Shooting.numberOfKills, 600, 500, "Comic Sans", 30, Color.white);
		numberOfWaves = new SpriteFont("Number Of Waves: " + CoopScreen.numberOfWaves, 600, 600, "Comic Sans", 30,
				Color.white);
		weaponsPicked = new SpriteFont("Number Of Waepons Picked: " + CoopScreen.numberOfWeaponsPicked, 600, 700, "Comic Sans", 30,
				Color.white);
		//timeSurvived = new SpriteFont("Time Survived: " + CreateFile.recordValue + " Minutes " + CreateFile.recordMinStr + " Seconds ", 600,
				//700, "Comic Sans", 30, Color.white);
		instructions = new SpriteFont("Press Escape to close the game ", 600, 300, "Comic Sans", 20, Color.white);
		keyLocker.unlockKey(Key.SPACE);
		keyLocker.unlockKey(Key.ESC);
	}

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
		graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
		winMessage.draw(graphicsHandler);
		instructions.draw(graphicsHandler);
		shots.draw(graphicsHandler);
		kills.draw(graphicsHandler);
		//timeSurvived.draw(graphicsHandler);
		weaponsPicked.draw(graphicsHandler);
		numberOfWaves.draw(graphicsHandler);

	}

}
