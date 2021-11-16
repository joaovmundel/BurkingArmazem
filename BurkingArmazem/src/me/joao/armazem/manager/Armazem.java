package me.joao.armazem.manager;

public class Armazem {

	private String regionName;

	private int cactosStored;
	private boolean cactoUse;

	private int melanciaStored;
	private boolean melanciaUse;

	private int aboboraStored;
	private boolean aboboraUse;

	private int fungoStored;
	private boolean fungoUse;

	private int canaStored;
	private boolean canaUse;

	private int trigoStored;
	private boolean trigoUse;

	public Armazem(String region) {
		this.regionName = region;
		this.cactosStored = 0;
		this.cactoUse = false;
		this.melanciaStored = 0;
		this.melanciaUse = false;
		this.aboboraStored = 0;
		this.aboboraUse = false;
		this.fungoStored = 0;
		this.fungoUse = false;
		this.canaStored = 0;
		this.canaUse = false;
		this.trigoStored = 0;
		this.trigoUse = false;
	}

	public Armazem(String rgname, int cactos) {
		this.setRegionName(rgname);
		this.setCactosStored(cactos);
	}

	public Armazem(String regionName, int cactosStored, boolean cactoUse, int melanciaStored, boolean melanciaUse,
			int aboboraStored, boolean aboboraUse, int fungoStored, boolean fungoUse, int canaStored, boolean canaUse,
			int trigoStored, boolean trigoUse) {
		this.regionName = regionName;
		this.cactosStored = cactosStored;
		this.cactoUse = cactoUse;
		this.melanciaStored = melanciaStored;
		this.melanciaUse = melanciaUse;
		this.aboboraStored = aboboraStored;
		this.aboboraUse = aboboraUse;
		this.fungoStored = fungoStored;
		this.fungoUse = fungoUse;
		this.canaStored = canaStored;
		this.canaUse = canaUse;
		this.trigoStored = trigoStored;
		this.trigoUse = trigoUse;
	}

	public int getCactosStored() {
		return cactosStored;
	}

	public void setCactosStored(int cactosStored) {
		this.cactosStored = cactosStored;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public boolean isCactoUse() {
		return cactoUse;
	}

	public void setCactoUse(boolean cactoUse) {
		this.cactoUse = cactoUse;
	}

	public int getMelanciaStored() {
		return melanciaStored;
	}

	public void setMelanciaStored(int melanciaStored) {
		this.melanciaStored = melanciaStored;
	}

	public boolean isMelanciaUse() {
		return melanciaUse;
	}

	public void setMelanciaUse(boolean melanciaUse) {
		this.melanciaUse = melanciaUse;
	}

	public int getAboboraStored() {
		return aboboraStored;
	}

	public void setAboboraStored(int aboboraStored) {
		this.aboboraStored = aboboraStored;
	}

	public boolean isAboboraUse() {
		return aboboraUse;
	}

	public void setAboboraUse(boolean aboboraUse) {
		this.aboboraUse = aboboraUse;
	}

	public int getFungoStored() {
		return fungoStored;
	}

	public void setFungoStored(int fungoStored) {
		this.fungoStored = fungoStored;
	}

	public boolean isFungoUse() {
		return fungoUse;
	}

	public void setFungoUse(boolean fungoUse) {
		this.fungoUse = fungoUse;
	}

	public int getCanaStored() {
		return canaStored;
	}

	public void setCanaStored(int canaStored) {
		this.canaStored = canaStored;
	}

	public boolean isCanaUse() {
		return canaUse;
	}

	public void setCanaUse(boolean canaUse) {
		this.canaUse = canaUse;
	}

	public int getTrigoStored() {
		return trigoStored;
	}

	public void setTrigoStored(int trigoStored) {
		this.trigoStored = trigoStored;
	}

	public boolean isTrigoUse() {
		return trigoUse;
	}

	public void setTrigoUse(boolean trigoUse) {
		this.trigoUse = trigoUse;
	}
}
