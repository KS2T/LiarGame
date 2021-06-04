
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

class ClientUi extends JFrame implements ActionListener {
  String ip, port, id;
  int portN = 0;
  Socket s;
  Client c;
  LoginUi ui;
  ////////////////////////////////////// ↓채팅프레임 멤버
  JTextArea ta = new JTextArea();
  JTextField topicTf, timeTf, chatTf;
  String idList[] = {"asd", "assd", "asdasasdasdasdasdd"};
  JComboBox idCb;
  JScrollPane sp;
  JPanel tfP, p1, p1_1, p1_2, p1_3, p1_4, p2, p2_1, p2_2, p2_3, p2_4, taP, northP, endP, chatP;
  RoundedButton endBtn = new RoundedButton("나가기");
  Container cp;
  Font f = new Font("맑은 고딕", Font.BOLD, 20);
  Font f2 = new Font("맑은 고딕", Font.PLAIN, 20);

  ClientUi(LoginUi ui) {
    try {
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

    cp = getContentPane();
    cp.setLayout(new BorderLayout());                                             //cp

    northP = new JPanel(new BorderLayout());
    tfP = new JPanel();
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
    tfP.add(idCb);
    tfP.setBackground(Color.black);                                       //상단 색
    northP.add(tfP, BorderLayout.SOUTH);
    cp.add(northP, BorderLayout.NORTH);                                      //northP

    chatP = new JPanel();
    chatTf = new JTextField(30);
    chatTf.setFont(f2);
    chatP.add(chatTf);
    endBtn.addActionListener(this);
    endP = new JPanel();
    chatP.add(endBtn);
    chatP.setBackground(Color.black);
    cp.add(chatP, BorderLayout.SOUTH);                                               //chatTf

    p1 = new JPanel();                                                                   //사이드 패널
    p1.setPreferredSize(new Dimension(150, 600));
    p1.setLayout(new GridLayout(4, 1));

    p1_1 = new JPanel(new BorderLayout());
    JLabel lb1_1 = new JLabel(new ImageIcon("buddy.jpg"));
    JLabel idlb1_1 = new JLabel("안녕");
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
    JLabel idlb1_2 = new JLabel("안녕");
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
    JLabel idlb1_3 = new JLabel("안녕");
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
    JLabel idlb1_4 = new JLabel("안녕");
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
    JLabel idlb2_1 = new JLabel("안녕");
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
    JLabel idlb2_2 = new JLabel("안녕");
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
    JLabel idlb2_3 = new JLabel("안녕");
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
    JLabel idlb2_4 = new JLabel("안녕");
    idlb2_4.setFont(f);
    idlb2_4.setHorizontalAlignment(0);
    idlb2_4.setForeground(Color.black);
    idlb2_4.setBackground(Color.gray);
    idlb2_4.setOpaque(true);
    p2_4.add(lb2_4);
    p2_4.add(idlb2_4, BorderLayout.SOUTH);
    p2.add(p2_4);                                                                     //사이드패널

    taP = new JPanel(new BorderLayout());
    taP.add(ta, BorderLayout.CENTER);
    sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    taP.add(sp);                                                                      //ta패널

    ta.setLineWrap(true);
    cp.add(taP, BorderLayout.CENTER);
    cp.add(p1, BorderLayout.WEST);
    cp.add(p2, BorderLayout.EAST);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(endBtn)) {
      dispose();
      ui.reopen();
    }
  }


  void setUi() {
    setVisible(true);
    setTitle(ip + ", " + port + "에서 채팅중..");
    setSize(900, 750);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  void human() {
    setTitle(ip + ", " + port + "에서 게임중..");
    String topic = null;                                                            //주제 받아오는 코딩
    topicTf.setText("주제: " + topic);

  }

  void liar() {
    setTitle(ip + ", " + port + "에서 게임중..");
    topicTf.setText("당신은 라이어입니다.");

  }

  public class RoundedButton extends JButton {
    public RoundedButton() {
      super();
      decorate();
    }

    public RoundedButton(String text) {
      super(text);
      decorate();
    }

    public RoundedButton(Action action) {
      super(action);
      decorate();
    }

    public RoundedButton(Icon icon) {
      super(icon);
      decorate();
    }

    public RoundedButton(String text, Icon icon) {
      super(text, icon);
      decorate();
    }

    protected void decorate() {
      setBorderPainted(false);
      setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Color c = new Color(255, 247, 242); //배경색 결정
      Color o = new Color(247, 99, 12); //글자색 결정
      int width = getWidth();
      int height = getHeight();
      Graphics2D graphics = (Graphics2D) g;
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      if (getModel().isArmed()) {
        graphics.setColor(c.darker());
      } else if (getModel().isRollover()) {
        graphics.setColor(c.brighter());
      } else {
        graphics.setColor(c);
      }
      graphics.fillRoundRect(0, 0, width, height, 10, 10);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
      int textX = (width - stringBounds.width) / 2;
      int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
      graphics.setColor(o);
      graphics.setFont(getFont());
      graphics.drawString(getText(), textX, textY);
      graphics.dispose();
      super.paintComponent(g);
    }
  }
}
