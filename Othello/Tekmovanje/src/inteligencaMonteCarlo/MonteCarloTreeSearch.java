package inteligencaMonteCarlo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import logika.Stanje;
import splosno.Poteza;

public class MonteCarloTreeSearch {

	final int time = 4800; //čas , katerega porabimo za potezo v milisekundah
	final double c = Math.sqrt(2); //konstanta za funkcijo za iskanje
	protected long start = System.currentTimeMillis();
	protected long end = start + time;
	
	protected Tree root;
	protected Igralec me;
	protected Igra game;
	
	public MonteCarloTreeSearch(Tree root, Igralec me, Igra game) {
		this.root = root;
		this.me = me;
		this.game = game;
	}
	
	//izračuna vrednost lista
	public double findUCT(Tree node) {
		if(node == null) return -1000000;
		double nodeValue = (double) node.value;
		double nodeVisit = (double) node.visits;
		double rootVisit = (double) root.visits;
		double uct = nodeValue / nodeVisit + c* Math.sqrt(Math.log(rootVisit) / nodeVisit);
		return uct;
	}
	
	//poišče podlist z najboljšo vrednostjo
	public Tree bestUCT(Tree node) {
		Tree best = null;
		double bestuct = 0;
		for(Tree child : node.children) {
			if(child == null) continue;
			double newUCT = findUCT(child);
			if(best == null || newUCT > bestuct) {
				best = child;
				bestuct = newUCT;
			}
		}
		return best;
	}
	
	//preveri, ali je list popolno obiskan (oz. ali so vsi podlisti obiskani)
	public boolean expanded(Tree node) {
		if(node == null) return true;
		for(Tree t : node.children) {
			if(!t.isVisited) return false;
		}
		return true;
	}
	
	//pravilo za izbiro podlista
	public Tree pickRandom(List<Tree> children) {
		Random rand = new Random();
		Tree randChild = children.get(rand.nextInt(children.size()));
		return randChild;
	}
	
	//izbere neobiskan podlist
	public Tree pickUnvisited(List<Tree> children) {
		List<Tree> unvisited = new ArrayList<Tree>();
		for(Tree child : children) {
			if(!child.isVisited) unvisited.add(child);
		}
		return pickRandom(unvisited);
	}
	
	//razdalja med dvema poljima
	public int distance(Poteza p, Poteza q) {
		int px = p.getX();
		int py = p.getY();
		int qx = q.getX();
		int qy = q.getY();
		int distanceX = Math.abs(px - qx);
		if(distanceX == 0) return Math.abs(py - qy);
		return distanceX;
	}
	
	public boolean check(Poteza p, Poteza q) {
		if(Math.abs(q.getX() - p.getX()) != Math.abs(q.getY() - p.getY())) return false;
		if(distance(p, q) <= 2) return true;
		return false;
	}
	
	//v seznam podlistov vstavi vse podliste za možne poteze
	public void putChildren(Tree node, Igra game) {
		if(!node.children.isEmpty()) return;
		else {
			List<Poteza> possibleMoves = game.izracunajMozne();
			List<Tree> playedMoves = new ArrayList<Tree>();
			for(Poteza p : possibleMoves) {
				if(node.isRoot()) {
					boolean cont = true;
					for (Poteza q : game.mnozicaIzvedenihPotez()) {
						if(check(p, q)) cont = false;
					}
					if (cont) continue;
				}
				Igra copyGame = game;
				copyGame.odigraj(p);
				Tree move = new Tree(copyGame, p, 0, 0, new ArrayList<Tree>());
				move.parent = node;
				playedMoves.add(move);
			}	
			node.children = playedMoves;
		}
	}
	
	//preveri, ali je list končen
	public boolean isTerminal(Tree node) {
		return(node.children.size() == 0);
	}
	
	//navključno izbere podlist 
	public Tree rolloutPolicy(Tree node) {
		return (pickRandom(node.children));
	}
	
	//poračuna rezultat lista
	public int result(Tree node) {
		int res = -2;
		Igra game = node.game;
		Stanje state = game.stanje();
		switch(state) {
		case ZMAGA_CRNI:
			if(me == Igralec.CRNI) res = 1;
			else res = -1;
			break;
		case ZMAGA_BELI:
			if(me == Igralec.BELI) res = 1;
			else res = -1;
			break;
		case NEODLOCENO: res = 0;
		default: new Error(); break;
		}
		if(res == -2) new Error();
		return res;
	}
	
	//odigra igro do konca in poračuna rezultat
	public int playUntilTheEnd(Tree node) {
		putChildren(node, node.game);
		while(!isTerminal(node)) {
			node = rolloutPolicy(node);
			putChildren(node, node.game);
		}
		return result(node);
	}
	
	//poračuna vrednosti listov od začetkoa do konca
	public void backpropagate(Tree node, int result) {
		if(node.isRoot()) {
			node.visits += 1;
			return;
		}
		node.value += result;
		node.visits += 1;
		backpropagate(node.parent, result);
	}
	
	//poišče podlist z najboljšo vrednostjo
	public Tree bestChild(Tree node) {
		Tree best = null;
		int bestValue = 0;
		for(Tree child : node.children) {
			int newValue = child.value;
			if(best == null || newValue > bestValue) {
				best = child;
				bestValue = newValue;
			}
		}
		return best;
	}
	
	//sprehaja se po listih
	public Tree traverse(Tree node) {
		Tree newNode = node;
		if(newNode.children.isEmpty()) return newNode;
		while(expanded(node)) {
			if(newNode.children.isEmpty()) return newNode;
			newNode = bestUCT(newNode);
		}
		Tree unvisited = pickUnvisited(newNode.children);
		return unvisited;
	}
	
	public Tree monteCarloTreeSearch() {
		while(System.currentTimeMillis() < end) {
			Tree node = traverse(root);
			int simulationResult = result(node);
			backpropagate(node, simulationResult);
		}
		return bestChild(root);
	}
	
}
