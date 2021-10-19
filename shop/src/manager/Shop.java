package manager;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import manager.UserManager;

public class Shop {
	public static Scanner sc = new Scanner(System.in);
	public static Random rn = new Random();
	private UserManager um = UserManager.instance;
	private ItemManager im = ItemManager.instance;
	private AdminManager am = AdminManager.instance;
	private FileManager fm = FileManager.instance;
	
	public void run() {
		boolean isRun = true;
		fm.load();
		while(isRun) {
			printMenu();
			isRun = selectMenu();
		}
		fm.save();
	}
	private void printMenu() {
		if(um.getLog() != -1) {
			String userId = um.getUsers().get(um.getLog()).getId();
			System.out.printf("%s님 로그인 중\n\n", userId);
			System.out.println("[1.가입] [2.탈퇴] [3.메뉴] [4.로그아웃]\n[100.관리자] [0.종료]");
		}
		else {
			System.out.println("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]\n[100.관리자] [0.종료]");
		}
	}
	private boolean selectMenu() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 가입
			if(um.getLog() == -1) {
				um.joinUser();
			}
			else {
				plzLogout();
			}
		}
		else if(Integer.parseInt(sel) == 2) { // 탈퇴
			if(um.getLog() != -1) {
				um.withdrawal();
			}
			else {
				plzLogin();
			}
		}
		else if(Integer.parseInt(sel) == 3) { // 로그인
			if(um.getLog() == -1) {
				if(um.login()) {
					boolean loginRun = true;
					while(loginRun) {
						printLoginMenu();
						loginRun = selLoginMenu();
					}
				}
			}
			else {
				boolean loginRun = true;
				while(loginRun) {
					printLoginMenu();
					loginRun = selLoginMenu();
				}
			}
		}
		else if(Integer.parseInt(sel) == 4) { // 로그아웃
			if(um.getLog() != -1) {
				um.logout();
			}
			else {
				plzLogin();
			}
		}
		else if(Integer.parseInt(sel) == 100) { // 관리자
			boolean loginRun = true;
			while(loginRun) {
				printAdminMenu();
				loginRun = selAdminMenu();
			}
		}
		else if(Integer.parseInt(sel) == 0) { // 종료
			return false;
		}
		return true;
	}
	
	private void plzLogout() {
		System.out.println("로그아웃 후 시도해 주십시오");
	}
	private void plzLogin() {
		System.out.println("로그인 후 시도해 주십시오");
	}
	
	private void printLoginMenu() {
		System.out.println("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
	}
	private void printCartMenu() {
		System.out.println("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");
	}
	
	private boolean selLoginMenu() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 쇼핑
			shopping();
		}
		else if(Integer.parseInt(sel) == 2) { // 장바구니 목록
			boolean isRun = true;
			while(isRun) {
				printCartMenu();
				isRun = selCartMenu();
			}
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	
	private void shopping() {
		im.printCate();
		int selCateNum = im.selCate();
		if(selCateNum != -1) {
			shoppingSelItem(selCateNum);
		}
		else {
			shopping();
		}
	}
	
	private void shoppingSelItem(int selCateNum) {
		ArrayList<Integer> selItemIdx = im.printItem(selCateNum);
		int selectItem = im.selItem(selItemIdx);
		if(selectItem != -1) {
			im.userCartListAdd(selectItem);
		}
		else {
			shoppingSelItem(selCateNum);
		}
	}
	
	private boolean selCartMenu() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 장바구니 출력
			if(!im.cartLogPrint()) {
				System.out.println("장바구니에 상품이 없습니다.");
			}
		}
		else if(Integer.parseInt(sel) == 2) { // 삭제
			if(im.cartLogPrint()) {
				String userId = um.getUsers().get(um.getLog()).getId();
				ArrayList<Integer> tempNums = im.selCartItem(userId);
				if(tempNums.size() != 0) {
					im.delCartItem(tempNums);
				}
			}
			else {
				System.out.println("장바구니에 상품이 없습니다.");
			}
		}
		else if(Integer.parseInt(sel) == 3) { // 구입
			if(im.cartLogPrint()) {
				buyCart();
			}
			else {
				System.out.println("장바구니에 상품이 없습니다.");
			}
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	
	private void buyCart() {
		String userId = um.getUsers().get(um.getLog()).getId();
		ArrayList<String> itemNames = im.userItemName(userId);
		ArrayList<String[]> userNC = im.overlapItemName(itemNames);
		int money = um.getUsers().get(um.getLog()).getMoney();
		int total = 0;
		for(int i =0; i < userNC.size(); i++) {
			String [] line = userNC.get(i);
			total += Integer.parseInt(line[2]);
		}
		printBill(userId, money, total);
	}
	
	private void printBill (String userId, int money, int total) {
		if(money >= total) {
			im.delUserCartItem(userId);
			um.getUsers().get(um.getLog()).setMoney(money - total);
			money = um.getUsers().get(um.getLog()).getMoney();
			System.out.printf("total : %d 원\n" , total);
			System.out.printf("구매후 잔고 : %d 원\n" , money);
			System.out.println("장바구니 목록 구매완료");
		}
		else {
			System.out.println("보유 잔액이 부족합니다");
		}
	}
	
	private void printAdminMenu() {
		System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리] [0.뒤로가기]");
	}
	
	private boolean selAdminMenu() {
		String sel = Shop.sc.next();
		if(Integer.parseInt(sel) == 1) { // 아이템 관리
			boolean isRun = true;
			while(isRun) {
				am.printIMS();
				isRun = am.selIMS();
			}
		}
		else if(Integer.parseInt(sel) == 2) { // 카테고리관리
			boolean isRun = true;
			while(isRun) {
				am.printCMS();
				isRun = am.selCMS();
			}
		}
		else if(Integer.parseInt(sel) == 3) { // 장바구니관리
			boolean isRun = true;
			while(isRun) {
				am.printCartMS();
				isRun = am.selCartMS();
			}
		}
		else if(Integer.parseInt(sel) == 4) { // 유저관리
			boolean isRun = true;
			while(isRun) {
				am.printUMS();
				isRun = am.selUMS();
			}
		}
		else if(Integer.parseInt(sel) == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
}