package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TCPServer.ServerC;

public class SongDAO {

	private Connection conn;
	private Statement stmt; // 텍스트 SQL 호출.
	private PreparedStatement ppsm;
	private ResultSet rs; // 튜플을 순차적으로 가리킨다.
	ServerC Sc = null;

	public SongDAO(ServerC serverC) {
		Sc = serverC;
		start();
	}

	private void start() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스 불러오라.
			System.out.println("Song 클래스 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("Song 클래스 로드 실패");
		}
	}

	public boolean connect() {
		boolean error = false;
		try {
			conn = DriverManager.getConnection("" + "jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			error = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return error;
	}

	
	public ArrayList<String[]> tableList() {
		ArrayList<String[]> list = new ArrayList<>();
		String sql = "select * from song";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						DTO dto = new DTO(); // while문 안에? 밖에?
						dto.setNo(String.valueOf(rs.getInt("no")));
						dto.setTitle(rs.getString("title"));
						dto.setName(rs.getString("name"));
						dto.setGenre(rs.getString("genre"));
						dto.setAlbum(rs.getString("album"));
						
						System.out.println(dto.getTitle() + " ■■■■■");
						
						list.add(dto.saveSong());
					}
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


}
