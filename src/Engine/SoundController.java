package Engine;

import GameSounds.SoundFX;

interface SoundController{

    SoundFX sound = new SoundFX();

    public default void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public default void stopMusic() {
        sound.stop();

    }

    public default void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public default void volumeUp(){
        sound.volumeUp();
    }

    public default void volumeDown(){
        sound.volumeDown();
    }

    public default void mute(){
        sound.volumeMute();
    }
}

