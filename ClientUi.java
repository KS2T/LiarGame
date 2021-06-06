
import com.sun.xml.internal.org.jvnet.mimepull.CleanUpExecutorFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

class ClientUi extends JFrame implements ActionListener, Runnable {
    Client c;
    String id, ip, port;
    ////////////////////////////////////// 클라유아이 멤버↓
    JTextArea ta = new JTextArea() {
        public void paintComponent(final Graphics g) {
            ImageIcon imageIcon = new ImageIcon("taBack.png");
            Rectangle rect = getVisibleRect();
            g.drawImage(imageIcon.getImage(), rect.x, rect.y, rect.width, rect.height, null);
            setOpaque(false);
            super.paintComponent(g);
        }
    };
    JTextField topicTf, timeTf, chatTf;
    String idList[] = {"asd", "assd", "asdasasdasdasdasdd"};
    JComboBox idCb;
    JScrollPane sp;
    JPanel tfP, p1, p1_1, p1_2, p1_3, p1_4, p2, p2_1, p2_2, p2_3, p2_4, taP, northP, endP, chatP;
    RoundedButton endBtn = new RoundedButton("서버 나가기");
    Container cp;
    Font f = new Font("맑은 고딕", Font.BOLD, 20);
    Font f2 = new Font("맑은 고딕", Font.PLAIN, 20);

    Thread listenTh = new Thread(this);
    Thread spkTh = new Thread(this);

    ClientUi(Client c) {
        try {
            this.c = c;
            this.id = c.id;
            this.ip = c.ip;
            this.port = String.valueOf(c.port);
            System.out.println(ip + port + id);
            init();
            setUi();
            listenTh.start();
            addAction();                                            //액션리스너 삽입
        } catch (Exception e) {

        }
    }


