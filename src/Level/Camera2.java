package Level;

import Engine.GraphicsHandler;
import Engine.ScreenManager;
import GameObject.GameObject;
import GameObject.Rectangle;

import java.awt.*;
import java.util.ArrayList;

// This class represents a Map's "Camera", aka a piece of the map that is currently included in a level's update/draw logic based on what should be shown on screen.
// A majority of its job is just determining which map tiles, enemies, npcs, and enhanced map tiles are "active" each frame (active = included in update/draw cycle)
public class Camera2 extends Rectangle {

    // the current map this camera is attached to
    private Map map;

    // width and height of each tile in the map (the map's tileset has this info)
    private int tileWidth, tileHeight;

    // if the screen is covered in full length tiles, often there will be some extra room that doesn't quite have enough space for another entire tile
    // this leftover space keeps track of that "extra" space, which is needed to calculate the camera's current "end" position on the screen (in map coordinates, not screen coordinates)
    private int leftoverSpaceX, leftoverSpaceY;

    // current map entities that are to be included in this frame's update/draw cycle
    private ArrayList<EnhancedMapTile> activeEnhancedMapTiles = new ArrayList<>();
    private ArrayList<NPC> activeNPCs = new ArrayList<>();
    private ArrayList<Trigger> activeTriggers = new ArrayList<>();
    private ArrayList<Enemy> activeEnemies = new ArrayList<>();
    private int yLocation;
    private int xLocation;
   
    // determines how many tiles off screen an entity can be before it will be deemed inactive and not included in the update/draw cycles until it comes back in range
    private final int UPDATE_OFF_SCREEN_RANGE = 4;

    public Camera2(int startX, int startY, int tileWidth, int tileHeight, Map map) {
        super(startX, startY, ScreenManager.getScreenWidth() / tileWidth-15, ScreenManager.getScreenHeight() / tileHeight);
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.leftoverSpaceX = ScreenManager.getScreenWidth() % tileWidth ;
        this.leftoverSpaceY = ScreenManager.getScreenHeight() % tileHeight;
    }

    // gets the tile index that the camera's x and y values are currently on (top left tile)
    // this is used to determine a starting place for the rectangle of area the camera currently contains on the map
    public Point getTileIndexByCameraPosition() {
        int xIndex = Math.round(getX()) / tileWidth;
        int yIndex = Math.round(getY()) / tileHeight;
        return new Point(xIndex, yIndex);
    }

    public void update(Player player) {
        updateMapTiles();
        updateMapEntities(player);
        updateScripts();
    }

    private void updateMapTiles() {
        for (MapTile tile : map.getAnimatedMapTiles()) {
            // update each animated map tile in order to keep animations consistent
            tile.update();
        }
    }

    // update map entities currently a part of the update/draw cycle
    // active entities are calculated each frame using the loadActiveEntity methods below
    public void updateMapEntities(Player player) {
        activeEnhancedMapTiles = loadActiveEnhancedMapTiles();
        activeNPCs = loadActiveNPCs();
        activeEnemies = loadActiveEnemies();

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            enhancedMapTile.update(player);
        }

