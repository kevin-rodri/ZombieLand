package Scripts.TestMap;

import Engine.Config;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import MoneySystem.MoneyBase;
import java.util.Timer;
import java.util.TimerTask;

public class DoublePointsScript extends Script<NPC> {



    @Override
    protected void setup() {


        //timer.schedule(task, 2000, 5000);

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
