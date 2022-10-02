package Maps;
import NPCs.*;



import NPCs.Lives;
import Players.*;
import PowerUp.ExtraLife;
import Screens.PlayLevelScreen;
import EnhancedMapTiles.Rock;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.Trigger;


import NPCs.Dinosaur;
import NPCs.Walrus;
import PowerUp.DoublePoints;
import PowerUp.Nuke;
import PowerUp.weapons;
import Scripts.SimpleTextScript;
import Scripts.TestMap.DinoScript;
import Scripts.TestMap.LostBallScript;
import Scripts.TestMap.TreeScript;
import Scripts.TestMap.WalrusScript;
import Tilesets.CommonTileset;
import Utils.Direction;

import java.util.ArrayList;

import Enemies.Shooting;
import Enemies.Zombie;


// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(17, 20).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        enhancedMapTiles.add(new Rock(getMapTile(2, 7).getLocation()));
        enhancedMapTiles.add(new EnhancedMapTiles.Lives(getMapTile(18, 21).getLocation()));
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

        DoublePoints doublePoints = new DoublePoints(3, getMapTile(14, 5).getLocation().subtractY(40));
        doublePoints.setInteractScript(new WalrusScript());
        npcs.add(doublePoints);

        Nuke nuke = new Nuke(4, getMapTile(14, 2).getLocation().subtractY(40));
        nuke.setInteractScript(new WalrusScript());
        npcs.add(nuke);
        
        weapons pistol = new weapons(6, getMapTile(14, 3).getLocation().subtractY(40));
        nuke.setInteractScript(new WalrusScript());
        npcs.add(pistol);

        ExtraLife lifeup = new ExtraLife(5, getMapTile(20, 1).getLocation().subtractY(40));
        lifeup.setInteractScript(new WalrusScript());
        npcs.add(lifeup);



        return npcs;
    }

    @Override
    public ArrayList<Enemy> loadEnemies(){
        ArrayList<Enemy> enemy = new ArrayList<>();
        // after playing with this, I have given up on picking a good starting point... 
        Zombie zombie = new Zombie(getMapTile(10, 6).getLocation(), Direction.RIGHT);
        enemy.add(zombie);
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

