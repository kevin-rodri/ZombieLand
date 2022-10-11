package Level;

import GameObject.GameObject;
import Utils.Direction;
import Utils.Point;

// This class has methods to check if a game object has collided with a map entity (map tile, enhanced map tile, npc, or trigger if applicable)
// it is used by the game object class to determine if and where a collision occurred
public class MapCollisionHandler {

    public static MapCollisionCheckResult getAdjustedPositionAfterCollisionCheckX(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
        float edgeBoundX = direction == Direction.LEFT ? gameObject.getBounds().getX1() : gameObject.getBounds().getX2();   
        Point tileIndex = map.getTileIndexByPosition(edgeBoundX, gameObject.getBounds().getY1());
        MapEntity entityCollidedWith = null;
    // checks for right side map collision 
        if( map.getMapTile(Math.round(tileIndex.x)+1, Math.round(tileIndex.y))==null)
        {	  
        	MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y));
        	if(mapTile!=null&&gameObject.getX2()>=map.getWidthPixels())
        	{
        		return new MapCollisionCheckResult((float)mapTile.getX1()-18 ,  mapTile);
        	}
        }
         // checks left side map collision        
        if(edgeBoundX<=0)
        {	
        	MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y));
        	if(mapTile!=null)
        	{
        		return new MapCollisionCheckResult((float)mapTile.getX1()-18, mapTile);
        	} 
        }
        
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y + j));
            if (mapTile != null && hasCollidedWithMapEntity(gameObject, mapTile, direction)) {
                entityCollidedWith = mapTile;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                    float adjustedPosition = mapTile.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                    float adjustedPosition = mapTile.getBoundsX2() - boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                }
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (!gameObject.equals(enhancedMapTile) && hasCollidedWithMapEntity(gameObject, enhancedMapTile, direction)) {
                entityCollidedWith = enhancedMapTile;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                    float adjustedPosition = enhancedMapTile.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                    float adjustedPosition = enhancedMapTile.getBoundsX2() - boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                }
            }
        }

        for (NPC npc : map.getActiveNPCs()) {
            if (!gameObject.equals(npc) && hasCollidedWithMapEntity(gameObject, npc, direction)) {
                entityCollidedWith = npc;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                    float adjustedPosition = npc.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                    float adjustedPosition = npc.getBoundsX2() - boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                }
            }
        }
        
        if (gameObject.isAffectedByTriggers()) {
            for (Trigger trigger : map.getActiveTriggers()) {
                if (!gameObject.equals(trigger) && trigger.exists() && hasCollidedWithMapEntity(gameObject, trigger, direction)) {
                    entityCollidedWith = trigger;
                    if (direction == Direction.RIGHT) {
                        float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                        float adjustedPosition = trigger.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                        return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                    } else if (direction == Direction.LEFT) {
                        float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                        float adjustedPosition = trigger.getBoundsX2() - boundsDifference;
                        return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                    }
                }
            }
        }

        // no collision occurred
        return new MapCollisionCheckResult(null, null);
    }
 
    public static MapCollisionCheckResult getAdjustedPositionAfterCollisionCheckY(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
        float edgeBoundY = direction == Direction.UP ? gameObject.getBounds().getY() : gameObject.getBounds().getY2();
        Point tileIndex = map.getTileIndexByPosition(gameObject.getBounds().getX1(), edgeBoundY);
        MapEntity entityCollidedWith = null;
//checks for top of map collision         
        if(edgeBoundY<20)
        {	
        	MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y));
        	if(mapTile!=null)  
        	{ 
        		return new MapCollisionCheckResult((float)mapTile.getY()-15, mapTile);
        	} 	    
        }
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x) + j, Math.round(tileIndex.y));
            if (mapTile != null && hasCollidedWithMapEntity(gameObject, mapTile, direction)) {
                entityCollidedWith = mapTile;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    float adjustedPosition = mapTile.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                    float adjustedPosition = mapTile.getBoundsY2() - boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                }
            }
        }

        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (!gameObject.equals(enhancedMapTile) && hasCollidedWithMapEntity(gameObject, enhancedMapTile, direction)) {
                entityCollidedWith = enhancedMapTile;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    float adjustedPosition = enhancedMapTile.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                    float adjustedPosition = enhancedMapTile.getBoundsY2() - boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                }
            }
        }

        for (NPC npc : map.getActiveNPCs()) {
            if (!gameObject.equals(npc) && hasCollidedWithMapEntity(gameObject, npc, direction)) {
                entityCollidedWith = npc;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    float adjustedPosition = npc.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                    float adjustedPosition = npc.getBoundsY2() - boundsDifference;
                    return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                }
            }
        }

        if (gameObject.isAffectedByTriggers()) {
            for (Trigger trigger : map.getActiveTriggers()) {
                if (!gameObject.equals(trigger) && trigger.exists() && hasCollidedWithMapEntity(gameObject, trigger, direction)) {
                    entityCollidedWith = trigger;
                    if (direction == Direction.DOWN) {
                        float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                        float adjustedPosition = trigger.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                        return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                    } else if (direction == Direction.UP) {
                        float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                        float adjustedPosition = trigger.getBoundsY2() - boundsDifference;
                        return new MapCollisionCheckResult(adjustedPosition, entityCollidedWith);
                    }
                }
            }
        }

        // no collision occurred
        return new MapCollisionCheckResult(null, null);
    }

    // based on tile type, perform logic to determine if a collision did occur with an intersecting tile or not
    private static boolean hasCollidedWithMapEntity(GameObject gameObject, MapEntity mapEntity, Direction direction) {
        if (mapEntity instanceof MapTile) {
            MapTile mapTile = (MapTile)mapEntity;
            switch (mapTile.getTileType()) {
                case PASSABLE:
                    return false;
                case NOT_PASSABLE:
                    return gameObject.intersects(mapTile);
                default:
                    return false;
            }
        }
        else {
            return mapEntity.intersects(gameObject);
        }
    }
}