    void setUi() {
        setVisible(true);
        setTitle(id + "(으)로 채팅중..(ip: " + ip + ", port: " + port + ")");
        setSize(900, 750);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void addAction() {
        endBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(endBtn)) {
            try {
                c.s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            dispose();
            c.ui.reopen();
        }

    }

    @Override
    public void run() {
        if (Thread.currentThread().equals(listenTh)) {
            while (true) {
                String msg = null;
                msg = c.listen();
                if (msg != null) {
                    if (msg.startsWith("topic:")) {
                        System.out.println(msg);
                        topicTf.setText(msg.substring(6));
                    } else {
                        System.out.println(msg);
                        ta.append(msg + "\n");
                    }
                }
            }
        } else if (Thread.currentThread().equals(spkTh)) {
            // c.speak();                                           todo c.speak(); 이용해서 말하기 구현
        }
    }

    void human() {                                                                          //todo 휴먼일경우
        setTitle(id + "(으)로 게임중..(ip: " + ip + ", port: " + port + ")");
        String topic = null;                                                            //
        topicTf.setText("주제: " + topic);

    }

    void liar() {                                                                           //todo 라이어일경우
        setTitle(id + "(으)로 게임중..(ip: " + ip + ", port: " + port + ")");
        topicTf.setText("당신은 라이어입니다.");

    }

    void init() {

        cp = getContentPane();
        cp.setLayout(new BorderLayout());                                             //cp

        northP = new JPanel(new BorderLayout());
        tfP = new ImagePanel("pBack.png");
        topicTf = new JTextField(10);
        topicTf.setEnabled(false);
        topicTf.setFont(f);
        JPanel topicP = new JPanel(new BorderLayout());
        topicP.add(topicTf, BorderLayout.EAST);
        timeTf = new JTextField(10);
        timeTf.setEnabled(false);
        timeTf.setFont(f);
        idCb = new JComboBox(idList);
        JPanel selectP = new JPanel(new BorderLayout());
        selectP.add(timeTf, BorderLayout.WEST);
        tfP.add(topicP);
        tfP.add(selectP);
        tfP.add(idCb);                                      //콤보박스 추가
        northP.add(tfP, BorderLayout.SOUTH);
        cp.add(northP, BorderLayout.NORTH);                                      //northP

        chatP = new ImagePanel("pBack.png");
        chatTf = new JTextField(30);
        chatTf.setFont(f2);
        chatTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        chatP.add(chatTf);
        endP = new JPanel();
        chatP.add(endBtn);
        cp.add(chatP, BorderLayout.SOUTH);                                               //chatTf

        p1 = new JPanel();                                                                   //사이드패널
        p1.setPreferredSize(new Dimension(150, 600));
        p1.setLayout(new GridLayout(4, 1));

        p1_1 = new JPanel(new BorderLayout());
        JLabel lb1_1 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb1_1 = new JLabel("아이디");
        idlb1_1.setFont(f);
        idlb1_1.setHorizontalAlignment(0);
        idlb1_1.setForeground(Color.black);
        idlb1_1.setBackground(Color.gray);
        idlb1_1.setOpaque(true);
        p1_1.add(lb1_1);
        p1_1.add(idlb1_1, BorderLayout.SOUTH);
        p1.add(p1_1);

        p1_2 = new JPanel(new BorderLayout());
        JLabel lb1_2 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb1_2 = new JLabel("아이디");
        idlb1_2.setFont(f);
        idlb1_2.setHorizontalAlignment(0);
        idlb1_2.setForeground(Color.black);
        idlb1_2.setBackground(Color.gray);
        idlb1_2.setOpaque(true);
        p1_2.add(lb1_2);
        p1_2.add(idlb1_2, BorderLayout.SOUTH);
        p1.add(p1_2);

        p1_3 = new JPanel(new BorderLayout());
        JLabel lb1_3 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb1_3 = new JLabel("아이디");
        idlb1_3.setFont(f);
        idlb1_3.setHorizontalAlignment(0);
        idlb1_3.setForeground(Color.black);
        idlb1_3.setBackground(Color.gray);
        idlb1_3.setOpaque(true);
        p1_3.add(lb1_3);
        p1_3.add(idlb1_3, BorderLayout.SOUTH);
        p1.add(p1_3);


        p1_4 = new JPanel(new BorderLayout());
        JLabel lb1_4 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb1_4 = new JLabel("아이디");
        idlb1_4.setFont(f);
        idlb1_4.setHorizontalAlignment(0);
        idlb1_4.setForeground(Color.black);
        idlb1_4.setBackground(Color.gray);
        idlb1_4.setOpaque(true);
        p1_4.add(lb1_4);
        p1_4.add(idlb1_4, BorderLayout.SOUTH);
        p1.add(p1_4);

        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(150, 600));
        p2.setLayout(new GridLayout(4, 1));


        p2_1 = new JPanel(new BorderLayout());
        JLabel lb2_1 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb2_1 = new JLabel("아이디");
        idlb2_1.setFont(f);
        idlb2_1.setHorizontalAlignment(0);
        idlb2_1.setForeground(Color.black);
        idlb2_1.setBackground(Color.gray);
        idlb2_1.setOpaque(true);
        p2_1.add(lb2_1);
        p2_1.add(idlb2_1, BorderLayout.SOUTH);
        p2.add(p2_1);

        p2_2 = new JPanel(new BorderLayout());
        JLabel lb2_2 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb2_2 = new JLabel("아이디");
        idlb2_2.setFont(f);
        idlb2_2.setHorizontalAlignment(0);
        idlb2_2.setForeground(Color.black);
        idlb2_2.setBackground(Color.gray);
        idlb2_2.setOpaque(true);
        p2_2.add(lb2_2);
        p2_2.add(idlb2_2, BorderLayout.SOUTH);
        p2.add(p2_2);

        p2_3 = new JPanel(new BorderLayout());
        JLabel lb2_3 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb2_3 = new JLabel("아이디");
        idlb2_3.setFont(f);
        idlb2_3.setHorizontalAlignment(0);
        idlb2_3.setForeground(Color.black);
        idlb2_3.setBackground(Color.gray);
        idlb2_3.setOpaque(true);
        p2_3.add(lb2_3);
        p2_3.add(idlb2_3, BorderLayout.SOUTH);
        p2.add(p2_3);

        p2_4 = new JPanel(new BorderLayout());
        JLabel lb2_4 = new JLabel(new ImageIcon("buddy.jpg"));
        JLabel idlb2_4 = new JLabel("아이디");
        idlb2_4.setFont(f);
        idlb2_4.setHorizontalAlignment(0);
        idlb2_4.setForeground(Color.black);
        idlb2_4.setBackground(Color.gray);
        idlb2_4.setOpaque(true);
        p2_4.add(lb2_4);
        p2_4.add(idlb2_4, BorderLayout.SOUTH);
        p2.add(p2_4);                                                                     //사이드패널 끝

        taP = new JPanel(new BorderLayout());
        taP.add(ta, BorderLayout.CENTER);
        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        taP.add(sp);                                                                            //ta패널


        ta.setLineWrap(true);
        cp.add(taP, BorderLayout.CENTER);
        cp.add(p1, BorderLayout.WEST);
        cp.add(p2, BorderLayout.EAST);

    }

}
