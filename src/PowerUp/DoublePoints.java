package PowerUp;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Level.PlayerState;
import Utils.Point;

import java.util.HashMap;

// This class is for DoublePoints
public class DoublePoints extends NPC implements SoundController {

    public DoublePoints(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("doublepoints.png"), 50, 43), "STAND_RIGHT");
    }

    public void update(Player player) {
        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
            this.setIsHidden(true);
            try {
                playSE(1);
       
             } catch(Exception e) {
                System.out.println("toString(): " + e.toString());
                System.out.println("getMessage(): " + e.getMessage());
                System.out.println("StackTrace: ");
                e.printStackTrace();
             }
         
            //this.setInteractScript(DoublePointsScript);
        }
        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(1)
                            //.withBounds(7, 13, 11, 7)
                            .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
