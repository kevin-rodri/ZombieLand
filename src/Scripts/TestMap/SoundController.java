package Scripts.TestMap;

import GameSounds.SoundFX;

public interface SoundController {
    
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
}
