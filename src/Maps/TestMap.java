package Maps;
import NPCs.*;



import NPCs.Lives;
import Players.*;
import PowerUp.ExtraLife;
import PowerUp.MachineGun;
import EnhancedMapTiles.Rock;
import Level.Camera;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.Trigger;


import NPCs.Dinosaur;
import NPCs.Walrus;
import PowerUp.AssaultRifle;
import PowerUp.DoublePoints;
import PowerUp.Nuke;
import PowerUp.weapons;
import Screens.PlayLevelScreen;
import Scripts.SimpleTextScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Enemies.SmallZombie;
import Enemies.Zombie;
import java.util.Random;
import java.lang.Math;


// Represents a test map to be used in a level
public class TestMap extends Map {
	public static Point location;
    private Timer zombieTimer;


    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(17, 20).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        enhancedMapTiles.add(new Rock(getMapTile(2, 7).getLocation()));
//        enhancedMapTiles.add(new EnhancedMapTiles.Lives(getMapTile(18, 21).getLocation()));
        return enhancedMapTiles;
    }
    
  
    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Walrus walrus = new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40));
        walrus.setInteractScript(new WalrusScript());
        npcs.add(walrus);

        Dinosaur dinosaur = new Dinosaur(2, getMapTile(13, 4).getLocation());
        dinosaur.setExistenceFlag("hasTalkedToDinosaur");
        dinosaur.setInteractScript(new DinoScript());
        npcs.add(dinosaur);

        DoublePoints doublePoints = new DoublePoints(3, getMapTile(18, (int) Math.floor(Math.random()*(30-1+1)+1)).getLocation().subtractY(40));
        doublePoints.setInteractScript(new DoublePointsScript());
        npcs.add(doublePoints);

        Nuke nuke = new Nuke(4, getMapTile(14, (int) Math.floor(Math.random()*(30-1+1)+1)).getLocation().subtractY(40));
        nuke.setInteractScript(new NukeScript());
        npcs.add(nuke);
        

        ExtraLife lifeup = new ExtraLife(5, getMapTile(30, (int) Math.floor(Math.random()*(30-1+1)+1)).getLocation().subtractY(40));
        lifeup.setInteractScript(new WalrusScript());
        npcs.add(lifeup);

        AmmoNPC ammonpc = new AmmoNPC(10, getMapTile(15, 28).getLocation().subtractY(40));
        ammonpc.setInteractScript(new AmmoScript());
        npcs.add(ammonpc);

        Gunsmith gunsmith = new Gunsmith(7, getMapTile(20, 28).getLocation().subtractY(40));
        gunsmith.setInteractScript(new GunsmithScript());
        npcs.add(gunsmith);

       
    
        return npcs;


    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
  ArrayList<Enemy> enemy = new ArrayList<>();
          // after playing with this, I have given up on picking a good starting point...
 Random random = new Random();
 // generate a number from 1 - 11
 int randomX  = 1 + random.nextInt(10);
 // generate a number from 1 - 11
 int randomY  = 1 + random.nextInt(10);
 Zombie zombieOne = new Zombie(getMapTile(randomX,randomX).getLocation(), Direction.DOWN);
 SmallZombie zombieWaveOne  = new SmallZombie(getMapTile(randomX + 3,randomY).getLocation(), zombieOne.getZombieDirection());
 enemy.add(zombieWaveOne); 
 enemy.add(zombieOne);
return enemy;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(790, 1030, 100, 10, new LostBallScript(), "hasLostBall"));
        triggers.add(new Trigger(790, 960, 10, 80, new LostBallScript(), "hasLostBall"));
        triggers.add(new Trigger(890, 960, 10, 80, new LostBallScript(), "hasLostBall"));
        return triggers;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setInteractScript(new SimpleTextScript("Cat's house"));

        getMapTile(7, 26).setInteractScript(new SimpleTextScript("Walrus's house"));

        getMapTile(20, 4).setInteractScript(new SimpleTextScript("Dino's house"));

        getMapTile(2, 6).setInteractScript(new TreeScript());
    }
}

