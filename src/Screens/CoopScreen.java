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

import Engine.Config;
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
import Players.AlexWithAssaultRifle;
import Players.AlexWithMachineGun;
import Players.SecondPlayer;
import SpriteFont.SpriteFont;
import Players.Alex;
import Players.Alex2WithAPistol;
import Players.Alex2WithAssaultRifle;
import Players.Alex2WithMachineGun;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;
import Engine.KeyLocker;
import Engine.Keyboard;
import PowerUp.*;


// This class is for when the platformer game is actually being played
public class CoopScreen extends Screen {
	protected ScreenCoordinator screenCoordinator;
	protected Map map;
	protected Player player;
	public Player player2;
	protected Player alexWithAPistol; // alex with pistol
 	protected Player alexWithARifle;
 	protected Player alexWithAMachineGun;
 	protected Player coOp;
 	protected Player alexTwoWithPistol;
 	protected Player alexTwoWithAssaultRifle;
 	protected Player alexTwoWithMachineGun;
	protected PlayLevelScreenState playLevelScreenState;
	protected SpriteFont waveCounter, money, healthBar ,ammoCount;
	protected WinScreen winScreen;
	protected FlagManager flagManager;
	protected Lives health;
	private SpriteFont pauseLabel;
	protected Key shootingKeyforPlayerTwo = Key.F; // shooting key for second 
 	protected Key shootingKeyForPlayerOne = Key.K; // shooting key for first 
	private final Key pauseKey = Key.P;
	private boolean isGamePaused = false;
	protected KeyLocker keyLocker = new KeyLocker();
	private Stopwatch Timer = new Stopwatch();
	protected int x2counter = 0;
	// timers for first player when they pick up weapons with gunsmith (76-78)
	private Stopwatch TimerPlayerOnePistol = new Stopwatch(); 
	private  Stopwatch TimerPlayerOneAssaultRifle = new Stopwatch(); 
	private  Stopwatch TimerPlayerOneMachineGun= new Stopwatch(); 
	// second player 
	private Stopwatch TimerPlayerTwoPistol = new Stopwatch(); 
	private  Stopwatch TimerPlayerTwoAssaultRifle = new Stopwatch(); 
	private  Stopwatch TimerPlayerTwoMachineGun= new Stopwatch(); 
	protected int counter = 0;

	int m = 1;
	Timer t;
	Timer xp;

