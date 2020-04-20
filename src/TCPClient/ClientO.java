package TCPClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import DB.DTO;
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
	
	// 직렬화 보내기 ----
	ObjectOutputStream oos = null;
	ByteArrayOutputStream boutput = null;
	// ----------------------
	ClientC Cc;

	ClientO(Socket socket, ClientC cc) {
		this.socketO = socket;
		this.Cc = cc;
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

	public void divid(String[] header4) {
		if(Cc.loging == false) {
			table2.tableModel_1.addRow(header4);
			System.out.println("냠냠");
			
		} else if ( Cc.loging == true) {
			System.out.println("쩝쩝");
			DTO dto = new DTO();
			dto.setId(table2.guest);
			dto.setTitle(header4[0]);
			dto.setName(header4[1]);
			dto.setGenre(header4[2]);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				oos = new ObjectOutputStream(baos);
				oos.writeObject(dto);
				
				byte bb[] = baos.toByteArray();
				outputO = socketO.getOutputStream();
				outputO.write(bb);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String list[] = new String[3];
			list[0] = dto.getTitle();
			list[1] = dto.getName();
			list[2] = dto.getGenre();
			table2.tableModel_1.addRow(list);	// 로그인 후 오른쪽 목록에 곡 추가하기.
		}
	}
	
}

