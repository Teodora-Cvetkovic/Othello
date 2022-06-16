package logika;

import java.util.ArrayList;
import java.util.List;
import splosno.Poteza;

public class Igra {
	
	public int steviloCrnih; // število črnih diskov
	public int steviloBelih; // število belih diskov
	public Polje [][] tabla; // igralna tabla
	public static int[][] smeri = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {-1,-1}, {1,-1}, {-1,1}}; // možne smeri igranja
	public Igralec naPotezi; // igralec, ki je na potezi

	
	public static void main(String[] args) {
		Igra igra = new Igra();
		Poteza p1 = new Poteza(2, 3);
		Poteza p2 = new Poteza(2, 4);
		Poteza p3 = new Poteza(2, 5);
		Poteza p4 = new Poteza(1, 4);
		igra.odigraj(p1);
		igra.odigraj(p2);
		igra.odigraj(p3);
		igra.odigraj(p4);
		igra.izpisPlosce();
		System.out.println(igra.izracunajMozne());
	}
	
	
	// naredi novo igro
	public Igra() {
		tabla = new Polje[8][8];
		steviloCrnih = steviloBelih = 2;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(i == j && (i == 3 || i == 4)) tabla[i][j] = Polje.BELO;
				else if((i == 3 && j == 4) || (i == 4 && j == 3)) tabla[i][j] = Polje.CRNO;
				else tabla[i][j] = Polje.PRAZNO;
			}
		}
		naPotezi = Igralec.CRNI;
	}
	
	
	// naredi kopijo igre
	public Igra(Igra igra) {
		this.tabla = new Polje[8][8];
		this.steviloCrnih = igra.steviloCrnih;
		this.steviloBelih = igra.steviloBelih;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.tabla[i][j] = igra.tabla[i][j];
			}
		}
		this.naPotezi = igra.naPotezi;
	}
	
	
	// izračuna možne poteze
	public List<Poteza> izracunajMozne () {
		List<Poteza> mozne = new ArrayList<Poteza>();
		Igralec nasprotnik = naPotezi.nasprotni();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				// če polje ni prazno, gre na naslednje polje
				if(tabla[i][j] != Polje.PRAZNO) continue;
				for(int k =0; k<8; k++) {
					int a = 1;
					
					// štejemo nasprotnikove diske
					while (true) {
						int x = i + a * smeri[k][0];
						int y = j + a * smeri[k][1];
						if (x < 0 || x >= 8 || y < 0 || y >= 8 || tabla[x][y] != nasprotnik.getPolje()) break;
						a++;	
					}
					
					// če je a = 1, nima nasprotnikovih diskov v tisti smeri
					if (a == 1) continue;
					int x = i + a * smeri[k][0];
					int y = j + a * smeri[k][1];
					if(x >= 0 && x < 8 && y >= 0 && y < 8 && tabla[x][y] == naPotezi.getPolje()) {
						Poteza p = new Poteza(i,j);
						mozne.add(p);
						break;
					}
					
				}
			}
		}
		return mozne;
	}
	
	
	//odigra potezo
	public boolean odigraj(Poteza p) {
		
		// če ni možnih potez, preda potezo nasprotniku
		if(p == null || izracunajMozne().isEmpty()) {
			dajNasprotniku();
			return true;
		}
		int x = p.getX();
		int y = p.getY();
		Igralec nasprotnik = naPotezi.nasprotni();
		
		// preverimo, če je poteza p možna
		if (izracunajMozne().contains(p)) {
			
			// če je poteza p možna, dodamo ustrezni disk
			if (naPotezi == Igralec.CRNI) steviloCrnih += 1;
			else steviloBelih += 1;
			
			// preštejemo nasprotnikove diske
			for(int k = 0; k < 8; k++) {
				int a = 1;
				while (true) {
					int u = x + a * smeri[k][0];
					int v = y + a * smeri[k][1];
					if (u < 0 || u >= 8 || v < 0 || v >= 8 || tabla[u][v] != nasprotnik.getPolje()) break;
					a++;	
				}
				
				// če je a = 1, ni nasprotnikovih diskov
				if (a == 1) continue;
				int u = x + a * smeri[k][0];
				int v = y + a * smeri[k][1];
				if(u >= 0 && u < 8 && v >= 0 && v < 8 && tabla[u][v] == naPotezi.getPolje()) {
					
					// nasprotnikovi diski postanejo naši
					for(int i = 0; i < a; i++) tabla[x + i * smeri[k][0]][y + i * smeri[k][1]] = naPotezi.getPolje();
					
					//preštejemo še naše in nasprotnikove diske
					if (naPotezi == Igralec.CRNI) {
						steviloCrnih += a - 1;
						steviloBelih -= a - 1;
					}
					else {
						steviloBelih += a - 1;
						steviloCrnih -= a - 1;
					}
				}
			}
			naPotezi = nasprotnik;
			return true;
		}
		return false;
	}
	
	
	// preveri, ali je konec igre - če noben igralec nima možne poteze, iigra je končana
	public boolean konec() {
		// preveri, ali igralec na potezi ima mozne poteze
		if (izracunajMozne().isEmpty()) {
			naPotezi = naPotezi.nasprotni();
			
			// preveri, ali igralec na potezi ima mozne poteze
			if (izracunajMozne().isEmpty()) {
				naPotezi = naPotezi.nasprotni();
				return true;
			}
		}
		return false;
	}
	
	
	// pogleda stanje igre
	public Stanje stanje() {
		// ali imamo zmagovalca?
		if (konec()) {
			if(steviloCrnih < steviloBelih) return Stanje.ZMAGA_BELI;
			else if(steviloCrnih > steviloBelih) return Stanje.ZMAGA_CRNI;
			else return Stanje.NEODLOCENO;
		}
		// ali igralec na potezi ima možne poteze
		if (izracunajMozne().isEmpty()) return Stanje.BLOKIRANO;
		return Stanje.V_TEKU;
	}

	
	// predamo potezo nasprotniku
	public void dajNasprotniku() {
		naPotezi = naPotezi.nasprotni();
		}

	
	//izpiše trenutno tablo
	private void izpisPlosce() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(j == 7) {
					if(tabla[i][j] == Polje.PRAZNO) System.out.println(" _ ");
					else if(tabla[i][j] == Polje.CRNO) System.out.println(" C ");
					else System.out.println(" B ");
				}
				else {
					if(tabla[i][j] == Polje.PRAZNO) System.out.print(" _ ");
					else if(tabla[i][j] == Polje.CRNO) System.out.print(" C ");
					else System.out.print(" B ");
				}
			}
		}
		System.out.println("Število črnih: " + steviloCrnih + " || Število belih: " + steviloBelih);
	}
}