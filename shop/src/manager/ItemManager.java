package manager;

import java.util.ArrayList;

import models.Cart;
import models.Item;
import models.User;

public class ItemManager {
	public static ItemManager instance = new ItemManager();
	private UserManager um = UserManager.instance;
	private ArrayList<String> category = new ArrayList<>();
	private ArrayList<Item> itemList = new ArrayList<>(); // 전체 아이템리스트
	private ArrayList<Cart> cartList = new ArrayList<>(); // 전체 장바구니
	private ItemManager() {
//		init();
	}
	private void init() {
		this.category.add("과자");
		this.category.add("생선");
		this.category.add("육류");
		this.category.add("음료수");
		Item temp = new Item("새우깡", 1000, this.category.get(0));
		this.itemList.add(temp);
		temp = new Item("고등어", 2000, this.category.get(1));
		this.itemList.add(temp);
		temp = new Item("칸쵸", 3600, this.category.get(0));
		this.itemList.add(temp);
		temp = new Item("소고기", 6500, this.category.get(2));
		this.itemList.add(temp);
		temp = new Item("콜라", 500, this.category.get(3));
		this.itemList.add(temp);
		temp = new Item("새우", 1800, this.category.get(1));
		this.itemList.add(temp);
	}
	
	public int getCateSize() {
		return this.category.size();
	}
	
	public String getCate(int idx) {
		return this.category.get(idx);
	}
	
	public int getItemSize() {
		return this.itemList.size();
	}
	public Item getItem(int idx) {
		return this.itemList.get(idx);
	}
	public int getCartSize() {
		return this.cartList.size();
	}
	public Cart getCart(int idx) {
		return this.cartList.get(idx);
	}
	public String getCateName(int idx) {
		return this.category.get(idx);
	}

	public void removeAllCate() {
		this.category.removeAll(this.category);
	}
	public void removeAllItem() {
		this.itemList.removeAll(this.itemList);
	}
	
	public void creatCate(String name) {
		this.category.add(name);
	}
	public void creatItem(Item item) {
		this.itemList.add(item);
	}
	public void creatCart(Cart cart) {
		this.cartList.add(cart);
	}
	
	
	public void printCate() {
		System.out.println("ㅡㅡㅡㅡ[카테고리]ㅡㅡㅡㅡ");
		for (int i = 0; i < this.category.size(); i++) {
			System.out.printf("[%d] %s\n" , i+1 ,this.category.get(i));
		}
	}
	
	public int selCate() {
		int sel = Integer.parseInt(Shop.sc.next()) - 1;
		if(0 <= sel && sel < this.category.size()) {
			return sel;
		}
		else {
			return -1;
		}
	}
	
