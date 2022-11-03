package Screens;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.GridLayout;

import Ammo.LightAmmo;
import Health.HealthSystem;
import MoneySystem.MoneyBase;
import PowerUp.weapons;

import javax.swing.Timer;

import Enemies.Shooting;
import Enemies.Zombie;
import GameObject.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import Engine.GameWindow;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.TestMap;
import NPCs.Lives;
import Players.AlexWithAPistol;
import Players.SecondPlayer;
import SpriteFont.SpriteFont;
import Players.Alex;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;
import Engine.KeyLocker;
import Engine.Keyboard;


// This class is for when the platformer game is actually being played
public class CoopScreen extends Screen {
	protected ScreenCoordinator screenCoordinator;
	protected Map map;
	protected Player player;
	public Player player2;
	protected Player coOp;
	protected PlayLevelScreenState playLevelScreenState;
	protected SpriteFont waveCounter, money, healthBar ,ammoCount;
	protected WinScreen winScreen;
	protected FlagManager flagManager;
	protected Lives health;
	private SpriteFont pauseLabel;
	protected Key shootingKey = Key.F;
	private final Key pauseKey = Key.P;
	private boolean isGamePaused = false;
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
				//money.setText("$" + m);
				MoneyBase.addMoneyOT();


				m = m * 2;
				counter++;
			}
		});
		t.start();
	}

	public CoopScreen(ScreenCoordinator screenCoordinator) {
		this.screenCoordinator = screenCoordinator;
		pauseLabel = new SpriteFont("PAUSE", 365, 280, "Comic Sans", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);
	}

	public void initialize() {

		// setup state
		flagManager = new FlagManager();
		waveCounter = new SpriteFont("WAVE " + counter + "/10", 300, 50, "z", 20, Color.WHITE);
		waveCounter.setOutlineColor(Color.black);
		waveCounter.setOutlineThickness(5);
		money = new SpriteFont("$" + MoneyBase.moneyCount, 10, 50, "z", 20, Color.WHITE);
		healthBar = new SpriteFont("" + HealthSystem.healthCount, 700, 50, "z", 20, Color.WHITE);

		money.setOutlineColor(Color.black);
		money.setOutlineThickness(5);
		time();
		healthBar.setOutlineColor(Color.black);
		healthBar.setOutlineThickness(5);
		Point HealthHUD = new Point(650,10);
		health = new Lives(2, HealthHUD);
		health.setHeight(50);
		health.setWidth(50);
		//health.setLocation(300, 300);

		ammoCount = new SpriteFont(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip ,20, 550, "z", 20, Color.red);
		ammoCount.setOutlineColor(Color.black);
		ammoCount.setOutlineThickness(5);


		
		flagManager.addFlag("hasLostBall", false);
		flagManager.addFlag("hasTalkedToWalrus", false);   
		flagManager.addFlag("hasTalkedToDinosaur", false);
		flagManager.addFlag("hasFoundBall", false);
		flagManager.addFlag("hasTalkedToAmmoNPC", false);
		flagManager.addFlag("hasTalkedToGunsmith", false);

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
		this.coOp = new SecondPlayer(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.coOp.setMap(map);
		this.coOp.setLocation(670, 120);
		this.coOp.setFacingDirection(player.getFacingDirection());
		this.player2 = new AlexWithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.player2.setMap(map);
		this.player2.setLocation(670, 120);
		this.player2.setFacingDirection(player.getFacingDirection());
//		 let pieces of map know which button to listen for as the "interact" button
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
				if (LightAmmo.ammoCount <= 0){
					LightAmmo.ammoClip -=30;
					LightAmmo.ammoCount += 30;
				}
				if(LightAmmo.ammoClip <= 0 && LightAmmo.ammoCount <=0){
					//For now setting it back to max but should be set to 0 and say no AMMO
					LightAmmo.ammoCount = 30;
					LightAmmo.ammoClip = 120;
					//ammoCount.setText("NO AMMO");
				}
				healthBar.setText("" + HealthSystem.healthCount);
				money.setText("$" + MoneyBase.moneyCount);
				ammoCount.setText(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip);

				if (weapons.check == true) {
					player.update();
					map.update(player2);
					coOp.update();
					map.update(coOp);
					Timer.isTimeUp();

					if (Timer.isTimeUp() && !keyLocker.isKeyLocked(shootingKey) && Keyboard.isKeyDown(shootingKey)) {
						float fireballX;
						float movementSpeed;
						LightAmmo.ammoCount -=1;
						if (player2.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 4.0f;
							fireballX = Math.round(player2.getX()) + 50;
						} else {
							movementSpeed = -4.0f;
							fireballX = Math.round(player2.getX());
						}
						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(player2.getY()) + 18;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 10000);


						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						Zombie zombie = new Zombie(new Point(4, 4), Direction.RIGHT);
						Timer.setWaitTime(500);
					}

				} else {
					coOp.update();
					map.update2(coOp);
					player.update();
					map.update(player);

				}

				break;
			// if level has been completed, bring up level cleared screen
			case LEVEL_COMPLETED:
				//winScreen.update();
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
					map.draw(coOp, graphicsHandler);
//					map.draw(coOp, player, graphicsHandler);



				} else {
					map.draw(player, graphicsHandler);
					map.draw2(coOp, graphicsHandler);
//					map.draw(coOp, player, graphicsHandler);

				}
				// pasue game logic was moved to here
				if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
					isGamePaused = !isGamePaused;
					keyLocker.lockKey(pauseKey);
				}
				
				if (Keyboard.isKeyUp(pauseKey)) {
					keyLocker.unlockKey(pauseKey);
				}
				pauseLabel = new SpriteFont("HELP", 365, 280, "z", 24, Color.white);
				// if game is paused, draw pause gfx over Screen gfx
				if (isGamePaused) {
					pauseLabel.draw(graphicsHandler);
					graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
				}
		
				break;
			case LEVEL_COMPLETED:
				winScreen.draw(graphicsHandler);
				break;
		}
	
		waveCounter.draw(graphicsHandler);
		money.draw(graphicsHandler);
		health.draw(graphicsHandler);
		healthBar.draw(graphicsHandler);
		ammoCount.draw(graphicsHandler);
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