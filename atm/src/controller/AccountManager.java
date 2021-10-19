package controller;

import models.Account;
import models.Bank;

public class AccountManager {
	public static AccountManager instance = new AccountManager();
	private UserManager um = UserManager.instance;
	
	public void addAcc() {
		if(um.users.get(Bank.log).acc.size() < 3) {
			int acc = randomAcc();
			um.users.get(Bank.log).acc.add(new Account( acc, 0));
			System.out.println("계좌번호 : " + acc + " 개설완료");
		}
		else {
			System.out.println("계좌 개설 한도 초과");
		}
	}
	
	public int selAcc() {
		for(int i = 0; i < um.users.get(Bank.log).acc.size(); i++) {
			System.out.println(i+1+ ") " +"계좌 번호 : " + um.users.get(Bank.log).acc.get(i).getAcc());
		}
		int sel = Bank.sc.nextInt() - 1;
		return sel;
	}
	
	
	public int[] checkAcc(int inSelAcc) {
		int[] check = {-1,-1};
		for(int i = 1; i < um.users.size(); i++) {
			for(int j = 0; j < um.users.get(i).acc.size(); j ++) {
				int acc = um.users.get(i).acc.get(j).getAcc();
				if(inSelAcc == acc) {
					check[0] = i;
					check[1] = j;
				}
			}
		}
		return check;
	}
	
	public int selPassAcc() {
		for(int i = 0; i < um.users.get(Bank.log).acc.size(); i++) {
			System.out.println(i+1+ ") " +"계좌 번호 : " + um.users.get(Bank.log).acc.get(i).getAcc());
		}
		int sel = Bank.sc.nextInt() - 1;
		return sel;
	}
	
	public void delAcc() {
		int delAccIdx = selAcc();
		int delAcc = um.users.get(Bank.log).acc.get(delAccIdx).getAcc();
		um.users.get(Bank.log).acc.remove(delAccIdx);
		System.out.println(delAcc + " 계좌 철회 완료");
	}
	
	private int randomAcc() {
		while(true) {
			int rAcc = Bank.rn.nextInt(8999999) + 1000000;
			boolean check = false;
			
			for(int i = 1; i < um.users.size(); i++) {
				for(int j = 0; j < um.users.get(i).acc.size(); j ++) {
					if(um.users.get(i).acc.get(j).getAcc() == rAcc) {
						check = true;
					}
				}
			}
			if(!check) {
				return rAcc;
			}
		}
	}
	
	public void searchACC() {
		System.out.println(um.users.get(Bank.log).getName() + "님 의 계좌");
		for(int i = 0; i < um.users.get(Bank.log).acc.size(); i ++) {
			int acc = um.users.get(Bank.log).acc.get(i).getAcc();
			int money = um.users.get(Bank.log).acc.get(i).getMoney();
			System.out.println("계좌번호 : " + acc + "\t잔액 :" + money + "원");
		}
	}
	
	
	public void inputAccMoney(int sel, int money) {
		int totalMoney = um.users.get(Bank.log).acc.get(sel).getMoney() + money;
		um.users.get(Bank.log).acc.get(sel).setMoney(totalMoney);
		System.out.println(money + " 원 입금완료");
		System.out.printf("%d 계좌 잔액 : %d원\n",  um.users.get(Bank.log).acc.get(sel).getAcc(), um.users.get(Bank.log).acc.get(sel).getMoney());
	}
	
	
	public boolean checkOutMoney(int sel, int outMoney) {
		boolean check = true;
		int inMoney = um.users.get(Bank.log).acc.get(sel).getMoney();
		if(inMoney < outMoney) {
			check = false;
		}
		return check;
	}
	
	public void outputAccMoney(int sel, int outMoney) {
		int totalMoney = um.users.get(Bank.log).acc.get(sel).getMoney() - outMoney;
		um.users.get(Bank.log).acc.get(sel).setMoney(totalMoney);
		System.out.println(outMoney + " 원 출금완료");
		System.out.printf("%d 계좌 잔액 : %d원\n",  um.users.get(Bank.log).acc.get(sel).getAcc(), um.users.get(Bank.log).acc.get(sel).getMoney());
	}
	
	public void passAccMoney(int sel, int passMoney, int [] inSelMoneyInfo) {
		int outTotalMoney = um.users.get(Bank.log).acc.get(sel).getMoney() - passMoney;
		int inTotalMoney = um.users.get(inSelMoneyInfo[0]).acc.get(inSelMoneyInfo[1]).getMoney() + passMoney;
		um.users.get(Bank.log).acc.get(sel).setMoney(outTotalMoney);
		um.users.get(inSelMoneyInfo[0]).acc.get(inSelMoneyInfo[1]).setMoney(inTotalMoney);
		System.out.println(passMoney + " 원 이체완료");
		System.out.printf("%d 계좌 잔액 : %d원\n",  um.users.get(Bank.log).acc.get(sel).getAcc(), um.users.get(Bank.log).acc.get(sel).getMoney());
	}
	
	public void printAllAcc() {
		for(int i = 1; i < um.users.size(); i++) {
			if(um.users.get(i).acc.size() >= 1) {
				System.out.println(um.users.get(i).getName() + "님 의 계좌");
				System.out.println("계좌 수 " + um.users.get(i).acc.size());
				for(int j = 0; j < um.users.get(i).acc.size(); j ++) {
					int acc = um.users.get(i).acc.get(j).getAcc();
					int money = um.users.get(i).acc.get(j).getMoney();
					System.out.println("계좌번호 : " + acc + "\t잔액 :" + money + "원");
				}
			}
		}
	}

}
