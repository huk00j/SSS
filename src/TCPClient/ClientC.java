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

	public String IDing; // 현재 로그인 한 계정.
	public Boolean loging = false;

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
	ClientC Cc;
	
	
	public ClientC(Socket socket) {
		this.socket = socket;
		run();
	}

	private void table() {
		this.Cc = this;
		table2 = new Table2(this, clientO);
//		divid();	// 노래 선택하는 곳.
	}

	@Override
	public void run() {
		streamset();
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
		table(); // N & O 서버*클라이언트 만들었으면 메인 프레임 생성.
		receive(); // 명령어 받기 시작.
	}

	private void forkCO() { // object 소켓 접속. // new Runnalbe 없애야하나?
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Osocket = new Socket("10.0.0.120", qq);
					clientO = new ClientO(Osocket, Cc);

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
		this.join2 = join; // 일단 이렇게 Join2 주소를 받았는데, 이렇게도 괜찮은건가.
	}

	public void send(String order) { // normal 명령문 output. // ClientO로 이동.
		try {
			output = socket.getOutputStream();
			output.write(order.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void codejoin(String order) {

		switch (order) { // ---------- visible이 아닌 set으로 변경.
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

		case "로그인성공":
			loging = true;
			table2.loginID = true;
			System.out.println("라라라라라라ㅏ라");

			if (loging) {
				table2.panel.setVisible(false);

				IDing = table2.textField.getText(); // 현재 로그인 한 계정.
				table2.guest = IDing;
				table2.logQ();
				table2.nonguest();	// 로그인시 리스트 제거.
				table2.panel_1.setVisible(true);
				System.out.println(loging +" 로그인성공 loging");
				
//				send(loging+"로그인음원리스트");
//				clientO.sendO(order);
				
				break;
			}
		case "로그인실패":
			System.out.println("와와와와오아아와와와");
			table2.lblNewLabel_3.setText("가입하지 않은 아이디거나 잘못된 번호입니다.");
			table2.lblNewLabel_3.setForeground(Color.RED);
			break;
		default:
			break;
		}

	}
	
}


