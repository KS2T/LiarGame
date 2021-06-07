import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Timer;

public class GameManager {

    String topic;
    String liar;
    Scanner sc;
    ArrayList<String> topicList;
    List<String> playerList = new ArrayList();
    LiarServer ls;
    Client c;
    Vector<Client> cv = new Vector<>();

    GameManager(LiarServer ls) {
        this.ls = ls;
        for (OneClientModul ocm : ls.v) {
            playerList.add(ocm.chatId);
        }
        setTopic();
        setLair();
        System.out.println(liar);
        System.out.println(topic);
        for (OneClientModul ocm : ls.v) {
            ocm.broadcast("liar:" + liar);
            ocm.broadcast("topic:" + topic);
        }
        printTimer();
        oneChat();
        vote();
        unlockAll();
    }

    GameManager(Client c) {
        this.c = c;
        cv.add(c);
    }

    void lockChat() {
        ls.ocm.broadcast("ä�ö�");
    }

    void unlockAll() {
        for (Client c : cv) {
            c.cui.chatTf.setEnabled(true);
        }
    }

    void oneChat() {
        ls.ocm.broadcast("ä���� ���ϴ�. 10�� �� ������� ������ �������ּ���.");
        lockChat();
        ls.sleep(10000);
        for (int i = 0; i < ls.v.size(); i++) {
            OneClientModul chatOcm = ls.v.get(i);
            for (Client c : cv) {
                if (c.id.equals(chatOcm.chatId)) {
                    printTimer();
                    break;
                }
            }
            ls.ocm.broadcast("ä�þ��" + chatOcm.chatId);
            ls.sleep(10000);
        }
    }

    public void setTopic() {

        File topicListFile = new File("����.txt");
        try {
            sc = new Scanner(topicListFile);
        } catch (FileNotFoundException e) {
            System.out.println("������ ã�� �� �����ϴ�.");
        }

        topicList = new ArrayList<>();
        while (sc.hasNextLine()) {
            topicList.add(sc.nextLine());
        }

        Random topicGenerator = new Random();
        int topicListIndex = topicGenerator.nextInt(topicList.size());
        topic = topicList.get(topicListIndex);

    }

    public void setLair() {

        Random lairSelector = new Random();
        int listIndex = lairSelector.nextInt(playerList.size());
        liar = playerList.get(listIndex);

    }

    /* public void setTimeLimit() {

        LocalTime startTime = LocalTime.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime endTime = startTime.plusSeconds(5L);
        while (endTime.isAfter(currentTime)) {
            currentTime = LocalTime.now();
        }

    } */

    public void vote() {

    }

    String printTimer() {

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (10 * 1000);
        final String[] time = new String[1];
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                long currentTime = System.currentTimeMillis();
                long leftTime = endTime - currentTime;
                long leftMinute = (leftTime / (60 * 1000));
                long leftSeconds = (leftTime / 1000) % 60;
                time[0] = (leftMinute + ":" + leftSeconds);
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        return time[0];
    }
}
