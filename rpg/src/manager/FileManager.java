package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import models.Item;
import models.Player;
import models.Unit;

public class FileManager {
	// commint
	public static FileManager Instance = new FileManager();
	GuildManager gm = GuildManager.Instance;
	ShopManager sm = ShopManager.Instance;
	InventoryManager im = InventoryManager.Instance;
	String filePlayerName = "Player";
	String fileGuildListName = "GuildList";
	String filePartyListName = "PartyList";
	String fileShopName = "Shop";
	String fileInvenName = "Inven";
	private File file;
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	public void save() {
		// Player
		// money
		String data = Player.money + "";
		try {
			this.file = new File(this.filePlayerName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
		}
		
		// guildList
		// name/level/hp/maxHp/att/def/exp/party/wea()/arm()/acc()
		//(kind, name, power, price)
		
		data = makeGLFile();
		
		try {
			this.file = new File(this.fileGuildListName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
		}
		
		// partyList
		// name/level/hp/maxHp/att/def/exp/party/wea()/arm()/acc()
		//(kind, name, power, price)
		data = makePLFile();
		
		try {
			this.file = new File(this.filePartyListName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
		}
		
		// Shop
		// itemList
		// kind, name, power, price
		data = makeShopFile();
		
		try {
			this.file = new File(this.fileShopName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
		}
		
		// Inven
		// itemList
		// kind, name, power, price
		data = makeInvenFile();
		
		try {
			this.file = new File(this.fileInvenName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
		}
	}
	
	private String makeGLFile() {
		String data = "";
		for(Unit unit : gm.getGuildList()) {
			data += unit.getName() + "/";
			data += unit.getLevel() + "/";
			data += unit.getHp() + "/";
			data += unit.getMaxHp() + "/";
			data += unit.getAtt() + "/";
			data += unit.getDef() + "/";
			data += unit.getExp() + "/";
			data += unit.getParty() + "/";
			if(unit.getWeapon() != null) {
				data += unit.getWeapon().getKind() + ",";
				data += unit.getWeapon().getName() + ",";
				data += unit.getWeapon().getPower() + ",";
				data += unit.getWeapon().getPrice() + "/";
			}
			else {
				data += null + "/";
			}
			if(unit.getArmor() != null) {
				data += unit.getArmor().getKind() + ",";
				data += unit.getArmor().getName() + ",";
				data += unit.getArmor().getPower() + ",";
				data += unit.getArmor().getPrice() + "/";
			}
			else {
				data += null + "/";
			}
			if(unit.getAcc() != null) {
				data += unit.getAcc().getKind() + ",";
				data += unit.getAcc().getName() + ",";
				data += unit.getAcc().getPower() + ",";
				data += unit.getAcc().getPrice();
			}
			else {
				data += null + "/";
			}
			data += "\n";
		}
		return data;
	}
	private String makePLFile() {
		String data = "";
		for(Unit unit : gm.getPartyList()) {
			if(unit != null) {
				data += unit.getName() + "/";
				data += unit.getLevel() + "/";
				data += unit.getHp() + "/";
				data += unit.getMaxHp() + "/";
				data += unit.getAtt() + "/";
				data += unit.getDef() + "/";
				data += unit.getExp() + "/";
				data += unit.getParty() + "/";
				if(unit.getWeapon() != null) {
					data += unit.getWeapon().getKind() + ",";
					data += unit.getWeapon().getName() + ",";
					data += unit.getWeapon().getPower() + ",";
					data += unit.getWeapon().getPrice() + "/";
				}
				else {
					data += null + "/";
				}
				if(unit.getArmor() != null) {
					data += unit.getArmor().getKind() + ",";
					data += unit.getArmor().getName() + ",";
					data += unit.getArmor().getPower() + ",";
					data += unit.getArmor().getPrice() + "/";
				}
				else {
					data += null + "/";
				}
				if(unit.getAcc() != null) {
					data += unit.getAcc().getKind() + ",";
					data += unit.getAcc().getName() + ",";
					data += unit.getAcc().getPower() + ",";
					data += unit.getAcc().getPrice();
				}
				else {
					data += null + "/";
				}
				data += "\n";
			}
			else {
				data += null + "\n";
			}
		}
		return data;
	}
	private String makeShopFile() {
		String data = null;
		boolean check = true;
		for(Item item : sm.getItemList()) {
			if(check) {
				data = "";
				check = false;
			}
			data += item.getKind() + ",";
			data += item.getName() + ",";
			data += item.getPower() + ",";
			data += item.getPrice() + "\n";
		}
		return data;
	}
	private String makeInvenFile() {
		String data = null;
		boolean check = true;
		for(Item item : im.getItemList()) {
			if(check) {
				data = "";
				check = false;
			}
			data += item.getKind() + ",";
			data += item.getName() + ",";
			data += item.getPower() + ",";
			data += item.getPrice() + "\n";
		}
		return data;
	}
	public void load() {
		// Player
		// money
		try {
			this.file = new File(this.filePlayerName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			
			String data = this.br.readLine();
			while(data != null) {
				Player.money = Integer.parseInt(data);
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// guildList
		// name/level/hp/maxHp/att/def/exp/party/wea()/arm()/acc()
		//(kind, name, power, price)
		try {
			this.file = new File(this.fileGuildListName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			gm.getGuildList().removeAll(gm.getGuildList());
			String data = this.br.readLine();
			while(data != null) {
				String [] temp = data.split("/");
				String n = temp[0];
				int l = Integer.parseInt(temp[1]);
				int h = Integer.parseInt(temp[2]);
				int mH = Integer.parseInt(temp[3]);
				int a = Integer.parseInt(temp[4]);
				int d = Integer.parseInt(temp[5]);
				int e = Integer.parseInt(temp[6]);
				boolean p = Boolean.parseBoolean(temp[7]);
				Item w;
				Item ar;
				Item acc;
				if(temp[8].equals("null")) {
					w = null;
				}
				else {
					String temp2 [] = temp[8].split(",");
					int kind = Integer.parseInt(temp2[0]);
					String name = temp2[1];
					int power = Integer.parseInt(temp2[2]);
					int price = Integer.parseInt(temp2[3]);
					w = new Item(kind, name, power, price);
				}
				if(temp[9].equals("null")) {
					ar = null;
				}
				else {
					String temp2 [] = temp[9].split(",");
					int kind = Integer.parseInt(temp2[0]);
					String name = temp2[1];
					int power = Integer.parseInt(temp2[2]);
					int price = Integer.parseInt(temp2[3]);
					ar = new Item(kind, name, power, price);
				}
				
				if(temp[10].equals("null")) {
					acc = null;
				}
				else {
					String temp2 [] = temp[10].split(",");
					int kind = Integer.parseInt(temp2[0]);
					String name = temp2[1];
					int power = Integer.parseInt(temp2[2]);
					int price = Integer.parseInt(temp2[3]);
					acc = new Item(kind, name, power, price);
				}
				Unit unit = new Unit(n,l,h,mH,a, d, e , p, w, ar, acc);
				gm.getGuildList().add(unit);
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// partyList
		// name/level/hp/maxHp/att/def/exp/party/wea()/arm()/acc()
		//(kind, name, power, price)
		try {
			this.file = new File(this.filePartyListName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			gm.resetPartyList();
			String data = this.br.readLine();
			int idx = 0;
			while(data != null) {
				String [] temp = data.split("/");
				String n = temp[0];
				int l = Integer.parseInt(temp[1]);
				int h = Integer.parseInt(temp[2]);
				int mH = Integer.parseInt(temp[3]);
				int a = Integer.parseInt(temp[4]);
				int d = Integer.parseInt(temp[5]);
				int e = Integer.parseInt(temp[6]);
				boolean p = Boolean.parseBoolean(temp[7]);
				Item w;
				Item ar;
				Item acc;
				if(temp[8].equals("null")) {
					w = null;
				}
				else {
					String temp2 [] = temp[8].split(",");
					int kind = Integer.parseInt(temp2[0]);
					String name = temp2[1];
					int power = Integer.parseInt(temp2[2]);
					int price = Integer.parseInt(temp2[3]);
					w = new Item(kind, name, power, price);
				}
				
				if(temp[9].equals("null")) {
					ar = null;
				}
				else {
					String temp2 [] = temp[9].split(",");
					int kind = Integer.parseInt(temp2[0]);
					String name = temp2[1];
					int power = Integer.parseInt(temp2[2]);
					int price = Integer.parseInt(temp2[3]);
					ar = new Item(kind, name, power, price);
				}
				
				if(temp[10].equals("null")) {
					acc = null;
				}
				else {
					String temp2 [] = temp[10].split(",");
					int kind = Integer.parseInt(temp2[0]);
					String name = temp2[1];
					int power = Integer.parseInt(temp2[2]);
					int price = Integer.parseInt(temp2[3]);
					acc = new Item(kind, name, power, price);
				}
				
				Unit unit = new Unit(n,l,h,mH,a, d, e , p, w, ar, acc);
				gm.setPartyList(idx, unit);
				idx++;
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// Shop
		// itemList
		// kind, name, power, price
		try {
			this.file = new File(this.fileShopName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			sm.getItemList().removeAll(sm.getItemList());
			String data = this.br.readLine();
			while(data != null) {
				String temp [] = data.split(",");
				int kind = Integer.parseInt(temp[0]);
				String name = temp[1];
				int power = Integer.parseInt(temp[2]);
				int price = Integer.parseInt(temp[3]);
				sm.getItemList().add(new Item (kind, name,power,price));
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		// Inven
		// itemList
		// kind, name, power, price
		
		try {
			this.file = new File(this.fileInvenName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			im.getItemList().removeAll(im.getItemList());
			
			String data = this.br.readLine();
			while(data != null) {
				String temp [] = data.split(",");
				int kind = Integer.parseInt(temp[0]);
				String name = temp[1];
				int power = Integer.parseInt(temp[2]);
				int price = Integer.parseInt(temp[3]);
				im.getItemList().add(new Item (kind, name,power,price));
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
