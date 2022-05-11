package logika;

import java.util.ArrayList;
import java.util.List;
import splosno.Poteza;

public class Igra {
	
	public int steviloCrnih;
	public int steviloBelih;
	public Igralec [][] tabla;
	public static int[][] smeri = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {-1,-1}, {1,-1}, {-1,1}};
	public Igralec naPotezi;

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
		tabla = new Igralec[8][8];
		Igralec i1 = Igralec.CRNI;
		Igralec i2 = Igralec.BELI;
		steviloCrnih = steviloBelih = 2;
		tabla[3][3] = i2;
		tabla[3][4] = i1;
		tabla[4][3] = i1;
		tabla[4][4] = i2;
		naPotezi = Igralec.CRNI;
		List<Poteza> mozneCrni = izracunajMozne();
		for(Poteza p : mozneCrni) System.out.println(p.getX() + " " + p.getY());
		
	}
	
	public List<Poteza> izracunajMozne () {
		List<Poteza> mozne = new ArrayList<Poteza>();
		Igralec nasprotnik = naPotezi.nasprotni();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(tabla[i][j] != null) continue;
				for(int k =0; k<8; k++) {
					int a = 1;
					while (true) {
						int x = i + a * smeri[k][0];
						int y = j + a * smeri[k][1];
						if (x < 0 || x >= 8 || y < 0 || y >= 8 || tabla[x][y] != nasprotnik) break;
						a++;	
					}
					if (a == 1) continue;
					int x = i + a * smeri[k][0];
					int y = j + a * smeri[k][1];
					if(x >= 0 && x < 8 && y >= 0 && y < 8 && tabla[x][y] == naPotezi) {
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
					if (u < 0 || u >= 8 || v < 0 || v >= 8 || tabla[u][v] != nasprotnik) break;
					a++;	
				}
				if (a == 1) continue;
				int u = x + a * smeri[k][0];
				int v = y + a * smeri[k][1];
				if(u >= 0 && u < 8 && v >= 0 && v < 8 && tabla[u][v] == naPotezi) {
					for(int i = 0; i < a; i++) tabla[x + i * smeri[k][0]][y + i * smeri[k][1]] = naPotezi;
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
	
	public boolean konec() {
		if (izracunajMozne().isEmpty()) {
			naPotezi = naPotezi.nasprotni();
			if (izracunajMozne().isEmpty()) return true;
		}
		return false;
	}
	
	public Stanje stanje() {
		//Ali imamo zmagovalca?
		if (konec()) {
			if(steviloCrnih < steviloBelih) return Stanje.ZMAGA_BELI;
			else if(steviloCrnih > steviloBelih) return Stanje.ZMAGA_CRNI;
			else return Stanje.NEODLOCENO;
		}
		return Stanje.V_TEKU;
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
					if(tabla[i][j] == null) System.out.println(" _ ");
					else if(tabla[i][j] == Igralec.CRNI) System.out.println(" C ");
					else System.out.println(" B ");
				}
				else {
					if(tabla[i][j] == null) System.out.print(" _ ");
					else if(tabla[i][j] == Igralec.CRNI) System.out.print(" C ");
					else System.out.print(" B ");
				}
			}
		}
		System.out.println("Število črnih: " + steviloCrnih + "; Število belih: " + steviloBelih);
	}
}
