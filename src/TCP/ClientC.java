package TCP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
	// -----------------

	public ClientC(Socket socket) {
		this.socket = socket;
		table2 = new Table2(this); // Table2랑 연결 되었음.
		run();
	}

	@Override
	public void run() {
		rec();
//		receive();
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
				// 리턴 값이랑은 상관없이 String으로 주었기 때문에 String으로 받아야 함.

				System.out.println("///////");

				for (int i = 0; i < dto.size(); i++) {
					table2.tableModel.addRow(dto.get(i));
				}

//					System.out.println(dto.getName() + " --- 확인 333");
//					
//					list2[0] = dto.getNo();
//					list2[1] = dto.getTitle();
//					list2[2] = dto.getName();
//					list2[3] = dto.getGenre();
//					list2[4] = dto.getAlbum();

//					System.out.println(list2[1] + " --- 확인 444");

//					list.add(dto.saveSong());
//					list.add(dto.getTitle());
//					list.add(dto.getName());
//					list.add(dto.getGenre());

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
//						table2.joinQQ(jjj);

						codejoin(jjj); // 어쩔 땐 버튼이 보이고 안 보이고 함.

						// 이 밑으로는 직렬화---------------

						// ---------------------------
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
