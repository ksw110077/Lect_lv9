package manager;

import java.util.ArrayList;

import models.Item;
import models.Player;
import models.Unit;

public class InventoryManager {
	// 착용/해제/판매/뒤로가기
	public static InventoryManager Instance = new InventoryManager();
	GuildManager gm = GuildManager.Instance;
	private ArrayList<Item> itemList = new ArrayList<>();
	
	private void printInvenItems() {
		if(this.itemList.size() != 0) {
			System.out.println("=============== [인벤토리 아이템] ================");
			int num = 1;
			for(int i = 0; i < this.itemList.size(); i++) {
				if(this.itemList.get(i).getKind() == Item.WEAPON) {
					String name = this.itemList.get(i).getName();
					int power = this.itemList.get(i).getPower();
					int price = this.itemList.get(i).getPrice();
					System.out.printf("[%d] [%s] [공격력 : %d] [%d원]\n",num,name,power,price);
					num++;
				}
				else if(this.itemList.get(i).getKind() == Item.ARMOR) {
					String name = this.itemList.get(i).getName();
					int power = this.itemList.get(i).getPower();
					int price = this.itemList.get(i).getPrice();
					System.out.printf("[%d] [%s] [방어력 : %d] [%d원]\n",num,name,power,price);
					num++;
				}
				else if(this.itemList.get(i).getKind() == Item.ACC) {
					String name = this.itemList.get(i).getName();
					int power = this.itemList.get(i).getPower();
					int price = this.itemList.get(i).getPrice();
					System.out.printf("[%d] [%s] [방어력 : %d] [%d원]\n",num,name,power,price);
					num++;
				}
			}
			System.out.println("======================================");
		}
	}
	
