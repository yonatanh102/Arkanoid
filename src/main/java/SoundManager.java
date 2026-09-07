import java.net.URL;

import javafx.scene.media.AudioClip;

public class SoundManager {
    private static AudioClip blockHitSound;
    private static AudioClip powerUpSound;
    private static AudioClip paddleHitSound;

    public static void loadSounds() {
        try {
            blockHitSound = loadClip("/sounds/impactGeneric_light_003.wav");
            powerUpSound = loadClip("/sounds/impactMining_002.wav");
            paddleHitSound = loadClip("/sounds/impactWood_medium_001.wav");
        } catch (Exception e) {
            System.out.println("Warning: Could not load sound files. " + e.getMessage());
        }
    }

    private static AudioClip loadClip(String path) {
        URL url = SoundManager.class.getResource(path);
        if (url != null) {
            return new AudioClip(url.toExternalForm());
        }
        return null;
    }

    public static void playBlockHit() {
        if (blockHitSound != null) blockHitSound.play();
    }

    public static void playPowerUp() {
        if (powerUpSound != null) powerUpSound.play();
    }
    
    public static void playPaddleHit() {
        if (paddleHitSound != null) paddleHitSound.play();
    }
}