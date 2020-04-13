package TCPServer;

import java.net.Socket;

public class ServerO {

	Socket Osocket = null;
	
	ServerO(Socket socket){
		this.Osocket = socket;
		System.out.println("서버 O");
	}
	
}
