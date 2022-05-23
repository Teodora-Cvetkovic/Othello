package vodja;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import inteligenca.Inteligenca;
import inteligenca.Minimax;
import inteligencaMonteCarlo.InteligencaMonteCarlo;
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
		
	public static void igramoNovoIgro () {
		igra = new Igra();
		igramo();
	}
	
	public static void igramo () {
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
				System.out.println("hi");
				clovekNaVrsti = true;
				break;
			case R:
				clovekNaVrsti = false;
				igrajRacunalnikovoPotezo();
				break;
			}
			break;
		case BLOKIRANO: //preveri
			Igralec nasprotni = igra.naPotezi.nasprotni();
			VrstaIgralca vrstaNasprotni = vrstaIgralca.get(nasprotni);
			switch (vrstaNasprotni) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				clovekNaVrsti = false;
				igrajRacunalnikovoPotezo();
				break;
			}
		}
	}
	
	public static Inteligenca racunalnikovaInteligenca = new Minimax(5);

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
					igra.izvediPotezo(poteza);
					igramo();
				}
			}
		};
		worker.execute();
	}
	
//	public static InteligencaMonteCarlo racunalnikovaInteligenca = new InteligencaMonteCarlo();
//
//	public static void igrajRacunalnikovoPotezo() {
//		Igra zacetnaIgra = igra;
//		SwingWorker<Poteza, Void> worker = new SwingWorker<Poteza, Void>(){
//			@Override
//			protected Poteza doInBackground() {
//				Poteza poteza = racunalnikovaInteligenca.chooseMove(zacetnaIgra);
//				try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
//				return poteza;
//			}
//			@Override
//			protected void done() {
//				Poteza poteza = null;
//				try {poteza = get();} catch (Exception e) {};
//				if(igra == zacetnaIgra) {
//					igra.izvediPotezo(poteza);
//					igramo();
//				}
//			}
//		};
//		worker.execute();
//	}
	
	public static void igrajClovekovoPotezo(Poteza poteza) {
		if(igra.izvediPotezo(poteza)) clovekNaVrsti = false;
		igramo();
	}
	
}
