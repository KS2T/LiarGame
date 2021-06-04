
import javax.print.DocFlavor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.lang.*;


class Client extends Thread {
  String name = "";
  String type;
  String topic[];
  Boolean playButton;
  int playNum;
  String filename = "";
  FileReader fr = null;
  BufferedReader brkey = new BufferedReader(new InputStreamReader(System.in));
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  String line = "";
  String id, ip, port;
  InputStream is;
  OutputStream os;
  DataInputStream dis;
  DataOutputStream dos;
  Socket s;
  ClientUi cui;
  ArrayList<String> topicNames = new ArrayList<String>();
  Thread listenTh = new Thread(this);
  Thread speakTh = new Thread(this);


  Client(ClientUi cui) {
    this.cui = cui;
    this.ip = cui.ip;
    this.port = cui.port;
    this.id = cui.id;
    System.out.println("ip: "+ip+"port: "+port+"id: "+id);
   /* id="ġ��"; // todo
    ui.ip="127.0.0.1"; // todo
    ui.port = "3000"; // todo

      System.out.println("����IP(�⺻:"+ui.ip+")");
      System.out.println("������ IP");
      System.out.print("=>");
      ui.ip = br.readLine();
      ui.ip.trim();
      if (ui.ip.length() == 0) ui.ip = "127.0.0.1"; // todo
      System.out.println("PORT(�⺻:"+ui.port+")");
      System.out.println("������ PORT");
      System.out.print("=>");
      ui.port = br.readLine();
      ui.port = ui.port.trim();
      if (ui.port.length() == 0) ui.port = "3000"; // todo
      System.out.println("PORT(�⺻:"+ui.id+")");
      System.out.println("������ ID");
      System.out.print("=>");
      ui.id=br.readLine();
      ui.id.trim();
      if(ui.id.length()==0) ui.id="ġ��";
      System.out.println("���� IP: " + ui.ip + "\n���� port: " + ui.port + "\n���� ID: " + ui.id + "(��)�� �α����մϴ�.");
      int uiport = Integer.parseInt(ui.port);
      if (uiport < 0 || uiport > 65535) {
        System.out.println("������ ��ȿ�����ʽ��ϴ�.\n�ٽ� �Է��Ͽ��ּ���");
        return;
      }*/
    try {
      s = new Socket(cui.ip, Integer.parseInt(cui.port));
      is = s.getInputStream();
      os = s.getOutputStream();
      dis = new DataInputStream(is);
      dos = new DataOutputStream(os);
      System.out.println("������ ���� �Ϸ�...");
      listenTh.start();
      speakTh.start();
    }  catch (IOException ie) {
    } finally {
      try {
        if (s != null) s.close();
      } catch (IOException ie) {
      }
    }
  }

  void listen() {
        DataInputStream dis = null;
        try {
              dis = new DataInputStream(is);
              String msg = "";
              while (true) {
                    msg = dis.readUTF();
                    System.out.println(id+">> " + msg);
              }
        } catch (IOException ie) {
          ie.printStackTrace();
              System.out.println("Server ����(�ٿ��!).. 2�� �Ŀ� �����ϰڽ��ϴ�!!");
              try {
                    Thread.sleep(2000);
              } catch (InterruptedException ie2) {
              }
              System.exit(0);
        } finally {
              try {
                    if (dis != null) dis.close();
                    if (is != null) is.close();
              } catch (IOException ie) {
              }
        }
  }

  public void run() {
    if(currentThread().equals(speakTh)){speak();}
    if(currentThread().equals(listenTh)){listen();}

  }

  void speak() {
    DataOutputStream dos = new DataOutputStream(os);
    try {
      while (true) {
        String line = br.readLine();
        dos.writeUTF(line);
        dos.flush();
      }
    } catch (IOException ie) {
      System.out.println("speak() ie: " + ie);
    } finally {
      try {
        if (dos != null) dos.close();
        if (os != null) os.close();
      } catch (IOException ie) {
      }
    }
  }

  void readerFileName() {
    try {
      line = brkey.readLine();//�ҷ��� ������ ���ο� ���پ� �Է�
      if (line != null) {
        line = line.trim();
      }
      if (line.length() == 0) {
        line = filename;
      }
      fr = new FileReader(line);
    } catch (FileNotFoundException fe) {
      System.out.println("������ ã�����߽��ϴ�.");
      readerFileName();
    } catch (IOException ie) {
    }
  }

  void inputIp() {
    try {
      System.out.println("IP: " + ip);
      String ip = br.readLine();
      if (ip != null) ip = ip.trim();
      if (ip.length() == 0) ;
    } catch (IOException ie) {
    }
  }
      /*void opening() {
            try {
                  Thread.sleep(3000);
                  System.out.println("���̾� ���迡 ���� ���� ȯ���մϴ�...\n������ �����Ͻ÷��� �÷��̹�ư�� �����ּ���.");
            } catch (InterruptedException ie) {
            }
            try {
                  while (br.readLine() == null){
                  }
            }catch(IOException ie){}
      }*/

  void chat() {

  }


  void liarSelect() {

  }

  void setPlayButton(Boolean playButton) {

  }

  void playerNumSelect() {
    while (true) {
      if (playNum == 1 || playNum < 0) {
        System.out.println("������ ������ �ο����� �۽��ϴ�.\n �ٽ� �����ϼ���...");
        playerNumSelect();
      } else if (playNum > 1) {
        try {
          Thread.sleep(1000);
          System.out.println("������ �����մϴ�...");
        } catch (InterruptedException ie) {
        }
      }
    }
  }

}
