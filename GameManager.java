import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameManager {

      String topic;
      String liar;
      Scanner sc;
      ArrayList<String> topicList;
      List<String> playerList = new ArrayList();
      LiarServer ls;
      Vector<Client> cv = new Vector<>();
      HashSet<OneClientModul>ocmSet = new HashSet<>();
      GameManager(LiarServer ls) {
            System.out.println("�׸� ����");
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
            gm("ä�ö�");
      }

      void unlockAll() {
            gm("�þ��");
      }

      void oneChat() {
            ls.ocm.broadcast("ä���� ���ϴ�. 10�� �� ������� ������ �������ּ���.");
            lockChat();
            ls.sleepTh(10);            ocmset();
            for (OneClientModul ocm : ocmSet) {
                  gm("ä�þ��" + ocm.chatId);
                  ls.sleepTh(10);

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
            System.out.println(topicList.size());
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
            System.out.println("�������");
      }

      void liarSelect() {
            int i = 0;
            String voteId = "";
            for (OneClientModul ocm : ls.v) {
                  System.out.println(ls.voteList.size());
                  int j = Collections.frequency(ls.voteList, ocm.chatId);
                  System.out.println(j);
                  if (j > i) {
                        i = j;
                        voteId = ocm.chatId;
                        System.out.println(i);
                  }
            }
            System.out.println("Max: " + voteId);
            if (liar.equals(voteId)) {
                  gm("votecom" + voteId);
                  ls.ocm.broadcast("���̾ ã�ҽ��ϴ�. \nLiar: "+liar+" \n���̾ ���þ �߸����Դϴ�.");
                  ls.sleepTh(10);
                  String liarTopic;
                  liarTopic = ls.liarTopic;
                  System.out.println(liarTopic);
                  if (liarTopic.equals(topic)) {
                        System.out.println("���̾�¸�");
                        ls.ocm.broadcast("���̾�¸�");
                        result("resultliarWin");
                  } else if (liarTopic.equals("10���ʰ�")) {
                        ls.ocm.broadcast("���̾ ���ѽð��� ���þ �Է����� ���߽��ϴ�.");
                        ls.ocm.broadcast("���̾��й�");
                        result("resultliarLose");

                  } else {
                        ls.ocm.broadcast("���̾ ���þ ������ ���޽��ϴ�." +
                                "\n���̾� �й�");
                        ls.ocm.broadcast(" ���þ� : " + topic + "\n���̾ �Է��� ���þ� : " + liarTopic);
                        result("resultliarLose");
                  }
                  ls.liarTopic = "10���ʰ�";
            } else {
                  System.out.println("���̾�¸�");
                  ls.ocm.broadcast("���̾ ã�Ƴ��� ���߽��ϴ�.\n Liar: " + liar);
                  ls.ocm.broadcast("���̾�¸�");
                  result("resultliarWin");
            }
            //result();
      }

      void result(String str) {
            gm(str);
      }
      void ocmset(){
            for (OneClientModul ocm : ls.v){
                  ocmSet.add(ocm);
            }
      }
      void gm(String str) {
            ls.ocm.broadcast("gm" + str);
      }

}
