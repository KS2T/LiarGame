
import javafx.scene.control.TextInputDialog;
import jdk.nashorn.internal.scripts.JD;

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
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


class Client implements Runnable, ActionListener {
    String id, ip;
    int port = 0;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    Socket s;
    LoginUi ui;
    int nop;
    String lsnMsg, spkMsg;
    ClientUi cui;
    JFrame frame;
    JOptionPane jop = new JOptionPane();
    Thread listenTh ;
    Thread chatTimeTh;
    Action enter = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            spkMsg = cui.chatTf.getText();
            spkMsg = spkMsg.trim();
            speak(spkMsg);
            cui.chatTf.setText(null);
        }
    };
    Action enter2 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            spkMsg = cui.chatTf.getText();
            spkMsg = spkMsg.trim();
            speak(spkMsg);
            cui.chatTf.setText(null);
            cui.chatTf.setEnabled(false);
            cui.chatTf.addActionListener(enter);
            cui.chatTf.removeActionListener(enter2);
        }
    };
    Action enter3 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            spkMsg = cui.chatTf.getText();
            spkMsg = spkMsg.trim();
            speak("liarTopic" + spkMsg);
            cui.chatTf.setText(null);
            cui.chatTf.setEnabled(false);
            cui.chatTf.addActionListener(enter);
            cui.chatTf.removeActionListener(enter3);
        }
    };

    Client(ClientUi cui) {
        this.cui = cui;
        this.ui = cui.ui;
        this.ip = cui.ip;
        this.port = Integer.parseInt(cui.port);
        this.id = cui.id;
        this.frame=cui;
        try {
            System.out.println(id + ip + port);
            s = new Socket(ip, port);
            is = s.getInputStream();
            os = s.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            System.out.println("연결");
            String ent = dis.readUTF();
            act();
            listenTh= new Thread(this);
            listenTh.start();
            if (ent.equals("false")) {
                System.out.println("false");
                JOptionPane.showMessageDialog(null, "해당 서버의 인원이 가득 찼습니다", "인원 초과", 0);
                cui.ui.reopen();
                cui.ui.clientBTn.doClick();
            } else {
                System.out.println("낫 폴스");
                nop = dis.read();
                dos.writeUTF(id);
                dos.flush();
            }
        } catch (IOException ie) {
            System.out.println("Client ie: " + ie);
            JOptionPane.showMessageDialog(null, "아이피 또는 포트가 올바르지 않습니다.", "연결 오류", 0);
            cui.dispose();
            cui.ui.reopen();
            cui.ui.clientBTn.doClick();
        }
    }


    String protocol() throws IOException {
        if (lsnMsg.startsWith(id + ">>") & !lsnMsg.startsWith(id + " ")) {
            lsnMsg = lsnMsg.replaceFirst(id, "나 ");
            return lsnMsg;
        } else if (lsnMsg.startsWith(id + "님이 강퇴")) {
            s.close();
            JOptionPane.showMessageDialog(null, "관리자에 의해 강퇴당하셨습니다.", "강퇴", 0);
            cui.dispose();
            ui.reopen();
            return "exit";
        } else if (lsnMsg.startsWith("gm")) {
            System.out.println(lsnMsg + "gm메세지");
            lsnMsg = lsnMsg.substring(2);
            System.out.println(lsnMsg);
            fromGm(lsnMsg);
            return null;
        } else {
            System.out.println(lsnMsg);
            return lsnMsg;
        }
    }

    void act() {

        cui.chatTf.addActionListener(enter);
        cui.endBtn.addActionListener(this);

    }

    void fromGm(String lsnMsg) {

        if (lsnMsg.startsWith("liar:")) {
            if (lsnMsg.substring(5).equals(id)) {
                cui.topicTf.setFont(new Font("맑은 고딕", Font.BOLD, 16));
                cui.topicTf.setText("당신은 라이어입니다");
            } else {
            }
        } else if (lsnMsg.startsWith("topic:")) {
            if (cui.topicTf.getText().equals("당신은 라이어입니다")) {
            } else {
                cui.topicTf.setText(lsnMsg.substring(6));
            }
        } else if (lsnMsg.startsWith("채팅락")) {
            cui.chatTf.setEnabled(false);
        } else if (lsnMsg.startsWith("채팅언락")) {
            if (lsnMsg.substring(4).equals(id)) {
                printTimer(cui.timeTf, 10);
                cui.ta.append("상단 제한시간안에 제시어에 대해 설명해주세요.\n");
                cui.chatTf.setEnabled(true);
                chatTimeTh = new Thread(this);
                chatTimeTh.start();
                cui.chatTf.removeActionListener(enter);
                cui.chatTf.addActionListener(enter2);
            }
        } else if (lsnMsg.startsWith("vote")) {
            if (lsnMsg.substring(4).equals(id)) {
                printTimer(cui.timeTf, 10);
                cui.ta.append("10초안에 제시어를 추리하여 입력해주세요.\n");
                cui.chatTf.setEnabled(true);
                chatTimeTh = new Thread(this);
                chatTimeTh.start();
                cui.chatTf.removeActionListener(enter);
                cui.chatTf.addActionListener(enter3);
            }
        } else if (lsnMsg.startsWith("올언락")) {
            cui.chatTf.setEnabled(true);
            cui.topicTf.setText("");
            cui.timeTf.setText("");
        }

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
            if (str.startsWith("liar")) {
                dos.writeUTF(str);
                dos.flush();
            } else {
                dos.writeUTF(id + ">> " + str);
                dos.flush();
            }
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
                        System.out.println("exit");
                        closeAll();
                        break;
                    } else {
                        cui.ta.append(msg + "\n");
                    }
                }
            }
        }
        if (Thread.currentThread().equals(chatTimeTh)) {
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cui.chatTf.setEnabled(false);
        }
    }

    String listen() {
        lsnMsg = "";
        try {
            lsnMsg = dis.readUTF();
            System.out.println(lsnMsg);
            return protocol();
        } catch (IOException ie) {
            System.out.println(ie);
            return "exit";
        }

    }
    void printTimer(JTextField tf, int i) {

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (i * 1000);
        final String[] time = new String[1];
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                long currentTime = System.currentTimeMillis();
                long leftTime = endTime - currentTime;
                long leftSeconds = (leftTime / 1000) % 60;
                if (leftSeconds == 0) {
                    tf.setText("");
                    timer.cancel();
                }
                tf.setText(leftSeconds + "초");

            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    void closeAll() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (is != null) is.close();
            if (os != null) os.close();
            if (s != null) s.close();
            cui.dispose();
            cui.ui.reopen();
        } catch (IOException ie) {
        }
    }
}