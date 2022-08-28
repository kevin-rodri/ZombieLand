package Level;

import Engine.GraphicsHandler;

public class LayeredMapTile {
    private MapTile bottomLayer;
    private MapTile topLayer;

    public LayeredMapTile(MapTile bottomLayer) {
        this.bottomLayer = bottomLayer;
    }

    public LayeredMapTile(MapTile bottomLayer, MapTile topLayer) {
        this.bottomLayer = bottomLayer;
        this.topLayer = topLayer;
    }

    public MapTile getBottomLayer() {
        return bottomLayer;
    }

    public void setBottomLayer(MapTile bottomLayer) {
        this.bottomLayer = bottomLayer;
    }

    public MapTile getTopLayer() {
        return topLayer;
    }

    public void setTopLayer(MapTile topLayer) {
        this.topLayer = topLayer;
    }

    public void update() {
        bottomLayer.update();
        if (topLayer != null) {
            topLayer.update();
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        bottomLayer.draw(graphicsHandler);
        if (topLayer != null) {
            topLayer.draw(graphicsHandler);
        }
    }
}
