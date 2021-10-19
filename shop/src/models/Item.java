package models;

public class Item {
	private String name;
	private int price;
	private String category;

	public Item(String name, int pr, String cate) {
		this.name = name;
		this.price = pr;
		this.category = cate;
	}
	void print() {
		System.out.printf("[%s][%d][%s]",this.name,this.price, this.category);
	}
	
	public String getName() {
		return this.name;
	}
	public String getCategory() {
		return this.category;
	}
	public int getPrice() {
		return this.price;
	}
}
