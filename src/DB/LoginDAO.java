package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TCPServer.ServerC;

public class LoginDAO {

	private Connection conn;
	private Statement stmt; // 텍스트 SQL 호출.
	private PreparedStatement ppsm;
	private ResultSet rs; // 튜플을 순차적으로 가리킨다.

	public LoginDAO() {
		start();
	}

	private void start() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스 불러오라.
			System.out.println("Login 클래스 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("Login 클래스 로드 실패");
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

	public int idCheck(String in) {
		if (connect()) {
			int k = 0;
			try {
				String sql = "select count(*) id from login where id = ?";
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, in);
				
				rs = ppsm.executeQuery();
				if (rs.next()) {
					k = rs.getInt(1);
				}
				return k;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DB에 아이디가 없나?");
		}
		return 0;
	}

	
	public int login(String id, String pw) {
		if(connect()) {
			String sql = "select id from login where id = ? and pw = ?";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, id);
				ppsm.setString(2, pw);
				int checklog = ppsm.executeUpdate();
				return checklog;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return 0;
	}
	
	
	public int insertQ(String id, String pw) {
		if(connect()) {
			String sql = "insert into login values(?, ?)";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, id);
				ppsm.setString(2, pw);
				int k = ppsm.executeUpdate();
				return k;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}









