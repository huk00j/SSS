package TCPServer;

import java.net.Socket;

public class ServerN {

	Socket Nsocket = null;
	
	ServerN(Socket socket){
		this.Nsocket = socket;
		System.out.println("서버 N");
	}
}
