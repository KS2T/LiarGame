import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class VoteDialog extends JDialog {

    //DefaultListModel<Object> voteListModel;
    JList<OneClientModul> voteList;
    JFrame frame;
    String title;
    ClientUi clientUi;
    //Window window;
    Container container;
    JPanel vPanel;
    JTextField vTextField = new JTextField();
    JButton vButton = new JButton("투표하기");
    String result;
    OneClientModul ocm;
    Vector<OneClientModul> v = new Vector<OneClientModul>();
    String name = ocm.chatId;


    VoteDialog(JFrame frame, ClientUi clientUi, String title) {
        super(frame, title, true);
        this.clientUi = clientUi;
        this.frame = frame;
        this.title = title;

        getResult();
        System.out.println(getResult());
    }

    String getResult(){

        voteList = new JList<OneClientModul>(v);
        voteList.setVisibleRowCount(4);
        voteList.setSelectedIndex(0);

        container = getContentPane();
        container.getMaximumSize();
        vPanel = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(vPanel, BorderLayout.CENTER);
        vPanel.setLayout(new GridLayout(3,1));
        vPanel.add(vButton);
        vPanel.add(voteList);
        vPanel.add(vTextField);
        vTextField.setSize(50,50);
        voteList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        voteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        voteList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = voteList.getSelectedIndex();
                result = voteListModel.get(index).toString();
            }
        });
        setSize(800, 300);
        setLocation(200, 100);
        setVisible(true);

        vButton.setActionCommand("투표하기");
        vButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(vButton)) {
                    int index = voteList.getSelectedIndex();
                    result = voteListModel.get(index).toString();
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
