import java.io.*;
import java.net.*;
import java.util.*;

class LiarServer extends Thread{
	ServerSocket ss;
	Socket s;
	int port = 3000;
	Vector<OneClientModul> v = new Vector<OneClientModul>();
	OneClientModul ocm;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	LiarServer(){
		
		try{
			ss = new ServerSocket(port);
			pln(port+"대기중");
			start();
			while(true) {
				s = ss.accept();
				ocm = new OneClientModul(this);
				v.add(ocm);
				ocm.start();
			}
		}catch(IOException ie) {
			pln(port+"사용중");
		}finally {
			try {
				if(ss != null) ss.close();
			}catch(IOException ie) {}
		}
	}
	public void run() {
		String msg = "";
		try {
			while(true) {
				msg = br.readLine();
				msg = msg.trim();
				if(v.size()  != 0) {
					OneClientModul ocm = v.get(0);
					ocm.broadcast(" 관리자>> " + msg);
					if(ocm.chatId.equals(msg)) {
						ocm.cut();
					}
				}else {
					pln("클라이언트가 아무도 없음");
			}
				}
		}catch(IOException ie) {
		}
	}
	
	void pln(String str) {
		System.out.println(str);
	}
	void p(String str) {
		System.out.print(str);
	}
	public static void main(String[] args) {
		new LiarServer();
	}
}