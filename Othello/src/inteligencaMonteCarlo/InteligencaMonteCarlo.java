package inteligencaMonteCarlo;

import logika.Igra;
import splosno.Poteza;

public class InteligencaMonteCarlo extends splosno.KdoIgra{

	public InteligencaMonteCarlo(String ime) {
		super(ime);
	}
	
	public InteligencaMonteCarlo() {
		this("Teodora Cvetković in Vesna Hozjan");
	}

	public Poteza monteCarlo(Igra igra) {
		Tree root = new Tree(igra);
		MonteCarloTreeSearch search = new MonteCarloTreeSearch(root, igra.naPotezi, igra);
		search.putChildren(root, igra);
		Tree best = search.monteCarloTreeSearch();
		return best.p;
	}
	
	public Poteza chooseMove(Igra igra) {
		Poteza bestMove = monteCarlo(igra);
		return bestMove;
	}
}
