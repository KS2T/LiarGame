import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

class ServerUi extends JFrame implements ActionListener {
    Object o;
    String ip, port, id;
    JFrame frame;
    LoginUi ui;
    ServerSocket ss;
    LiarServer ls;
    Socket s;
    /////////////////////////////////////////////¼­¹ö ui¸â¹ö
    JTextArea ta = new JTextArea();
    JTextField topicTf, selectTf, chatTf;
    JScrollPane sp;
    JPanel tfP, endP, chatP, btnP, p1_3, p1_4, p2, p2_1, p2_2, p2_3, p2_4, taP;
    Container cp;
    JButton startBtn,banBtn,endBtn,clearBtn;
    JComboBox idBox;
    String idArray[] = {"¤±","¤Ð"};

    Font ¸¼Àº°íµñ = new Font("¸¼Àº °íµñ", Font.BOLD, 20);

    ServerUi(LoginUi ui, JFrame frame,LiarServer ls) {
        this.ui = ui;
        this.frame = frame;
        this.ls=ls;
        init();
        setUi();
    }

    void init() {
        cp = getContentPane();
        cp.setLayout(new BorderLayout());                                                           //cp

        endP = new JPanel(new BorderLayout());
        endBtn = new JButton("Á¾·á");
        endBtn.addActionListener(this);
        endP.add(endBtn,BorderLayout.WEST);
        cp.add(endP,BorderLayout.NORTH);                                                                //endP

        taP = new JPanel(new BorderLayout());
        taP.add(ta, BorderLayout.CENTER);
        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ta.setLineWrap(true);
        taP.add(sp);
        cp.add(taP,BorderLayout.CENTER);                                                            //taP

        chatP = new JPanel(new BorderLayout());
        btnP = new JPanel(new GridLayout(1,4));
        startBtn = new JButton("°ÔÀÓ ½ÃÀÛ!");
        banBtn = new JButton("°­Åð");
        idBox = new JComboBox(idArray);
        clearBtn = new JButton("Ã¤ÆÃ Áö¿ì±â");
        btnP.add(startBtn);
        btnP.add(banBtn);
        btnP.add(idBox);
        btnP.add(clearBtn);
        chatTf = new JTextField("sp");
        chatP.add(chatTf,BorderLayout.CENTER);
        chatP.add(btnP,BorderLayout.SOUTH);
        cp.add(chatP,BorderLayout.SOUTH);                                                            //chatP


    }

    void setUi() {
        setVisible(true);
        setTitle(ip + ", " + port + "¿¡¼­ Ã¤ÆÃÁß..");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(endBtn)) {
            dispose();
            ui.reopen();
        }
    }

}
