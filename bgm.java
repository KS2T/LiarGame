import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
public class bgm extends JFrame implements Runnable {
  public bgm() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Test Sound Clip");
      this.setSize(300, 200);
      this.setVisible(true);

  }
   public void run(){
   
      try {
         // Open an audio input stream.

         File file = new File("C:/Users/wnsgu/Downloads/abc.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
		 //clip.loop(Clip.LOOP_CONTINUOUSLY);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   
   }

  
 public static void main(String[] args) {
      bgm m = new bgm();
	  m.run();
   }
}