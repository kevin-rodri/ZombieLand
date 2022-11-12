package Scripts.TestMap;

import Engine.Key;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import Engine.Keyboard;

// script for talking to walrus npc
public class AmmoScript extends Script<NPC> {

    @Override
    protected void setup() {
        lockPlayer();
        showTextbox();

        // changes what walrus says when talking to him the first time (flag is not set) vs talking to him afterwards (flag is set)
        if (!isFlagSet("hasTalkedToAmmoNPC")) {
            addTextToTextboxQueue( "Hi!");
            addTextToTextboxQueue( "How much ammo would you like to purchase?\n1) 30 2) 60 3) 120");
            if(Keyboard.isKeyDown(Key.ONE)){

            }
            if(Keyboard.isKeyDown(Key.TWO)){

            }
            if(Keyboard.isKeyDown(Key.THREE)){

            }

        }
        else {
            addTextToTextboxQueue("You're on Cooldown please wait" + "x" + " minutes");
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
