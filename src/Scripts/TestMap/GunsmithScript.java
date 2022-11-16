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
