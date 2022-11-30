package GameSounds;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundFX {

    Clip clip;
    float previousVolume = 0;
    float currentVolume = 0;
    FloatControl fc;
    boolean mute = false;

    // increased the size to this much since alex and kyle's voice lines are going
    // to be a lot.
    URL soundURL[] = new URL[50];
    String soundeffects[] = new String[50];

    public SoundFX() {

        soundeffects[0] = ".//Resources//sounds/Explosion.wav";
        soundeffects[1] = ".//Resources//sounds/DoublePoints.wav";
        soundeffects[2] = ".//Resources//sounds/225GameSoundtrack.wav";
        soundeffects[3] = ".//Resources//sounds/Footstep.wav";
        soundeffects[4] = ".//Resources//sounds/HealthPack.wav";
        soundeffects[16] = ".//Resources//sounds/ReloadSoundFX.wav";
        soundeffects[19] = ".//Resources/sounds/GameSoundTrack.wav";
        // ammo script voice lines
        soundeffects[5] = ".//Resources//sounds/Ammo1.wav";
        soundeffects[6] = ".//Resources//sounds/Ammo2.wav";
        soundeffects[7] = ".//Resources//sounds/Ammo3.wav";
        soundeffects[8] = ".//Resources//sounds/Ammo4.wav";
        soundeffects[9] = ".//Resources//sounds/Ammo5.wav";

        // gun smith sctipt voice lines
        soundeffects[10] = ".//Resources/sounds/GunSmith1(audioBoost).wav";
        soundeffects[11] = ".//Resources/sounds/GunSmith2(audioBoost).wav";
        soundeffects[12] = ".//Resources//sounds/GunSmith3.wav";

        // Alex voice lines
        soundeffects[13] = ".//Resources//sounds/a-double-points_EXCITED.wav";
        soundeffects[14] = ".//Resources//sounds/a-reloading.wav";
        soundeffects[15] = ".//Resources//sounds/a-thats-gonna-leave-a-mark_SARCASTIC.wav";
        soundeffects[17] = ".//Resources//sounds/a-kaboom_EXCITED.wav";
        soundeffects[18] = ".//Resources//sounds/a-double-points_EXCITED.wav";
        soundeffects[20] = ".//Resources/sounds/a-itsshowtime.wav";
        soundeffects[21] = ".//Resources/sounds/a-hey-mush-brain-over-here_ATTEMPTED_ATTITUDE.wav";
        // Phalex voice lines
    }

    public String[] getSoundEffects() {
        return soundeffects;
    }

    public String getSoundEffString(int i) {
        return soundeffects[i];
    }

    // give it the current index and have it move on to the next
    public void playNext(int i) {
        setFile(i + 1);
        play();
    }

    public void setFile(int i) {
        try {
            File file = new File(soundeffects[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            // System.out.println("PRINTING");
            // Game lags when you touch zombie

        } catch (Exception e) {
        }
    }

    public void play() {
        // clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        // clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();

    }

    public void volumeUp() {
        currentVolume += 1.0f;
        System.out.println("Current Volume:" + currentVolume);
        if (currentVolume > 6.0f) {
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeDown() {
        currentVolume -= 1.0f;
        System.out.println("Current Volume:" + currentVolume);
        if (currentVolume < -80.0f) {
            currentVolume = -80.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeMute() {
        if (mute == false) {
            previousVolume = currentVolume;
            System.out.println("Current Volume:" + currentVolume);
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
        } else if (mute == true) {
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            mute = false;
        }
    }
}