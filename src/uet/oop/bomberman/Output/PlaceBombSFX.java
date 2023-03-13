package uet.oop.bomberman.Output;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaceBombSFX implements Runnable {
    private static Clip sfx;

    public PlaceBombSFX() {
        try {
            URL placeBombPath = getClass().getResource("/audio/placebomb.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(placeBombPath);
            sfx = AudioSystem.getClip();
            sfx.open(sound);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }

    @Override
    public void run() {
        sfx.setFramePosition(0);
        sfx.start();
    }
}
