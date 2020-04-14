package TCPClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import GUI.Table2;

public class ClientO extends Thread {

	Socket socketO = null;
	InputStream inputO = null;
	OutputStream outputO = null;

	// 직렬화에서 쓴 것 -----------
	Object object;
	ObjectInputStream ois = null;
	ByteArrayInputStream binput = null;

	Table2 table2 = null;
	
	// ----------------------

	ClientO(Socket socket) {
		this.socketO = socket;
		System.out.println("클라이언트 O");
		run();
	}

	@Override
	public void run() {
		receiveO();

	}
	
	public void Taddress(Table2 table2) {	// Table2는 ClientO를 알지만 ClientO는 Table2을 모르기 때문에 걍 받아옴.
		this.table2 = table2;
	}
	
	public void sendO(String order) {	// 프레임으로부터 명령어 전달 받아 Server로 전송.
		try {
			outputO = socketO.getOutputStream();
			outputO.write(order.getBytes());
			System.out.println("오브젝트 신호 보냅니다.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void receiveO() { // 직렬화로 object 받는 곳.
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					inputO = socketO.getInputStream();
					byte[] bb = new byte[1024];
					inputO.read(bb);

					binput = new ByteArrayInputStream(bb);
					ois = new ObjectInputStream(binput);

					try {
						object = ois.readObject();
						ArrayList<String[]> dto = (ArrayList<String[]>) object;

						for (int i = 0; i < dto.size(); i++) {
							table2.tableModel.addRow(dto.get(i));
						}

						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

}
