package TCPServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import DB.LoginDAO;
import DB.SongDAO;
import GUI.Join2;

public class ServerC extends Thread {

	Socket socket = null;
	InputStream input = null;
	OutputStream output = null;

	String check = null;
	LoginDAO ldao = new LoginDAO();
	SongDAO sDAO = null;
	Join2 join2 = null;

	ObjectOutputStream oos; // 직렬화

	// ---- 2개 port 사용 ---------------------------------------------
	ServerSocket Nserver; // 전역 변수로 둬야 밑줄이 사라짐. ex) never close.
	ServerSocket Oserver;

	Socket Nsocket = null;
	Socket Osocket = null;

	Random r = new Random();
	// ---------------------------------------------------------------

	ServerC(Socket socket) {
		this.socket = socket;

		socketSetting();	// 소켓 셋팅.

		run();
	}

	private void socketSetting() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Nserver = new ServerSocket();
					Oserver = new ServerSocket();
					
					int normalN = 9000 + r.nextInt(498);
					int objectN = 9500 + r.nextInt(498);
					
					Nserver.bind(new InetSocketAddress("10.0.0.120", normalN));
					Oserver.bind(new InetSocketAddress("10.0.0.120", objectN));

					Streamset(normalN, objectN);
				
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private void Streamset(int normalN, int objectN) {
		try {
			String pp = String.valueOf(normalN);
			String qq = String.valueOf(objectN);
			String oo = pp + "/" + qq;

			output = socket.getOutputStream();
			output.write(oo.getBytes());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void forkN() throws IOException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Nsocket = Nserver.accept();
					new ServerN(Nsocket);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();

	}

	private void forkO() throws IOException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Osocket = Oserver.accept();
					new ServerO(Osocket);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}

	@Override
	public void run() {
		try {
			bridge();
			forkN();
			forkO();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//================================= 밑으로는 명령문. ==========================================	
	
	public void bridge() {
		try {
			input = socket.getInputStream();
			byte bb[] = new byte[100];
			input.read(bb);
			String jj = new String(bb);
			jj = jj.trim();
			joincode(jj); // 명령어 분류.

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void joincode(String jj) {
		switch (jj) {
		case "노래목록불러오기":
			sDAO = new SongDAO(this); // 여기에 dao 객체 생성.
			ArrayList<String[]> slist = sDAO.tableList();

			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(slist);

				byte bb[] = baos.toByteArray();
				output = socket.getOutputStream();
				output.write(bb);
				System.out.println("-----1111111");

			} catch (IOException e) {
				e.printStackTrace();
			}
//★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆

			this.check = "노래목록수락";
			break;
		case "회원가입신청":
			this.check = "회원가입수락";
			break;
		case "계정중복확인":
			break;
		default:
			break;
		}

	}

}
