package DB;

import java.io.Serializable;

public class DTO implements Serializable {

	String no;
	String title;
	String name;
	String genre;
	String album;
	
	String id;
	String pw;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
	//-------------------------------------
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	//-------------------------------------
	
	public String[] saveSong() {
		String turnData[] = new String[5];
		turnData[0] = this.no;
		turnData[1] = this.title;
		turnData[2] = this.name;
		turnData[3] = this.genre;
		turnData[4] = this.album;
		
		return turnData;
	}
	
	public String[] saveJoin() {
		String turnData2[] = new String[2];
		turnData2[0] = this.id;
		turnData2[1] = this.pw;
		
		return turnData2;
	}
	
	public String[] cyclist() {
		String turnData3[] = new String[4];
		turnData3[0] = this.id;
		turnData3[1] = this.title;
		turnData3[2] = this.name;
		turnData3[3] = this.genre;
		
		return turnData3;
	}
	
}
