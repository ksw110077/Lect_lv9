package controller;

import java.util.ArrayList;

import models.Account;
import models.Bank;
import models.User;

public class UserManager {
	public static UserManager instance = new UserManager();
	//users : 중앙 (총) 데이터
	public ArrayList<User> users = new ArrayList<>();
	private UserManager() {
		this.users.add(new User("admin", "admin", "0000"));
	}
	
	// 가입
	public void joinUser() {
		System.out.print("id : ");
		String id = Bank.sc.next();
		System.out.print("pw : ");
		String pw = Bank.sc.next();
		System.out.print("name : ");
		String name = Bank.sc.next();
		
		int check = -1;
		for(int i = 1; i < this.users.size(); i++) {
			if(id.equals(this.users.get(i).getId())) {
				check ++;
			}
		}
		
		if(check == -1) {
			User newUser = new User( name, id, pw);
			this.users.add(newUser);
		}
		else {
			System.out.println("중복된 ID 입니다.");
		}
	}
	
	public int login() {
		System.out.print("id : ");
		String id = Bank.sc.next();
		System.out.print("pw : ");
		String pw = Bank.sc.next();
		
		for(int i = 0; i < this.users.size(); i++) {
			if(this.users.get(i).getId().equals(id) 
					&& this.users.get(i).getPw().equals(pw)) {
				System.out.println("로그인 성공");
				return i;
			}
		}
		return -1;
	}
	
	public void logout() {
		System.out.println("로그아웃 완료");
		Bank.log = -1;
	}

	public void printAllUser() {
		if(this.users.size() > 1) {
			System.out.println("이름 / ID : PW");
			for(int i = 1; i < this.users.size(); i++) {
				String name = this.users.get(i).getName();
				String id = this.users.get(i).getId();
				String pw = this.users.get(i).getPw();
				System.out.printf("%s / %s : %s\n", name, id, pw);
			}
		}
	}
}
