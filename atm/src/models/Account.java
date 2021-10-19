package models;

public class Account {
	private int acc;
	private int money;
	public Account() {}
	
	public Account(int acc, int money) {
		this.acc = acc;
		this.money = money;
	}
	
	public int getAcc() {
		return this.acc;
	}
	
	public int getMoney() {
		return this.money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
}
