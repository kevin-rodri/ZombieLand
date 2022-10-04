package Scripts.TestMap;

import Engine.Config;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import MoneySystem.MoneyBase;

public class DoublePointsScript extends Script<NPC> {



    @Override
    protected void setup() {
        showTextbox();
        addTextToTextboxQueue("DOUBLEPOINTS!");
        //MoneyBase.addMoney();

    }

    @Override
    protected void cleanup() {


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
