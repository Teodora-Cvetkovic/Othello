package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Poteza;

public class AlfaBeta extends Inteligenca{
	
	private static final int ZMAGA = 100;
	private static final int ZGUBA = - ZMAGA;
	private static final int NEODLOCENO = 0;
	
	private int globina;
	
	public AlfaBeta (int globina) {
		super();
		this.globina = globina;
	}
	
	@Override
	public Poteza izberiPotezo(Igra igra) {
		OcenjenaPoteza najboljsa = alphabeta(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi);
		return najboljsa.poteza;
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
					break;
				case ZMAGA_BELI:
					if(jaz == Igralec.BELI) ocenap = ZMAGA;
					else ocenap = ZGUBA;
					break;
				case NEODLOCENO: ocenap = NEODLOCENO; break;
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


