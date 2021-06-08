
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.Socket;
import java.text.AttributedCharacterIterator;
import java.util.Vector;

class ClientUi extends JFrame {
    LoginUi ui;
    String id, ip, port;
    ////////////////////////////////////// Ŭ�������� �����
    JTextArea ta = new JTextArea() {
        public void paintComponent(final Graphics g) {
            ImageIcon imageIcon = new ImageIcon("���̾� ���.png");
            Rectangle rect = getVisibleRect();
            g.drawImage(imageIcon.getImage(), rect.x, rect.y, rect.width, rect.height, null);
            setOpaque(false);
            super.paintComponent(g);
        }
    };

    JTextField topicTf, timeTf, chatTf;
    //String idList[] = {"asd", "assd", "asdasasdasdasdasdd"};
    //JComboBox idCb;
    JScrollPane sp;
    JPanel tfP, p1, p2, p3, p4, taP, northP, endP, chatP, sideP;
    RoundedButton endBtn = new RoundedButton("���� ������");
    Container cp;
    Font f = new Font("���� ���", Font.BOLD, 20);
    Font f2 = new Font("���� ���", Font.PLAIN, 20);
    GridBagLayout g = new GridBagLayout();
    GridBagConstraints gc, gc2;
    Color c1, c2;
    Dimension d1;

    //Vector<PanelUi> pv = new Vector<>();

    ClientUi (){
        init();
        setUi();
    }

    //ClientUi(){}

    ClientUi(LoginUi ui) {
        try {
            this.ui = ui;
            this.id = ui.id;
            this.ip = ui.ip;
            this.port = String.valueOf(ui.port);
            System.out.println("Cui��: " + ip + port + id);
            init();
            setUi();
            new Client(this);                               //�׼Ǹ����� ����
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


    void human() {                                                                          //todo �޸��ϰ��
        setTitle(id + "(��)�� ������..(ip: " + ip + ", port: " + port + ")");
        String topic = null;                                                            //
        topicTf.setText("����: " + topic);

    }

    void liar() {                                                                           //todo ���̾��ϰ��
        setTitle(id + "(��)�� ������..(ip: " + ip + ", port: " + port + ")");
        topicTf.setText("����� ���̾��Դϴ�.");

    }

    void init() {

        c1 = new Color(134, 131, 132, 176);
        c2 = new Color(81, 84, 83);

        d1 = new Dimension(900, 125);
        //d2 = new Dimension(225, 90);

        cp = getContentPane();

        northP = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();

        taP = new JPanel();

        sideP = new JPanel();

        chatP = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();

        endP = new JPanel();

        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //����
        topicTf = new JTextField(10);
        topicTf.setPreferredSize(new Dimension(225,50));
        topicTf.setEnabled(false);
        topicTf.setFont(f);

        //Ÿ��
        timeTf = new JTextField(10);
        timeTf.setPreferredSize(new Dimension(225, 50));
        timeTf.setEnabled(false);
        timeTf.setFont(f);

        //ä��
        chatTf = new JTextField(30);
        chatTf.setFont(f2);
        chatTf.setPreferredSize(new Dimension(700,50));
        chatTf.setBackground(c1);
        chatTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        cp.setPreferredSize(new Dimension(900, 750));
        cp.add(northP, BorderLayout.NORTH);//cp
        cp.add(sideP, BorderLayout.WEST);
        cp.add(taP, BorderLayout.CENTER);
        cp.add(chatP, BorderLayout.SOUTH);


        northP.setPreferredSize(d1);
        northP.setLayout(new GridLayout(1,2));
        northP.add(p1);
        northP.add(p2);

        p1.setBackground(c2);
        p2.setBackground(c2);

        //p1.add(topicTf, BorderLayout.CENTER);
        //p2.add(timeTf, BorderLayout.CENTER);

        p1.setLayout(new GridBagLayout());
        gc2 = new GridBagConstraints();
        gc2.gridwidth = 3;
        gc2.gridheight = 3;
        gc2.weightx = 1;

        gc2.gridx = 1;
        gc2.gridy = 1;
        //gc.gridwidth = 6;
        //gc.gridheight = 2;
        p1.add(topicTf, gc2);

        p2.setLayout(new GridBagLayout());
        gc2.gridwidth = 3;
        gc2.gridheight = 3;
        gc2.weightx = 1;

        gc2.gridx = 1;
        gc2.gridy = 1;
        p2.add(timeTf, gc2);


        taP.setPreferredSize(new Dimension(700,500));
        taP.add(ta, BorderLayout.CENTER);
        taP.add(sp);

        ta.setPreferredSize(new Dimension(700,500));
        ta.setEnabled(false);
        ta.setDisabledTextColor(Color.white);
        //ta.setBackground(c2);
        ta.setLineWrap(true);

        sideP.setPreferredSize(new Dimension(200,500));
        sideP.setBackground(c1);

        chatP.setPreferredSize(d1);
        chatP.setBackground(c2);
        //chatP.add(p3, BorderLayout.WEST);
        //chatP.add(p4, BorderLayout.SOUTH);
        //p3.setBackground(c2);
        //p4.setBackground(c2);
        //p3.add(chatTf, BorderLayout.CENTER);
        //p4.add(endBtn, BorderLayout.CENTER);

        chatP.setLayout(g);
        gc = new GridBagConstraints();

        gc.gridwidth = 6;
        gc.gridheight = 11;
        gc.weightx = 1;

        gc.gridx = 1;
        gc.gridy = 2;

        chatP.add(chatTf, gc);

        gc.gridx = 8;
        gc.gridy = 2;
        chatP.add(endBtn, gc);

        endBtn.setPreferredSize(new Dimension(150, 50));

        //endP.add(endBtn, BorderLayout.CENTER);
        //endP.setPreferredSize(d2);

        //tfP = new ImagePanel("pBack.png");
        //JPanel topicP = new JPanel(new BorderLayout());
        //topicP.add(topicTf, BorderLayout.EAST);
        //idCb = new JComboBox(idList);
        //JPanel selectP = new JPanel(new BorderLayout());
        //selectP.add(timeTf, BorderLayout.WEST);
        //tfP.add(topicP);
        //tfP.add(selectP);
        //tfP.add(idCb);                                      //�޺��ڽ� �߰�
        //northP.add(tfP, BorderLayout.SOUTH);


        //p1 = new JPanel();                                                                   //���̵��г�
        //p1.setPreferredSize(new Dimension(150, 600));
        //p1.setLayout(new GridLayout(4, 1));
        //p2 = new JPanel();
        //p2.setPreferredSize(new Dimension(150, 600));
        //p2.setLayout(new GridLayout(4, 1));
        //new PanelUi(this);
        //���̵��г� ��

        //cp.add(p1, BorderLayout.WEST);
        //cp.add(p2, BorderLayout.EAST);

    }

    public static void main(String[] args) {
        ClientUi c = new ClientUi();
    }

}