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
    Thread thread = new Thread(new Runnable() {
        @Override

        public void run() {

            String msg = "";
            try {
                while (true) {
                    msg = br.readLine();
                    msg = msg.trim();
                    if (v.size() != 0) {
                        OneClientModul ocm = v.get(0);
                        ocm.broadcast(" 관리자>> " + msg);
                    } else {
                        pln("클라이언트가 아무도 없음");
                    }
                }
            } catch (IOException ie) {
            }
        }
    });

    LiarServer(LoginUi ui) {
        try {
            this.ui = ui;
            this.portN = ui.port;
            port = Integer.parseInt(portN);
            ss = new ServerSocket(port);
            thread.start();
            start();
            new ServerUi(this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                s = ss.accept();
                ocm = new OneClientModul(this);
                v.add(ocm);
                ocm.start();
            }
        } catch (IOException ie) {
            pln(port + "사용중");
        } finally {
            try {
                if (ss != null) ss.close();
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
}

class OneClientModul extends Thread {
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
            broadcast(chatId + " 님이 참가하였습니다. (현재 참가자: " + ls.v.size() + "명)");
            ls.pln(chatId + " 님이 참가하였습니다. (현재 참가자: " + ls.v.size() + "명)");
            while (true) {
                msg = dis.readUTF();
                broadcast(msg);
                ls.pln(msg);
            }
        } catch (IOException ie) {
            ls.v.remove(this);
            broadcast(chatId + " 님이 퇴장하였습니다. (현재인원: " + ls.v.size() + "명)");
            ls.pln(chatId + " 님이 퇴장하였습니다. (현재인원: " + ls.v.size() + "명)");
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

    void cut() {
        String banId = "밴";
        for (OneClientModul ocm : ls.v) {
            if (ocm.chatId.equals(banId)) {
                ls.v.remove(ocm);
                broadcast(ocm.chatId + "님이 강퇴당했습니다.");
                break;
            }
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
