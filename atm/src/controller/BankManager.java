package controller;
import models.Bank;
public class BankManager {
	public static BankManager instance = new BankManager();
	private AccountManager am = AccountManager.instance;
	private UserManager um = UserManager.instance;
	private FileManager fm = FileManager.instance;
	private BankManager() {}
	private boolean isRun = true;
	
	public void run() {
		// 실행 시작
		while(this.isRun) {
			System.out.println(Bank.getName() + " ATM");
			printMenu();
			selectMenu();
		}
	}
	
	private void printMenu() {
		if(Bank.log == -1) {
			System.out.println("1.로그인\n2.회원가입\n3.파일관리\n4.종료");
		}
		else {
			if(Bank.log == 0) { // 관리자 모드
				System.out.println("1.전체회원조회\n2.전계좌조회\n3.로그아웃");
			}
			else {
				System.out.println("1.계좌관리\n2.뱅킹기능\n3.로그아웃");
			}
		}
	}
	
	private void selectMenu() {
		System.out.print("입력 : ");
		String input = Bank.sc.next();
		try {
			int sel = Integer.parseInt(input);
			if(Bank.log == -1) {
				if(sel == 1) {
					Bank.log = um.login();
				}
				else if(sel == 2) {
					// 회원가입 메소드를 호출
					um.joinUser();
					// UserManager.instance.joinUser();
				}
				else if(sel == 3) {
					System.out.println("1.파일저장\n2.파일로드");
					int loginSel = Bank.sc.nextInt();
					
					if(loginSel == 1) {
						fm.save();
					}
					else if (loginSel == 2) {
						fm.load();
					}
				}
				else if (sel == 4) {
					System.out.println("시스템 종료");
					this.isRun = false;
				}
			}
			else {
				if(Bank.log == 0) {
					if(sel == 1) { // 전체 회원 조회
						um.printAllUser();
					}
					else if (sel == 2) { // 전계좌 조회
						am.printAllAcc();
					}
					else if (sel == 3) { // 로그아웃
						um.logout();
					}
				}
				else {
//					3. 계좌개설/ 철회 (계정당 3개 계좌 제한)
//					 * 4. 뱅킹기능 (입금/출금/ 이체/조회)
//					 * 5. 파일처리 (저장/ 로드)
					if(sel == 1) { // 계좌관리
						System.out.println("1.계좌개설\n2.계좌철회");
						int loginSel = Bank.sc.nextInt();
						if(loginSel == 1) {
							am.addAcc();
						}
						else if (loginSel == 2) {
							am.delAcc();
						}
					}
					else if (sel == 2) { // 뱅킹기능
						if(um.users.get(Bank.log).acc.size() >= 1) {
							System.out.println("1.입금\n2.출금\n3.이체\n4.조회");
							int loginSel = Bank.sc.nextInt();
							if(loginSel == 1) {
								int selAcc = am.selAcc();
								inputMoney(selAcc);
							}
							else if (loginSel == 2) {
								int selAcc = am.selAcc();
								outputMoney(selAcc);
							}
							else if (loginSel == 3) {
								System.out.println("보내는 계좌 선택");
								int outSelAcc = am.selAcc();
								System.out.println("받으실분 계좌 입력");
								int inSelAcc = Bank.sc.nextInt();
								int check [] = am.checkAcc(inSelAcc);
								if(check[0] != -1) {
									passMoney(outSelAcc, check);
								}
								else {
									System.out.println("받으실 분의 계좌를 확인해 주세요");
								}
							}
							else if (loginSel == 4) {
								am.searchACC();
							}
						}
						else {
							System.out.println("개설된 계좌가 없습니다.");
						}
					}
					else if (sel == 3) { // 로그아웃
						um.logout();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void inputMoney(int selAcc) {
		System.out.print("입금액 : ");
		int input = Bank.sc.nextInt();
		
		if(input > 0) {
			am.inputAccMoney(selAcc, input);
		}
		else {
			System.out.println("입금액은 0 원 초과여야 합니다.");
		}
	}
	private void outputMoney(int selAcc) {
		System.out.println("선택 계좌 잔액 : " + um.users.get(Bank.log).acc.get(selAcc).getMoney());
		if(um.users.get(Bank.log).acc.get(selAcc).getMoney() > 0) {
			System.out.print("출금액 : ");
			int output = Bank.sc.nextInt();
			
			if(am.checkOutMoney(selAcc, output)) {
				am.outputAccMoney(selAcc, output);
			}
			else {
				System.out.println("출금액은 계좌 잔고를 초과할 수  없습니다.");
			}
		}
		else {
			System.out.println("선택된 계좌의 잔고가 0원 입니다.");
		}
	}
	private void passMoney(int outSelAcc, int[] check) {
		if(um.users.get(Bank.log).acc.get(outSelAcc).getMoney() > 0) {
			System.out.print("이체금액 : ");
			int output = Bank.sc.nextInt();
			if(am.checkOutMoney(outSelAcc, output)) {
				am.passAccMoney(outSelAcc, output, check);
			}
			else {
				System.out.println("이체금액은 계좌 잔고를 초과할 수  없습니다.");
			}
		}
		else {
			System.out.println("선택된 계좌의 잔고가 0원 입니다.");
		}
	}
	
	
	
	
}
