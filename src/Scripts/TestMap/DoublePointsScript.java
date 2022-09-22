package Scripts.TestMap;

import Engine.Config;
import Level.NPC;
import Level.Script;
import Level.ScriptState;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

// script for talking to walrus npc
public class DoublePointsScript extends Script<NPC> {

    AudioInputStream audioInputStream;
    static String filePath;

    @Override
    protected void setup() {
        //audioInputStream = AudioSystem.getAudioInputStream(new File(Config.RESOURCES_PATH + doublepoints.ogg).getAbsoluteFile());
        //dpoints = AudioSystem.getClip();
        //dpoints.open(audioInputStream);



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
