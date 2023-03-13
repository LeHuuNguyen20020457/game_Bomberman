package uet.oop.bomberman.Output;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MonsterKillSFX implements Runnable {
    private static Clip sfx;

    public MonsterKillSFX() {
        try {
            URL monsterKillPath = getClass().getResource("/audio/monsterdead.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(monsterKillPath);
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
