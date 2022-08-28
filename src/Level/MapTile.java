package Level;

import Engine.GraphicsHandler;
import GameObject.GameObject;
import GameObject.IntersectableRectangle;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Utils.Point;

// Represents a map tile in a Map's tile map
public class MapTile extends MapEntity {
    // this determines a tile's properties, like if it's passable or not
    protected TileType tileType;

    // bottom layer of tile
    protected GameObject bottomLayer;

    // top layer of tile ("pasted on top of" bottom layer, covers player)
    protected GameObject topLayer;

    private int tileIndex;

    public MapTile(float x, float y, GameObject bottomLayer, GameObject topLayer, TileType tileType, int tileIndex) {
        super(x, y);
        this.bottomLayer = bottomLayer;
        this.topLayer = topLayer;
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, GameObject bottomLayer, GameObject topLayer, TileType tileType) {
        super(x, y);
        this.bottomLayer = bottomLayer;
        this.topLayer = topLayer;
        this.tileType = tileType;
    }

    public MapTile(float x, float y, SpriteSheet spriteSheet, TileType tileType) {
        super(x, y);
        this.bottomLayer = loadBottomLayer(spriteSheet);
        this.topLayer = loadTopLayer(spriteSheet);
        this.tileType = tileType;
    }

    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        return null;
    }

    protected GameObject loadTopLayer(SpriteSheet spriteSheet) {
        return null;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public GameObject getBottomLayer() { return bottomLayer; }
    public void setBottomLayer(GameObject bottomLayer) { this.bottomLayer = bottomLayer; }

    public GameObject getTopLayer() { return topLayer; }
    public void setTopLayer(GameObject topLayer) { this.topLayer = topLayer; }

    // determines if tile is animated or not
    public boolean isAnimated() {
        return (bottomLayer.getCurrentAnimation().length > 1) ||
                (topLayer != null && topLayer.getCurrentAnimation().length > 1);
    }

    // set this game object's map to make it a "part of" the map, allowing calibrated positions and collision handling logic to work
    // note that both the bottom layer and the top layer need the map reference
    public void setMap(Map map) {
        this.map = map;
        this.bottomLayer.setMap(map);
        if (topLayer != null) {
            this.topLayer.setMap(map);
        }
    }

    @Override
    public void update() {
        bottomLayer.update();
        if (topLayer != null) {
            topLayer.update();
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        bottomLayer.draw(graphicsHandler);
        if (topLayer != null) {
            topLayer.draw(graphicsHandler);
        }
        //drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
    }

    public void drawBottomLayer(GraphicsHandler graphicsHandler) {
        bottomLayer.draw(graphicsHandler);
    }

    public void drawTopLayer(GraphicsHandler graphicsHandler) {
        topLayer.draw(graphicsHandler);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getX1() { return bottomLayer.getX(); }

    @Override
    public float getY1() { return bottomLayer.getY(); }

    @Override
    public float getX2() { return bottomLayer.getX2(); }

    @Override
    public float getY2() { return bottomLayer.getY2(); }

    @Override
    public float getBoundsX1() {
        return bottomLayer.getBoundsX1();
    }

    @Override
    public float getBoundsX2() {
        return bottomLayer.getBoundsX2();
    }

    @Override
    public float getBoundsY1() {
        return bottomLayer.getBoundsY1();
    }

    @Override
    public float getBoundsY2() {
        return bottomLayer.getBoundsY2();
    }

    @Override
    public Point getLocation() {
        return new Point(x, y);
    }

    @Override
    public Rectangle getIntersectRectangle() {
        return bottomLayer.getIntersectRectangle();
    }

    @Override
    public int getWidth() {
        return bottomLayer.getWidth();
    }

    @Override
    public int getHeight() {
        return bottomLayer.getHeight();
    }

    @Override
    public Rectangle getBounds() {
        return bottomLayer.getBounds();
    }

    @Override
    public void setX(float x) {
        this.x = x;
        bottomLayer.setX(x);
        if (topLayer != null) {
            topLayer.setX(x);
        }
    }

    @Override
    public void setY(float y) {
        this.y = y;
        bottomLayer.setY(y);
        if (topLayer != null) {
            topLayer.setY(y);
        }
    }

    @Override
    public void setLocation(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void moveX(float dx) {
        this.x += dx;
        bottomLayer.moveX(dx);
        if (topLayer != null) {
            topLayer.moveX(dx);
        }
    }

    @Override
    public void moveRight(float dx) {
        this.x += dx;
        bottomLayer.moveRight(dx);
        if (topLayer != null) {
            topLayer.moveX(dx);
        }
    }

    @Override
    public void moveLeft(float dx) {
        this.x -= dx;
        bottomLayer.moveLeft(dx);
        if (topLayer != null) {
            topLayer.moveLeft(dx);
        }
    }

    @Override
    public void moveY(float dy) {
        this.y += dy;
        bottomLayer.moveY(dy);
        if (topLayer != null) {
            topLayer.moveY(dy);
        }
    }

    @Override
    public void moveDown(float dy) {
        this.y += dy;
        bottomLayer.moveDown(dy);
        if (topLayer != null) {
            topLayer.moveDown(dy);
        }
    }

    @Override
    public void moveUp(float dy) {
        this.y -= dy;
        bottomLayer.moveUp(dy);
        if (topLayer != null) {
            topLayer.moveUp(dy);
        }
    }

    @Override
    public boolean intersects(IntersectableRectangle other) {
        return bottomLayer.intersects(other);
    }

    @Override
    public boolean overlaps(IntersectableRectangle other) { return bottomLayer.overlaps(other); }

    @Override
    public float getAreaOverlapped(IntersectableRectangle other) { return bottomLayer.getAreaOverlapped(other); }
}
