package models;

public class Cart {
	private String userId;
	private String itemName;
	
	public Cart(String id, String iN) {
		this.userId = id;
		this.itemName = iN;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	public String getUserId() {
		return this.userId;
	}
	
	public void print() {
		System.out.printf("[%s] item : %s\n", this.userId,this.itemName);
	}
}
