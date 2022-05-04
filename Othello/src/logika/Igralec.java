package logika;

public enum Igralec {
	CRNI, BELI;
	
	public Igralec nasprotni() {
		if (this == CRNI) return BELI;
		else return CRNI;
	}
	
//	public Polje getPolje() {
//		if (this == CRNI) return Polje.CRNO;   ***kasneje***
//		else return Polje.BELO;
//	}
}
