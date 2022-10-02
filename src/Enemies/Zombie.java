package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Level.PlayerState;
import Utils.Direction;
import Utils.Point;
import java.util.HashMap;

/*
 * The following code is a zombie class and will be one of the zombies that are apart of the game 
 * Code is from the SER-225 platformer game 
 */
public class Zombie extends Enemy {

	private float zombieSpeed = 1.5f;
    private Direction startFacingDirection;
    private Direction facingDirection;

    public Zombie(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Zombie.png"),23, 24), "WALK_RIGHT");
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
    }

    // Update player's state
    public void update(Player player){
        // this conditional will be temporary as I added it to test if the walk method works
        if (player.intersects(this) && player.getPlayerState() == PlayerState.WALKING){
            walk(facingDirection, zombieSpeed);
        }
        super.update();
    }
    
    public void remove(Shooting shooting, Player player2 ) {
    	 if (shooting.intersects(this)) {
             this.setIsHidden(true);
             
          
             //this.setInteractScript(DoublePointsScript);
         }
    	 super.update();
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        // hopefully will do after my issue with magenta
        return new HashMap<String, Frame[]>() {{
           put("WALK_RIGHT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(1, 0), 150)
                           .withScale(3)
                           .build(), 

                    new FrameBuilder(spriteSheet.getSprite(1, 1), 150)
                    .withScale(3)
                    .build()
            
           });
            put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 150)
                .withScale(3)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build(), 

         new FrameBuilder(spriteSheet.getSprite(1, 1), 150)
         .withScale(3)
         .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
         .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}