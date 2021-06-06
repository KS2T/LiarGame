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
    String player[] = {"asd", "sss", "ffff", "asdwww"};

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
            ocm.broadcast("topic:" + topic);
            ocm.broadcast("liar:");
        }
    }

    public void setTopic() {

        File topicListFile = new File("주제.txt");
        try {
            sc = new Scanner(topicListFile);
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
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

    public void printTimer() {

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (5 * 60 * 1000);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask(){

            @Override
            public void run() {

                long currentTime = System.currentTimeMillis();
                long leftTime = endTime - currentTime;
                long leftMinute = (leftTime / (60 * 1000) );
                long leftSeconds = (leftTime / 1000) % 60;

                System.out.println(leftMinute + ":" + leftSeconds);
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
}
