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
import MoneySystem.MoneyBase;
import Screens.PlayLevelScreen;

import com.sun.jdi.event.MonitorWaitedEvent;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

// This class is for DoublePoints
public class DoublePoints extends NPC implements SoundController {
    protected int counter = 0;
    private Timer timer = new Timer();
    public DoublePoints(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("doublepoints.png"), 50, 43), "STAND_RIGHT");
    }

    public void update(Player player) {
        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
            this.setIsHidden(true);
            MoneyBase.addMoneyX2();
            playSE(18);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    
                }
            }, 1000);
            

             if(PlayLevelScreen.x2End == true){
                this.setIsHidden(true);
    
            }
            //this.setInteractScript(DoublePointsScript);
        }
        super.update(player);
    }
    public void endX2(){
        this.setIsHidden(true);
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
