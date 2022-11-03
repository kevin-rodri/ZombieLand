package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;

import java.util.HashMap;

// This is the class for the Alex player character
// basically just sets some values for physics and then defines animations 
public class Alex extends Player {

    public Alex(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("Alex.png"), 24, 24), x, y, "STAND_RIGHT");


        walkSpeed = 5f;
    }

    public void update() {
        super.update();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        // drawBounds(graphicsHandler, new Color(255, 0, 0, 170));
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                   new FrameBuilder(spriteSheet.getSprite(1, 2), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                   new FrameBuilder(spriteSheet.getSprite(1, 3), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                  new FrameBuilder(spriteSheet.getSprite(1, 4), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                  new FrameBuilder(spriteSheet.getSprite(1, 5), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                  new FrameBuilder(spriteSheet.getSprite(1, 6), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                  new FrameBuilder(spriteSheet.getSprite(1, 7), 100)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 4), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                   new FrameBuilder(spriteSheet.getSprite(1, 5), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                  new FrameBuilder(spriteSheet.getSprite(1, 6), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                  new FrameBuilder(spriteSheet.getSprite(1, 7), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
            });
        }};
    }
}