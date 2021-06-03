import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Cl {
    Socket s;
    Cl(){
        try {
            s = new Socket(InetAddress.getLocalHost().getHostAddress(),3000);
            System.out.println("서버와접속");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cl();
    }
}
