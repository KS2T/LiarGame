
import com.sun.xml.internal.org.jvnet.mimepull.CleanUpExecutorFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

class ClientUi extends JFrame implements ActionListener, Runnable {
    Client c;
    String id, ip, port;
    ////////////////////////////////////// Ŭ�������� �����
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
    RoundedButton endBtn = new RoundedButton("���� ������");
    Container cp;
    Font f = new Font("���� ���", Font.BOLD, 20);
    Font f2 = new Font("���� ���", Font.PLAIN, 20);
    int nop;
    Vector<JPanel> pv = new Vector<>();
    Thread listenTh = new Thread(this);
    Thread spkTh = new Thread(this);

    ClientUi(Client c) {
        try {
            this.c = c;
            this.id = c.id;
            this.ip = c.ip;
            this.port = String.valueOf(c.port);
            this.nop = c.nop;
            System.out.println(nop);
            System.out.println(ip + port + id);
            init();
            setUi();
            listenTh.start();
            addAction();                                            //�׼Ǹ����� ����
        } catch (Exception e) {

        }
    }


    void setUi() {
        setVisible(true);
        setTitle(id + "(��)�� ä����..(ip: " + ip + ", port: " + port + ")");
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
            // c.speak();                                           todo c.speak(); �̿��ؼ� ���ϱ� ����
        }
    }

    void human() {                                                                          //todo �޸��ϰ��
        setTitle(id + "(��)�� ������..(ip: " + ip + ", port: " + port + ")");
        String topic = null;                                                            //
        topicTf.setText("����: " + topic);

    }

    void liar() {                                                                           //todo ���̾��ϰ��
        setTitle(id + "(��)�� ������..(ip: " + ip + ", port: " + port + ")");
        topicTf.setText("����� ���̾��Դϴ�.");

    }

    void panelUi() {
        for (int i = 0; i < 8; i++) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel imgLb = new JLabel(new ImageIcon("buddy.jpg"));
            JLabel idLb = new JLabel("���̵�");
            idLb.setFont(f);
            idLb.setHorizontalAlignment(0);
            idLb.setForeground(Color.black);
            idLb.setBackground(Color.gray);
            idLb.setOpaque(true);
            panel.add(imgLb);
            panel.add(idLb, BorderLayout.SOUTH);
            pv.add(panel);

            if (pv.size() < 4) {
                p1.add(panel);
            } else if (pv.size() >= 4) {
                p2.add(panel);
            }
        }
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
        tfP.add(idCb);                                      //�޺��ڽ� �߰�
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

        p1 = new JPanel();                                                                   //���̵��г�
        p1.setPreferredSize(new Dimension(150, 600));
        p1.setLayout(new GridLayout(4, 1));
        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(150, 600));
        p2.setLayout(new GridLayout(4, 1));
        panelUi();
        //���̵��г� ��

        taP = new JPanel(new BorderLayout());
        taP.add(ta, BorderLayout.CENTER);
        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        taP.add(sp);                                                                            //ta�г�


        ta.setLineWrap(true);
        cp.add(taP, BorderLayout.CENTER);
        cp.add(p1, BorderLayout.WEST);
        cp.add(p2, BorderLayout.EAST);

    }

}
