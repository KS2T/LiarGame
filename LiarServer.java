import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

class LiarServer extends Thread {
    ServerSocket ss;
    Socket s;
    int port = 3000;
    String portN;
    Vector<OneClientModul> v = new Vector<OneClientModul>();
    OneClientModul ocm;
    LoginUi ui;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Thread spkThread = new Thread(this);
    Thread serverThread = new Thread(this);

    LiarServer(LoginUi ui) {
        try {
            this.ui = ui;
            this.portN = ui.port;
            port = Integer.parseInt(portN);
            ss = new ServerSocket(port);
            spkThread.start();
            serverThread.start();
            new ServerUi(this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    void cut() {
        String banId = "��";
        for (OneClientModul ocm : v) {
            if (ocm.chatId.equals(banId)) {
                v.remove(ocm);
                ocm.closeAll();
                ocm.broadcast(ocm.chatId + "���� ������߽��ϴ�..");
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
                    ocm = new OneClientModul(this);
                    v.add(ocm);
                    ocm.start();
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

        else if (currentThread().equals(spkThread)) {
            String msg;
            try {
                while (true) {
                    msg = br.readLine();
                    msg = msg.trim();
                    msg = "������ >> " + msg;
                    if (v.size() != 0) {
                        OneClientModul ocm = v.get(0);
                        ocm.broadcast(msg);
                        System.out.println(msg);
                    } else {
                        pln("������ �ο��� �����ϴ�.");
                    }
                }
            } catch (IOException ie) {
            }
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
        } catch (IOException ie) {
        }
    }

    public void run() {
        listen();
    }

    void listen() {
        String msg = "";
        try {
            chatId = dis.readUTF();
            broadcast(chatId + " ���� �����ϼ̽��ϴ�. (���� �ο�: " + ls.v.size() + "��)");
            ls.pln(chatId + " ���� �����ϼ̽��ϴ�. (���� �ο�: " + ls.v.size() + "��)");
            while (true) {
                msg = dis.readUTF();
                broadcast(msg);
                ls.pln(msg);
            }
        } catch (IOException ie) {
            ls.v.remove(this);
            broadcast(chatId + " ���� �����ϼ̽��ϴ�. (���� �ο�: " + ls.v.size() + "��)");
            ls.pln(chatId + " ���� �����ϼ̽��ϴ�. (���� �ο�: " + ls.v.size() + "��)");
        } finally {
            closeAll();
        }
    }

    void broadcast(String msg) {
        try {
            for (OneClientModul ocm : ls.v) {
                ocm.dos.writeUTF(msg);
                ocm.dos.flush();
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
