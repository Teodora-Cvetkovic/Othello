package logika;

public enum Igralec {
	CRNI, BELI;
	
	public Igralec nasprotni() {
		if (this == CRNI) return BELI;
		else return CRNI;
	}
	
	public Polje getPolje() {
		if (this == CRNI) return Polje.CRNO;
		else return Polje.BELO;
	}
	
	public String getIgralca() {
		if(this == CRNI) return "črni igralec";
		else return "beli igralec";
	}
}