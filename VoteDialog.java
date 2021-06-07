import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

class VoteDialog extends JDialog {

    JList<String> voteList;
    JFrame frame;
    ClientUi clientUi;
    Client c;
    Container container;
    JPanel vPanel;
    JButton vButton = new JButton("투표하기");
    String result;


    VoteDialog(Client c) {
        super(c.cui, "투표하기", true);
        this.clientUi = c.cui;
        this.c=c;
        this.frame = c.frame;

        getResult();
        System.out.println(getResult());
    }

    String getResult() {

        voteList = new JList<>(c.idList);
        voteList.setVisibleRowCount(4);
        voteList.setSelectedIndex(0);
        container = getContentPane();
        container.getMaximumSize();
        vPanel = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(vPanel, BorderLayout.CENTER);
        vPanel.setLayout(new GridLayout(2, 1));
        vPanel.add(vButton);
        vPanel.add(voteList);
        voteList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        voteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        voteList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = voteList.getSelectedIndex();
                result = c.idList.get(index);
                dispose();
            }
        });
        setSize(800, 300);
        setLocation(200, 100);
        setVisible(true);

        vButton.setActionCommand("투표하기");
        vButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(vButton)) {
                    int index = voteList.getSelectedIndex();
                    result = c.idList.get(index);
                    dispose();
                }
            }
        });

        voteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    vButton.doClick();

                }
            }
        });

        return result;
    }


}
