

import javax.swing.*;
import java.awt.*;

class Result extends JDialog {
    JTextArea ta;
    Container cp;
    Font f = new Font("궁서체",Font.BOLD,40);
    JButton okBtn;
    Result() {
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        String liarWin = "라이어가 제시어 추리에 성공했습니다.";
        String liarLose = "라이어가 제시어 추리에 실패했습니다.";

        okBtn = new JButton("확인");
        okBtn.setVisible(false);
        String str = liarWin;                                   //win lose 선택
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
        setTitle("결과!");
        setVisible(true);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}
