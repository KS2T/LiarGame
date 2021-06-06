import java.io.File;
import java.io.FileNotFoundException;
// import java.time.LocalTime;
import java.util.*;

public class GameManager {

    String topic;
    String lair;
    Scanner sc;
    ArrayList<String> topicList;
    List<String> playerList = new Vector<>();


    GameManager(){

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
        int listIndex = lairSelector.nextInt(playerList.size());
        lair = playerList.get(listIndex);
        return lair;

    }

    /* public void setTimeLimit() {

        LocalTime startTime = LocalTime.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime endTime = startTime.plusSeconds(5L);
        while (endTime.isAfter(currentTime)) {
            currentTime = LocalTime.now();
        }

    } */

    public void vote(){
        
    }

    public static void main(String[] args){

        GameManager gm = new GameManager();

    }

}
