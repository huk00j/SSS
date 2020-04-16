package TCPClient;

import java.awt.Color;
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

	
	public void jj2(Join2 join) {
		this.join2 = join;	// 일단 이렇게 Join2 주소를 받았는데, 이렇게도 괜찮은건가.
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
//		case "아이디공백":
//			System.out.println("공백 받았습니다!");
//			join2.lblNewLabel_2.setVisible(false);
//			join2.lblNewLabel_3.setVisible(false);
//			join2.lblNewLabel_4.setVisible(true);
//			break;
//			
//		case "0":
//			join2.checkId = true;
//			System.out.println("없는 아이디");
//			join2.lblNewLabel_2.setVisible(false);
//			join2.lblNewLabel_4.setVisible(false);
//			join2.lblNewLabel_3.setVisible(true);
//			break;
//			
//		case "1":
//			join2.checkId = false;
//			System.out.println("이미 있는 아이디");
//			join2.lblNewLabel_4.setVisible(false);
//			join2.lblNewLabel_3.setVisible(false);
//			join2.lblNewLabel_2.setVisible(true);
//			break;
		
		
		//------------------------------------------- visible이 아닌 set으로 변경해봅시다.
		case "아이디공백":
			join2.lblNewLabel_20.setText("아이디를 입력하세요.");
			join2.lblNewLabel_20.setForeground(Color.MAGENTA);
			break;
		
		case "0":
			join2.checkId = true;
			join2.lblNewLabel_20.setText("사용 가능한 계정입니다.");
			join2.lblNewLabel_20.setForeground(Color.BLUE);
			break;
		
		case "1":
			join2.checkId = false;
			join2.lblNewLabel_20.setText("이미 사용중인 계정입니다.");
			join2.lblNewLabel_20.setForeground(Color.RED);
			break;
			
		//------------ 까지 비밀번호 ------------------------
			
//		case "회원가입수락":
//			join2 = new Join2(this);
//			break;


		default:
			break;
		}

	}

	
}
