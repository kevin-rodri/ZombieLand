package PowerUp;

import Builders.FrameBuilder;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.FlagManager;
import Level.NPC;
import Level.Player;
import Level.PlayerState;
import Scripts.TestMap.DoublePointsScript;
import Scripts.TestMap.NukeScript;
import Utils.Point;

import java.util.HashMap;

// This class is for DoublePoints
public class Nuke extends NPC implements SoundController {

    public Nuke(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("nuke.png"), 50, 43), "STAND_RIGHT");
    }
    public static boolean usedNuke = false;

    public void update(Player player) {
        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
            this.setIsHidden(true);
            usedNuke=true;

            try {
                playSE(0);
       
             } catch(Exception e) {
                System.out.println("toString(): " + e.toString());
                System.out.println("getMessage(): " + e.getMessage());
                System.out.println("StackTrace: ");
                e.printStackTrace();
             }
         

        }
        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {
            {

                put("STAND_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(1)
                                // .withBounds(7, 13, 11, 7)
                                .build()
                });
            }
        };
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
