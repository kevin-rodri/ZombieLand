package Scripts.TestMap;

import java.util.Timer;
import java.util.TimerTask;

import Engine.Key;
import Engine.KeyLocker;
import Level.FlagManager;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import Level.ScriptTwo;
import Engine.Keyboard;
import MoneySystem.MoneyBase;
// script for talking to walrus npc

public class GunSmithScriptTwo extends ScriptTwo<NPC> implements SoundController {
public static boolean runningGUNSMITH = false;
private Timer timer = new Timer();

    @Override
    protected void setup() {
        lockPlayer();
        showTextbox();

        // changes what walrus says when talking to him the first time (flag is not set) vs talking to him afterwards (flag is set)
        if (!isFlagSet("hasTalkedToGunsmithTwo") ) {
            addTextToTextboxQueue( "Why hello there, young sir Alex.");
            playSE(10);

//What gun are you looking for
            addTextToTextboxQueue( "Tis’ the season to enjoy a gun fight!\n1) Pistol $50  2) Assault Rifle $100  3) Machine Gun $500");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO: Compress the four sound files to one.
                    playSE(11);
                }
            }, 4000);
           
            
             runningGUNSMITH = true;
        } else {
            addTextToTextboxQueue( "Why hello there, young sir Alex.");
            playSE(10);

//What gun are you looking for
            addTextToTextboxQueue( "Tis’ the season to enjoy a gun fight!\n1) Pistol $50  2) Assault Rifle $100  3) Machine Gun $500");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO: Compress the four sound files to one.
                    playSE(11);
                }
            }, 4000);
        }
        entity.facePlayer(player);
    }

    @Override
    protected void cleanup() {
        unlockPlayer();
        hideTextbox();

        // set flag so that if walrus is talked to again after the first time, what he says changes
        setFlag("hasTalkedToGunsmithTwo");
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

