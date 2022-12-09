package player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Son implements Runnable {

    byte[] taille;

    public Son(byte[] taille) {
        this.taille = taille;
    }

    @Override
    public void run() {
        try {
            play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException {
        DataInputStream dataiInputStream = new DataInputStream(new ByteArrayInputStream(this.taille));
        Player player = new Player(dataiInputStream);
        player.play();
    }
    
}
