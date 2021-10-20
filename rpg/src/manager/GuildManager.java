package manager;

import java.util.ArrayList;

import models.Player;
import models.Unit;

public class GuildManager {
	public static GuildManager Instance = new GuildManager();
	private final int PARTY_SIZE = 4;
	private ArrayList<Unit> guildList = new ArrayList<>();
	private Unit[] partyList;
	// commint
	// 길드원 목록/길드원 고용/길드원 해고/파티원 교체/정렬/뒤로가기
	
	public GuildManager(){
		setGuildUnit();
	}
	
	private void setGuildUnit() {
		this.partyList = new Unit[this.PARTY_SIZE];
		// 유닛 추가
		for(int i = 0; i < 5; i++) {
			if(!creatGuildUnit()) {
				i--;
			}
		}
	}
	public void printGuildMenu() {
		System.out.println("\n=============== [길드] ================");
		String guildMenu = "길드원목록/길드원고용/길드원판매"
				+ "/파티원추가/파티원교체/정렬/뒤로가기";
		String [] temp = guildMenu.split("/");
		for(int i = 0 ; i < temp.length; i++) {
			if(i != temp.length -1) {
				System.out.printf("[%d. %s]\n",i+1,temp[i]);
			}
			else {
				System.out.printf("[%d. %s]\n",0,temp[i]);
			}
		}
	}
	
	
	public boolean selectGuildMenu() {
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString);
		
