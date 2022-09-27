package Scripts.TestMap;

import Engine.Config;
import Level.NPC;
import Level.Script;
import Level.ScriptState;

public class DoublePointsScript extends Script<NPC> {



    @Override
    protected void setup() {


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
