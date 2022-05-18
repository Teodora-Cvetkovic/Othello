package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;

public class OceniPozicijo {

	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		Polje[][] tabla = igra.tabla;
		int ocena = 0;
		int prestejC = 0;
		int prestejB = 0;
		for (int i = 0; i < 8 && (prestejC == 0 || prestejB == 0); i++) {
			for (int j = 0; j < 8; j++) {
				switch(tabla[i][j]) {
				case CRNO: prestejC += 1; break;
				case BELO: prestejB += 1; break;
				case PRAZNO: break;
				}
			}
		}
		if (jaz == Igralec.CRNI) ocena = prestejC - prestejB;
		else ocena = prestejB - prestejC;
		return ocena;
	}
	
}
