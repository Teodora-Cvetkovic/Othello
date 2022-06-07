package inteligenca;

import logika.Igra;
import logika.Igralec;
import splosno.Poteza;

import java.util.List;

public class Minimax extends Inteligenca{

	private static final int ZMAGA = 100;
	private static final int ZGUBA = - ZMAGA;
	private static final int NEODLOCENO = 0;
	
	private int globina;
	
	public Minimax (int globina) {
		super();
		this.globina = globina;
	}
	
	@Override
	public Poteza izberiPotezo(Igra igra) {
		OcenjenaPoteza najboljsa = minimax(igra, this.globina, igra.naPotezi);
		return najboljsa.poteza;
	}
	
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		List<Poteza> moznePoteze = igra.izracunajMozne();
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
		return najboljsaPoteza;
	}
}