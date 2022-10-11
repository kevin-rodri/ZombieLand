package Maps;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.ImageEffect;
import GameObject.Sprite;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Colors;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Sprite alex;
    private Sprite alex2;
    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        Point alexLocation = getMapTile(5, 6).getLocation().subtractX(6).subtractY(7);
        alex = new Sprite(ImageLoader.loadSubImage("Alex.png", Colors.MAGENTA, 0, 0, 24, 24));
        alex.setScale(3);
        alex.setImageEffect(ImageEffect.FLIP_HORIZONTAL);
        alex.setLocation(50, 50);
        
        alex2 = new Sprite(ImageLoader.loadSubImage("Alex2.png", Colors.MAGENTA, 0, 0, 24, 24));
        alex2.setScale(3);
    //    alex2.setImageEffect(ImageEffect.FLIP_HORIZONTAL);
        alex2.setLocation(100, 50);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        alex.draw(graphicsHandler);
        alex2.draw(graphicsHandler);
    }
}
