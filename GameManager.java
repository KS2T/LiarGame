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
            gm("liar:" + liar);
            gm("topic:" + topic);
        }
        oneChat();
        vote();
        liarSelect();
        unlockAll();
    }

    void lockChat() {
        gm("채팅락");
    }

    void unlockAll() {
        gm("올언락");
    }

    void oneChat() {
        ls.ocm.broadcast("채팅이 잠깁니다. 10초 후 순서대로 주제를 설명해주세요.");
        lockChat();
        ls.sleepTh(10);
        for (int i = 0; i < ls.v.size(); i++) {
            OneClientModul chatOcm = ls.v.get(i);
            gm("채팅언락" + chatOcm.chatId);
            ls.sleepTh(10);
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

    public void vote() {
        for (OneClientModul ocm : ls.v) {
            gm("list" + ocm.chatId);
        }
        gm("vote");
        ls.sleepTh(20);
        System.out.println("쓰레드깸");
    }

    void liarSelect() {
        int i=0;
        String voteId = "";
        for (OneClientModul ocm : ls.v){
            System.out.println(ls.voteList.size());
            int j = Collections.frequency(ls.voteList,ocm.chatId);
            System.out.println(j);
            if (j>i){
                i=j;
                voteId = ocm.chatId;
                System.out.println(i);
            }
        }
        System.out.println("Max: "+voteId);
        if (liar.equals(voteId)) {
            gm("votecom" + voteId);
            ls.sleepTh(10);
            String liarTopic;
            liarTopic = ls.liarTopic;
            System.out.println(liarTopic);
            if (liarTopic.equals(topic)) {
                System.out.println("라이어승리");
                ls.ocm.broadcast("라이어승리");
            } else if (liarTopic.equals("10초초과")) {
                ls.ocm.broadcast("라이어가 제한시간에 제시어를 입력하지 못했습니다.");
                ls.ocm.broadcast("라이어패배");

            } else {
                ls.ocm.broadcast("라이어가 제시어를 맞히지 못햇습니다." +
                        "\n라이어 패배");
            }
            ls.liarTopic = "10초초과";
        } else {
            System.out.println("라이어승리");
            ls.ocm.broadcast("라이어를 찾아내지 못했습니다 Liar: " + liar);
            ls.ocm.broadcast("라이어승리");
        }
        //result();
    }

    void gm(String str) {
        ls.ocm.broadcast("gm" + str);
    }
}