        for (NPC npc : activeNPCs) {
            npc.update(player);
        }
        for (Enemy enemy : activeEnemies) {
            enemy.update(player);
        }
    }
    
    // updates any currently running script
    // only one script should be able to be running (active) at a time
    private void updateScripts() {
        // if there is an active interact script, update the script
        if (map.getActiveScript() != null) {
            map.getActiveScript().update();
        }

        // if there is an active trigger, update the script
        activeTriggers = loadActiveTriggers();
        for (Trigger trigger : activeTriggers) {
            if (trigger.getTriggerScript() != null && trigger.getTriggerScript().isActive()) {
                trigger.getTriggerScript().update();
            }
        }
    }

    // determine which enhanced map tiles are active (exist and are within range of the camera)
    private ArrayList<EnhancedMapTile> loadActiveEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> activeEnhancedMapTiles = new ArrayList<>();
        for (int i = map.getEnhancedMapTiles().size() - 1; i >= 0; i--) {
            EnhancedMapTile enhancedMapTile = map.getEnhancedMapTiles().get(i);

            if (isMapEntityActive(enhancedMapTile)) {
                activeEnhancedMapTiles.add(enhancedMapTile);
                if (enhancedMapTile.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    enhancedMapTile.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (enhancedMapTile.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                enhancedMapTile.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (enhancedMapTile.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getEnhancedMapTiles().remove(i);
            }
        }
        return activeEnhancedMapTiles;
    }

    // determine which npcs are active (exist and are within range of the camera)
    private ArrayList<NPC> loadActiveNPCs() {
        ArrayList<NPC> activeNPCs = new ArrayList<>();
        for (int i = map.getNPCs().size() - 1; i >= 0; i--) {
            NPC npc = map.getNPCs().get(i);

            if (isMapEntityActive(npc)) {
                activeNPCs.add(npc);
                if (npc.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    npc.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (npc.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                npc.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (npc.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getNPCs().remove(i);
            }
        }
        return activeNPCs;
    }
    // determine which enemies are active (exist and are within range of the camera)
    private ArrayList<Enemy> loadActiveEnemies() {
        ArrayList<Enemy> activeEnemies = new ArrayList<>();
        for (int i = map.getEnemies().size() - 1; i >= 0; i--) {
            Enemy enemy = map.getEnemies().get(i);

            if (isMapEntityActive(enemy)) {
                activeEnemies.add(enemy);
                if (enemy.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    enemy.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (enemy.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                enemy.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (enemy.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getEnemies().remove(i);
            }
        }
        return activeEnemies;
    }

    // determine which trigger map tiles are active (exist and are within range of the camera)
    private ArrayList<Trigger> loadActiveTriggers() {
        ArrayList<Trigger> activeTriggers = new ArrayList<>();
        for (int i = map.getTriggers().size() - 1; i >= 0; i--) {
            Trigger trigger = map.getTriggers().get(i);

            if (isMapEntityActive(trigger)) {
                activeTriggers.add(trigger);
                if (trigger.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    trigger.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (trigger.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                trigger.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (trigger.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getTriggers().remove(i);
            }
        }
        return activeTriggers;
    }

    /*
        determines if map entity (enemy, enhanced map tile, or npc) is active by the camera's standards
        1. if entity's status is REMOVED, it is not active, no questions asked
        2. if an entity is hidden, it is not active
        3. if entity's status is not REMOVED and the entity is not hidden, then there's additional checks that take place:
            1. if entity's isUpdateOffScreen attribute is true, it is active
            2. OR if the camera determines that it is in its boundary range, it is active
     */
    private boolean isMapEntityActive(MapEntity mapEntity) {
        return mapEntity.getMapEntityStatus() != MapEntityStatus.REMOVED && !mapEntity.isHidden() && mapEntity.exists() && (mapEntity.isUpdateOffScreen() || containsUpdate(mapEntity));
    }
	public void draw(Player player, GraphicsHandler graphicsHandler) {
        drawMapTilesBottomLayer(graphicsHandler);
        drawMapEntities(player, graphicsHandler);
        drawMapTilesTopLayer(graphicsHandler);
    }
	public void draw(Player player, Player player2, GraphicsHandler graphicsHandler) {	
		drawMapTilesBottomLayer(graphicsHandler);
		drawMapEntities(player, player2, graphicsHandler);
		drawMapTilesTopLayer(graphicsHandler);	
	}
    // draws the bottom layer of visible map tiles to the screen
    // this is different than "active" map tiles as determined in the update method -- there is no reason to actually draw to screen anything that can't be seen
    // so this does not include the extra range granted by the UPDATE_OFF_SCREEN_RANGE value
    public void drawMapTilesBottomLayer(GraphicsHandler graphicsHandler) {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + height + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + width + 1; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null) {
                    tile.drawBottomLayer2(graphicsHandler);
                }
            }
        }

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            if (containsDraw(enhancedMapTile)) {
                enhancedMapTile.drawBottomLayer2(graphicsHandler);
            }
        }
    }

    // draws the top layer of visible map tiles to the screen where applicable
    public void drawMapTilesTopLayer(GraphicsHandler graphicsHandler) {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + height + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + width + 1; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null && tile.getTopLayer() != null) {
                    tile.drawTopLayer2(graphicsHandler);
                }
            }
        }

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            if (containsDraw(enhancedMapTile) && enhancedMapTile.getTopLayer() != null) {
                enhancedMapTile.drawTopLayer2(graphicsHandler);
            }
        }
    }

    // draws active map entities to the screen
    public void drawMapEntities(Player player, GraphicsHandler graphicsHandler) {
        ArrayList<NPC> drawNpcsAfterPlayer = new ArrayList<>();
        
        for (Enemy enemy : activeEnemies) {
            if (containsDraw(enemy)) {
                enemy.draw2(graphicsHandler);
            }
        }

        // goes through each active npc and determines if it should be drawn at this time based on their location relative to the player
        // if drawn here, npc will later be "overlapped" by player
        // if drawn later, npc will "cover" player
        for (NPC npc : activeNPCs) {
            if (containsDraw(npc)) {
                if (npc.getBounds().getY() < player.getBounds().getY1()  + (player.getBounds().getHeight() / 2f)) {
                    npc.draw2(graphicsHandler);
                }
                else {
                    drawNpcsAfterPlayer.add(npc);
                }
            }
        }

        // player is drawn to screen
        player.draw2(graphicsHandler);

        // npcs determined to be drawn after player from the above step are drawn here
        for (NPC npc : drawNpcsAfterPlayer) {
            npc.draw2(graphicsHandler);
        }

        // Uncomment this to see triggers drawn on screen
        // helps for placing them in the correct spot/debugging
        /*
         * for (Trigger trigger : activeTriggers) {
            if (containsDraw(trigger)) {
                trigger.draw(graphicsHandler);
            }
        }
         */
        
    }
    public void drawMapEntities(Player player, Player player2, GraphicsHandler graphicsHandler) {
		ArrayList<NPC> drawNpcsAfterPlayer = new ArrayList<>();
		for (Enemy enemy : activeEnemies) {
			if (containsDraw(enemy)) {
				enemy.draw2(graphicsHandler);
			}
		}

		// goes through each active npc and determines if it should be drawn at this
		// time based on their location relative to the player
		// if drawn here, npc will later be "overlapped" by player
		// if drawn later, npc will "cover" player
		for (NPC npc : activeNPCs) {
			if (containsDraw(npc)) {
				if (npc.getBounds().getY() < player.getBounds().getY1() + (player.getBounds().getHeight() / 2f)) {
					npc.draw2(graphicsHandler);
				} else {
					drawNpcsAfterPlayer.add(npc);
				}
			}
		}

		// player is drawn to screen
//		player.draw(graphicsHandler);
		player2.draw2(graphicsHandler);

		
		NPC playerNpc = new NPC(99,player.getX(),player.getY(),player.getCurrentAnimation()[player.getCurrentFrameIndex()]);
		playerNpc.setMap(map);
		playerNpc.draw2(graphicsHandler);
		
		// npcs determined to be drawn after player from the above step are drawn here
		for (NPC npc : drawNpcsAfterPlayer) {
			npc.draw2(graphicsHandler);
		}

		// Uncomment this to see triggers drawn on screen
		// helps for placing them in the correct spot/debugging
		/*
		 * for (Trigger trigger : activeTriggers) { if (containsDraw(trigger)) {
		 * trigger.draw(graphicsHandler); } }
		 */

	}


    // checks if a game object's position falls within the camera's current radius
    public boolean containsUpdate(GameObject gameObject) {
        return getX1() - (tileWidth * UPDATE_OFF_SCREEN_RANGE) < gameObject.getX() + gameObject.getWidth() &&
                getEndBoundX() + (tileWidth * UPDATE_OFF_SCREEN_RANGE) > gameObject.getX() &&
                getY1() - (tileHeight * UPDATE_OFF_SCREEN_RANGE) <  gameObject.getY() + gameObject.getHeight()
                && getEndBoundY() + (tileHeight * UPDATE_OFF_SCREEN_RANGE) > gameObject.getY();
    }

    // checks if a game object's position falls within the camera's current radius
    // this does not include the extra range granted by the UPDATE_OFF_SCREEN_RANGE value, because there is no point to drawing graphics that can't be seen
    public boolean containsDraw(GameObject gameObject) {
        return getX1() - tileWidth < gameObject.getX() + gameObject.getWidth() && getEndBoundX() + tileWidth > gameObject.getX() &&
                getY1() - tileHeight <  gameObject.getY() + gameObject.getHeight() && getEndBoundY() + tileHeight >  gameObject.getY();
    }

    public ArrayList<EnhancedMapTile> getActiveEnhancedMapTiles() {
        return activeEnhancedMapTiles;
    }

    public ArrayList<NPC> getActiveNPCs() {
        return activeNPCs;
    }

    public ArrayList<Trigger> getActiveTriggers() {
        return activeTriggers;
    }

    public ArrayList<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    // gets end bound X position of the camera (start position is always 0)
    public float getEndBoundX() {
        return x + (width * tileWidth) + leftoverSpaceX;
    }

    // gets end bound Y position of the camera (start position is always 0)
    public float getEndBoundY() {
        return y + (height * tileHeight) + leftoverSpaceY;
    }

    public boolean isAtTopOfMap() {
        return this.getY() <= 0;
    }

    public boolean isAtBottomOfMap() {
        return this.getEndBoundY() >= map.getEndBoundY();
    }
}