	private void time() {
		t = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (counter > 9) {
					t.stop();
				}

				waveCounter.setText("WAVE " + counter );
				//money.setText("$" + m);
				MoneyBase.addMoneyOT();


				m = m * 2;
				counter++;
			}
		});
		t.start();
	}
	public void x2time() {
		xp = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (x2counter>9) {
					xp.stop();
					flagManager.setFlag("x2Exp");
					PlayLevelScreen.x2End = true;
				}

				x2counter++;
			}
		});
		xp.start();
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
		waveCounter = new SpriteFont("WAVE " + counter , 300, 50, "z", 20, Color.WHITE);
		waveCounter.setOutlineColor(Color.black);
		waveCounter.setOutlineThickness(5);
		money = new SpriteFont("$" + MoneyBase.moneyCount, 10, 50, "z", 20, Color.WHITE);
		healthBar = new SpriteFont("" + HealthSystem.healthCount, 650, 50, "z", 20, Color.WHITE);

		money.setOutlineColor(Color.black);
		money.setOutlineThickness(5);
		time();
		healthBar.setOutlineColor(Color.black);
		healthBar.setOutlineThickness(5);
		Point HealthHUD = new Point(600,10);
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
		this.coOp = new SecondPlayer(map.getPlayerStartPosition().x+50, map.getPlayerStartPosition().y);
		this.coOp.setMap(map);
		this.coOp.setLocation(playerStartPosition.x,playerStartPosition.y);
		this.coOp.setFacingDirection(player.getFacingDirection());
		this.player2 = new AlexWithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.player2.setMap(map);  
		this.player2.setLocation(670, 120);
		this.player2.setFacingDirection(player.getFacingDirection());

 		this.alexWithAPistol = new AlexWithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
 		this.alexWithAPistol.setMap(map);
 		this.alexWithAPistol.setLocation(670, 120);
 		this.alexWithAPistol.setFacingDirection(player.getFacingDirection());

 		this.alexWithARifle = new AlexWithAssaultRifle(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
 		this.alexWithARifle.setMap(map);
 		this.alexWithARifle.setLocation(672, 296);
 		this.alexWithARifle.setFacingDirection(player.getFacingDirection());

 		this.alexWithAMachineGun = new AlexWithMachineGun(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
 		this.alexWithAMachineGun.setMap(map);
 		this.alexWithAMachineGun.setLocation(1104, 824);
 		this.alexWithAMachineGun.setFacingDirection(player.getFacingDirection());

 		this.alexTwoWithPistol =  new Alex2WithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
 		this.alexTwoWithPistol.setMap(map);
 		this.alexTwoWithPistol.setLocation(670, 120);
 		this.alexTwoWithPistol.setFacingDirection(player.getFacingDirection());


 		this.alexTwoWithAssaultRifle = new Alex2WithAssaultRifle(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
 		this.alexTwoWithAssaultRifle.setMap(map);
 		this.alexTwoWithAssaultRifle.setLocation(672, 296);
 		this.alexTwoWithAssaultRifle.setFacingDirection(player.getFacingDirection());

 		this.alexTwoWithMachineGun = new Alex2WithMachineGun(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
 		this.alexTwoWithMachineGun.setMap(map);
 		this.alexTwoWithMachineGun.setLocation(1104, 824);
 		this.alexTwoWithMachineGun.setFacingDirection(player.getFacingDirection());
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
				if(Nuke.usedNuke==true){
					map.getEnemies().removeAll(map.getEnemies());
					Nuke.usedNuke = false;
				} else if (LightAmmo.ammoClip ==0 && LightAmmo.ammoCount ==0){
					//ammoCount.setText("NO AMMO");
					PlayLevelScreen.noAmmo = true;
				} else {
					if (PlayLevelScreen.noAmmo ==true) {
					} else {
						ammoCount.setText(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip);
					}
				}
	
				if(HealthSystem.healthCount <= 0){
					flagManager.setFlag("hasDied");
					HealthSystem.setMaxHealth();
				}
				healthBar.setText("" + HealthSystem.healthCount);
				money.setText("$" + MoneyBase.moneyCount);
				ammoCount.setText(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip);

				if (weapons.check){
				
						alexWithAPistol.update();
						map.update(alexWithAPistol);
						alexTwoWithPistol.update();
						map.update(alexTwoWithPistol);

						TimerPlayerOnePistol.isTimeUp();

					if (TimerPlayerOnePistol.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyForPlayerOne) && Keyboard.isKeyDown(shootingKeyForPlayerOne)) {
						float fireballX;
						float movementSpeed;
						LightAmmo.ammoCount -=1;
						if (alexWithAPistol.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 5.0f;
							fireballX = Math.round(alexWithAPistol.getX()) + 50;
						} else {
							movementSpeed = -5.0f;
							fireballX = Math.round(alexWithAPistol.getX());
						}

						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(alexWithAPistol.getY()) + 18;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);


						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						TimerPlayerOnePistol.setWaitTime(500);				
						}
		
						
						TimerPlayerTwoPistol.isTimeUp();

					if (TimerPlayerTwoPistol.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyforPlayerTwo) && Keyboard.isKeyDown(shootingKeyforPlayerTwo)) {
						float fireballX;
						float movementSpeed;
						LightAmmo.ammoCount -=1;

						if (alexTwoWithPistol.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 5.0f;
							fireballX = Math.round(alexTwoWithPistol.getX()) + 50;
						} else {
							movementSpeed = -5.0f;
							fireballX = Math.round(alexTwoWithPistol.getX());
						}

						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(alexTwoWithPistol.getY()) + 18;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);


						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						TimerPlayerTwoPistol.setWaitTime(500);				
						}
				


					} else if(AssaultRifle.check){

							alexWithARifle.update();
						map.update(alexWithARifle);
						alexTwoWithAssaultRifle.update();
						map.update(alexTwoWithAssaultRifle);
						TimerPlayerOneAssaultRifle.isTimeUp();
						if (TimerPlayerOneAssaultRifle.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyForPlayerOne) && Keyboard.isKeyDown(shootingKeyForPlayerOne)) {
							float fireballX;
							float movementSpeed;
							LightAmmo.ammoCount -=1;
							if (alexWithARifle.getFacingDirection() == Direction.RIGHT) {
								movementSpeed = 10.0f;
								fireballX = Math.round(alexWithARifle.getX()) + 50;
							} else {
								movementSpeed = -10.0f;
								fireballX = Math.round(alexWithARifle.getX());
							}

							// int fireballY = (int) (player2.getY2() - player2.getY1());
							int fireballY = Math.round(alexWithARifle.getY()) + 18;
							Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);


							// add fireball enemy to the map for it to offically spawn in the level
							map.addEnemy(bullet);
							TimerPlayerOneAssaultRifle.setWaitTime(500);
					}

					

							TimerPlayerTwoAssaultRifle.isTimeUp();
						if (TimerPlayerTwoAssaultRifle.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyforPlayerTwo) && Keyboard.isKeyDown(shootingKeyforPlayerTwo)) {
							float fireballX;
							float movementSpeed;
							LightAmmo.ammoCount -=1;
							if (alexTwoWithAssaultRifle.getFacingDirection() == Direction.RIGHT) {
								movementSpeed = 10.0f;
								fireballX = Math.round(alexTwoWithAssaultRifle.getX()) + 50;
							} else {
								movementSpeed = -10.0f;
								fireballX = Math.round(alexTwoWithAssaultRifle.getX());
							}

							// int fireballY = (int) (player2.getY2() - player2.getY1());
							int fireballY = Math.round(alexTwoWithAssaultRifle.getY()) + 18;
							Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);


							// add fireball enemy to the map for it to offically spawn in the level
							map.addEnemy(bullet);
							TimerPlayerTwoAssaultRifle.setWaitTime(500);
					
						  }
						

				} else if (MachineGun.check){

						alexWithAMachineGun.update();
						map.update(alexWithAMachineGun);
						alexTwoWithMachineGun.update();
						map.update(alexTwoWithMachineGun);
						TimerPlayerOneMachineGun.isTimeUp();
						if (TimerPlayerOneMachineGun.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyForPlayerOne) && Keyboard.isKeyDown(shootingKeyForPlayerOne)) {
							float fireballX;
							float movementSpeed;
							LightAmmo.ammoCount -=1;
							if (alexWithAMachineGun.getFacingDirection() == Direction.RIGHT) {
								movementSpeed = 15.0f;
								fireballX = Math.round(alexWithAMachineGun.getX()) + 50;
							} else {
								movementSpeed = -15.0f;
								fireballX = Math.round(alexWithAMachineGun.getX());
							}

							// int fireballY = (int) (player2.getY2() - player2.getY1());
							int fireballY = Math.round(alexWithAMachineGun.getY()) + 18;
							Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);


							// add fireball enemy to the map for it to offically spawn in the level
							map.addEnemy(bullet);
							TimerPlayerOneMachineGun.setWaitTime(500);
						}

					

						TimerPlayerTwoMachineGun.isTimeUp();
						if (TimerPlayerTwoMachineGun.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyforPlayerTwo) && Keyboard.isKeyDown(shootingKeyforPlayerTwo)) {
							float fireballX;
							float movementSpeed;
							LightAmmo.ammoCount -=1;
							if (alexTwoWithMachineGun.getFacingDirection() == Direction.RIGHT) {
								movementSpeed = 15.0f;
								fireballX = Math.round(alexTwoWithMachineGun.getX()) + 50;
							} else {
								movementSpeed = -15.0f;
								fireballX = Math.round(alexTwoWithMachineGun.getX());
							}

							// int fireballY = (int) (player2.getY2() - player2.getY1());
							int fireballY = Math.round(alexTwoWithMachineGun.getY()) + 18;
							Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);


							// add fireball enemy to the map for it to offically spawn in the level
							map.addEnemy(bullet);
							TimerPlayerTwoMachineGun.setWaitTime(500);
						
					}
			} else {
					player.update();
					map.update(player);
					coOp.update();
					map.update(coOp);
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
		// create buffered image canvas here
		BufferedImage subImage = new BufferedImage(Config.GAME_WINDOW_WIDTH/2,Config.GAME_WINDOW_HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics2D buffG = subImage.createGraphics();
		// store old graphics from graphics handler in variable
		Graphics2D tempG = graphicsHandler.getGraphics();
		// change graphics handler to point to buffered images graphics
		graphicsHandler.setGraphics(buffG);
		// do drawing things
		
		// paint entire buffered image at the end

		
		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
			case RUNNING:
			if (weapons.check){
				map.draw(alexWithAPistol,  graphicsHandler);
			} else if (MachineGun.check){
			map.draw(alexWithAMachineGun,  graphicsHandler);
		} else if (AssaultRifle.check) {
		   map.draw(alexWithARifle,graphicsHandler);
		} else {
			map.draw(player, graphicsHandler);
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
		graphicsHandler.setGraphics(tempG);
		graphicsHandler.drawImage(subImage,0, 0);	
		graphicsHandler.drawFilledRectangleWithBorder(Config.GAME_WINDOW_WIDTH/2, 0, 25, 1000, new Color(40,40,40), new Color(40,40,40), 30);
	//	graphicsHandler.drawImage(subImage,Config.GAME_WINDOW_WIDTH/2, 0);
// RIGHT SIDE		
		// create buffered image canvas here
		BufferedImage subImage2 = new BufferedImage(Config.GAME_WINDOW_WIDTH/2,Config.GAME_WINDOW_HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics2D buffG2 = subImage2.createGraphics();
		// store old graphics from graphics handler in variable
		Graphics2D tempG2 = graphicsHandler.getGraphics();
		// change graphics handler to point to buffered images graphics
		graphicsHandler.setGraphics(buffG2);
		// do drawing things
		
		// paint entire buffered image at the end
		
		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
			case RUNNING:
			if (weapons.check){
				map.draw(alexTwoWithPistol,  graphicsHandler);
			}else if (MachineGun.check){
				map.draw(alexTwoWithMachineGun,  graphicsHandler);
			} else if (AssaultRifle.check){
				map.draw(alexTwoWithAssaultRifle,graphicsHandler);
			 }else {
				map.draw(coOp, graphicsHandler);
			}
				// pause game logic was moved to here
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
		graphicsHandler.setGraphics(tempG2);
		graphicsHandler.drawImage(subImage2,Config.GAME_WINDOW_WIDTH/2, 0);	
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