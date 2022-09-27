package Enemies;

import GameSounds.SoundFX;

interface SoundController{

    SoundFX sound = new SoundFX();

    public default void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public default void stopMuisc() {
        sound.stop();

    }

    public default void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}