	private boolean overlapCate(String name) {
		for(int i = 0; i < this.category.size(); i++) {
			if(this.category.get(i).equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void addCate() {
		System.out.print("추가할 카테고리 이름 : ");
		String name = Shop.sc.next();
		if(!overlapCate(name)) {
			this.category.add(name);
			System.out.println(name + "추가 완료");
		}
		else {
			System.out.println("중복된 카테고리명");
		}
	}
	public void delCate() {
		System.out.print("추가할 카테고리 이름 : ");
		String name = Shop.sc.next();
		if(overlapCate(name)) {
			this.category.remove(name);
			System.out.println(name + "삭제 완료");
		}
		else {
			System.out.println("없는 카테고리명");
		}
	}
	
	public ArrayList<Integer> printItem(int sel) {
		ArrayList<Integer> selItemIdx = new ArrayList<>();
		for(int i = 0; i < this.itemList.size(); i++) {
			if(this.itemList.get(i).getCategory().equals(this.category.get(sel))) {
				selItemIdx.add(i);
				String name = this.itemList.get(i).getName();
				int price = this.itemList.get(i).getPrice();
				System.out.printf("[%d][%s][%d원]\n",selItemIdx.size(), name,price);
			}
		}
		return selItemIdx;
	}
	
	public int selItem(ArrayList<Integer> selItemIdx) {
		int sel = Integer.parseInt(Shop.sc.next()) - 1;
		if(0 <= sel && sel < selItemIdx.size()) {
			return selItemIdx.get(sel);
		}
		else {
			return -1;
		}
	}
	
	public void userCartListAdd(int sel) {
		int log = um.getLog();
		String id = um.getUsers().get(log).getId();
		String name = this.itemList.get(sel).getName();
		this.cartList.add(new Cart(id, name));
		System.out.printf("장바구니에 %s 상품이 추가 되었습니다\n", name);
	}
	
	public void addItem(Item a) {
		this.itemList.add(a);
	}
	public String removeItem(int a) {
		String name = this.itemList.get(a).getName();
		this.itemList.remove(a);
		return name;
	}
	
	public ArrayList<String[]> overlapItemName(ArrayList<String> itemName) {
		ArrayList<String> olName = new ArrayList<>();
		ArrayList<Integer> olCnt = new ArrayList<>();
		for(String item : itemName) {
			int check = -1;
			for(int i = 0; i < olName.size(); i++) {
				if(olName.get(i).equals(item)) {
					check = i;
				}
			}
			if(check == -1){
				olName.add(item);
				olCnt.add(1);
			}
			else {
				olCnt.set(check, olCnt.get(check) + 1);
			}
		}
		
		ArrayList<String[]> nameCnt = new ArrayList<>();
		for(int i = 0; i < olName.size(); i++) {
			String [] nC = new String [3];
			String temName = olName.get(i);
			String temPrice = "";
			for(Item items : this.itemList) {
				if(temName.equals(items.getName())) {
					temPrice += (items.getPrice() * olCnt.get(i));
				}
			}
			nC[0] = temName;
			nC[1] = String.valueOf(olCnt.get(i));
			nC[2] = temPrice;
			nameCnt.add(nC);
		}
		return nameCnt;
	}

	public void AllItemPrint() {
		for(int i = 0; i < this.category.size(); i++) {
			System.out.printf("ㅡㅡㅡㅡ %s ㅡㅡㅡㅡ\n",  this.category.get(i));
			for(Item item : this.itemList) {
				if(item.getCategory().equals(this.category.get(i))) {
					System.out.printf("[%s] : %s원\n",item.getName(), item.getPrice());
				}
			}
		}
	}
	
	public void AllCartPrint() {
		for(User user : um.getUsers()) {
			String userId = user.getId();
			userCartPrint(userId);
		}
	}
	
	public void removeAllCart() {
		this.cartList.removeAll(cartList);
	}
	public boolean cartLogPrint() {
		int log = um.getLog();
		String userId = um.getUsers().get(log).getId();
		return userCartPrint(userId);
	}
	
	public ArrayList<String> userItemName(String userId) {
		ArrayList<String> itemName = new ArrayList<>();
		for(Cart cart : this.cartList) {
			if(cart.getUserId().equals(userId)) {
				itemName.add(cart.getItemName());
			}
		}
		return itemName;
	}
	
	public ArrayList<Integer> selCartItem(String userId) {
		ArrayList<String> itemName = userItemName(userId);
		ArrayList<String[]> userNC = overlapItemName(itemName);
		System.out.print("아이템 선택 : ");
		int tempNum = Shop.sc.nextInt() - 1;
		if(0 <= tempNum && tempNum < userNC.size()) {
			String [] userItemName = userNC.get(tempNum);
			String selItem = userItemName[0];
			ArrayList<Integer> selItemIdx = new ArrayList<>();
			for(int i = 0; i < this.cartList.size(); i++) {
				String cartListUser = this.cartList.get(i).getUserId();
				String cartListItem = this.cartList.get(i).getItemName();
				if(cartListUser.equals(userId) && cartListItem.equals(selItem)) {
					selItemIdx.add(i);
				}
			}
			return selItemIdx;
		}
		else {
			 ArrayList<Integer> abc = new ArrayList<Integer>();
			return abc;
		}
	}
	
	public void delUserCartItem(String id) {
		for(int i = 0; i < this.cartList.size(); i++) {
			if(this.cartList.get(i).getUserId().equals(id)){
				this.cartList.remove(i);
				i--;
			}
		}
	}
	public void delCartItem(ArrayList<Integer> delItemIdx) {
		int cnt = 0;
		String itemName = this.cartList.get(delItemIdx.get(0)).getItemName();
		for(int i = 0; i < delItemIdx.size(); i++) {
			this.cartList.remove(delItemIdx.get(i) - cnt);
			cnt++;
		}
		System.out.println(itemName + " 삭제 완료");
	}
	
	public String getCartItemName(int idx) {
		return this.cartList.get(idx).getItemName();
	}
	public int getCartItemPrice(String name) {
		for(int i = 0; i < this.itemList.size(); i++) {
			if(name.equals(this.itemList.get(i).getName())) {
				return this.itemList.get(i).getPrice();
			}
		}
		
		return -1;
	}
	
	private boolean userCartPrint(String userId) {
		ArrayList<String> itemName = userItemName(userId);
		if(itemName.size() != 0) {
			System.out.printf("ㅡㅡㅡㅡ%s회원ㅡㅡㅡㅡ\n", userId);
			ArrayList<String[]> userNC = overlapItemName(itemName);
			int i = 1;
			for(String[] NC : userNC) {
				System.out.printf("[%d] %s : %s 개 = %s원\n",i,NC[0],NC[1],NC[2]);
				i++;
			}
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			return true;
		}
		return false;
	}
}
