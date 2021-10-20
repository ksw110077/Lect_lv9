package manager;

import java.util.Random;
import java.util.Scanner;

import models.Player;

public class Game {
	public static Scanner sc = new Scanner(System.in);
	public static Random rn = new Random();
	GuildManager gm = GuildManager.Instance;
	ShopManager sm = ShopManager.Instance;
	InventoryManager im = InventoryManager.Instance;
	FileManager fm = FileManager.Instance;
	
	public void run() {
		// commint
		// 길드 GuildManager
		// 맴버변수
		// ㄴ 길드원 목록
		// ㄴ 파티원 목록
		// 길드원 목록
		// 길드원 고용 골드감소
		// 길드원 해고 골드 일부 반환
		// 길드원 정렬
		// ㄴ 파티시스템
		//		파티원 교체

		// 상점 ShopManager
		// 맴버변수
		// 아이템 리스트
		
		// 인벤토리 InventoryManager
		// 맴버변수
		// ㄴ 골드
		// ㄴ 소유 아이템을 리스트로
		// 		무기 , 방어구 , 반지
		
		
		// 파일 관리 FileManager 
		
		// 저장
		// 로드
		
		
		//----------------------------------------------
		// Unit
		// Player
		// Item
		boolean isRun = true;
		while(isRun) { // 게임 종료 전까지 실행
			printGameMenu();
			isRun = selectGameMenu();
		}
	}
	
	private void printGameMenu() {
		Game.printGold();
		System.out.println("=============== [메인메뉴] ================");
		String gameMenu = "길드/상점/인벤토리/저장/로드/종료";
		String [] temp = gameMenu.split("/");
		for(int i = 0 ; i < temp.length; i++) {
			if(i != temp.length -1) {
				System.out.printf("[%d. %s]\n",i+1,temp[i]);
			}
			else {
				System.out.printf("[%d. %s]\n",0,temp[i]);
			}
		}
	}
	
	private boolean selectGameMenu() {
		printInput();
		String selString = sc.next();
		int sel = Integer.parseInt(selString);
		if(sel == 1) { // 길드
			boolean isRun = true;
			while(isRun) {
				gm.printGuildMenu();
				isRun = gm.selectGuildMenu();
			}
		}
		else if (sel == 2) { // 상점
			boolean isRun = true;
			while(isRun) {
				sm.printShopMenu();
				isRun = sm.selectShopMenu();
			}
		}
		else if (sel == 3) { // 인벤토리
			boolean isRun = true;
			while(isRun) {
				im.printInvenMenu();
				isRun = im.selectInvenMenu();
			}
		}
		else if (sel == 4) { // 저장 
			fm.save();
			System.out.println("저장완료");
		}
		else if (sel == 5) { // 로드
			fm.load();
			System.out.println("로드완료");
		}
		else if (sel == 0) { // 종료
			System.out.println("GAME을 종료합니다");
			return false;
		}
		else {
			printWrongInput();
		}
		return true;
	}
	
	public static void printInput() {
		System.out.print("입력 : ");
	}
	
	public static void printWrongInput() {
		System.out.println("잘못된 입력입니다.");
	}
	
	public static void printGold() {
		System.out.println("=====================================");
		System.out.println("[골드 : " + Player.money + "]");
		System.out.println("=====================================");
	}
	
	public static void delayPrint() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
