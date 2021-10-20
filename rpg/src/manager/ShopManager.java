package manager;

import java.util.ArrayList;

import models.Item;
import models.Player;

public class ShopManager {
	// commint
	public static ShopManager Instance = new ShopManager();
	private ArrayList<Item> itemList = new ArrayList<>();
	InventoryManager im = InventoryManager.Instance;
	
	// 무기/방어구/엑세서리/뒤로가기
	private ShopManager() {
		init();
	}

	private void init() {
		int kind = Item.WEAPON;
		String name = "죽검";
		int power = 5;
		int price = 1000;
		Item temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
		
		kind = Item.WEAPON;
		name = "한손검";
		power = 7;
		price = 2000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);

		kind = Item.WEAPON;
		name = "두손검";
		power = 9;
		price = 3000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
	
		kind = Item.ARMOR;
		name = "천 방어구";
		power = 5;
		price = 1000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);

		kind = Item.ARMOR;
		name = "가죽 방어구";
		power = 7;
		price = 2000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
		
		kind = Item.ARMOR;
		name = "강철 방어구";
		power = 9;
		price = 3000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
		
		kind = Item.ACC;
		name = "은반지";
		power = 5;
		price = 1000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
		
		kind = Item.ACC;
		name = "금목걸이";
		power = 7;
		price = 2000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
		
		kind = Item.ACC;
		name = "사파이어 귀걸이";
		power = 9;
		price = 3000;
		temp = new Item(kind, name,power,price);
		this.itemList.add(temp);
	}
	
	public void printShopMenu() {
		System.out.println("=============== [상점] ================");
		String shopMenu = "무기/갑옷/엑세서리/뒤로가기";
		String [] temp = shopMenu.split("/");
		for(int i = 0 ; i < temp.length; i++) {
			if(i != temp.length -1) {
				System.out.printf("[%d. %s]\n",i+1,temp[i]);
			}
			else {
				System.out.printf("[%d. %s]\n",0,temp[i]);
			}
		}
	}
	
	
	private int [] cntItem() {
		int [] items = new int [3]; // 무기/방어구/악세
		for(int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getKind() == Item.WEAPON) {
				items[0] ++;
			}
			if(this.itemList.get(i).getKind() == Item.ARMOR) {
				items[1] ++;
			}
			if(this.itemList.get(i).getKind() == Item.ACC) {
				items[2] ++;
			}
		}
		
		return items;
	}
	
	private int [] printWeapon() {
		int [] items = cntItem();
		int [] idx = new int[items[0]];
		int num = 1;
		for(int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getKind() == Item.WEAPON) {
				String name = this.itemList.get(i).getName();
				int power = this.itemList.get(i).getPower();
				int price = this.itemList.get(i).getPrice();
				System.out.printf("[%d] [%s] [공격력 : %d] [%d원]\n",num,name,power,price);
				idx[num - 1] = i; 
				num++;
			}
		}
		System.out.println("[0] [뒤로가기]");
		return idx;
	}
	private int [] printArmor() {
		int [] items = cntItem();
		int [] idx = new int[items[1]];
		int num = 1;
		for(int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getKind() == Item.ARMOR) {
				String name = this.itemList.get(i).getName();
				int power = this.itemList.get(i).getPower();
				int price = this.itemList.get(i).getPrice();
				System.out.printf("[%d] [%s] [방어력 : %d] [%d원]\n",num,name,power,price);
				idx[num - 1] = i; 
				num++;
			}
		}
		System.out.println("[0] [뒤로가기]");
		return idx;
	}
	private int [] printAcc() {
		int [] items = cntItem();
		int [] idx = new int[items[0]];
		int num = 1;
		for(int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getKind() == Item.ACC) {
				String name = this.itemList.get(i).getName();
				int power = this.itemList.get(i).getPower();
				int price = this.itemList.get(i).getPrice();
				System.out.printf("[%d] [%s] [방어력 : %d] [%d원]\n",num,name,power,price);
				idx[num - 1] = i; 
				num++;
			}
		}
		System.out.println("[0] [뒤로가기]");
		return idx;
	}
	
	private int selItem(int [] itemIdx) {
		int idx = -2;
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString) - 1;
		
		if(0 <= sel && sel < itemIdx.length) {
			idx = itemIdx[sel];
		}
		else if(sel == -1) {
			idx = -1;
		}
		return idx;
	}
	
	private boolean selBuyItem(int selIdx) {
		if(selIdx == -1) {
			return false;
		}
		else if(selIdx == -2) {
			Game.printWrongInput();
		}
		else {
			if(Player.money >= this.itemList.get(selIdx).getPrice()) {
				buyItem(this.itemList.get(selIdx));
			}
			else {
				System.out.println("골드가 부족합니다.");
			}
		}
		
		return true;
	}
	
	private void buyItem(Item item) {
		im.getItemList().add(item);
		String name = item.getName();
		int price = item.getPrice();
		Player.money -= price;
		System.out.printf("%s %d원 구매 완료\n", name, price);
		Game.printGold();
	}
	
	public boolean selectShopMenu() {
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString);
		if(sel == 1) { // 무기
			boolean isRun = true;
			while(isRun) {
				int weIdx[] = printWeapon();
				int selIdx = selItem(weIdx);
				isRun = selBuyItem(selIdx);
			}
		}
		else if (sel == 2) { // 방어구
			boolean isRun = true;
			while(isRun) {
			int amIdx [] = printArmor();
			int selIdx = selItem(amIdx);
			isRun = selBuyItem(selIdx);
			}
		}
		else if (sel == 3) { // 엑세서리
			boolean isRun = true;
			while(isRun) {
			int accIdx [] = printAcc();
			int selIdx = selItem(accIdx);
			isRun = selBuyItem(selIdx);
			}
		}
		else if (sel == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	
	
	public ArrayList<Item> getItemList(){
		return this.itemList;
	}
	
}
