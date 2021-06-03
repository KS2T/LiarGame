
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.*;
import java.util.zip.CheckedOutputStream;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

class LoginUi extends JFrame implements ActionListener {

  Client c;
  String id, ip, port;
  JTextField idTf, ipTf, portTf;
  JLabel idLb, ipLb, portLb, imgLb;
  JButton serverBtn, clientBTn, endBtn;
  Container cp;
  JPanel tfP, btnP, radioP, inputP, p1;
  ImageIcon titleIcon, backIcon;
  Thread th = new Thread(new Runnable() {
    @Override
    public void run() {
      try {
        sleep(4000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      serverBtn.setVisible(true);
      clientBTn.setVisible(true);
      endBtn.setVisible(true);
      System.out.println("실행종료");
      interrupted();
    }
  });

  LoginUi() {
    init();
  }

  void init() { //로그인화면 구성
    serverBtn = new JButton("서버 생성하기");
    clientBTn = new JButton("서버 입장하기");
    endBtn = new JButton("종료하기");
    p1 = new ImagePanel("back2.gif");
    setContentPane(p1);
    p1.add(serverBtn, BorderLayout.EAST);
    p1.add(clientBTn, BorderLayout.EAST);
    p1.add(endBtn, BorderLayout.EAST);
    setUi();
    serverBtn.setBounds(580, 320, 150, 40);
    serverBtn.setVisible(false);
    clientBTn.setBounds(580, 400, 150, 40);
    clientBTn.setVisible(false);
    endBtn.setBounds(580, 480, 150, 40);
    endBtn.setVisible(false);
    action();
    th.start();
  }

  void setUi() {
    setTitle("라이어게임");
    setVisible(true);
    setSize(815, 638);
    setResizable(false);
    setLayout(null);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent e) { //로그인화면 버튼 액션리스너
    if (e.getSource().equals(serverBtn)) {
      LoginDialog lD = new LoginDialog(this, this, "서버 생성하기");
      ip = lD.ipTf.getText();
      port = lD.portTf.getText();

    }
    if (e.getSource().equals(clientBTn)) {
      LoginDialog lD = new LoginDialog(this, this, "서버 입장하기");
      ip = lD.ipTf.getText();
      port = lD.portTf.getText();
    }
    if (e.getSource().equals(endBtn)) {
      System.exit(0);
    }
  }

  void action() { //로그인화면 버튼 액션리스너 입력
    serverBtn.addActionListener(this);
    clientBTn.addActionListener(this);
    endBtn.addActionListener(this);
  }

  public static void main(String[] args) {
    new LoginUi();
  }
}

class ImagePanel extends JPanel {

  Image image;

  public ImagePanel(String str) {                 //로그인화면 배경이미지 저장
    image = Toolkit.getDefaultToolkit().createImage(str);
  }

  @Override
  public void paintComponent(Graphics g) {//로그인화면 배경이미지로 그리기
    super.paintComponent(g);
    if (image != null) {
      g.drawImage(image, 0, 0, this);
    }
  }

}

class LoginDialog extends JDialog implements ActionListener { //서버생성하기 클릭시 팝업메세지          todo 디자인선정 후 수정
  JTextField idTf, ipTf, portTf;
  JLabel idLb, ipLb, portLb;
  JButton okBtn, noBtn, ipBtn;
  JPanel p1;
  JFrame frame;
  LoginUi ui;
  String title;

  LoginDialog(JFrame frame, LoginUi ui, String title) {
    super(frame, title, true);
    this.frame = frame;
    this.ui = ui;
    this.title = title;
    init();
    if (getTitle().equals("서버 생성하기")) {
      addS();
      setUiS();
    } else if (getTitle().equals("서버 입장하기")) {
      addC();
      setUiC();
    }
  }

  void init() {

    idLb = new JLabel("아이디");
    idTf = new JTextField(10);
    ipLb = new JLabel("아이피");
    ipBtn = new JButton("내 ip불러오기");
    ipTf = new JTextField(10);
    ipLb = new JLabel("아이피");
    portLb = new JLabel("포트");
    portTf = new JTextField(10);
    okBtn = new JButton("확인");
    noBtn = new JButton("취소");
    ipBtn.addActionListener(this);
    okBtn.addActionListener(this);
    noBtn.addActionListener(this);
    p1 = new JPanel();
  }

  void setUiS() {
    setLayout(new GridLayout(3, 2));
    pack();
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  void setUiC() {
    setLayout(new GridLayout(4, 2));
    pack();
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  void addS() {
    p1.add(ipLb);
    p1.add(ipBtn);
    add(p1);
    add(ipTf);
    add(portLb);
    add(portTf);
    add(okBtn);
    add(noBtn);
  }

  void addC() { //
    add(idLb);
    add(idTf);
    add(ipLb);
    add(ipTf);
    add(portLb);
    add(portTf);
    add(okBtn);
    add(noBtn);
  }


  public void actionPerformed(ActionEvent e) {                                            //todo null잡아주기
    if (e.getSource().equals(ipBtn)) {
      try {
        ipTf.setText(InetAddress.getLocalHost().getHostAddress());
      } catch (UnknownHostException unknownHostException) {
        unknownHostException.printStackTrace();
      }
    }
    if (e.getSource().equals(okBtn) & title.equals("서버 생성하기")) {
      ui.ip = ipTf.getText();
      ui.port = portTf.getText();
      dispose();
      new Server(ui, frame);
    } else if (e.getSource().equals(okBtn) & title.equals("서버 입장하기")) {
      ui.id = idTf.getText();
      ui.ip = ipTf.getText();
      ui.port = portTf.getText();
      dispose();
      frame.dispose();
      new Client(ui, frame);
    } else if (e.getSource().equals(noBtn)) {
      ipTf.setText("");
      portTf.setText("");
      dispose();
    }
  }


} //로그인다이얼로그

