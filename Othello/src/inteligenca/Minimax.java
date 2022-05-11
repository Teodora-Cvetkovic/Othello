package inteligenca;

import logika.Igra;
import logika.Igralec;
import splosno.Poteza;

import java.util.List;

import inteligenca.OcenjenaPoteza;

public class Minimax {

	private static final int ZMAGA = 100;
	private static final int ZGUBA = - ZMAGA;
	private static final int NEODLOCENO = 0;
	
	private int globina;
	
	public Minimax (int globina) {
//		super("minimax globina " + globina);
		this.globina = globina;
	}
	
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		List<Poteza> moznePoteze = igra.izracunajMozne();
		for (Poteza p : moznePoteze) {
			Igra kopijaIgre = igra;
			kopijaIgre.izvediPotezo(p);
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
			}
		}
		return najboljsaPoteza;
	}
}
