package Builders;

import GameObject.Frame;
import GameObject.GameObject;
import Level.MapTile;
import Level.TileType;

import java.util.HashMap;

// Builder class to instantiate a MapTile class
public class MapTileBuilder {

    private TileType tileType = TileType.PASSABLE;
    private int tileIndex = -1;
    private HashMap<String, Frame[]> bottomLayer = new HashMap<>();;
    private HashMap<String, Frame[]> topLayer = new HashMap<>();;

    public MapTileBuilder(Frame bottomLayer) {
        this.bottomLayer.put("DEFAULT", new Frame[] { bottomLayer });
    }

    public MapTileBuilder(Frame[] bottomLayer) {
        this.bottomLayer.put("DEFAULT", bottomLayer);
    }

    public MapTileBuilder withTileType(TileType tileType) {
        this.tileType = tileType;
        return this;
    }

    public MapTileBuilder withTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        return this;
    }

    public MapTileBuilder withBottomLayer(Frame bottomLayer) {
        this.bottomLayer.put("DEFAULT", new Frame[] { bottomLayer });
        return this;
    }

    public MapTileBuilder withBottomLayer(Frame[] bottomLayer) {
        this.bottomLayer.put("DEFAULT", bottomLayer);
        return this;
    }

    public MapTileBuilder withTopLayer(Frame topLayer) {
        this.topLayer.put("DEFAULT", new Frame[] { topLayer });
        return this;
    }

    public MapTileBuilder withTopLayer(Frame[] topLayer) {
        this.topLayer.put("DEFAULT", topLayer);
        return this;
    }

    public HashMap<String, Frame[]> cloneAnimations(HashMap<String, Frame[]> animations) {
        HashMap<String, Frame[]> animationsCopy = new HashMap<>();
        for (String key : animations.keySet()) {
            Frame[] frames = animations.get(key);
            Frame[] framesCopy = new Frame[frames.length];
            for (int i = 0; i < framesCopy.length; i++) {
                framesCopy[i] = frames[i].copy();
            }
            animationsCopy.put(key, framesCopy);
        }
        return animationsCopy;
    }

    public MapTile build(float x, float y) {
        GameObject bottomLayerAnimation = new GameObject(x, y, cloneAnimations(bottomLayer), "DEFAULT");
        GameObject topLayerAnimation = null;
        if (!topLayer.isEmpty()) {
            topLayerAnimation = new GameObject(x, y, cloneAnimations(topLayer), "DEFAULT");
        }

        return new MapTile(x, y, bottomLayerAnimation, topLayerAnimation, tileType, tileIndex);
    }
}
