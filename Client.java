
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


class Client {
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
            System.out.println("연결");
            String ent = dis.readUTF();
            if (ent.equals("false")) {
                System.out.println("false");
                JOptionPane.showMessageDialog(null, "해당 서버의 인원이 가득 찼습니다", "인원 초과", 0);
                ui.reopen();
                ui.clientBTn.doClick();
            } else {
                System.out.println("낫 폴스");
                dos.writeUTF(id);
                dos.flush();
                new ClientUi(this);
            }
        } catch (IOException ie) {
            System.out.println("Client ie: " + ie);
            JOptionPane.showMessageDialog(null, "아이피 또는 포트가 올바르지 않습니다.", "연결 오류", 0);
            ui.reopen();
            ui.clientBTn.doClick();
        }
    }

    String listen() {
        String msg = "";
        try {
            msg = dis.readUTF();
            if (msg.startsWith(id + ">>") & !msg.startsWith(id + " ")) {
                msg = msg.replaceFirst(id, "나 ");
                return msg;
            } else if (msg.startsWith("topic:")) {
                msg = "topic:" + msg.substring(6);
                return msg;
            }else{
            System.out.println(msg);
            return msg;}
        } catch (IOException ie) {
            System.out.println("listen ie: " + ie);
        }

        return null;
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
