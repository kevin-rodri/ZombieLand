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
import Players.Alex;
import Players.Alex2WithAPistol;
import Players.Alex2WithAssaultRifle;
import Players.Alex2WithMachineGun;
import Players.AlexWithAPistol;
import Players.AlexWithAssaultRifle;
import Players.AlexWithMachineGun;
import Players.SecondPlayer;
import Screens.PlayLevelScreen;
import Screens.PlayLevelScreen.PlayLevelScreenState;
import Scripts.TestMap.DoublePointsScript;
import Scripts.TestMap.GunsmithScript;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;
import Level.NPC;

public class weapons extends NPC implements SoundController{
	protected Player player;
	protected PlayLevelScreenState playLevelScreenState;
	public static boolean check = false;
	public static boolean second = false;
	 public weapons(int id, Point location) {
	        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("gamePistol.png"), 45, 45), "STAND_RIGHT");
	    }

	    public void update(Player player) {
			
			if ((player instanceof SecondPlayer || player instanceof Alex2WithAssaultRifle || player instanceof Alex2WithMachineGun) && player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING){
				second = true;
				this.setIsHidden(true);
				weapons.check = true;
				AssaultRifle.second = false;
				AssaultRifle.check = false;
				MachineGun.check = false;
				MachineGun.second = false;
			}

	        if ( (player instanceof Alex || player instanceof AlexWithAssaultRifle || player instanceof AlexWithMachineGun) && player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
	        	check = true;
				weapons.second = true;
	        	AssaultRifle.second = false;
				AssaultRifle.check = false;
				MachineGun.check = false;
				MachineGun.second = false;
	            this.setIsHidden(true);
	        	PlayLevelScreen playLevelScreen = new PlayLevelScreen(null);
				playLevelScreen.initialize();
				playLevelScreen.update();
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
