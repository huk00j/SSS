package TCPClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import DB.SongDAO;
import GUI.Join2;
import GUI.Table2;

public class ClientC extends Thread {

	Socket socket = null;
	OutputStream output = null;
	InputStream input = null;

	Table2 table2 = null;
	Join2 join2 = null;
	SongDAO sDAO = null;

	// 직렬화 ------------
	ObjectInputStream ois = null;
	ByteArrayInputStream binput = null;

	Object object;
//	ArrayList<DTO> list = new ArrayList<>();
	String list2[] = new String[5];

	// ----------- 소켓 나누기. --------------------------
	Socket Nsocket = null;
	Socket Osocket = null;
	ClientO clientO;

	int qq = 0; // object.
	// -----------------------------------------------

	public ClientC(Socket socket) {
		this.socket = socket;
		run();
	}

	private void table() {
		table2 = new Table2(this, clientO);
	}

	@Override
	public void run() {
		streamset();

//////	rec();	// ClientO 로 이동.
//		receive();
	}

	private void streamset() { // normal & object 소켓 나누기 위한 포트넘버 받아서 분류하는 곳.
		try {
			String first = "메인프레임";
			output = socket.getOutputStream();
			output.write(first.getBytes());
			// ====== 위에 프레임 명령어 보내는 곳 ====================================

			input = socket.getInputStream();
			byte bb[] = new byte[1024];
			input.read(bb);
			String qq = new String(bb);
			qq = qq.trim();

			this.qq = Integer.valueOf(qq);
			second();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void second() {
		forkCO();
		table();	// N & O 서버*클라이언트 만들었으면 메인 프레임 생성.
		receive();	// 명령어 받기 시작.
	}

	private void forkCO() { // object 소켓 접속.	// new Runnalbe 없애야하나?
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Osocket = new Socket("10.0.0.120", qq);
					clientO = new ClientO(Osocket);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

//=========== 밑으로 send & receive & 명령어 ======================================================

/*	public void rec() { // 직렬화로 object 받는 곳.
		try {
			input = socket.getInputStream();
			byte[] bb = new byte[1024];
			input.read(bb);

			binput = new ByteArrayInputStream(bb);
			ois = new ObjectInputStream(binput);
			System.out.println(ois + " --- 확인 111");

			try {
				object = ois.readObject();
				System.out.println(object + " --- 확인 222");
//////				DTO dto = (DTO) object;	// 여기서 에러.
//////				String[] dto = (String[]) object; // 변경.
				ArrayList<String[]> dto = (ArrayList<String[]>) object;
				// String으로 주었기 때문에 String으로 받아야 함.

				System.out.println("///////");

				for (int i = 0; i < dto.size(); i++) {
					table2.tableModel.addRow(dto.get(i));
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	} */

	private void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						input = socket.getInputStream();
						byte bb[] = new byte[100];
						input.read(bb);
						String order = new String(bb);
						order = order.trim();
						System.out.println(order + " ☜    jjj 가 뭐니");

						codejoin(order); // 어쩔 땐 버튼이 보이고 안 보이고 함.

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public void send(String order) {	// normal 명령문 output.	// ClientO로 이동.
		try {
			output = socket.getOutputStream();
			output.write(order.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void codejoin(String order) {

		switch (order) {
//		case "회원가입수락":
//			join2 = new Join2(this);
//			break;

//		case "노래목록수락":
//			sDAO = new SongDAO();	// 여기에 dao 객체 생성.
//			ArrayList<String[]> tList = new ArrayList<>();
//			tList = sDAO.tableList();
//			for (int i = 0; i < tList.size(); i++) {
//				table2.tableModel.addRow(tList.get(i));
//			}
//			break;

		default:
			break;
		}

	}

}
