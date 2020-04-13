package TCPClient;

import java.net.Socket;

public class ClientN {

	Socket Nsocket = null;
	
	ClientN(Socket socket){
		this.Nsocket = socket;
		System.out.println("클라이언트 N");
	}
	
}
