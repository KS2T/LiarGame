

import javax.swing.*;
import java.awt.*;

class Result extends JDialog {
    JTextArea ta;
    Container cp;
    Font f = new Font("�ü�ü",Font.BOLD,40);
    JButton okBtn;
    Result() {
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        String liarWin = "���̾ ���þ� �߸��� �����߽��ϴ�.";
        String liarLose = "���̾ ���þ� �߸��� �����߽��ϴ�.";

        okBtn = new JButton("Ȯ��");
        okBtn.setVisible(false);
        String str = liarWin;                                   //win lose ����
         ta = new JTextArea();
        ta.setFont(f);
        ta.setEnabled(false);
        ta.setLineWrap(true);

        cp.add(ta,BorderLayout.CENTER);
        cp.add(okBtn,BorderLayout.SOUTH);
        setUi();
        for (int i = 0; i < str.length(); i++) {
            ta.setText(str.substring(0, i + 1));
            try {
                Thread.sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            if (i == 12) {
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }


            }
        }

        okBtn.setVisible(true);
    }

    void setUi() {
        setTitle("���!");
        setVisible(true);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}
