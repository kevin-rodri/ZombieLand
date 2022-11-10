package Scripts.TestMap;

import Level.Script;
import Level.ScriptState;

// trigger script at beginning of game to set that heavy emotional plot
public class NukeScript extends Script {
    @Override
    protected void setup() {
        setFlag("usedNuke");
        System.out.println("NUKE!");
    }

    @Override
    protected void cleanup() {

    }

    @Override
    public ScriptState execute() {
        if (!isFlagSet("usedNuke")) {
            start();
            if (!isTextboxQueueEmpty()) {
                return ScriptState.RUNNING;
            }
            end();
        }
        return ScriptState.COMPLETED;
    }
}
