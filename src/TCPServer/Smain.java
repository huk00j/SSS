package TCPServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Smain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ServerSocket server = new ServerSocket();
		Socket socket = null;
		
		server.bind(new InetSocketAddress("10.0.0.120", 9997));
		
		while(true) {
			System.out.println("accept 대기중~");
			socket = server.accept();
			new ServerC(socket);
		}
	}

}
