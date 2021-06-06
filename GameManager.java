import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameManager {

    String topic;
    String lair;
    Scanner sc;
    ArrayList<String> topicList;
    List<String> humanList;


    GameManager(){

        humanList = new Vector<>();
        humanList.add("까치");
        humanList.add("집오리");
        humanList.add("가마우지");
        humanList.add("고니");
        humanList.add("기러기");

   }

    public String setTopic(){

        File topicListFile = new File("C:\\Users\\성하\\Desktop\\새.txt");
        try{
            sc = new Scanner(topicListFile);
        }catch (FileNotFoundException e){
            System.out.println("파일을 찾을 수 없습니다.");
        }

        topicList = new ArrayList<>();
        while (sc.hasNextLine()){
            topicList.add(sc.nextLine());
        }

        Random topicGenerator = new Random();
        int topicListIndex = topicGenerator.nextInt(topicList.size());
        topic = topicList.get(topicListIndex);
        return topic;

    }

    public String setLair() {

        Random lairSelector = new Random();
        int humanListIndex = lairSelector.nextInt(humanList.size());
        lair = humanList.get(humanListIndex);
        return lair;

    }

    public void vote() {

        int i;
        for(i = 0; i < humanList.size(); i++){
            System.out.println(humanList.get(i);
        }
        

    }

    public static void main(String[] args){

    }
}
