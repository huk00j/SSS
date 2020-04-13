package TCPClient;

import java.net.Socket;

public class ClientO {

	Socket Osocket = null;

	ClientO(Socket socket){
		this.Osocket = socket;
		System.out.println("클라이언트 O");
	}

}
