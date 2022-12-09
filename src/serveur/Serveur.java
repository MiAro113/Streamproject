package serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import java.lang.*;
import javax.imageio.ImageIO;

public class Serveur {

  public static DataOutputStream dataOutputStream = null;
  public static DataInputStream dataInputStream = null;
  public  static Socket socket = null;

  public static void main(String[] args) throws IOException, ClassNotFoundException,InterruptedException {

    ServerSocket serverSocket = new ServerSocket(1910);

    Socket socket = serverSocket.accept();
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    File file = new File("F:/Mota/etudes/s3/reseau/Streamproj/ficher/ColReyel.mp3");
    out.writeUTF(file.getName().toLowerCase());

    File fichierMp3 = new File("F:/Mota/etudes/s3/reseau/Streamproj/ficher/fort.jpg");
    out.writeUTF(fichierMp3.getName().toLowerCase());

    File fichier = new File("F:/Mota/etudes/s3/reseau/Streamproj/ficher/changes.mp4");
    dataOutputStream = new DataOutputStream(socket.getOutputStream());
    dataOutputStream.writeUTF(fichier.getName().toLowerCase());

    while(true) {
      ObjectInputStream objectinputStream = new ObjectInputStream(socket.getInputStream());
      String demande = (String) objectinputStream.readObject();

      if(demande.contains(".jpg")) {
        System.out.println("patientez de l'image");
        ObjectOutputStream objectoutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectoutputStream.writeObject(demande);
        OutputStream outputStream=socket.getOutputStream();
        BufferedImage image=ImageIO.read(file);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg",byteArrayOutputStream );
        byte[] size =ByteBuffer.allocate(10).putInt(byteArrayOutputStream.size()).array();

        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Sending image......");
        System.out.println("Flushed "+System.currentTimeMillis());

        Thread.sleep(120000);
        System.out.println("Closing "+System.currentTimeMillis());
        socket.close();
      }

      if(demande.contains(".mp3")) {
        System.out.println("patientez du son ");
        ObjectOutputStream objectoutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectoutputStream.writeObject(demande);
        
        FileInputStream inputStream = new FileInputStream(fichierMp3);
        byte[] mybytearray = inputStream.readAllBytes();

        
        while (true) {
          System.out.println("Connected");
          System.out.println(socket.getInetAddress());
          out.writeUTF(fichierMp3.getName().toLowerCase());
          out.write(mybytearray);
        }
      }

      if(demande.contains(".mp4")) {
        System.out.println(" patientez du video !");
        ObjectOutputStream objectoutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectoutputStream.writeObject(demande);
        try {
          System.out.println("Connected");
          dataInputStream = new DataInputStream(socket.getInputStream());
          dataOutputStream = new DataOutputStream(socket.getOutputStream());

          int bytes = 0;
          FileInputStream fileInputStream = new FileInputStream(fichier);

          dataOutputStream.writeLong(fichier.length());

          byte[] buffer = new byte[4 * 1024];
          while ((bytes = fileInputStream.read(buffer)) != -1) {
          
          dataOutputStream.write(buffer, 0, bytes);
          dataOutputStream.flush();
          }
          fileInputStream.close();
          dataInputStream.close();
          dataOutputStream.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }    
}