import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

class LiarServer extends Thread implements ActionListener {
    ServerSocket ss;
    Socket s;
    int port = 3000;
    String portN;
    Vector<OneClientModul> v = new Vector<OneClientModul>();
    OneClientModul ocm;
    LoginUi ui;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Thread gameThread = new Thread(this);
    Thread serverThread = new Thread(this);
    ServerUi sui;
    String msg;


    LiarServer(ServerUi sui) {
        try {
            this.sui = sui;
            this.portN = sui.ui.port;
            port = Integer.parseInt(portN);
            ss = new ServerSocket(port);
            sui.setTitle("ip: " + InetAddress.getLocalHost().getHostAddress() + ", port: " + port + " ����������");
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverThread.start();
        this.sui = sui;
        this.s = sui.s;
        act();
    }

    void kick() {
        String banId = String.valueOf(sui.idBox.getSelectedItem());
        for (OneClientModul ocm : v) {
            if (ocm.chatId.equals(banId)) {
                ocm.broadcast(ocm.chatId + "���� ������߽��ϴ�..");
                v.remove(ocm);
                ocm.closeAll();
                break;
            }
        }
    }

    @Override
    public void run() {
        if (currentThread().equals(serverThread)) {
            try {
                while (true) {
                    s = ss.accept();
                    System.out.println(s);
                    OutputStream os = s.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    if (v.size() == 8) {
                        System.out.println(os);
                        System.out.println(dos);
                        dos.writeUTF("false");
                        System.out.println("false");
                    } else if (v.size() < 8) {
                        dos.writeUTF("true");
                        dos.write(v.size());
                        ocm = new OneClientModul(this);
                        sui.idBox.addItem(ocm.chatId);
                        v.add(ocm);
                        ocm.start();
                    }
                }
            } catch (IOException ie) {
                pln(port + "�� ��Ʈ �����.");
            } finally {
                try {
                    if (ss != null) ss.close();
                } catch (IOException ie) {
                }
            }
        }
if(currentThread().)
    }

    void act() {
        Action enter = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msg = sui.chatTf.getText();
                msg = msg.trim();
                msg = "������ >> " + msg;
                sui.chatTf.setText(null);
                if (v.size() != 0) {
                    ocm.broadcast(msg);
                } else {
                    sui.ta.append("������ �ο��� �����ϴ�.\n");
                }
            }
        };
        sui.chatTf.addActionListener(enter);
        sui.banBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(sui.banBtn)) {
            kick();
        }
    }

    void pln(String str) {
        System.out.println(str);
    }

    void p(String str) {
        System.out.print(str);
    }
}                                                                                               //���̾��


class OneClientModul extends Thread {                                                           //��Ŭ���
    LiarServer ls;
    Socket s;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    String chatId;

    OneClientModul(LiarServer ls) {
        this.ls = ls;
        this.s = ls.s;
        try {
            is = s.getInputStream();
            os = s.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            chatId = dis.readUTF();
        } catch (IOException ie) {
        }
    }

    public void run() {
        listen();
    }

    void listen() {
        String msg = "";
        try {
            broadcast(chatId + " ���� �����ϼ̽��ϴ�. (���� �ο�: " + ls.v.size() + "��)");
            while (true) {
                msg = dis.readUTF();
                broadcast(msg);
            }
        } catch (IOException ie) {
            ls.v.remove(this);
            broadcast(chatId + " ���� �����ϼ̽��ϴ�. (���� �ο�: " + ls.v.size() + "��)");
        } finally {
            closeAll();
        }
    }

    void broadcast(String msg) {
        try {
            for (OneClientModul ocm : ls.v) {
                ocm.dos.writeUTF(msg);
                ocm.dos.flush();
                ls.sui.ta.append(msg + "\n");
            }
        } catch (IOException ie) {
        }
    }

    void closeAll() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (is != null) is.close();
            if (os != null) os.close();
            if (s != null) s.close();
        } catch (IOException ie) {
        }
    }
}