package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import models.Account;
import models.User;

public class FileManager {
	private String fileName = "bms.txt";
	private File file = new File(this.fileName);
	private FileWriter fw = null;
	private FileReader fr = null;
	private BufferedReader br = null;
	public static FileManager instance = new FileManager();
	private UserManager um = UserManager.instance;
	
	public void save() {
		// 이름 / ID , PW/ 계좌,잔액 / 계좌2,잔액2/\n
		String data = "";
		for (int i = 1; i < um.users.size(); i++) {
			data += um.users.get(i).getName() + "/";
			data += um.users.get(i).getId() + ",";
			data += um.users.get(i).getPw() + "/";
			for (int j = 0; j < um.users.get(i).acc.size(); j++) {
				data += um.users.get(i).acc.get(j).getAcc() + ",";
				data += um.users.get(i).acc.get(j).getMoney() + "/";
			}
			data = data.substring(0, data.length() - 1); // 마지막 / 제거
			data += "\n";
		}
		data = data.substring(0, data.length() - 1);
		try {
			this.fw = new FileWriter(this.file);
			this.fw.write(data);
			this.fw.close();
			System.out.println("저장 성공");
		} catch (Exception e) {
			System.out.println("저장 실패");
		}
	}
	public void load() {
		um.users = new ArrayList<>();
		um.users.add(new User("admin","admin","0000"));
		String data = "";
		try {
			this.fr = new FileReader(this.file);
			this.br = new BufferedReader(this.fr);

			String info = br.readLine(); // 회원
			int a = 1;
			while (info != null) {
				String temp[] = info.split("/"); // 이름 / ID , PW/ 계좌,잔액 / 계좌2,잔액2/ 계좌3,잔액3
				String name = temp[0];
				String idPw[] = temp[1].split(",");
				um.users.add(new User(name,idPw[0], idPw[1]));
				for(int i = 2; i < temp.length; i++) {
					String [] AccMoney = temp[i].split(",");
					um.users.get(a).acc.add(new Account(Integer.parseInt(AccMoney[0]), Integer.parseInt(AccMoney[1])));
				}
				a++;
				info = this.br.readLine();
			}
			this.fr.close();
			this.br.close();
			System.out.printf("로드 성공 (%d)\n", um.users.size()-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("로드 실패");
		}
	}
}