	private int selInvenItem() {
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString) - 1;
		if(0 <= sel && sel < this.itemList.size()) {
			return sel;
		}
		else {
			Game.printWrongInput();
			return -1;
		}
	}
	
	
	private boolean printUnitItems(Unit unit) {
		boolean check = false;
		gm.printSingleUnit(unit);
		if(unit.getWeapon() != null) {
			String name = unit.getWeapon().getName();
			int power = unit.getWeapon().getPower();
			System.out.printf("[무기 : %s] [공격력 : %d]\n", name, power);
			check = true;
		}
		else {
			System.out.println("[무기 : 없음]");
		}
		if(unit.getArmor() != null) {
			String name = unit.getArmor().getName();
			int power = unit.getArmor().getPower();
			System.out.printf("[방어구 : %s] [방어력 : %d]\n", name, power);
			check = true;
		}
		else {
			System.out.println("[방어구 : 없음]");
		}
		if(unit.getAcc() != null) {
			String name = unit.getAcc().getName();
			int power = unit.getAcc().getPower();
			System.out.printf("[악세서리 : %s] [방어력 : %d]\n", name, power);
			check = true;
		}
		else {
			System.out.println("[악세서리 : 없음]");
		}
		System.out.println("======================================");
		
		return check;
	}
	
	public void printInvenMenu() {
		System.out.println("=============== [인벤토리] ================");
		String shopMenu = "착용/해제/판매/뒤로가기";
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
	
	private void printWear() {
		System.out.println("착용 완료");
	}
	
	private int wearItemPower(Item item) {
		return item.getPower();
	}
	
	private void wearNNWea(int selIdx,int selItemIdx,Item temp) {
		Item temp2; // 해제할 아이템
		temp2 = gm.getGuildList().get(selIdx).getWeapon();
		
		int unitAtt = gm.getGuildList().get(selIdx).getAtt();
		int addAtt = wearItemPower(temp);
		int minusAtt = remoItemPower(temp2);
		
		unitAtt -= minusAtt;
		unitAtt += addAtt;

		gm.getGuildList().get(selIdx).setWeapon(temp);
		gm.getGuildList().get(selIdx).setAtt(unitAtt);

		this.itemList.remove(selItemIdx);
		this.itemList.add(temp2);
	}
	private void wearNWea(int selIdx,int selItemIdx,Item temp) {
		
		int unitAtt = gm.getGuildList().get(selIdx).getAtt();
		int addAtt = wearItemPower(temp);
		
		unitAtt += addAtt;
		
		gm.getGuildList().get(selIdx).setWeapon(temp);
		gm.getGuildList().get(selIdx).setAtt(unitAtt);
		
		this.itemList.remove(selItemIdx);
	}
	private void wearNNArm(int selIdx,int selItemIdx,Item temp) {
		Item temp2; // 해제할 아이템
		temp2 = gm.getGuildList().get(selIdx).getArmor();

		int unitDef = gm.getGuildList().get(selIdx).getDef();
		int addDef = wearItemPower(temp);
		int minusDef = remoItemPower(temp2);
		
		unitDef -= minusDef;
		unitDef += addDef;
		
		gm.getGuildList().get(selIdx).setArmor(temp);
		gm.getGuildList().get(selIdx).setDef(unitDef);

		this.itemList.remove(selItemIdx);
		this.itemList.add(temp2);
	}
	private void wearNArm(int selIdx,int selItemIdx,Item temp) {
		int unitDef = gm.getGuildList().get(selIdx).getDef();
		int addDef = wearItemPower(temp);

		unitDef += addDef;

		gm.getGuildList().get(selIdx).setArmor(temp);
		gm.getGuildList().get(selIdx).setDef(unitDef);

		this.itemList.remove(selItemIdx);
	}
	private void wearNNAcc(int selIdx,int selItemIdx,Item temp) {
		Item temp2; // 해제할 아이템
		temp2 = gm.getGuildList().get(selIdx).getAcc();
		
		int unitDef = gm.getGuildList().get(selIdx).getDef();
		int addDef = wearItemPower(temp);
		int minusDef = remoItemPower(temp2);

		unitDef -= minusDef;
		unitDef += addDef;

		gm.getGuildList().get(selIdx).setAcc(temp);
		gm.getGuildList().get(selIdx).setDef(unitDef);

		this.itemList.remove(selItemIdx);
		this.itemList.add(temp2);
	}
	private void wearNAcc(int selIdx,int selItemIdx,Item temp) {
		int unitDef = gm.getGuildList().get(selIdx).getDef();
		int addDef = wearItemPower(temp);

		unitDef += addDef;
		
		gm.getGuildList().get(selIdx).setAcc(temp);
		gm.getGuildList().get(selIdx).setDef(unitDef);
		
		this.itemList.remove(selItemIdx);
	}
	
	private void checkItemKind(int selIdx, int selItemIdx, Item temp) {
		if(temp.getKind() == Item.WEAPON) {
			if(gm.getGuildList().get(selIdx).getWeapon() != null) {
				wearNNWea(selIdx,selItemIdx,temp);
			}
			else {
				wearNWea(selIdx, selItemIdx, temp);
			}
		}
		else if(temp.getKind() == Item.ARMOR) {
			if(gm.getGuildList().get(selIdx).getArmor() != null) {
				wearNNArm(selIdx,selItemIdx,temp);
			}
			else {
				wearNArm(selIdx, selItemIdx, temp);
			}
		}
		else if(temp.getKind() == Item.ACC) {
			if(gm.getGuildList().get(selIdx).getAcc() != null) {
				wearNNAcc(selIdx,selItemIdx,temp);
			}
			else {
				wearNAcc(selIdx, selItemIdx, temp);
			}
		}
	}
	
	private void wear() {
		if(this.itemList.size() != 0) {
			gm.printGuildUnit();
			int selIdx = gm.selGulidUnit();
			if(selIdx != -1) {
				printUnitItems(gm.getGuildList().get(selIdx));
				printInvenItems(); // 인벤토리 아이템 출력
				int selItemIdx = selInvenItem(); // 선택
				if(selItemIdx != -1) { // 착용 or 교체
					Item temp = this.itemList.get(selItemIdx); // 착용할 아이템
					checkItemKind(selIdx, selItemIdx, temp);
					Game.delayPrint();
					printUnitItems(gm.getGuildList().get(selIdx));
					printInvenItems(); // 인벤토리 아이템 출력
					printWear();
				}
			}
		}
		else {
			System.out.println("\n현재 착용 가능한 장비가 없습니다.");
			System.out.println("구매 또는 해제를 선행해주세요.\n");
		}
	}
	
	private int selRemovalItem() {
		System.out.println("[1.무기][2.방어구][3.엑세서리]");
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString);
		if(1 <= sel && sel <= 3) {
			return sel;
		}
		else {
			Game.printWrongInput();
		}
		
		return -1;
	}
	
	private void removalWea(int selIdx, Unit unit) {
		int unitPower = unit.getAtt();
		int minusPower = remoItemPower(unit.getWeapon());
		unitPower -= minusPower;
		Item temp = gm.getGuildList().get(selIdx).getWeapon();
		gm.getGuildList().get(selIdx).setAtt(unitPower);
		gm.getGuildList().get(selIdx).setWeapon(null);
		this.itemList.add(temp);
	}
	private void removalArmor(int selIdx, Unit unit) {
		int unitPower = unit.getDef();
		int minusPower = remoItemPower(unit.getArmor());
		unitPower -= minusPower;
		Item temp = gm.getGuildList().get(selIdx).getArmor();
		gm.getGuildList().get(selIdx).setDef(unitPower);
		gm.getGuildList().get(selIdx).setWeapon(null);
		this.itemList.add(temp);
	}
	private void removalAcc(int selIdx, Unit unit) {
		int unitPower = unit.getDef();
		int minusPower = remoItemPower(unit.getAcc());
		unitPower -= minusPower;
		Item temp = gm.getGuildList().get(selIdx).getAcc();
		gm.getGuildList().get(selIdx).setDef(unitPower);
		gm.getGuildList().get(selIdx).setWeapon(null);
		this.itemList.add(temp);
	}
	
	private boolean remoCheckNWea(Unit unit) {
		boolean check = false;
		if(unit.getWeapon() == null) {
			check = true;
		}
		return check;
	}
	private boolean remoCheckNArm(Unit unit) {
		boolean check = false;
		if(unit.getArmor() == null) {
			check = true;
		}
		return check;
	}
	private boolean remoCheckNAcc(Unit unit) {
		boolean check = false;
		if(unit.getAcc() == null) {
			check = true;
		}
		return check;
	}
	
	private int remoItemPower(Item item) {
		return item.getPower();
	}
	
	private void checkUnitItem(int itemKind, int selIdx) {
		Unit unit = gm.getGuildList().get(selIdx);
		if(itemKind == Item.WEAPON) {
			if(!remoCheckNWea(unit)) {
				removalWea(selIdx, unit);
			}
			else {
				printNullRemovalItem();
			}
		}
		else if(itemKind == Item.ARMOR) {
			if(!remoCheckNArm(unit)) {
				removalArmor(selIdx, unit);
			}
			else {
				printNullRemovalItem();
			}
		}
		else if(itemKind == Item.ACC) {
			if(!remoCheckNAcc(unit)) {
				removalAcc(selIdx, unit) ;
			}
			else {
				printNullRemovalItem();
			}
		}
	}
	
	private void printNullRemovalItem() {
		System.out.println("해제할 아이템이 없습니다.");
	}
	
	private void Removal() {
		gm.printGuildUnit();
		int selIdx = gm.selGulidUnit();
		if(selIdx != -1) {
			if(printUnitItems(gm.getGuildList().get(selIdx))) {
				int selItem = selRemovalItem();
				if(selItem != -1) {
					checkUnitItem(selItem, selIdx);
					Game.delayPrint();
					printUnitItems(gm.getGuildList().get(selIdx));
					printInvenItems(); // 인벤토리 아이템 출력
				}
			}
			else {
				printNullRemovalItem();
			}
		}
	}
	
	private void sale() {
		if(this.itemList.size() != 0) {
			printInvenItems(); // 인벤토리 아이템 출력
			int selItemIdx = selInvenItem(); // 선택
			if(selItemIdx != -1) {
				int gold = this.itemList.get(selItemIdx).getPrice();
				Player.money += gold;
				this.itemList.remove(selItemIdx);
				Game.delayPrint();
				printInvenItems(); // 인벤토리 아이템 출력
				System.out.printf("%d 골드 판매 완료\n",gold);
				Game.printGold();
			}
		}
		else {
			System.out.println("\n판매 가능한 장비가 없습니다.");
			System.out.println("구매 또는 해제를 선행해주세요.\n");
		}
	}
	
	public boolean selectInvenMenu() {
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString);
		if(sel == 1) { // 착용 Wear
			wear();
		}
		else if (sel == 2) { // 해제 Removal
			Removal();
		}
		else if (sel == 3) { // 판매 sale
			sale();
		}
		else if (sel == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	public ArrayList<Item> getItemList() {
		return this.itemList;
	}
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
}