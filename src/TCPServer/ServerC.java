package TCPServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
	ServerSocket Oserver;

	Socket Osocket = null;

	Random r = new Random();
	// ---------------------------------------------------------------

	ServerC(Socket socket) {
		this.socket = socket;
		bridge();
		run();
	}

	@Override
	public void run() {
		
	}

	private void socketSetting() {	// ServerO 소켓 만들어놓고 포트 번호 생성.
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					int objectN = 9000 + r.nextInt(998);
					Oserver = new ServerSocket();
					Oserver.bind(new InetSocketAddress("10.0.0.120", objectN));
					
					forkSO();
					
					String qq = String.valueOf(objectN);
					output = socket.getOutputStream();
					output.write(qq.getBytes());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void forkSO() throws IOException {	// Object Server.
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


//================================= 밑으로는 명령문. ==========================================	
	
	public void bridge() {	// normal 명령어 받는 곳.		//일단 serverO로 이동.
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

	private void joincode(String jj) {	// 명령어 분류하는 곳.	// //일단 serverO로 이동.
		switch (jj) {
		case "메인프레임":
			socketSetting();
			break;
			
/*		case "노래목록불러오기":
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
			break;	*/
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
