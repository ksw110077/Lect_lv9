package manager;

import java.util.ArrayList;

import models.Item;

public class AdminManager {
	public static AdminManager instance = new AdminManager();
	private UserManager um = UserManager.instance;
	private ItemManager im = ItemManager.instance;
	
	public void printIMS() {
		System.out.println("[1.전체아이템] [2.아이템추가] [3.아이템삭제] [0.뒤로가기]");
	}
	public boolean selIMS() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 전체아이템
			im.AllItemPrint();
		}
		else if(Integer.parseInt(sel) == 2) { // 아이템추가
			im.printCate();
			int selectCate = im.selCate();
			if(selectCate != -1) {
				addItem(selectCate);
			}
		}
		else if(Integer.parseInt(sel) == 3) { // 아이템삭제
			im.printCate();
			int selCateNum = im.selCate();
			if(selCateNum != -1) {
				removeItem(selCateNum);
			}
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	
	private void addItem(int selectCate) {
		System.out.print("아이템 이름 : ");
		String name = Shop.sc.next();
		System.out.print("아이템 가격 : ");
		int price = Integer.parseInt(Shop.sc.next());
		String category = im.getCateName(selectCate);
		im.addItem(new Item(name, price, category));
		System.out.println("아이템 추가 성공");
	}
	
	private void removeItem(int selCateNum) {
		ArrayList<Integer> selItemIdx = im.printItem(selCateNum);
		int selectItem = im.selItem(selItemIdx);
		if(selectItem != -1) {
			String itemName = im.removeItem(selectItem);
			System.out.printf("%s 삭제 완료\n", itemName);
		}
	}
	
	public void printCMS() {
		System.out.println("[1.전체카테고리] [2.카테고리추가] [3.카테고리삭제] [0.뒤로가기]");
	}
	
	public boolean selCMS() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 전체카테고리
			im.printCate();
		}
		else if(Integer.parseInt(sel) == 2) { // 카테고리추가
			im.addCate();
		}
		else if(Integer.parseInt(sel) == 3) { // 카테고리삭제
			im.delCate();
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	public void printCartMS() {
		System.out.println("[1.전체장바구니][2.장바구니비우기][0.뒤로가기]");
	}
	public boolean selCartMS() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 전체장바구니
			im.AllCartPrint();
		}
		else if(Integer.parseInt(sel) == 2) { // 장바구니비우기
			im.removeAllCart();
			System.out.println("전체 장바구니 삭제 완료");
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	public void printUMS() {
		System.out.println("[1.전체유저] [2.유저추가] [3.유저삭제] [0.뒤로가기]");
	}
	public boolean selUMS() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 전체유저
			um.printAllUser();
		}
		else if(Integer.parseInt(sel) == 2) { // 유저추가
			um.addUser();
		}
		else if(Integer.parseInt(sel) == 3) { // 유저삭제
			um.removeUser();
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
}
