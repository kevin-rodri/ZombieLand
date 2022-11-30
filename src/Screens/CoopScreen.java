package Screens;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.GridLayout;

import Ammo.LightAmmo;
import Health.HealthSystem;
import MoneySystem.MoneyBase;
import PowerUp.weapons;
import Screens.PlayLevelScreen.PlayLevelScreenState;
import Scripts.TestMap.GunsmithScript;
import Scripts.TestMap.AmmoScript;
import javax.swing.Timer;

import Enemies.Shooting;
import Enemies.SmallZombie;
import Enemies.Zombie;
import GameObject.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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
	protected Alex player;
	public Player player2;
	protected AlexWithAPistol alexWithAPistol; // alex with pistol
	protected AlexWithAssaultRifle alexWithARifle;
	protected AlexWithMachineGun alexWithAMachineGun;
	protected SecondPlayer coOp;
	protected Alex2WithAPistol alexTwoWithPistol;
	protected Alex2WithAssaultRifle alexTwoWithAssaultRifle;
	protected Alex2WithMachineGun alexTwoWithMachineGun;
	protected PlayLevelScreenState playLevelScreenState;
	protected SpriteFont waveCounter, money, healthBar, ammoCount;
	protected CoopWinScreen winScreen;
	protected FlagManager flagManager;
	protected Lives health;
	private SpriteFont pauseLabel;
	protected Key shootingKeyforPlayerTwo = Key.F; // shooting key for second
	protected Key shootingKeyForPlayerOne = Key.K; // shooting key for first
	private final Key pauseKey = Key.P;
	protected Key movingForPlayer2W = Key.W; // shooting key for second
	protected Key movingForPlayer2A = Key.A;
	protected Key movingForPlayer2S = Key.S;
	protected Key movingForPlayer2D = Key.A;
	private boolean isGamePaused = false;
	protected KeyLocker keyLocker = new KeyLocker();
	private Stopwatch Timer = new Stopwatch();
	protected int x2counter = 0;
	// timers for first player when they pick up weapons with gunsmith (76-78)
	private Stopwatch TimerPlayerOnePistol = new Stopwatch();
	private Stopwatch TimerPlayerOneAssaultRifle = new Stopwatch();
	private Stopwatch TimerPlayerOneMachineGun = new Stopwatch();
	// second player
	private Stopwatch TimerPlayerTwoPistol = new Stopwatch();
	private Stopwatch TimerPlayerTwoAssaultRifle = new Stopwatch();
	private Stopwatch TimerPlayerTwoMachineGun = new Stopwatch();
	public static int shotsFired = 0;
	public static int numberOfWaves = 0;
	protected int counter = 1;
	boolean szOutsideOfMap = false;
	boolean zOutsideOfMap = false;
	private boolean playerTwoIntersectsPistol = false;

	int m = 1;
	Timer t;
	Timer xp;

	int randomX = 100;
	int randomY = 100;
	int z = 10;
	int sz = 10;
	Random random = new Random();

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
				if (randomX <= 0 && randomY <= 1620 || randomX >= 1620 && randomY <= 1620 || randomY <= 0) {
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
				if (randomX <= 0 && randomY <= 1620 || randomX >= 1620 && randomY <= 1620 || randomY <= 0) {
					zOutsideOfMap = true;
				}
			}

			Zombie zombieWaveOne = new Zombie(new Point(randomX, randomY), Direction.LEFT);
			map.addEnemy(zombieWaveOne);
			zombieWaveOne.update();
			zOutsideOfMap = false;
		}
	}

	public void x2time() {
		xp = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (x2counter > 9) {
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
		waveCounter = new SpriteFont("WAVE " + counter, 300, 50, "z", 20, Color.WHITE);
		waveCounter.setOutlineColor(Color.black);
		waveCounter.setOutlineThickness(5);
		money = new SpriteFont("$" + MoneyBase.moneyCount, 10, 50, "z", 20, Color.WHITE);
		healthBar = new SpriteFont("" + HealthSystem.healthCount, 650, 50, "z", 20, Color.WHITE);
		winScreen = new CoopWinScreen(this);

		money.setOutlineColor(Color.black);
		money.setOutlineThickness(5);
		time();
		healthBar.setOutlineColor(Color.black);
		healthBar.setOutlineThickness(5);
		Point HealthHUD = new Point(600, 10);
		health = new Lives(2, HealthHUD);
		health.setHeight(50);
		health.setWidth(50);
		// health.setLocation(300, 300);

		ammoCount = new SpriteFont(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip, 20, 550, "z", 20, Color.red);
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
		this.coOp.setLocation(playerStartPosition.x, playerStartPosition.y);
		this.coOp.setFacingDirection(player.getFacingDirection());

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

		this.alexTwoWithPistol = new Alex2WithAPistol(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.alexTwoWithPistol.setMap(map);
		this.alexTwoWithPistol.setLocation(670, 120);
		this.alexTwoWithPistol.setFacingDirection(player.getFacingDirection());

		this.alexTwoWithAssaultRifle = new Alex2WithAssaultRifle(map.getPlayerStartPosition().x,
				map.getPlayerStartPosition().y);
		this.alexTwoWithAssaultRifle.setMap(map);
		this.alexTwoWithAssaultRifle.setLocation(672, 296);
		this.alexTwoWithAssaultRifle.setFacingDirection(player.getFacingDirection());

		this.alexTwoWithMachineGun = new Alex2WithMachineGun(map.getPlayerStartPosition().x,
				map.getPlayerStartPosition().y);
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
		map.getCamera().setWidth(map.getCamera().getWidth() - 15);
		coOp.setLocation(coOp.getX()+150, coOp.getY());

		startWave(z, sz);
	}

	public void update() {
		// based on screen state, perform specific actions
		switch (playLevelScreenState) {
		// if level is "running" update player and map to keep game logic for the
		// platformer level going
		case RUNNING:
			if (LightAmmo.ammoCount <= 0) {
				LightAmmo.ammoClip -= 30;
				LightAmmo.ammoCount += 30;
			}
			if (LightAmmo.ammoClip <= 0 && LightAmmo.ammoCount <= 0) {
				// For now setting it back to max but should be set to 0 and say no AMMO
				LightAmmo.ammoCount = 30;
				LightAmmo.ammoClip = 120;
				// ammoCount.setText("NO AMMO");
			}
			if (Nuke.usedNuke == true) {
				map.getEnemies().removeAll(map.getEnemies());
				Nuke.usedNuke = false;
			} else if (LightAmmo.ammoClip == 0 && LightAmmo.ammoCount == 0) {
				// ammoCount.setText("NO AMMO");
				PlayLevelScreen.noAmmo = true;
			} else {
				if (PlayLevelScreen.noAmmo == true) {
				} else {
					ammoCount.setText(LightAmmo.ammoCount + "/" + LightAmmo.ammoClip);
				}
			}

			if (HealthSystem.healthCount <= 0) {
				flagManager.setFlag("hasDied");
				playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
//				HealthSystem.setMaxHealth();
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
			if (AmmoScript.ammoScriptRunning) {
				if (Keyboard.isKeyDown(Key.ONE)) {

				}
				if (Keyboard.isKeyDown(Key.TWO)) {

				}
				if (Keyboard.isKeyDown(Key.THREE)) {

				}
			}

			// To control the amount of enemies that spawn per round. Cap is 72 enemies
			if (map.getEnemies().size() >= 80) {

				t.stop();

			}
			if (map.getEnemies().size() < 80) {

				t.start();

			}

			weapons pistol = new weapons(6, new Point(672, 104));
			pistol.setIsHidden(true);
			map.addNPC(pistol);
			AssaultRifle assaultRifle = new AssaultRifle(8, new Point(672, 296));
			assaultRifle.setIsHidden(true);
			map.addNPC(assaultRifle);
			MachineGun machineGun = new MachineGun(9, new Point(1104, 824));
			machineGun.setIsHidden(true);
			map.addNPC(machineGun);

			// Will check to see if the GunSmith script is running
			if (GunsmithScript.runningGUNSMITH == true) {
				// Case One: A pistol will appear on the map
				if (Keyboard.isKeyDown(Key.ONE) && MoneyBase.moneyCount >= 50) {
					MoneyBase.buyPistol();
					pistol.setIsHidden(false);

					// Case Two: A asssault rifle will appear on the map
				} else if (Keyboard.isKeyDown(Key.TWO) && MoneyBase.moneyCount >= 100) {
					MoneyBase.buyAssault();
					assaultRifle.setIsHidden(false);

					// Case Three: A machine gun will appear on the map
				} else if (Keyboard.isKeyDown(Key.THREE) && MoneyBase.moneyCount >= 500) {
					MoneyBase.buyMG();
					machineGun.setIsHidden(false);

				}
			}

			if (weapons.second) {		
				alexTwoWithPistol.update();
				map.update(alexTwoWithPistol);

				TimerPlayerTwoPistol.isTimeUp();

				if (TimerPlayerTwoPistol.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyforPlayerTwo)
						&& Keyboard.isKeyDown(shootingKeyforPlayerTwo)) {
					float fireballX;
					float movementSpeed;
					LightAmmo.ammoCount -= 1;

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
					shotsFired++;

					// add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					TimerPlayerTwoPistol.setWaitTime(500);
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
					shotsFired++;
					Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

					// add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					TimerPlayerOnePistol.setWaitTime(500);
				}

			}

			if (AssaultRifle.check) {
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
					shotsFired++;
					Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

					// add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					TimerPlayerOneAssaultRifle.setWaitTime(500);
				}

			}

			if (AssaultRifle.second) {

				alexTwoWithAssaultRifle.update();
				map.update(alexTwoWithAssaultRifle);

				TimerPlayerTwoAssaultRifle.isTimeUp();
				if (TimerPlayerTwoAssaultRifle.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyforPlayerTwo)
						&& Keyboard.isKeyDown(shootingKeyforPlayerTwo)) {
					float fireballX;
					float movementSpeed;
					LightAmmo.ammoCount -= 1;
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
					shotsFired++;

					// add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					TimerPlayerTwoAssaultRifle.setWaitTime(500);

				}

			}

			if (MachineGun.check) {
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
					shotsFired++;
					Shooting bullet = new Shooting(new Point(fireballX, fireballY), movementSpeed, 100000);

					// add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					TimerPlayerOneMachineGun.setWaitTime(500);
				}
			}

			if (MachineGun.second) {

				alexTwoWithMachineGun.update();
				map.update(alexTwoWithMachineGun);

				TimerPlayerTwoMachineGun.isTimeUp();
				if (TimerPlayerTwoMachineGun.isTimeUp() && !keyLocker.isKeyLocked(shootingKeyforPlayerTwo)
						&& Keyboard.isKeyDown(shootingKeyforPlayerTwo)) {
					float fireballX;
					float movementSpeed;
					LightAmmo.ammoCount -= 1;
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
					shotsFired++;

					// add fireball enemy to the map for it to offically spawn in the level
					map.addEnemy(bullet);
					TimerPlayerTwoMachineGun.setWaitTime(500);

				}

			}			
			map.update(player);	
			map.update2(coOp);	
			player.update();
			coOp.update();
			break;
		// if level has been completed, bring up level cleared screen
		case LEVEL_COMPLETED:
			 winScreen.update();
			break;
		}
		
		if (map.getFlagManager().isFlagSet("hasDied")) {
			System.out.println("True");
			System.exit(0);
			playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
		}

		// if flag is set at any point during gameplay, game is "won"

	}

	public void draw(GraphicsHandler graphicsHandler) {
		// create buffered image canvas here
		
		// do drawing things

		// paint entire buffered image at the end

		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
		case RUNNING:
			BufferedImage subImage = new BufferedImage(Config.GAME_WINDOW_WIDTH / 2, Config.GAME_WINDOW_HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D buffG = subImage.createGraphics();
			// store old graphics from graphics handler in variable
			Graphics2D tempG = graphicsHandler.getGraphics();
			// change graphics handler to point to buffered images graphics
			graphicsHandler.setGraphics(buffG);
			if (weapons.check) {
//				map.draw(alexWithAPistol, coOp, graphicsHandler);
				map.draw(alexWithAPistol, graphicsHandler);
			} else if (MachineGun.check) {
//				map.draw(alexWithAMachineGun, coOp, graphicsHandler);
				map.draw(alexWithAMachineGun, graphicsHandler);

			} else if (AssaultRifle.check) {
//				map.draw(alexWithARifle, coOp, graphicsHandler);
				map.draw(alexWithARifle, graphicsHandler);

			}
			if (!AssaultRifle.check && !weapons.check && !MachineGun.check) {
				map.draw3(player, coOp, graphicsHandler,1);
		//		map.draw(player, graphicsHandler);

			}
			
			// pasue game logic was moved to here
			if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
				isGamePaused = !isGamePaused;		
		}
			if (Keyboard.isKeyUp(pauseKey)) {
				keyLocker.unlockKey(pauseKey);
			}
			pauseLabel = new SpriteFont("HELP", 365, 280, "z", 24, Color.white);
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
			graphicsHandler.setGraphics(tempG);
			graphicsHandler.drawImage(subImage, 0, 0);		
			graphicsHandler.drawFilledRectangleWithBorder(Config.GAME_WINDOW_WIDTH / 2, 0, 25, 1000, new Color(40, 40, 40),new Color(40, 40, 40), 30);
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


			waveCounter.setText("");
			money.setText("");
			healthBar.setText("");
			ammoCount.setText("");
			//gameOver.draw(graphicsHandler);
			health.setIsHidden(true);
		}
