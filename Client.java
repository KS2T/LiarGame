
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    String id, ip;
    int port = 0;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    Socket s;
    LoginUi ui;
    ArrayList<String> topicNames = new ArrayList<String>();
    Thread listenTh = new Thread(this);


    Client(LoginUi ui) {
        this.ui = ui;
        this.ip = ui.ip;
        this.port = Integer.parseInt(ui.port);
        this.id = ui.id;
        try {
            s = new Socket(ip, port);
            is = s.getInputStream();
            os = s.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            dos.writeUTF(id);
            dos.flush();
            listenTh.start();
            new ClientUi(this);
        } catch (IOException ie) {
            System.out.println("Client ie: " + ie);
            JOptionPane.showMessageDialog(null, "아이피 또는 포트가 올바르지 않습니다.", "연결 오류", 0);
            ui.reopen();
            ui.clientBTn.doClick();
        }
    }

    void listen() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(is);
            String msg = "";
            while (true) {
                msg = dis.readUTF();
                if (msg.startsWith(id + ">>") & !msg.startsWith(id + " ")) {
                    msg = msg.replaceFirst(id, "나 ");

                }
                System.out.println(msg);
            }
        } catch (IOException ie) {
            System.out.println("listen ie: " + ie);
        } finally {
            try {
                if (dis != null) dis.close();
                if (is != null) is.close();
            } catch (IOException ie) {
            }
        }
    }

    public void run() {
        if (currentThread().equals(listenTh)) {
            listen();
        }

    }

    void speak(String str) {                                                //todo Client Ui에서 chatTf리스너로 작동 구현할것
        try {
            dos.writeUTF(id + ">> " + str);
            dos.flush();
        } catch (IOException ie) {
            System.out.println("speak() ie: " + ie);
        }
    }

    void readerFileName() {
        try {
            line = brkey.readLine();
            if (line != null) {
                line = line.trim();
            }
            if (line.length() == 0) {
                line = filename;
            }
            fr = new FileReader(line);
        } catch (FileNotFoundException fe) {
            System.out.println("파일을 찾을 수 없습니다..");
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
                  System.out.println("");
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
                System.out.println("...");
                playerNumSelect();
            } else if (playNum > 1) {
                try {
                    Thread.sleep(1000);
                    System.out.println("...");
                } catch (InterruptedException ie) {
                }
            }
        }
    }

}
