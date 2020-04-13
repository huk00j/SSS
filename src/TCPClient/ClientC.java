package TCPClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
	
	//----------- 소켓 나누기. --------------------------
	Socket Nsocket = null;
	Socket Osocket = null;
	
	int pp = 0;	// normal.
	int qq = 0;	// object.
	//-----------------------------------------------
	
	public ClientC(Socket socket) {
		this.socket = socket;
		table2 = new Table2(this); // Table2랑 연결 되었음.
	
		run();
	}

	
	@Override
	public void run() {
		streamset();
		forkN();
		forkO();
		
		rec();
//		receive();
	}

	private void streamset() {
		try {
			input = socket.getInputStream();
			byte bb[] = new byte[1024];
			input.read(bb);
			String oo = new String(bb);
			oo = oo.trim();
			
			StringTokenizer st = new StringTokenizer(oo, "/");
			while(st.hasMoreTokens()) {
				String p = st.nextToken();
				String q = st.nextToken();
				
				this.pp = Integer.valueOf(p);
				System.out.println(pp + " ??? 1");

				this.qq = Integer.valueOf(q);
				System.out.println(qq + " ??? 2");
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void forkN() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Nsocket = new Socket("10.0.0.120", pp);
					new ClientN(Nsocket);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	private void forkO() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Osocket = new Socket("10.0.0.120", qq);
					new ClientO(Osocket);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}


	public void rec() {
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
//					DTO dto = (DTO) object;	// 여기서 에러.
//					String[] dto = (String[]) object; // 변경.
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

	}

	private void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						input = socket.getInputStream();
						byte bb[] = new byte[100];
						input.read(bb);
						String jjj = new String(bb);
						jjj = jjj.trim();
						System.out.println(jjj + " ☜    jjj 가 뭐니");

						codejoin(jjj); // 어쩔 땐 버튼이 보이고 안 보이고 함.

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public void send(String jj) {
		try {
			output = socket.getOutputStream();
			output.write(jj.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void codejoin(String jjj) {

		switch (jjj) {
		case "회원가입수락":
			join2 = new Join2(this);
			break;

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
