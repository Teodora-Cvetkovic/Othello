package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.KdoIgra;
import splosno.Poteza;

public class Inteligenca extends KdoIgra {
	
	private static final int ZMAGA = 100;
	private static final int ZGUBA = - ZMAGA;
	private static final int NEODLOCENO = 0;
	
	private int globina;

	public Inteligenca () {
		super("Teodora in Vesna");
		this.globina = 9;
	}
	
	public Poteza izberiPotezo(Igra igra) {
		OcenjenaPoteza najboljsa = alphabeta(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi);
		return najboljsa.poteza;
	}
	
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		List<Poteza> moznePoteze = igra.izracunajMozne();
		if(moznePoteze.isEmpty()) {
			return null;
		}
		else {
			for (Poteza p : moznePoteze) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(p);
				int ocena;
				switch (kopijaIgre.stanje()) {
				case ZMAGA_CRNI: 
					if (jaz == Igralec.CRNI) ocena = ZMAGA;
					else ocena = ZGUBA;
					break;
				case ZMAGA_BELI:
					if (jaz == Igralec.BELI) ocena = ZMAGA;
					else ocena = ZGUBA;
					break;
				case NEODLOCENO: ocena = NEODLOCENO; break;
				default:
					if(globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
					else ocena = minimax(kopijaIgre, globina - 1, jaz).ocena;
				}
				if(najboljsaPoteza == null
						|| jaz == igra.naPotezi && ocena > najboljsaPoteza.ocena
						|| jaz != igra.naPotezi && ocena < najboljsaPoteza.ocena)
					najboljsaPoteza = new OcenjenaPoteza(p, ocena);
			}
		}
		return najboljsaPoteza;
	}
	
	public OcenjenaPoteza alphabeta(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		if(igra.naPotezi == jaz) ocena = ZGUBA;
		else ocena = ZMAGA;
		List<Poteza> moznePoteze = igra.izracunajMozne();
		if(moznePoteze.isEmpty()) {
			return null;
		}
		else {
			Poteza kandidat = moznePoteze.get(0);
			for(Poteza p: moznePoteze) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(p);
				int ocenap = 0;
				switch(kopijaIgre.stanje()) {
				case ZMAGA_CRNI:
					if(jaz == Igralec.CRNI) ocenap = ZMAGA;
					else ocenap = ZGUBA;
				case ZMAGA_BELI:
					if(jaz == Igralec.BELI) ocenap = ZMAGA;
					else ocenap = ZGUBA;
				case NEODLOCENO: ocenap = NEODLOCENO;
				default:
					if(globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
					else ocenap = alphabeta(kopijaIgre, globina - 1, alpha, beta, jaz).ocena;
				}
				if(igra.naPotezi == jaz) {
					if(ocenap > ocena) {
						ocena = ocenap;
						kandidat = p;
						alpha = Math.max(alpha, ocena);
					}
				}
				else {
					if(ocenap < ocena) {
						ocena = ocenap;
						kandidat = p;
						beta = Math.min(beta, ocena);
					}
				}
				if(alpha >= beta) return new OcenjenaPoteza(kandidat, ocena); 
			}
			return new OcenjenaPoteza(kandidat, ocena);
		}
	}


}
