package GameSounds;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundFX {

    Clip clip;
    URL soundURL[] = new URL[10];
    String soundeffects[] = new String[10];
    public static void main(String[]args){
        SoundFX test = new SoundFX();
        test.setFile(0);
        test.play();

    }
    public SoundFX() {



        soundeffects[0]= ".//Resources//sounds/Explosion.wav";
        soundeffects[1]= ".//Resources//sounds/DoublePoints.wav";
        soundeffects[2]= ".//Resources//sounds/225GameSoundtrack.wav";
        soundeffects[3]= ".//Resources//sounds/Footstep.wav";
        soundeffects[4]= ".//Resources//sounds/HealthPack.wav";

    }

    public void setFile(int i) {
        try {
            File file = new File(soundeffects[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
            //System.out.println("PRINTING");
            //Game lags when you touch zombie


        } catch (Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();

    }

}