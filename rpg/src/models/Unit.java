package models;

public class Unit {
	// commint
	private String name;
	private int	 level;
	private int hp;
	private int maxHp;
	private int att;
	private int def;
	private int exp;
	private boolean party;
	private Item weapon;
	private Item armor;
	private Item acc;
	// 이름/현재체력/최대체력/공격력/방어력/레벨
	
	public Unit(String n, int l, int h, int a, int d, int e) {
		this.name = n;	this.level = l;	this.maxHp = h;	this.att = a;
		this.def = d;	this.exp = e;	this.hp = this.maxHp;	this.party = false;
		this.weapon = null;		this.armor = null;	this.acc = null;
	}
	public Unit(String n, int l, int h, int a, int d, int e , boolean p) {
		this.name = n;	this.level = l;	this.maxHp = h;	this.att = a;
		this.def = d;	this.exp = e;	this.hp = this.maxHp;	this.party = p;
		this.weapon = null;		this.armor = null;	this.acc = null;
	}
	public Unit(String n, int l, int h, int mH, int a, int d, int e , boolean p, Item w, Item ar, Item acc) {
		this.name = n;	this.level = l;	this.maxHp = mH;	this.att = a;
		this.def = d;	this.exp = e;	this.hp = h;	this.party = p;
		this.weapon = w;		this.armor = ar;	this.acc = acc;
	} // 로드용
	
	public void setItem(Item w , Item a , Item ac) {
		weapon = w; armor= a; acc = ac;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public int getAtt() {
		return att;
	}
	public void setAtt(int att) {
		this.att = att;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public boolean getParty() {
		return party;
	}
	public void setParty(boolean party) {
		this.party = party;
	}
	public Item getWeapon() {
		return weapon;
	}
	public void setWeapon(Item weapon) {
		this.weapon = weapon;
	}
	public Item getArmor() {
		return armor;
	}
	public void setArmor(Item armor) {
		this.armor = armor;
	}
	public Item getAcc() {
		return acc;
	}
	public void setAcc(Item ac) {
		this.acc = ac;
	}
	
	
	
}