		if(sel == 1) { // 길드원 목록
			Game.printGold();
			printGuildUnit();
			Game.delayPrint();
		}
		else if (sel == 2) { // 길드원 고용
			addGuildUnit();
			Game.delayPrint();
			printGuildUnit();
		}
		else if (sel == 3) { // 길드원 판매
			removeGuildUnit();
			Game.delayPrint();
		}
		else if (sel == 4) { // 파티원 추가
			addPartyUnit();
			Game.delayPrint();
		}
		else if (sel == 5) { // 파티원 교체
			changePartUnit();
			Game.delayPrint();
		}
		else if (sel == 6) { // 정렬
			sortGuildUnit();
			Game.delayPrint();
			printGuildUnit();
			System.out.println("정렬완료\n");
		}
		else if (sel == 0) { // 뒤로가기
			return false;
		}
		return true;
	}
	
	private void printNullUnit() {
		System.out.println("길드원이 없습니다.\n");
	}
	
	public void printGuildUnit() {
		if(this.guildList.size() != 0) {
			System.out.println("============= [길드원] =================");
			for(int i = 0; i < this.guildList.size(); i++) {
				System.out.print("[" + (i + 1) + "번]");
				System.out.print(" [이름 : " + this.guildList.get(i).getName() + "]");
				System.out.print(" [레벨 : " + this.guildList.get(i).getLevel() + "]");
				System.out.print(" [체력 : " + this.guildList.get(i).getHp());
				System.out.println(" / " + this.guildList.get(i).getMaxHp() + "]");
				System.out.print("[공격력 : " + this.guildList.get(i).getAtt() + "]");
				System.out.print(" [방어력 : " + this.guildList.get(i).getDef() + "]");
				System.out.println(" [파티중 : " + this.guildList.get(i).getParty() + "]");
				System.out.println("");
			}
		}
		else {
			printNullUnit();
		}
		System.out.println("======================================");
	}
	
	public void printSingleUnit(Unit unit) {
		System.out.println("=====================================");
		System.out.print("[이름 : " + unit.getName() + "]");
		System.out.print(" [레벨 : " + 1 + "]");
		System.out.print(" [체력 : " + unit.getHp());
		System.out.println(" / " + unit.getMaxHp() + "]");
		System.out.print("[공격력 : " + unit.getAtt() + "]");
		System.out.println(" [방어력 : " + unit.getDef() + "]");
		System.out.println("=====================================");
	}
	public int selGulidUnit() {
		Game.printInput();
		String selString = Game.sc.next();
		int sel = Integer.parseInt(selString) - 1;
		
		if(0 <= sel && sel < this.guildList.size()) {
			return sel;
		}
		else {
			Game.printWrongInput();
			return -1;
		}
	}
	
	private boolean creatGuildUnit() {
		Unit temp = randomUnit(); // 랜덤 유닛 생성
		int check = checkOLUnit(temp); // 이름 중복 확인
		if(check == -1) {
			int cntPU = cntPartyUnit();
			this.guildList.add(temp);
			if(cntPU < this.PARTY_SIZE) {
				this.partyList[cntPU] = temp;
			}
			return true;
		}
		return false;
	}
	
	private void addGuildUnit() {
		if(Player.money < 5000) {
			System.out.println("골드가 부족합니다.\n");
			return;
		}
		else {
			boolean isRun = true;
			while(isRun) {
				isRun = !creatGuildUnit();
				if(!isRun) {
					Unit temp = this.guildList.get(this.guildList.size()-1);
					printSingleUnit(temp);
					Player.money -= 5000;
					Game.printGold();
					System.out.printf("%s : 5,000골드\n고용 완료\n\n", temp.getName());
				}
			}
		}
	}
	
	private int cntPartyUnit() {
		int cnt = 0;
		for(int i = 0; i < this.partyList.length; i++) {
			if(this.partyList[i] != null) {
				cnt++;
			}
		}
		return cnt;
	}
	
	private void addPartyUnit() {
		int cnt = cntPartyUnit();
		if(cnt < this.PARTY_SIZE) {
			ArrayList <Integer> nonPartyUnitIdx = printNonPartyUnit();
			if(nonPartyUnitIdx.size() == 0) {
				System.out.println("추가할 길드원이 없습니다.");
				return;
			}
			else {
				int selNPU = selPartyUnit();
				if(0 <= selNPU && selNPU < nonPartyUnitIdx.size()) {
					this.guildList.get(nonPartyUnitIdx.get(selNPU)).setParty(true);
					this.partyList[cnt] = this.guildList.get(nonPartyUnitIdx.get(selNPU));
					System.out.println("추가 완료\n");
				}
			}
		}
		else {
			System.out.println("파티제한을 초과했습니다.");
		}
	}
	
	private int checkOLUnit(Unit unit) {
		int check = -1;
		for(int i = 0 ;  i < this.guildList.size(); i++) {
			if(unit.getName().equals(this.guildList.get(i).getName())) {
				check = i;
			}
		}
		return check;
	}
	
	private int checkOLPUnit(String name) {
		int partyIdx = -1;
		for(int i = 0 ; i < this.partyList.length; i++) {
			if(this.partyList[i] != null && name.equals(this.partyList[i].getName())) {
				partyIdx = i;
			}
		}
		return partyIdx;
	}
	
	private int cntNPU() {
		int cnt = 0;
		for(Unit unit : this.guildList) {
			if(!unit.getParty()) {
				cnt ++;
			}
		}
		
		return cnt;
	}
	
	private int [] checkNPUIdx() {
		int cnt = cntNPU();
		int [] idxNPU = new int [cnt];
		cnt = 0;
		for(int i = 0; i <this.guildList.size(); i++) {
			if(!this.guildList.get(i).getParty()) {
				idxNPU[cnt] = i;
				cnt++;
			}
		}
		
		return idxNPU;
	}
	
	private void removePartyUnit(int partyIdx) {
		Unit[] temp = this.partyList;
		this.partyList = new Unit[this.PARTY_SIZE];
		int n = 0;
		for(int i = 0 ; i < this.partyList.length; i++) {
			if(i != partyIdx) {
				this.partyList[n] = temp[i];
				n++;
			}
		}
	}
	
	private void supplePartyUnit() {
		int n = 0;
		while(true) {
			int partyUnitCnt = cntPartyUnit(); // 파티중인 유닛
			System.out.println(partyUnitCnt);
			System.out.println(this.guildList.size());
			if(partyUnitCnt < this.PARTY_SIZE && partyUnitCnt < this.guildList.size() - 1) {
				int [] NPUIdx = checkNPUIdx();
				this.guildList.get(NPUIdx[n]).setParty(true);
				this.partyList[partyUnitCnt] = this.guildList.get(NPUIdx[n]);
				n++;
			}
			else {
				break;
			}
		}
	}
	
	
	private void returnItem(Unit unit) {
		int check = 0;
		if(unit.getWeapon() != null) {
			String name = unit.getWeapon().getName();
			InventoryManager.Instance.getItemList().add(unit.getWeapon());
			System.out.printf("[무기][%s] 인벤토리 반환완료\n", name);
			check ++;
		}
		if(unit.getArmor() != null) {
			String name = unit.getArmor().getName();
			InventoryManager.Instance.getItemList().add(unit.getArmor());
			System.out.printf("[방어구][%s] 인벤토리 반환완료\n", name);
			check ++;
		}
		if(unit.getAcc() != null) {
			String name = unit.getAcc().getName();
			InventoryManager.Instance.getItemList().add(unit.getAcc());
			System.out.printf("[악세서리][%s] 인벤토리 반환완료\n", name);
			check ++;
		}
		
		if(check != 0) {
			System.out.println("=====================================\n");		}
	}
	
	private void removeGuildUnit() {
		if(this.guildList.size() > 0) {
			printGuildUnit();
			int idx = selGulidUnit();
			if(idx != -1) {
				String name = this.guildList.get(idx).getName();
				int partyIdx = checkOLPUnit(name);
				if(partyIdx != -1){ // 입력한 이름이 파티중
					removePartyUnit(partyIdx);
					supplePartyUnit();
				}
				
				// 아이템 인벤토리 반환
				returnItem(this.guildList.get(idx));
				this.guildList.remove(idx);
				Player.money += 2500;
				printGuildUnit();
				System.out.println(name + " : 2,500 골드\n판매 완료\n");
				Game.printGold();
			}
		}
		else {
			printNullUnit();
		}
	}
	
	private ArrayList<Integer> printPartyUnit() {
		int num = 1;
		ArrayList <Integer> partyUnitIdx = new ArrayList<>();
		for(int i = 0 ; i < this.partyList.length; i ++) {
			if(this.partyList[i] != null) {
				System.out.print("[" + (num) + "번]");
				System.out.print(" [이름 : " + this.partyList[i].getName() + "]");
				System.out.print(" [레벨 : " + this.partyList[i].getLevel() + "]");
				System.out.print(" [체력 : " + this.partyList[i].getHp());
				System.out.println(" / " + this.partyList[i].getMaxHp() + "]");
				System.out.print("[공격력 : " + this.partyList[i].getAtt() + "]");
				System.out.print(" [방어력 : " + this.partyList[i].getDef() + "]");
				System.out.println(" [파티중 : " + this.partyList[i].getParty() + "]");
				System.out.println("");
				partyUnitIdx.add(i);
				num++;
			}
		}
		return partyUnitIdx;
	}
	
	private ArrayList<Integer> printNonPartyUnit() {
		int num = 1;
		ArrayList <Integer> nonPartyUnitIdx = new ArrayList<>();
		for(int i = 0 ; i < this.guildList.size(); i ++) {
			if(!this.guildList.get(i).getParty()) {
				System.out.print("[" + (num) + "번]");
				System.out.print(" [이름 : " + this.guildList.get(i).getName() + "]");
				System.out.print(" [레벨 : " + this.guildList.get(i).getLevel() + "]");
				System.out.print(" [체력 : " + this.guildList.get(i).getHp());
				System.out.println(" / " + this.guildList.get(i).getMaxHp() + "]");
				System.out.print("[공격력 : " + this.guildList.get(i).getAtt() + "]");
				System.out.print(" [방어력 : " + this.guildList.get(i).getDef() + "]");
				System.out.println(" [파티중 : " + this.guildList.get(i).getParty() + "]");
				System.out.println("");
				nonPartyUnitIdx.add(i);
				num++;
			}
		}
		return nonPartyUnitIdx;
	}
	
	private int selPartyUnit() {
		Game.printInput();
		String selString = Game.sc.next();
		int num = Integer.parseInt(selString) - 1;
		return num;
	}
	
	private void changePartUnit() {
		ArrayList <Integer> partyUnitIdx = printPartyUnit();
		if(partyUnitIdx.size() > 0) {
			System.out.print("바꿀 파티원 번호 선택 : ");
			int num = selPartyUnit();
			if(0 <= num && num < partyUnitIdx.size()) {
				ArrayList <Integer> nonPartyUnitIdx = printNonPartyUnit();
				if(nonPartyUnitIdx.size() == 0) {
					System.out.println("교체할 길드원이 없습니다.\n");
					return;
				}
				else {
					int selNPU = selPartyUnit();
					if(0 <= selNPU && selNPU < nonPartyUnitIdx.size()) {
						String outName = this.guildList.get(partyUnitIdx.get(num)).getName();
						Unit inUnit = this.guildList.get(nonPartyUnitIdx.get(selNPU));
						int partyIdx = checkOLPUnit(outName);
						if(partyIdx != -1) {
							this.partyList[partyIdx] = inUnit;
							this.guildList.get(partyUnitIdx.get(num)).setParty(false);
							this.guildList.get(nonPartyUnitIdx.get(selNPU)).setParty(true);
							System.out.println("교체 완료\n");
						}
					}
					else {
						Game.printWrongInput();
						return;
					}
				}
			}
			else {
				Game.printWrongInput();
				return;
			}
		}
		else {
			System.out.println("현재 파티 중인 길드원이 없습니다.\n");
			return;
		}
	}
	
	private void sortGuildUnit() {
		for(int i = 0; i < this.guildList.size(); i++) {
			for(int j = i; j < this.guildList.size(); j++) {
				String name1 = this.guildList.get(i).getName();
				String name2 = this.guildList.get(j).getName();
				if(name1.compareTo(name2) > 0) {
					Unit temp = this.guildList.get(i);
					Unit temp2 = this.guildList.get(j);
					this.guildList.set(i, temp2);
					this.guildList.set(j, temp);
				}
			}
		}
		for(int i = 0; i < this.partyList.length; i++) {
			if(this.partyList[i] != null) {
				for(int j = i; j < this.partyList.length; j++) {
					if(this.partyList[j] != null) {
						String name1 = this.partyList[i].getName();
						String name2 = this.partyList[j].getName();
						if(name1.compareTo(name2) > 0) {
							Unit temp = this.partyList[i];
							this.partyList[i] = this.partyList[j];
							this.partyList[j] = temp;
						}
					}
				}
			}
		}
	}
	
	public Unit[] getPartyList() {
		return this.partyList;
	}
	
	public void resetPartyList() {
		this.partyList = new Unit[this.PARTY_SIZE];
	}
	
	public void setPartyList(int idx, Unit unit) {
		this.partyList[idx] = unit;
	}
	
	public void setPartyList(Unit[] partyList) {
		this.partyList = partyList;
	}
	
	public ArrayList<Unit> getGuildList(){
		return this.guildList;
	}
	
	private String randomName() {
		String[] n1 = { "박", "강", "김", "임", "유", "구", "오"};
		String[] n2 = { "명", "경", "수", "지", "재", "혜", "민"};
		String[] n3 = { "주", "화", "정", "윤", "석", "준", "서"};
		String name = n1[Game.rn.nextInt(n1.length)];
		name += n2[Game.rn.nextInt(n1.length)];
		name += n3[Game.rn.nextInt(n1.length)];
		return name;
	}
	
	private Unit randomUnit() {
		String n = randomName();
		int l = 1;
		int mH = Game.rn.nextInt(50) + 50;
		int a = Game.rn.nextInt(10) + 1;
		int d = Game.rn.nextInt(10) + 1;
		int e = 0;
		
		int cntPU = cntPartyUnit();
		Unit unit;
		if(cntPU < this.PARTY_SIZE) {
			unit = new Unit(n,l,mH,a,d,e,true);
		}
		else {
			unit = new Unit(n,l,mH,a,d,e);
		}
		return unit;
	}
}