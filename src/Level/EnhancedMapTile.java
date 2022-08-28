package Level;

import Engine.GraphicsHandler;
import GameObject.GameObject;
import GameObject.SpriteSheet;

// This class is a base class for all enhanced map tiles in the game -- all enhanced map tiles should extend from it
public class EnhancedMapTile extends MapTile {

    public EnhancedMapTile(float x, float y, GameObject bottomLayer, GameObject topLayer, TileType tileType) {
        super(x, y, bottomLayer, topLayer, tileType);
    }

    public EnhancedMapTile(float x, float y, SpriteSheet spriteSheet, TileType tileType) {
        super(x, y, spriteSheet, tileType);
    }

    public void update(Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
