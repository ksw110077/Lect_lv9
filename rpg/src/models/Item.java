package models;

public class Item {
	// commint
	public static final int WEAPON = 1;
	public static final int ARMOR = 2;
	public static final int ACC = 3;
	private int kind;
	private String name;
	private int power;
	private int price;
	
	public Item(int k , String n , int p , int pr) {
		setKind(k); setName(n); setPower(p); setPrice(pr);
	}

	public int getKind() {
		return this.kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return this.power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
