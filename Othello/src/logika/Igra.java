package logika;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import splosno.Poteza;

public class Igra {
	
	public int steviloCrnih;
	public int steviloBelih;
	public Polje [][] tabla;
	public static int[][] smeri = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {-1,-1}, {1,-1}, {-1,1}};
	public Igralec naPotezi;
	public HashSet<Poteza> izvedenePoteze;

	public static void main(String[] args) {
		Igra igra = new Igra();
		Poteza p1 = new Poteza(2, 3);
		Poteza p2 = new Poteza(2, 4);
		Poteza p3 = new Poteza(2, 5);
		Poteza p4 = new Poteza(1, 4);
		igra.izvediPotezo(p1);
		igra.izvediPotezo(p2);
		igra.izvediPotezo(p3);
		igra.izvediPotezo(p4);
		igra.izpisPlosce();
		System.out.println(igra.izracunajMozne());
	}
	
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
		izvedenePoteze  = new HashSet<Poteza>();
	}
	
	public List<Poteza> izracunajMozne () {
		List<Poteza> mozne = new ArrayList<Poteza>();
		Igralec nasprotnik = naPotezi.nasprotni();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(tabla[i][j] != Polje.PRAZNO) continue;
				for(int k =0; k<8; k++) {
					int a = 1;
					while (true) {
						int x = i + a * smeri[k][0];
						int y = j + a * smeri[k][1];
						if (x < 0 || x >= 8 || y < 0 || y >= 8 || tabla[x][y] != nasprotnik.getPolje()) break;
						a++;	
					}
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
	
	public boolean izvediPotezo(Poteza p) {
		if(p == null) return false;
		int x = p.getX();
		int y = p.getY();
		Igralec nasprotnik = naPotezi.nasprotni();
		if (izracunajMozne().contains(p)) {
			if (naPotezi == Igralec.CRNI)steviloCrnih += 1;
			else steviloBelih += 1;
			for(int k = 0; k < 8; k++) {
				int a = 1;
				while (true) {
					int u = x + a * smeri[k][0];
					int v = y + a * smeri[k][1];
					if (u < 0 || u >= 8 || v < 0 || v >= 8 || tabla[u][v] != nasprotnik.getPolje()) break;
					a++;	
				}
				if (a == 1) continue;
				int u = x + a * smeri[k][0];
				int v = y + a * smeri[k][1];
				if(u >= 0 && u < 8 && v >= 0 && v < 8 && tabla[u][v] == naPotezi.getPolje()) {
					for(int i = 0; i < a; i++) tabla[x + i * smeri[k][0]][y + i * smeri[k][1]] = naPotezi.getPolje();
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
			izvedenePoteze.add(p);
			naPotezi = nasprotnik;
			return true;
		}
		return false;
	}
	
	public boolean konec() {
		if (izracunajMozne().isEmpty()) {
			naPotezi = naPotezi.nasprotni();
			if (izracunajMozne().isEmpty()) {
				naPotezi = naPotezi.nasprotni();
				return true;
			}
		}
		naPotezi = naPotezi.nasprotni();
		return false;
	}
	
	public Stanje stanje() {
		//Ali imamo zmagovalca?
		if (konec()) {
			if(steviloCrnih < steviloBelih) return Stanje.ZMAGA_BELI;
			else if(steviloCrnih > steviloBelih) return Stanje.ZMAGA_CRNI;
			else return Stanje.NEODLOCENO;
		}
		//Ali igralec na potezi ima možne poteze
		if (izracunajMozne().isEmpty()) {
			naPotezi = naPotezi.nasprotni();
			if (!izracunajMozne().isEmpty()) return Stanje.BLOKIRANO;
		}
		return Stanje.V_TEKU;
	}
	
	public HashSet<Poteza> mnozicaIzvedenihPotez() {
		return izvedenePoteze;
	}
	
//	public void zmaga() {
//		if(konec()) {
//			if(steviloCrnih < steviloBelih) System.out.println("Zmagal je BELI igralec!");
//			else if(steviloCrnih > steviloBelih)System.out.println("Zmagal je ČRNI igralec!");
//			else System.out.println("NEODLOČENO...");
//		}
//	}
	
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
		System.out.println("Število črnih: " + steviloCrnih + "; Število belih: " + steviloBelih);
	}
}

//Prekopiraj class Igra bez metode main
