package KS6teamPJ;

import javax.naming.ldap.UnsolicitedNotification;
import javax.print.DocFlavor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.lang.*;


public class Human extends Thread {
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
      ArrayList<String> topicNames = new ArrayList<String>();

      Human(LoginUi ui) {
            this.ip = ui.ip;
            this.port = ui.port;
            this.id = ui.id;
            id="치훈"; // todo
            ui.ip="127.0.0.1"; // todo
            ui.port = "3000"; // todo
            try {
                  System.out.println("서버IP(기본:"+ui.ip+")");
                  System.out.println("설정할 IP");
                  System.out.print("=>");
                  ui.ip = br.readLine();
                  ui.ip.trim();
                  if (ui.ip.length() == 0) ui.ip = "127.0.0.1"; // todo
                  System.out.println("PORT(기본:"+ui.port+")");
                  System.out.println("설정할 PORT");
                  System.out.print("=>");
                  ui.port = br.readLine();
                  ui.port = ui.port.trim();
                  if (ui.port.length() == 0) ui.port = "3000"; // todo
                  System.out.println("PORT(기본:"+ui.id+")");
                  System.out.println("설정할 ID");
                  System.out.print("=>");
                  ui.id=br.readLine();
                  ui.id.trim();
                  if(ui.id.length()==0) ui.id="치훈";
                  System.out.println("받은 IP: " + ui.ip + "\n받은 port: " + ui.port + "\n받은 ID: " + ui.id + "(으)로 로그인합니다.");
                  int uiport = Integer.parseInt(ui.port);
                  if (uiport < 0 || uiport > 65535) {
                        System.out.println("범위가 유효하지않습니다.\n다시 입력하여주세요");
                        return;
                  }

                  s = new Socket(ui.ip, Integer.parseInt(ui.port));
                  is = s.getInputStream();
                  os = s.getOutputStream();
                  dis = new DataInputStream(is);
                  dos = new DataOutputStream(os);
                  System.out.println("서버와 접속 완료...");
                  start();
                  speak();
            } catch (UnknownHostException he) {
                  System.out.println("서버를 찾지못함");
            } catch (IOException ie) {
            } finally {
                  try {
                        if (s != null) s.close();
                  } catch (IOException ie) {
                  }
            }
      }

      /*void listen() {
            DataInputStream dis = null;
            try {
                  dis = new DataInputStream(is);
                  String msg = "";
                  while (true) {
                        msg = dis.readUTF();
                        System.out.println(id+">> " + msg);
                  }
            } catch (IOException ie) {
                  System.out.println("Server 퇴장(다운됨!).. 2초 후에 종료하겠습니다!!");
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
*/
      public void run() {
            speak();
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
                  line = brkey.readLine();//불러온 파일을 라인에 한줄씩 입력
                  if (line != null) {
                        line = line.trim();
                  }
                  if (line.length() == 0) {
                        line = filename;
                  }
                  fr = new FileReader(line);
            } catch (FileNotFoundException fe) {
                  System.out.println("파일을 찾지못했습니다.");
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
                  System.out.println("라이어 세계에 오신 것을 환영합니다...\n게임을 시작하시려면 플레이버튼을 눌려주세요.");
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
                        System.out.println("게임을 진행할 인원수가 작습니다.\n 다시 선택하세요...");
                        playerNumSelect();
                  } else if (playNum > 1) {
                        try {
                              Thread.sleep(1000);
                              System.out.println("게임을 진행합니다...");
                        } catch (InterruptedException ie) {
                        }
                  }
            }
      }


      public static void main(String args[]) {
            LoginUi ui = new LoginUi();
            new Human(ui);
      }
}

class LoginUi {
      String ip, port, id;
}
