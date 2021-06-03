import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

class Client extends JFrame {
  String ip, port, id;
  int portN = 0;
  Socket s;
  JFrame frame;
  LoginUi ui;
  ////////////////////////////////////// ↓채팅프레임 멤버
  JTextArea ta = new JTextArea();
  JTextField topicTf, selectTf, chatTf;
  JScrollPane sp = new JScrollPane(ta);
  JPanel tfP,p1,p2;
  Container cp;

  Client(LoginUi ui, JFrame frame) {
    try {
      this.frame = frame;
      this.ui = ui;
      id = ui.id.trim();
      ip = ui.ip.trim();

      System.out.println(ui.ip + ui.port + ui.id);

      init();
      setUi();
    } catch (Exception e) {

    }
  }

  void init() {
    ta.setPreferredSize(new Dimension(400,400));
    topicTf = new JTextField();
    selectTf = new JTextField();
    tfP = new JPanel(new GridLayout(1, 2));
    tfP.add(topicTf);
    tfP.add(selectTf);
    chatTf = new JTextField();
    p1= new JPanel();
    p2= new JPanel();
    p1.setPreferredSize(new Dimension(150,600));
    p1.setLayout(new GridLayout(4,1));
    p1.add(new JLabel(new ImageIcon("buddy.jpg")));
    p1.add(new JLabel(new ImageIcon("buddy.jpg")));
    p1.add(new JLabel(new ImageIcon("buddy.jpg")));
    p1.add(new JLabel(new ImageIcon("buddy.jpg")));
    p2.setPreferredSize(new Dimension(150,600));
    p2.setLayout(new GridLayout(4,1));
    p2.add(new JLabel(new ImageIcon("buddy.jpg")));
    p2.add(new JLabel(new ImageIcon("buddy.jpg")));
    p2.add(new JLabel(new ImageIcon("buddy.jpg")));
    p2.add(new JLabel(new ImageIcon("buddy.jpg")));
    cp = new ImagePanel("buddy.jpg");
    setContentPane(cp);
    cp.setLayout(new BorderLayout());
    cp.add(tfP,BorderLayout.NORTH);
    cp.add(ta,BorderLayout.CENTER);
    cp.add(chatTf,BorderLayout.SOUTH);
    cp.add(p1,BorderLayout.WEST);
    cp.add(p2,BorderLayout.EAST);


  }

  void setUi() {
    setVisible(true);
    setTitle(ip + ", " + port + "에서 채팅중..");
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
