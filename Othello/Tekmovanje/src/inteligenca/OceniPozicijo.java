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
				if((i == 0 || i == 7) && (j == 0 || j == 7)) {
					switch(tabla[i][j]) {
					case CRNO: prestejC += 3; break;
					case BELO: prestejB += 3; break;
					case PRAZNO: break;
					}
				}
				else if((i == 1 && j == 0) || (i == 0 & j == 1) || (i == 1 && j == 1)
						|| (i == 6 && j == 0) || (i == 0 && j == 6) || (i == 6 && j == 6)
						|| (i == 1 && j == 7) || (i == 7 && j == 1) || (i == 1 && j == 6)
						|| (i == 6 && j == 7) || (i == 7 && j == 6) || (i == 6 && j == 1)) {
					switch(tabla[i][j]) {
					case CRNO: prestejC += 1; break;
					case BELO: prestejB += 1; break;
					case PRAZNO: break;
					}
				}
				else {
					switch(tabla[i][j]) {
					case CRNO: prestejC += 2; break;
					case BELO: prestejB += 2; break;
					case PRAZNO: break;
					}
				}
			}
		}
		if (jaz == Igralec.CRNI) ocena = prestejC - prestejB;
		else ocena = prestejB - prestejC;
		return ocena;
	}
	
}
