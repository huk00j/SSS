package TCP;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cmain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		Socket socket = new Socket("10.0.0.120", 9999);
		new ClientC(socket);
		
	}

}
