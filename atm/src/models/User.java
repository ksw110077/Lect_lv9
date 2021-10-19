package models;

import java.util.ArrayList;

public class User {
	private String id;
	private String pw;
	private String name;
	private int accCnt;
	public ArrayList<Account> acc = new ArrayList<>();
	// 보유 계좌의 객체주소 배열
	
	public User(String name, String id, String pw) {
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	
	public String getId() {
		return this.id;
	}
	public String getPw() {
		return this.pw;
	}
	public String getName() {
		return this.name;
	}
}
