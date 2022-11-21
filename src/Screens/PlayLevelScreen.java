package Screens;

import java.awt.Color;
import Ammo.LightAmmo;
import Health.HealthSystem;
import MoneySystem.MoneyBase;
import PowerUp.AssaultRifle;
import PowerUp.MachineGun;
import PowerUp.Nuke;
import PowerUp.weapons;
import javax.swing.Timer;
import Enemies.Shooting;
import Enemies.SmallZombie;
import Enemies.Zombie;
import GameObject.*;
import Level.EnhancedMapTile;
import Level.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;
import Screens.PlayLevelScreen;
import Scripts.TestMap.AmmoScript;
import Scripts.TestMap.GunsmithScript;
import Utils.Point;

// This class is for when the platformer game is actually being played
// note: Got rid of SoundController but we can add it back.. I was getting a werid error 
public class PlayLevelScreen extends Screen  implements SoundController {
	protected ScreenCoordinator screenCoordinator;
	protected Map map;
	protected Player player;
	protected Player alexWithAPistol; // alex with pistol
	protected Player alexWithARifle;
	protected Player alexWithAMachineGun;
	public Player player2;
	protected Player coOp;
	protected PlayLevelScreenState playLevelScreenState;
	protected SpriteFont waveCounter, money, healthBar, ammoCount;
	protected WinScreen winScreen;
	protected FlagManager flagManager;
	protected Lives health;
	private SpriteFont pauseLabel;
	private SpriteFont gameOver;
	public static int numberShotsFired = 0;
	public static int numberOfWaves = 0;
	protected Key shootingKeyforPlayerTwo = Key.F; // shooting key for second
	protected Key shootingKeyForPlayerOne = Key.K; // shooting key for first
	private final Key pauseKey = Key.P;
	private boolean isGamePaused = false;
	protected KeyLocker keyLocker = new KeyLocker();
	private Stopwatch TimerPlayerOnePistol = new Stopwatch(); // timer for first player
	private Stopwatch TimerPlayerOneAssaultRifle = new Stopwatch();
	private Stopwatch TimerPlayerOneMachineGun = new Stopwatch();
	protected int counter = 1;
	boolean szOutsideOfMap = false;
	boolean zOutsideOfMap = false;
	int randomX = 100;
	int randomY = 100;
	protected int x2counter = 0;
	public static boolean noAmmo = false;
	public static boolean x2End = false;

	int m = 1;
	int z = 10;
	int sz = 10;
	Random random = new Random();

	Timer t;
	Timer xp;
	Timer fuGame;
	Timer fuGameMin;

	public static int fullGameTime = 0;
	public static int fullGameMin = 0;

