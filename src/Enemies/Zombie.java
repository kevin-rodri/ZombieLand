package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapCollisionCheckResult;
import Level.MapEntity;
import Level.Player;
import Level.PlayerState;
import Utils.Direction;
import Utils.Point;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * The following code is a zombie class and will be one of the zombies that are apart of the game 
 * Code is from the SER-225 platformer game 
 */
public class Zombie extends Enemy {


	private float zombieSpeed = 0.5f;
    private Direction startFacingDirection;
    private Direction facingDirection;
	public static boolean disappear;
	public static boolean check = true;

	public Zombie(Point location, Direction facingDirection) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Zombie.png"), 23, 24), "WALK_RIGHT");
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




	public void removeZombie(Shooting shooting) {
		// this conditional will be temporary as I added it to test if the walk method
		// works
		if (shooting.overlaps(this)) {
			disappear = true;
			System.out.println(true);
			update();
		}
		super.update();
	}


    // Update player's state
   public void update(Player player){
    // will be used to update direction of enemy
    Direction targetDirection;
    // Will get player's key movements in order to move zombie to the direction player is heading towards
    float xPosition = player.getX() - x;
    float yPosition = player.getY() - y;
    
    if (xPosition > zombieSpeed){
         targetDirection = Direction.RIGHT;
         walktoPlayer(targetDirection, zombieSpeed, player);
    } else {
       targetDirection = Direction.LEFT;
        walktoPlayer(targetDirection, zombieSpeed, player);
    }

    if (yPosition < zombieSpeed){
        targetDirection = Direction.UP;
        walktoPlayer(targetDirection, zombieSpeed, player);
    } else {
        targetDirection = Direction.DOWN;
        walktoPlayer(targetDirection, zombieSpeed, player);
    }

    // added this to avoid the glicthy collision
    if (player.intersects(this) && player.getPlayerState() == PlayerState.WALKING){
            this.setIsHidden(true);
     }   
     if(disappear == true) {
        this.setIsHidden(true);
    }
     super.update();
    }

//method to see if enemies collide with one another
// Really useful for current enemies in the game
// Code for this was taken from here: https://stackoverflow.com/questions/16250790/stopping-sprites-from-overlapping-going-through-each-other
    // also applies for the  onEndCollisionCheckY method
    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        ArrayList<Enemy> enemy = map.getEnemies();
        for (int i= 0;i < enemy.size(); i++){
            // get reference of one enemy 
            Enemy get = enemy.get(i);
        for (int enemies = i + 1; enemies < enemy.size(); enemies++){
            //  Get a reference of a another enemy 
                 Enemy getCurrentEnemy = enemy.get(enemies);
                 // check to see if they collide with one another
                hasCollided = get.getBounds().overlaps(getCurrentEnemy.getBounds());
                  // if an enemy collides with another one, separate them 
                  if (hasCollided){
                    entityCollidedWith = getCurrentEnemy; 
                    // check which one has a bigger x position
                    // if so, update their position
                     if (get.getX() >= getCurrentEnemy.getX()){
                          get.setX( get.getX() + 5);
                          getCurrentEnemy.setX(getCurrentEnemy.getX() - 5);
                      } else {
                        get.setX(get.getX() - 5);
                        getCurrentEnemy.setX(getCurrentEnemy.getX() + 5); 
                      }
                  }
            }
        }
    }

// method to see if enemies collide with one another
// Really useful for current enemies in the game
    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        ArrayList<Enemy> enemy = map.getEnemies();
        for (int i= 0;i < enemy.size(); i++){
                      // get reference of one enemy 
            Enemy get = enemy.get(i);
        for (int enemies = i + 1; enemies < enemy.size(); enemies++){
              //  Get a reference of a another enemy 
            Enemy getCurrentEnemy = enemy.get(enemies);
            hasCollided = get.getBounds().overlaps(getCurrentEnemy.getBounds());
               if (hasCollided){
                entityCollidedWith = getCurrentEnemy;
                 // check which one has a bigger y position
                    // if so, update their position
              if (get.getY() >= getCurrentEnemy.getY()){
                       get.setY(get.getY() + 5);      
                       getCurrentEnemy.setY(getCurrentEnemy.getY() - 5);
                 } else {
                       getCurrentEnemy.setY(getCurrentEnemy.getY() + 5);
                       get.setY(get.getY() - 5);     
                   }
               }
            }
        }
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
                           .withBounds(10, 10, 20, 20)
                           .build(), 

                    new FrameBuilder(spriteSheet.getSprite(1, 1), 150)
                    .withScale(3)
                    .withBounds(10, 10, 20, 20)
                    .build()
            
           });
            put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 150)
                .withScale(3)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(10, 10, 20, 20)
                .build(), 

         new FrameBuilder(spriteSheet.getSprite(1, 1), 150)
         .withScale(3)
         .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
         .withBounds(10, 10, 20, 20)
         .build()
            });
        }};
    }



	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
	}

}