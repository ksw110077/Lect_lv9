package models;

import manager.Shop;

public class User {
	private String id;
	private String pw;
	private int money;
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
		this.money = Shop.rn.nextInt(999000) + 1000; // 회원가입 랜덤 축하금
	}
	public User(String id, String pw, int money) {
		this.id = id;
		this.pw = pw;
		this.money = money;
	}
	
	private int randomMoney() {
		return 0;
	}
	
	public String getId() {
		return this.id;
	}
	public String getPw() {
		return this.pw;
	}
	public int getMoney() {
		return this.money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
}