// RIGHT SIDE		

		// do drawing things

		// paint entire buffered image at the end

		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
		case RUNNING:
			// create buffered image canvas here
			BufferedImage subImage2 = new BufferedImage(Config.GAME_WINDOW_WIDTH / 2, Config.GAME_WINDOW_HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D buffG2 = subImage2.createGraphics();
			// store old graphics from graphics handler in variable
			Graphics2D tempG2 = graphicsHandler.getGraphics();
			// change graphics handler to point to buffered images graphics
			graphicsHandler.setGraphics(buffG2);
			if (weapons.second) {
				map.draw2(alexTwoWithPistol, graphicsHandler);
			} else if (MachineGun.second) {
				map.draw2(alexTwoWithMachineGun, graphicsHandler);
			} else if (AssaultRifle.second) {
				map.draw2(alexTwoWithMachineGun, graphicsHandler);
			}
			if (!AssaultRifle.second && !weapons.second && !MachineGun.second) {
				map.draw3(coOp, player, graphicsHandler,2);
		//		map.draw2(coOp, graphicsHandler);
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
				graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(),
						ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
			}
			waveCounter.draw(graphicsHandler);
			money.draw(graphicsHandler);
			health.draw(graphicsHandler);
			healthBar.draw(graphicsHandler);
			ammoCount.draw(graphicsHandler);
			graphicsHandler.setGraphics(tempG2);
			graphicsHandler.drawImage(subImage2, Config.GAME_WINDOW_WIDTH / 2, 0);
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