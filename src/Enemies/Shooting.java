package Enemies;
import java.util.ArrayList;
import java.util.HashMap;

import Ammo.LightAmmo;
import MoneySystem.MoneyBase;
import PowerUp.Nuke;
import Screens.PlayLevelScreen;
import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.Player;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

public class Shooting extends Enemy {
	private float movementSpeed;
	private Stopwatch existenceTimer = new Stopwatch();
	public static int bigDmg  = 0;

	public Shooting(Point location, float movementSpeed, int existenceTime) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("pistolBullet.png"), 7, 7), "DEFAULT");
		this.movementSpeed = movementSpeed;

		// how long the fireball will exist for before disappearing
		existenceTimer.setWaitTime(existenceTime);

		// this enemy will not respawn after it has been removed
//	        isRespawnable = false;

		initialize();
	}
	
	@Override
	public void update(Player player) {
		// if timer is up, set map entity status to REMOVED
		// the camera class will see this next frame and remove it permanently from the
		// map
		if (existenceTimer.isTimeUp()) {
			this.mapEntityStatus = MapEntityStatus.REMOVED;


		} else {
			// move fireball forward
			moveXHandleCollision(movementSpeed);
			super.update(player);
		}
		if (LightAmmo.ammoCount ==0 && LightAmmo.ammoClip == 0){
			this.mapEntityStatus = MapEntityStatus.REMOVED;
		}
	}
	@Override
	public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
		// if fireball collides with anything solid on the x axis, it is removed
		if (hasCollided) {
			this.mapEntityStatus = MapEntityStatus.REMOVED;
		}
		
		// if fireball collides with anything solid on the x axis, it is removed
		ArrayList<Enemy> enemy = map.getEnemies();
        for (int i= 0;i < enemy.size(); i++){
            // get reference of one enemy 
            Enemy get = enemy.get(i);
        for (int enemies = i + 1; enemies < enemy.size(); enemies++){
            //  Get a reference of a another enemy 
                 Enemy getCurrentEnemy = enemy.get(enemies);
                 // check to see if they collide with one another
                hasCollided = get.getBounds().intersects(getCurrentEnemy.getBounds());
                  // if an enemy collides with another one, get rid of both 
                  if (hasCollided){
					  bigDmg+=1;
					  this.mapEntityStatus = MapEntityStatus.REMOVED;
                    // get rid of both enemies
                  }
				  if(bigDmg ==2){
					  bigDmg=0;
					  MoneyBase.addMoneyMini();
					  entityCollidedWith = get;
					  enemy.remove(entityCollidedWith);
				  }

		}
	}
	}
	
	@Override
	public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("DEFAULT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0)).withScale(3).withBounds(1, 1, 5, 5).build() });
			}
		};
	}
}