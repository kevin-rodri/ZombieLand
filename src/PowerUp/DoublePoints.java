package PowerUp;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import javax.swing.Timer;
import Level.PlayerState;
import Utils.Point;
import Utils.Stopwatch;
import MoneySystem.MoneyBase;
import javax.swing.Timer;
import Level.Script;


import com.sun.jdi.event.MonitorWaitedEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.TimerTask;


// This class is for DoublePoints
public class DoublePoints extends NPC implements SoundController {

    protected int counter = 0;
    public DoublePoints(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("doublepoints.png"), 50, 43), "STAND_RIGHT");

    }
    /*
    Timer t;
    private void time(){
        t = new Timer(60000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(counter>9){
                    t.stop();
                }


            }
        }
    }
*/
    public void update(Player player) {



        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
            this.setIsHidden(true);
            MoneyBase.addMoneyX2();


            try {
                playSE(1);

            } catch (Exception e) {
                System.out.println("toString(): " + e.toString());
                System.out.println("getMessage(): " + e.getMessage());
                System.out.println("StackTrace: ");
                e.printStackTrace();
            }

            //this.setInteractScript(DoublePointsScript);
        }
        /*java.util.Timer timer = new java.util.Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                System.out.println("Worked!");
            }
        };
        long delay = 30000L;
        timer.schedule(task,delay);*/

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
