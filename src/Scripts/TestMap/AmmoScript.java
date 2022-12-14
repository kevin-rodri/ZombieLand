package Scripts.TestMap;

import java.util.Timer;
import java.util.TimerTask;

import Engine.Key;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import Utils.Stopwatch;
import Engine.Keyboard;


// script for talking to walrus npc
public class AmmoScript extends Script<NPC> implements SoundController {
    public static boolean ammoScriptRunning = false;
    public static boolean notEnoughFunds = false;
    private Timer timer = new Timer();

    @Override
    protected void setup() {
        lockPlayer();
        showTextbox();

        // changes what walrus says when talking to him the first time (flag is not set) vs talking to him afterwards (flag is set)
        if (!isFlagSet("hasTalkedToAmmoNPC")) {

            addTextToTextboxQueue( "Woah, it’s you Alex! What brings you here today?");
            playSE(5);
        

            addTextToTextboxQueue( "Oh right… they’re everywhere. I think I can help with \nthat.");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    playSE(6);
                }
            }, 5000);
           
            addTextToTextboxQueue( "I have plenty of options to choose from: \n1) 30 - $100 2) 60 - $150 3) 120 - $200");
             
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: Compress the four sound files to one.
                        playSE(7);
                    }
                }, 10000);

            
                addTextToTextboxQueue( "Alright, good luck. Watch out for the crazy’s... ");
               timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: Compress the four sound files to one.
                        playSE(8);
                    }
                }, 20000);
                
            
       
            ammoScriptRunning = true;


        } else {

            addTextToTextboxQueue( "Woah, it’s you Alex! What brings you here today?");
            playSE(5);
        

            addTextToTextboxQueue( "Oh right… they’re everywhere. I think I can help with \nthat.");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    playSE(6);
                }
            }, 5000);
           
            addTextToTextboxQueue( "I have plenty of options to choose from: \n1) 30 - $100 2) 60 - $150 3) 120 - $200");
             
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: Compress the four sound files to one.
                        playSE(7);
                    }
                }, 10000);

            
                addTextToTextboxQueue( "Alright, good luck. Watch out for the crazy’s... ");
               timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: Compress the four sound files to one.
                        playSE(8);
                    }
                }, 20000);
                
            
    }

        entity.facePlayer(player);
    }

    @Override
    protected void cleanup() {
        unlockPlayer();
        hideTextbox();

        // set flag so that if walrus is talked to again after the first time, what he says changes
        setFlag("hasTalkedToAmmoNPC");
    }

    @Override
    public ScriptState execute() {
        start();
        if (!isTextboxQueueEmpty()) {
            return ScriptState.RUNNING;
        }
        end();
        return ScriptState.COMPLETED;
    }
}
