package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import models.Cart;
import models.Item;
import models.User;


public class FileManager {
	private UserManager um = UserManager.instance;
	private ItemManager im = ItemManager.instance;
	public static FileManager instance = new FileManager();
	private String fileUsersName = "users.txt";
	private String fileCateName = "cates.txt";
	private String fileItemName = "items.txt";
	private String fileCartName = "carts.txt";
	private File file;
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	// save
	public void save() {
		// um.users
		// id/pw/money
		String data = makeUsersData();
		try {
			this.file = new File(this.fileUsersName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		data = makeCateData();
		try {
			this.file = new File(this.fileCateName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		data = makeItemData();
		try {
			this.file = new File(this.fileItemName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		data = makeCartData();
		try {
			this.file = new File(this.fileCartName);
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// um.users
			// id/pw/money
	private String makeUsersData() {
		String data = "";
		for(int i = 0; i < um.getUserSize(); i++) {
			data += um.getUser(i).getId() + "/";
			data += um.getUser(i).getPw() + "/";
			data += um.getUser(i).getMoney() + "\n";
		}
		return data;
	}
	// im.category
			// name
	private String makeCateData() {
		String data = "";
		for(int i = 0; i < im.getCateSize(); i++) {
			data += im.getCate(i) + "\n";
		}
		return data;
	}
	// im.itemList
			// name/ price/ cate
	private String makeItemData() {
		String data = "";
		for(int i = 0; i < im.getItemSize(); i++) {
			data += im.getItem(i).getName() + "/";
			data += im.getItem(i).getPrice() + "/";
			data += im.getItem(i).getCategory() + "\n";
		}
		return data;
	}
	// im.cartList
	// id/name
	private String makeCartData() {
		String data = "";
		for(int i = 0; i < im.getCartSize(); i++) {
			data += im.getCart(i).getUserId() + "/";
			data += im.getCart(i).getItemName() + "\n";
		}
		return data;
	}
	
	// load
	public void load() {
		// um.users
		// id/pw/money
		try {
			this.file = new File(this.fileUsersName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			
			um.removeAllUser();
			String data = this.br.readLine();
			while(data != null) {
				String info[] = data.split("/");
				String id = info[0];
				String pw = info[1];
				int money = Integer.parseInt(info[2]);
				User user = new User(id,pw,money);
				um.creatUser(user);
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// im.category
		// name
		try {
			this.file = new File(this.fileCateName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			im.removeAllCate();
			String data = this.br.readLine();
			while(data != null) {
				im.creatCate(data);
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// im.itemList
		// name/ price/ cate
		try {
			this.file = new File(this.fileItemName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			im.removeAllItem();
			String data = this.br.readLine();
			while(data != null) {
				String info[] = data.split("/");
				String name = info[0];
				int price = Integer.parseInt(info[1]);
				String cate = info[2];
				im.creatItem(new Item(name, price, cate));
				data = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// im.cartList
		// id/name
		try {
			this.file = new File(this.fileCartName);
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);
			im.removeAllCart();
			String data = this.br.readLine();
			while(data != null) {
				String info[] = data.split("/");
				String userId = info[0];
				String itemName = info[1];
				im.creatCart(new Cart(userId, itemName));
				data = this.br.readLine();
			}
			
			this.fr.close();
			this.br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