	private void time() {
		t = new Timer(30000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (HealthSystem.healthCount <= 0) {
					t.stop();
				}
				waveCounter.setText("WAVE " + counter);
				numberOfWaves++;
				// money.setText("$" + m);
				MoneyBase.addMoneyOT();

				startWave(z, sz);
				m = m * 2;

			}
		});
		t.start();
	}
	public void gameTime(){
		fuGame = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flagManager.isFlagSet("hasDied")){

					fuGame.stop();

				}
				if(fullGameTime>59){
					fullGameTime = 0;
				}
				fullGameTime ++;

			}
		});
		fuGame.start();
	}
	public void gameMin(){
		fuGameMin = new Timer(60000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flagManager.isFlagSet("hasDied")){
					fuGameMin.stop();
				}

				fullGameMin++;
			}
		});
		fuGameMin.start();
	}

	public void x2time() {
		xp = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (x2counter > 9) {
					xp.stop();
					flagManager.setFlag("x2Exp");
					x2End = true;
				}

				x2counter++;
			}
		});
		xp.start();
	}

	// Method that handles generating small and regular Zombies
	public void startWave(int z, int sz) {
		counter++;
		for (int i = 0; i < sz; i++) {

			// to randomize zombie spawns outside of the game map
			while (!szOutsideOfMap) {

				// generate a number from -500 --> 1620
				randomX = -50 + random.nextInt(1620);
				// generate a number from -500 --> 1620
				randomY = -50 + random.nextInt(1620);
				if (randomX <= 0 && randomY <= 1750 || randomX >= 1620 && randomY <= 1750 || randomY <= 0) {
					szOutsideOfMap = true;
				}
			}

			SmallZombie zombieWaveOne = new SmallZombie(new Point(randomX, randomY), Direction.LEFT);
			map.addEnemy(zombieWaveOne);
			zombieWaveOne.update();
			szOutsideOfMap = false;
		}

		for (int i = 0; i < sz; i++) {
			// to randomize zombie spawns outside of the game map
			while (!zOutsideOfMap) {

				// generate a number from -50 --> 1620
				randomX = -50 + random.nextInt(1620);
				// generate a number from -50 --> 1620
				randomY = -50 + random.nextInt(1620);
				if (randomX <= 0 && randomY <= 1750 || randomX >= 1620 && randomY <= 1750 || randomY <= 0) {
					zOutsideOfMap = true;
				}
			}

			Zombie zombieWaveOne = new Zombie(new Point(randomX, randomY), Direction.LEFT);
			map.addEnemy(zombieWaveOne);
			zombieWaveOne.update();
			zOutsideOfMap = false;
		}
	}

	public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
		this.screenCoordinator = screenCoordinator;
		pauseLabel = new SpriteFont("PAUSE", 365, 280, "z", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);
	}

	public void initialize() {
		// setup state

		flagManager = new FlagManager();
		waveCounter = new SpriteFont("WAVE " + counter, 600, 50, "z", 20, Color.WHITE);
		waveCounter.setOutlineColor(Color.black);
		waveCounter.setOutlineThickness(5);
		money = new SpriteFont("$" + MoneyBase.moneyCount, 10, 50, "z", 20, Color.WHITE);
		healthBar = new SpriteFont("" + HealthSystem.healthCount, 1350, 50, "z", 20, Color.WHITE);
		money.setOutlineColor(Color.black);
		money.setOutlineThickness(5);
		time();
		x2time();
		healthBar.setOutlineColor(Color.black);
		healthBar.setOutlineThickness(5);
		Point HealthHUD = new Point(1300, 10);
		health = new Lives(2, HealthHUD);
		health.setHeight(50);
		health.setWidth(50);
		// health.setLocation(300, 300);
		ammoCount = new SpriteFont(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip, 20, 600, "z", 20, Color.red);
		ammoCount.setOutlineColor(Color.black);
		ammoCount.setOutlineThickness(5);
		flagManager.addFlag("hasLostBall", false);
		flagManager.addFlag("hasTalkedToWalrus", false);
		flagManager.addFlag("hasTalkedToDinosaur", false);
		flagManager.addFlag("hasFoundBall", false);
		flagManager.addFlag("hasTalkedToAmmoNPC", false);
		flagManager.addFlag("hasTalkedToGunsmith", false);
		flagManager.addFlag("hasDied", false);
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
		winScreen = new WinScreen(this);
		this.player.setFacingDirection(Direction.LEFT);
		gameTime();
		gameMin();
		this.coOp = new SecondPlayer(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.coOp.setMap(map);
		this.coOp.setLocation(670, 120);
		this.coOp.setFacingDirection(Direction.LEFT);

		this.alexWithAPistol = new AlexWithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.alexWithAPistol.setMap(map);
		this.alexWithAPistol.setLocation(670, 120);
		this.alexWithAPistol.setFacingDirection(player.getFacingDirection());

		this.alexWithARifle = new AlexWithAssaultRifle(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.alexWithARifle.setMap(map);
		this.alexWithARifle.setLocation(672, 296);
		this.alexWithARifle.setFacingDirection(player.getFacingDirection());

		this.alexWithAMachineGun = new AlexWithMachineGun(map.getPlayerStartPosition().x,
				map.getPlayerStartPosition().y);
		this.alexWithAMachineGun.setMap(map);
		this.alexWithAMachineGun.setLocation(1104, 824);
		this.alexWithAMachineGun.setFacingDirection(player.getFacingDirection());

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
		startWave(z, sz);
	}

	public void update() {
		// based on screen state, perform specific actions
		switch (playLevelScreenState) {
			// if level is "running" update player and map to keep game logic for the
			// platformer level going
			case RUNNING:
				Zombie zombie = new Zombie(new Point(4, 4), Direction.RIGHT);
				if (LightAmmo.ammoCount == 0 && LightAmmo.ammoClip > 0) {
					LightAmmo.ammoClip -= 30;
					LightAmmo.ammoCount += 30;
				}

				if (Nuke.usedNuke == true) {
					Shooting.numberOfKills = Shooting.numberOfKills + map.getEnemies().size();
					map.getEnemies().removeAll(map.getEnemies());
					Nuke.usedNuke = false;
				} else if (LightAmmo.ammoClip == 0 && LightAmmo.ammoCount == 0) {
					// ammoCount.setText("NO AMMO");
					noAmmo = true;
				} else {
					if (noAmmo == true) {
					} else {
						ammoCount.setText(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip);
					}
				}

				if (HealthSystem.healthCount <= 0) {
					flagManager.setFlag("hasDied");
					HealthSystem.setMaxHealth();
				}

				healthBar.setText("" + HealthSystem.healthCount);
				money.setText("$" + MoneyBase.moneyCount);
				ammoCount.setText(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip);

				// To control the amount of enemies that spawn per round. Cap is 72 enemies
				if (map.getEnemies().size() >= 80) {

					t.stop();

				}
				if (map.getEnemies().size() < 80) {

					t.start();

				}
				// will check to see if ammo script is running
				// TO-DO: Implement whatever has to go here.
				if (AmmoScript.ammoScriptRunning){
					if(Keyboard.isKeyDown(Key.ONE)){

					}
					if(Keyboard.isKeyDown(Key.TWO)){
		
					}
					if(Keyboard.isKeyDown(Key.THREE)){
		
					}
				}


				// Will check to see if the AmmoSmith script is running
				if(AmmoScript.ammoScriptRunning == true){
					if (Keyboard.isKeyDown(Key.ONE) && MoneyBase.moneyCount >= 100) {
						MoneyBase.buy30Rounds();
						LightAmmo.ammoClip += 30;
					} else if (Keyboard.isKeyDown(Key.TWO) && MoneyBase.moneyCount >= 150){
						MoneyBase.buy60Rounds();
						LightAmmo.ammoClip +=60;
					} else if (Keyboard.isKeyDown(Key.THREE) && MoneyBase.moneyCount >=200){
						MoneyBase.buy120Rounds();
						LightAmmo.ammoClip +=120;
					}
				}
				// Will check to see if the GunSmith script is running
				if(GunsmithScript.runningGUNSMITH==true) {
					// Case One: A pistol will appear on the map
					if (Keyboard.isKeyDown(Key.ONE) && MoneyBase.moneyCount >= 50) {
						MoneyBase.buyPistol();
						weapons pistol = new weapons(6, new Point(672, 104));
						map.addNPC(pistol);
						// Case Two: A asssault rifle will appear on the map
					} else if (Keyboard.isKeyDown(Key.TWO) && MoneyBase.moneyCount >= 100) {
						MoneyBase.buyAssault();
						AssaultRifle assaultRifle = new AssaultRifle(8, new Point(672, 296));
						map.addNPC(assaultRifle);
							// Case Three: A machine gun will appear on the map
					} else if (Keyboard.isKeyDown(Key.THREE) && MoneyBase.moneyCount >= 500) {
						MoneyBase.buyMG();
						MachineGun machineGun = new MachineGun(9, new Point(1104, 824));
        				map.addNPC(machineGun);
					}
				}
			
				if (weapons.check) {
					alexWithAPistol.update();
					map.update(alexWithAPistol);
					TimerPlayerOnePistol.isTimeUp();
					if (TimerPlayerOnePistol.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyForPlayerOne)
							&& Keyboard.isKeyDown(shootingKeyForPlayerOne)) {
						float fireballX;
						float movementSpeed;
						LightAmmo.ammoCount -= 1;
						if (alexWithAPistol.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 5.0f;
							fireballX = Math.round(alexWithAPistol.getX()) + 50;
						} else {
							movementSpeed = -5.0f;
							fireballX = Math.round(alexWithAPistol.getX());
						}

						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(alexWithAPistol.getY()) + 18;
						numberShotsFired++;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						TimerPlayerOnePistol.setWaitTime(500);
					}

				} else if (AssaultRifle.check) {
					alexWithARifle.update();
					map.update(alexWithARifle);
					TimerPlayerOneAssaultRifle.isTimeUp();
					if (TimerPlayerOneAssaultRifle.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyForPlayerOne)
							&& Keyboard.isKeyDown(shootingKeyForPlayerOne)) {
						float fireballX;
						float movementSpeed;
						LightAmmo.ammoCount -= 1;
						if (alexWithARifle.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 10.0f;
							fireballX = Math.round(alexWithARifle.getX()) + 50;
						} else {
							movementSpeed = -10.0f;
							fireballX = Math.round(alexWithARifle.getX());
						}

						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(alexWithARifle.getY()) + 18;
						numberShotsFired++;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						TimerPlayerOneAssaultRifle.setWaitTime(500);
					}

				} else if (MachineGun.check) {
					alexWithAMachineGun.update();
					map.update(alexWithAMachineGun);
					TimerPlayerOneMachineGun.isTimeUp();
					if (TimerPlayerOneMachineGun.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyForPlayerOne)
							&& Keyboard.isKeyDown(shootingKeyForPlayerOne)) {
						float fireballX;
						float movementSpeed;
						LightAmmo.ammoCount -= 1;
						if (alexWithAMachineGun.getFacingDirection() == Direction.RIGHT) {
							movementSpeed = 15.0f;
							fireballX = Math.round(alexWithAMachineGun.getX()) + 50;
						} else {
							movementSpeed = -15.0f;
							fireballX = Math.round(alexWithAMachineGun.getX());
						}

						// int fireballY = (int) (player2.getY2() - player2.getY1());
						int fireballY = Math.round(alexWithAMachineGun.getY()) + 18;
						numberShotsFired++;
						Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

						// add fireball enemy to the map for it to offically spawn in the level
						map.addEnemy(bullet);
						TimerPlayerOneMachineGun.setWaitTime(500);
					}

				}
				if (!AssaultRifle.check && !weapons.check && !MachineGun.check) {
					player.update();
					map.update(player);

				}
				break;
			// if level has been completed, bring up level cleared screen
			case LEVEL_COMPLETED:
				winScreen.update();
				// winScreen.update();
				break;
		}

		// if flag is set at any point during gameplay, game is "won"
		if (map.getFlagManager().isFlagSet("hasDied")) {
			playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
		}
	}

	public void draw(GraphicsHandler graphicsHandler) {
		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
			case RUNNING:
				if (weapons.check) {
					map.draw(alexWithAPistol, graphicsHandler);
				} else if (MachineGun.check) {
					map.draw(alexWithAMachineGun, graphicsHandler);
				} else if (AssaultRifle.check) {
					map.draw(alexWithARifle, graphicsHandler);
				}
				if (!AssaultRifle.check && !weapons.check && !MachineGun.check) {
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
				pauseLabel = new SpriteFont("HELP", 365, 280, "Comic Sans", 24, Color.white);
				// if game is paused, draw pause gfx over Screen gfx
				if (isGamePaused) {
					pauseLabel.draw(graphicsHandler);
					graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(),
							ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
				}
				waveCounter.draw(graphicsHandler);
				money.draw(graphicsHandler);
				health.draw(graphicsHandler);
				healthBar.draw(graphicsHandler);
				ammoCount.draw(graphicsHandler);

				break;
			case LEVEL_COMPLETED:

				try {
					CreateFile.record();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					CreateFile.recordMin();
				} catch (IOException e) {
					e.printStackTrace();
				}
				winScreen.initialize();
				winScreen.draw(graphicsHandler);
				//screenCoordinator.setGameState(GameState.WinScreen);


				waveCounter.setText("");
				money.setText("");
				healthBar.setText("");
				ammoCount.setText("");
				//gameOver.draw(graphicsHandler);
				health.setIsHidden(true);


				break;
		}
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