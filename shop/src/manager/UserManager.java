package manager;

import java.util.ArrayList;
import models.User;
import manager.ItemManager;

public class UserManager {
	public static UserManager instance = new UserManager();
	private ArrayList<User> users = new ArrayList<>();
	private int log = -1;
	private UserManager() {
	}
	
	public int getUserSize() {
		return users.size();
	}
	
	public void printAllUser() {
		for(User user : this.getUsers()) {
			System.out.printf("ID : %s / PW : %s / Money : %d\n",user.getId(),user.getPw(),user.getMoney());
		}
	}
	
	public String inputId() {
		System.out.print("id : ");
		String id = Shop.sc.next();
		return id;
	}
	public String inputPw() {
		System.out.print("pw : ");
		String pw = Shop.sc.next();
		return pw;
	}
	
	public int findUserIdx(String id) {
		int check = -1;
		for(int i = 0; i < this.getUsers().size(); i++) {
			if(id.equals(this.getUsers().get(i).getId())) {
				check = i;
			}
		}
		return check;
	}
	
	public void creatUser(User user) {
		this.users.add(user);
	}
	public void addUser() {
		String id = inputId();
		int idx = findUserIdx(id);
		if(idx == -1) {
			String pw = inputPw();
			System.out.print("입금액 : ");
			int money = Shop.sc.nextInt();
			User newUser = new User(id, pw, money);
			this.getUsers().add(newUser);
		}
		else {
			System.out.println("중복된 ID 입니다.");
		}
	}
	
	public void joinUser() {
		String id = inputId();
		int idx = findUserIdx(id);
		if(idx == -1) {
			String pw = inputPw();
			User newUser = new User(id, pw);
			this.getUsers().add(newUser);
			System.out.printf("%s 회원님 가입축하금 %d원 지급되었습니다.\n", id, this.getUsers().get(this.getUsers().size()-1).getMoney());
		}
		else {
			System.out.println("중복된 ID 입니다.");
		}
	}
	
	public void removeAllUser() {
		this.users = new ArrayList<>();
	}
	
	public void removeUser() {
		printAllUser();
		System.out.println("삭제할 아이디");
		String id = Shop.sc.next();
		int delIdx = findUserIdx(id);
		if(delIdx != -1) {
			this.getUsers().remove(delIdx);
			ItemManager.instance.delUserCartItem(id);
			System.out.printf("%s 아이디 삭제 완료.\n", id);
		}
		else {
			System.out.println("잘못된 입력");
		}
	}
	
	public void withdrawal() {
		String id = this.getUsers().get(this.log).getId();
		ItemManager.instance.delUserCartItem(id);
		this.getUsers().remove(this.log);
		this.log = -1;
		System.out.printf("%s님 탈퇴처리가 완료되었습니다.\n", id);
	}
	public boolean login() {
		String id = inputId();
		int idx = findUserIdx(id);
		String pw = inputPw();
		if(idx != -1) {
			if(pw.equals(getUsers().get(idx).getPw())) {
				this.log = idx;
				System.out.printf("%s님 로그인\n", id);
				return true;
			}
		}
		else {
			checkPrint();
		}
		return false;
	}
	
	public void logout() {
		String id = this.getUsers().get(this.log).getId();
		this.log = -1;
		System.out.printf("%s님 로그아웃\n", id);
	}
	public void checkPrint() {
		System.out.println("회원 정보를 확인하세요");
	}
	public int getLog() {
		return this.log;
	}
	public void setLog(int log) {
		this.log = log;
	}
	
	public User getUser(int idx) {
		return users.get(idx);
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
}
