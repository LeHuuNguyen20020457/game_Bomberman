package uet.oop.bomberman.Output;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Music implements Runnable {
    private static Clip music;

    public Music() {
        try {
            URL backgroundMusicPath = getClass().getResource("/audio/background.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(backgroundMusicPath);
            music = AudioSystem.getClip();
            music.open(sound);
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


    public void stop() {
        if (music != null && music.isRunning()) {
            music.stop();
        }
    }

    @Override
    public void run() {
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }
}