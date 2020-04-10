package TCP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import DB.LoginDAO;
import DB.SongDAO;
import GUI.Join2;

public class ServerC extends Thread {

	Socket socket = null;
	InputStream input = null;
	OutputStream output = null;

	String check = null;
	LoginDAO ldao = new LoginDAO();
	Join2 join2 = null;
	SongDAO sDAO = null;

	// 직렬화 ----------------
	ObjectOutputStream oos;
	
	
	//---------------------
	
	ServerC(Socket socket) {
		this.socket = socket;
		run();
	}

	@Override
	public void run() {
		bridge();
	}


	public void bridge() {
		try {
				input = socket.getInputStream();
				byte bb[] = new byte[100];
				input.read(bb);
				String jj = new String(bb);
				jj = jj.trim();
				joincode(jj); // 회원가입신청 받기

//				output = socket.getOutputStream();
//				output.write(this.check.getBytes());
				
				//------ 직렬화 칸 ---------------------------------
				
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void joincode(String jj) {
		switch (jj) {
		case "노래목록불러오기":
//★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆
			sDAO = new SongDAO(this);	// 여기에 dao 객체 생성.
//			ArrayList<DTO> slist = sDAO.tableList();
//			slist = sDAO.tableList();
			
			ArrayList<String[]> slist = sDAO.tableList();
//			slist = sDAO.tableList();
			// 여기서부터 inputStream.
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
			
		//--------------------------------------------
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




