package TCPServer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import DB.DTO;
import DB.SongDAO;

public class ServerO extends Thread {

	Socket socketSo = null;
	InputStream inputSo = null;
	OutputStream outputSo = null;

	SongDAO sDAO = null;

	// 직렬화 -----
	ObjectOutputStream oos; // 직렬화

	Object object;
	ObjectInputStream ois;
	ByteArrayInputStream binput = null;
	// -----------
	Random r = new Random();

	ServerO(Socket socket) {
		this.socketSo = socket;
		System.out.println("서버 O");

		run();
	}

	@Override
	public void run() {
		bridge();
		listen();
	}

	public void bridge() { // normal 명령어 받는 곳. //일단 serverO로 이동.
		try {
			inputSo = socketSo.getInputStream();
			byte bb[] = new byte[100];
			inputSo.read(bb);
			String order = new String(bb);
			order = order.trim();
			codeSo(order); // 명령어 분류.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listen() {
		try {
			while (true) {
				inputSo = socketSo.getInputStream();
				byte[] bb = new byte[1024];
				inputSo.read(bb);

				binput = new ByteArrayInputStream(bb);
				ois = new ObjectInputStream(binput);

				try {
					object = ois.readObject();
					DTO dto = (DTO) object;
					sDAO.listenList(dto);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void codeSo(String order) {
		if (order.contains("노래목록불러오기")) {
//			sDAO = new SongDAO(this); // 여기에 dao 객체 생성.
			sDAO = SongDAO.sigleton(); // 싱글톤 // 크~.
			ArrayList<String[]> slist = sDAO.tableList();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(slist);

				byte bb[] = baos.toByteArray();
				outputSo = socketSo.getOutputStream();
				outputSo.write(bb);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
