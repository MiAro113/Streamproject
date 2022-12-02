package client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import javazoom.jl.*;
public class Client{
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException, JavaLayerException{
        Socket socket = new Socket("localhost", 2812);
        Thread thread = new Thread(new RequesterThread(socket));
        thread.start();
        DataInputStream inputdata = new DataInputStream(socket.getInputStream());

        int leng = 128*1024;
        int receive = 0;
        int i = 0;
        byte[] byt = new byte[leng];
        byte msg;
        String filename = inputdata.readUTF();
        System.out.println(filename);

        if(filename.contains(".mp3")){
            while(true){
                inputdata.readFully(byt, 0,  leng);
                play(byt);
                System.out.println("play...");
            }
        }
        else if(filename.contains(".jpg") || filename.contains(".png") || filename.contains(".jpeg")){

        }
        else if(filename.contains(".mkv")){
            File temp = File.createTempFile("temp","mkv");
            FileOutputStream outputfile = new FileOutputStream(temp);
            Thread thrd = new Thread(new Write(inputdata, outputfile));
            thrd.start();
            EmbeddedMediaPlayerComponent medcomponent = new EmbeddedMediaPlayerComponent();
            JFrame frame = new JFrame();
            frame.setContentPane(medcomponent);
            frame.setBounds(new Rectangle(200,200,800,600));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            Thread.sleep(2000);
            medcomponent.mediaPlayer().media().play(temp.toPath().toString());
        }
    }
}