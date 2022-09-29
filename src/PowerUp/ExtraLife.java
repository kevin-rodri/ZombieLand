package PowerUp;

import java.util.HashMap;

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

public class ExtraLife extends NPC implements SoundController {
    public ExtraLife(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Heart.png"), 13, 13), "STAND_LEFT");
    }

    public void update(Player player) {
        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
            this.setIsHidden(true);
            playSE(4);

        }
        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
//                            .withBounds(7, 13, 11, 7)
                            .build()
            });

        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
