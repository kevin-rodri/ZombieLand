package Level;

import GameObject.Frame;
import GameObject.SpriteSheet;
import Engine.GraphicsHandler;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;


// This class is a base class for all enemies in the game -- all enemies should extend from it
// taken from SER-225 platformer game
public class Enemy extends MapEntity {

    public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public Enemy(float x, float y, Frame[] frames) {
        super(x, y, frames);
    }

    public Enemy(float x, float y, Frame frame) {
        super(x, y, frame);
    }

    public Enemy(float x, float y) {
        super(x, y);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(Player player) {
        super.update();
        if (intersects(player)) {
            touchedPlayer(player);
        }
    }

 
   // allows enemy freedom to walk around the map (from the npc class)
   // Note: changed move methods to check for collision
   public void walk(Direction direction, float speed) {
    if (direction == Direction.RIGHT) {
        this.currentAnimationName = "WALK_RIGHT";
    }
    else if (direction == Direction.LEFT) {
        this.currentAnimationName = "WALK_LEFT";
    }
    else {
        if (this.currentAnimationName.contains("RIGHT")) {
            this.currentAnimationName = "WALK_RIGHT";
        }
        else {
            this.currentAnimationName = "WALK_LEFT";
        }
    }
    if (direction == Direction.UP) {
        moveYHandleCollision(-speed);
    }
    else if (direction == Direction.DOWN) {
        moveYHandleCollision(speed);
    }
    else if (direction == Direction.LEFT) {
        moveXHandleCollision(-speed);
    }
    else if (direction == Direction.RIGHT) {
        moveXHandleCollision(speed);
    }
}
// method that will be used to ensure any enemy walks to the player
public void walktoPlayer(Direction direction, float speed, Point location) {
    walk(direction, speed);
}

    // A subclass can override this method to specify what it does when it touches the player
	public void touchedPlayer(Player player) {
        player.hurtPlayer(this);
    }


    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}