package Scripts.TestMap;

import Engine.Key;
import Engine.KeyLocker;
import Level.FlagManager;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import Engine.Keyboard;
import MoneySystem.MoneyBase;
// script for talking to walrus npc
import Utils.Stopwatch;

public class GunsmithScript extends Script<NPC> {
public static boolean pistolVis = false;
public static boolean assaultVis = false;
public static boolean mgVis = false;
private float counter;
private Stopwatch watch = new Stopwatch();
public static boolean runningGUNSMITH = false;

    @Override
    protected void setup() {
        lockPlayer();
        showTextbox();

        // changes what walrus says when talking to him the first time (flag is not set) vs talking to him afterwards (flag is set)
        if (!isFlagSet("hasTalkedToGunsmith") ) {
            addTextToTextboxQueue( "Hi!");
            addTextToTextboxQueue( "What gun would you like to purchase?\n1) Pistol  2) Assault Rifle  3) Special Machine Gun");
             runningGUNSMITH = true;

            watch.setWaitTime(30000);
        } else {
            if (counter == 0.0f){
                unsetFlag("hasTalkedToGunsmith");
                System.out.println("This works.");
            }
            counter = (((30000 - watch.getTimeLeft()) / 1000) %  60);
            addTextToTextboxQueue("You're on Cooldown please wait " + counter + " seconds");
            
        }
        entity.facePlayer(player);
    }

    @Override
    protected void cleanup() {
        unlockPlayer();
        hideTextbox();

        // set flag so that if walrus is talked to again after the first time, what he says changes
        setFlag("hasTalkedToGunsmith");
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
