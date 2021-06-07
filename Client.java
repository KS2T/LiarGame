
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.lang.*;


class Client implements Runnable, ActionListener {
    String name = "";
    String type;
    String topic[];
    Boolean playButton;
    JFrame frame;
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
    int nop;
    ArrayList<String> topicNames = new ArrayList<String>();
    String lsnMsg, spkMsg;
    ClientUi cui;
    Thread listenTh = new Thread(this);

    Client(ClientUi cui) {
        this.cui = cui;
        this.ui = cui.ui;
        this.ip = cui.ip;
        this.port = Integer.parseInt(cui.port);
        this.id = cui.id;
        try {
            System.out.println(id + ip + port);
            s = new Socket(ip, port);
            is = s.getInputStream();
            os = s.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            System.out.println("����");
            String ent = dis.readUTF();
            act();
            listenTh.start();
            if (ent.equals("false")) {
                System.out.println("false");
                JOptionPane.showMessageDialog(null, "�ش� ������ �ο��� ���� á���ϴ�", "�ο� �ʰ�", 0);
                cui.ui.reopen();
                cui.ui.clientBTn.doClick();
            } else {
                System.out.println("�� ����");
                nop = dis.read();
                dos.writeUTF(id);
                dos.flush();
            }
            new GameManager(this);
        } catch (IOException ie) {
            System.out.println("Client ie: " + ie);
            JOptionPane.showMessageDialog(null, "������ �Ǵ� ��Ʈ�� �ùٸ��� �ʽ��ϴ�.", "���� ����", 0);
            cui.ui.reopen();
            cui.ui.clientBTn.doClick();
        }
    }

    String listen() {
        lsnMsg = "";
        try {
            lsnMsg = dis.readUTF();
            System.out.println(lsnMsg);
            return protocol();
        } catch (IOException ie) {
            return "exit";
        }

    }

    String protocol() throws IOException {
        if (lsnMsg.startsWith(id + ">>") & !lsnMsg.startsWith(id + " ")) {
            lsnMsg = lsnMsg.replaceFirst(id, "�� ");
            return lsnMsg;
        } else if (lsnMsg.startsWith("liar:")) {
            if (lsnMsg.substring(5).equals(id)) {
                cui.topicTf.setText("����� ���̾��Դϴ�");
            } else {
                cui.topicTf.setFont(new Font("���� ���", Font.BOLD, 12));
            }
            return null;
        } else if (lsnMsg.startsWith("topic:")) {
            if (cui.topicTf.getText().equals("����� ���̾��Դϴ�")) {
                return null;
            }
            cui.topicTf.setText(lsnMsg.substring(6));
            return null;
        } else if (lsnMsg.startsWith(id + "���� ����")) {
            s.close();
            JOptionPane.showMessageDialog(null, "�����ڿ� ���� ������ϼ̽��ϴ�.", "����", 0);
            cui.dispose();
            ui.reopen();
            return "exit";
        } else if (lsnMsg.startsWith("ä�ö�")) {
            cui.chatTf.setEnabled(false);
            return null;
        } else if (lsnMsg.startsWith("ä�þ��")) {
            if (lsnMsg.substring(4).equals(id)) {
                String msg = JOptionPane.showInputDialog("10�ʾȿ� �Ѹ���� ������ �������ּ���.");
                speak(msg);
            }
            return null;
        } else {
            System.out.println(lsnMsg);
            return lsnMsg;
        }
    }

    void act() {
        Action enter = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spkMsg = cui.chatTf.getText();
                spkMsg = spkMsg.trim();
                speak(spkMsg);
                cui.chatTf.setText(null);
            }
        };
        cui.chatTf.addActionListener(enter);
        cui.endBtn.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(cui.endBtn)) {
            try {
                s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            cui.dispose();
            ui.reopen();
        }
    }


    void speak(String str) {
        try {
            dos.writeUTF(id + ">> " + str);
            dos.flush();
        } catch (IOException ie) {
            System.out.println("speak() ie: " + ie);
        }
    }

    @Override
    public void run() {
        if (Thread.currentThread().equals(listenTh)) {
            while (true) {
                String msg = null;
                msg = listen();
                if (msg != null) {
                    if (msg.equals("exit")) {
                        break;
                    } else {
                        cui.ta.append(msg + "\n");
                    }
                }
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
            System.out.println("������ ã�� �� �����ϴ�..");
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
