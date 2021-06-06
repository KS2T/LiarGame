import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.ServerSocket;
import java.net.Socket;

class ServerUi extends JFrame implements ActionListener,Runnable {
    Object o;
    String ip, port, id;
    JFrame frame;
    LoginUi ui;
    ServerSocket ss;
    LiarServer ls;
    Socket s;
    /////////////////////////////////////////////서버 ui멤버
    JTextArea ta = new JTextArea();
    JTextField topicTf, selectTf, chatTf;
    JScrollPane sp;
    JPanel tfP, endP, chatP, btnP, p1_3, p1_4, p2, p2_1, p2_2, p2_3, p2_4, taP;
    Container cp;
    JButton startBtn, banBtn, endBtn, clearBtn;
    JComboBox idBox;
    String idArray[] = {"ㅁ", "ㅠ"};
    String msg;
    Thread gmThread = new Thread(this);

    ServerUi(LiarServer ls) {
        this.ls = ls;
        init();
        setUi();
    }

    @Override
    public void run() {
         if (Thread.currentThread().equals(gmThread)){
            new GameManager(ls);
        }
    }
    void init() {
        cp = getContentPane();
        cp.setLayout(new BorderLayout());                                                           //cp

        endP = new JPanel(new BorderLayout());
        endBtn = new JButton("종료");
        endBtn.addActionListener(this);
        endP.add(endBtn, BorderLayout.WEST);
        cp.add(endP, BorderLayout.NORTH);                                                                //endP

        taP = new JPanel(new BorderLayout());
        taP.add(ta, BorderLayout.CENTER);
        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ta.setLineWrap(true);
        taP.add(sp);
        ta.setEditable(false);
        cp.add(taP, BorderLayout.CENTER);                                                            //taP

        chatP = new JPanel(new BorderLayout());
        btnP = new JPanel(new GridLayout(1, 4));
        startBtn = new JButton("게임 시작!");
        banBtn = new JButton("강퇴");
        idBox = new JComboBox(idArray);
        clearBtn = new JButton("채팅 지우기");
        btnP.add(startBtn);
        btnP.add(banBtn);
        btnP.add(idBox);
        btnP.add(clearBtn);
        chatTf = new JTextField("sp");

        chatP.add(chatTf, BorderLayout.CENTER);
        chatP.add(btnP, BorderLayout.SOUTH);
        cp.add(chatP, BorderLayout.SOUTH);                                                            //chatP

        setUi();
        act();
    }

    void setUi() {
        setVisible(true);
        setTitle(ip + ", " + port + "에서 채팅중..");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /*public void sendMessage() {
        try {
            String text = chatTf.getText();
            ta.append(text + "\n");


            //프로그램 종료
            System.exit(0);
        else{
                //입력된 메세지가 "/exit"가 아닐 경우( 전송할 메세지인 경우)
                //클라이언트에게 메세지 전송
                dos.writeUTF(text);

                //초기화 및 커서요청
                chatTf.setText("");
                chatTf.requestFocus();
            }
        } catch (IOException e) {
        }
    }*/
    void act() {
        endBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        startBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(endBtn)) {
            dispose();
            ui.reopen();
        }
        if (e.getSource().equals(clearBtn)) {
            ta.setText(null);
        }
        if (e.getSource().equals(startBtn)) {
            System.out.println("스타트 클릭");
            gmThread.start();
        }
    }
}