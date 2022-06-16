package vodja;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import inteligenca.AlfaBeta;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import splosno.KdoIgra;
import splosno.Poteza;

public class Vodja {

	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	public static GlavnoOkno okno;
	public static Igra igra = null;
	public static boolean clovekNaVrsti = false;
	public static boolean vTeku = false;
	
		
	// požene novo igro
	public static void igramoNovoIgro () {
		igra = new Igra();
		vTeku = true;
		igramo();
	}
	
	
	// odigra potezo
	public static void igramo () {
		if(vTeku) {
			okno.osveziGUI();
			switch (igra.stanje()) {
			case ZMAGA_CRNI: 
			case ZMAGA_BELI: 
			case NEODLOCENO: 
				return; // odhajamo iz metode igramo
			case V_TEKU: 
				Igralec igralec = igra.naPotezi;
				VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
				switch (vrstaNaPotezi) {
				case C: 
					clovekNaVrsti = true;
					break;
				case R:
					clovekNaVrsti = false;
					igrajRacunalnikovoPotezo();
					break;
				}
				break;
			case BLOKIRANO: 
				igra.dajNasprotniku();
				break;
		}
		}
	}
	
	
	// naredimo "pametnega" računalnika
	public static Inteligenca racunalnikovaInteligenca = new AlfaBeta(9);

	
	// odigra računalnikovo potezo
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		SwingWorker<Poteza, Void> worker = new SwingWorker<Poteza, Void>(){
			@Override
			protected Poteza doInBackground() {
				Poteza poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				return poteza;
			}
			@Override
			protected void done() {
				Poteza poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if(igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo();
				}
			}
		};
		worker.execute();
	}
	
	
	// odigra človekovo potezo
	public static void igrajClovekovoPotezo(Poteza poteza) {
		if(igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo();
	}
	
}