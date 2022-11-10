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
import Players.AlexWithAPistol;
import Screens.PlayLevelScreen;
import Screens.PlayLevelScreen.PlayLevelScreenState;
import Scripts.TestMap.DoublePointsScript;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;
import Level.NPC;

public class weapons extends NPC implements SoundController{
	protected Player player;
	protected PlayLevelScreenState playLevelScreenState;
	public static boolean check = false;
	 public weapons(int id, Point location) {
	        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("gamePistol.png"), 45, 45), "STAND_RIGHT");
	    }

	    public void update(Player player) {
	        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
	        	check = true;
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
