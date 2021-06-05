
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
    String id, ip;
    int port = 0;
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
        try {
            s = new Socket(ip, port);
            is = s.getInputStream();
            os = s.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            listenTh.start();
            speakTh.start();
        } catch (IOException ie) {
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
            ie.printStackTrace();
            System.out.println("Server와 연결이 끊겼습니다.. 2초후 종료합니다.");
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
        if (currentThread().equals(speakTh)) {
            speak();
        }
        if (currentThread().equals(listenTh)) {
            listen();
        }

    }

    void speak() {
        DataOutputStream dos = new DataOutputStream(os);
        try {
            dos.writeUTF(id);
            dos.flush();
            while (true) {
                String line = br.readLine();
                dos.writeUTF(id + ">> " + line);
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
