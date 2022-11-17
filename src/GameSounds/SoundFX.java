package GameSounds;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundFX {

    Clip clip;
    // increased the size to this much since alex and kyle's voice lines are going to be a lot. 
    URL soundURL[] = new URL[50];
    String soundeffects[] = new String[50];
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

        // ammo script voice lines
        soundeffects[5] =".//Resources//sounds/Ammo1.wav";
        soundeffects[6] = ".//Resources//sounds/Ammo2.wav";
        soundeffects[7] = ".//Resources//sounds/Ammo3.wav";
        soundeffects[8] = ".//Resources//sounds/Ammo4.wav";
        soundeffects[9] = ".//Resources//sounds/Ammo5.wav";

        //gun smith sctipt voice lines 
        soundeffects[10] = ".//Resources//sounds/GunSmith1.wav";
        soundeffects[11] = ".//Resources//sounds/GunSmith2.wav";
        soundeffects[12] = ".//Resources//sounds/GunSmith3.wav";

        // Alex voice lines 


        // Phalex voice lines 
    }

    public String[] getSoundEffects(){
        return soundeffects;
    }

    public String getSoundEffString(int i){
        return soundeffects[i];
    }

    // give it the current index and have it move on to the next 
    public void playNext(int i){
        setFile(i + 1);
        play();
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