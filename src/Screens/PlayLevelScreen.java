package Screens;

import java.awt.Color;

import PowerUp.weapons;

import javax.swing.Timer;

import Enemies.Shooting;
import GameObject.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.TestMap;
import Players.Cat;
import SpriteFont.SpriteFont;
import Players.Alex;
import Utils.Direction;
import Utils.Point;
import Engine.KeyLocker;
import Engine.Keyboard;
import java.util.HashMap;
import Screens.PlayLevelScreen;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen {
	protected ScreenCoordinator screenCoordinator;
	protected Map map;
	protected Player player;
	public Player player2;
	protected PlayLevelScreenState playLevelScreenState;
	protected SpriteFont instructions;
	protected WinScreen winScreen;
	protected FlagManager flagManager;
	protected Key shootingKey = Key.S;
	protected KeyLocker keyLocker = new KeyLocker();

	protected int counter = 0;
	Timer t;

	private void time() {
		t = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (counter > 9) {
					t.stop();
				}
				instructions = new SpriteFont("NUMBER OF WAVES: " + counter + "/10", 300, 50, "Comic Sans", 20,
						Color.white);
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
		instructions = new SpriteFont("NUMBER OF WAVES: " + counter + "/10", 300, 50, "Comic Sans", 20, Color.white);
		time();
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
		this.player2 = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
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

				boolean shoot = true;

				if (shoot == true && !keyLocker.isKeyLocked(shootingKey) && Keyboard.isKeyDown(shootingKey)) {
					shoot = false;
					float movementSpeed;
					if (player2.getFacingDirection() == Direction.RIGHT) {
						movementSpeed = 1.5f;
//		            fireballX = Math.round(getX()) + getWidth();

					} else {
						movementSpeed = -1.5f;
//					fireballX = Math.round(getX()) + 4;

					}
//				  int fireballY = Math.round(getY()) + 4;	
					Shooting bullet = new Shooting(player2.getLocation(), movementSpeed, 10000);

//				 add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					shoot = true;
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
		instructions.draw(graphicsHandler);

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