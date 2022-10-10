package Screens;

import java.awt.Color;

import PowerUp.weapons;

import javax.swing.Timer;

import Enemies.Shooting;
import Enemies.Zombie;
import GameObject.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.TestMap;
import Players.AlexWithAPistol;
import SpriteFont.SpriteFont;
import Players.Alex;
import Players.AlexWithAPistol;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;
import Engine.KeyLocker;
import Engine.Keyboard;
import java.util.HashMap;
import Screens.PlayLevelScreen;
import NPCs.Lives;
import Utils.Point;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen {
	protected ScreenCoordinator screenCoordinator;
	protected Map map;
	protected Player player;
	public Player player2;
	protected PlayLevelScreenState playLevelScreenState;
	protected SpriteFont waveCounter, money;
	protected WinScreen winScreen;
	protected FlagManager flagManager;
	protected Key shootingKey = Key.S;
	protected KeyLocker keyLocker = new KeyLocker();
	private Stopwatch Timer = new Stopwatch();

	protected int counter = 0;
	int m = 1;
	Timer t;

	private void time() {
		t = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (counter > 9) {
					t.stop();
				}

				waveCounter.setText("WAVE " + counter + "/10");
				money.setText("$" + m);

				m = m * 25;
				counter++;
			}
		});
		t.start();
	}

	public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
		this.screenCoordinator = screenCoordinator;
	}

	public void initialize() {

		// setup state
		flagManager = new FlagManager();
		waveCounter = new SpriteFont("WAVE " + counter + "/10", 300, 50, "z", 20, Color.WHITE);
		waveCounter.setOutlineColor(Color.black);
		waveCounter.setOutlineThickness(5);
		money = new SpriteFont("$" + counter, 10, 50, "z", 20, Color.WHITE);

		money.setOutlineColor(Color.black);
		money.setOutlineThickness(5);
		time();
		;
		flagManager.addFlag("hasLostBall", false);
		flagManager.addFlag("hasTalkedToWalrus", false);
		flagManager.addFlag("hasTalkedToDinosaur", false);
		flagManager.addFlag("hasFoundBall", false);

		// define/setup map
		this.map = new TestMap();
		map.reset();
		map.setFlagManager(flagManager);

		// setup player

		this.player = new Alex(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.player.setMap(map);
		Point playerStartPosition = map.getPlayerStartPosition();
		this.player.setLocation(playerStartPosition.x, playerStartPosition.y);
		this.playLevelScreenState = PlayLevelScreenState.RUNNING;
		this.player.setFacingDirection(Direction.LEFT);
		this.player2 = new AlexWithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.player2.setMap(map);
		this.player2.setLocation(670, 120);
		this.player2.setFacingDirection(player.getFacingDirection());
		// let pieces of map know which button to listen for as the "interact" button
		map.getTextbox().setInteractKey(player.getInteractKey());

		// setup map scripts to have references to the map and player
		for (MapTile mapTile : map.getMapTiles()) {
			if (mapTile.getInteractScript() != null) {
				mapTile.getInteractScript().setMap(map);
				mapTile.getInteractScript().setPlayer(player);
			}
		}
		for (NPC npc : map.getNPCs()) {
			if (npc.getInteractScript() != null) {
				npc.getInteractScript().setMap(map);
				npc.getInteractScript().setPlayer(player);
			}
		}
		for (EnhancedMapTile enhancedMapTile : map.getEnhancedMapTiles()) {
			if (enhancedMapTile.getInteractScript() != null) {
				enhancedMapTile.getInteractScript().setMap(map);
				enhancedMapTile.getInteractScript().setPlayer(player);
			}
		}
		for (Trigger trigger : map.getTriggers()) {
			if (trigger.getTriggerScript() != null) {
				trigger.getTriggerScript().setMap(map);
				trigger.getTriggerScript().setPlayer(player);
			}
		}

	}

	public void update() {
		// based on screen state, perform specific actions
		switch (playLevelScreenState) {
			// if level is "running" update player and map to keep game logic for the
			// platformer level going
			case RUNNING:
				if (weapons.check == true) {
					player2.update();
					map.update(player2);
					// Timer.isTimeUp();

					if (Timer.isTimeUp() && !keyLocker.isKeyLocked(shootingKey) && Keyboard.isKeyDown(shootingKey)) {
						float movementSpeed;
						float fireballX;
						if (player2.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 1.5f;
							fireballX = Math.round(player2.getX()) + 50;
						} else {
							movementSpeed = -1.5f;
							fireballX = Math.round(player2.getX());
						}
						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(player2.getY()) + 18;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						Zombie zombie = new Zombie(new Point(4, 4), Direction.RIGHT);
						zombie.removeZombie(bullet);
						Timer.setWaitTime(500);
					}

				} else {

					player.update();
					map.update(player);
				}

				break;
			// if level has been completed, bring up level cleared screen
			case LEVEL_COMPLETED:
				winScreen.update();
				break;
		}

		// if flag is set at any point during gameplay, game is "won"
		if (map.getFlagManager().isFlagSet("hasFoundBall")) {
			playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
		}

	}

	public void draw(GraphicsHandler graphicsHandler) {
		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
			case RUNNING:
				if (weapons.check == true) {
					map.draw(player2, graphicsHandler);

				} else {
					map.draw(player, graphicsHandler);

				}

				break;
			case LEVEL_COMPLETED:
				winScreen.draw(graphicsHandler);
				break;
		}
		waveCounter.draw(graphicsHandler);
		money.draw(graphicsHandler);

	}

	public PlayLevelScreenState getPlayLevelScreenState() {
		return playLevelScreenState;
	}

	public void resetLevel() {
		initialize();
	}

	public void goBackToMenu() {
		screenCoordinator.setGameState(GameState.MENU);
	}

	// This enum represents the different states this screen can be in
	public enum PlayLevelScreenState {
		RUNNING, LEVEL_COMPLETED
	}

}