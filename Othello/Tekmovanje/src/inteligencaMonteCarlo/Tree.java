package inteligencaMonteCarlo;

import java.util.ArrayList;
import java.util.List;

import logika.Igra;
import splosno.Poteza;

public class Tree {

	public Igra game;
	public int visits;
	public int value;
	public Tree parent;
	public List<Tree> children;
	public boolean isVisited = false;
	public Poteza p;
	
	public Tree(Igra game, Poteza p, int visits, int value, List<Tree> children) {
		this.game = game;
		this.p = p;
		this.visits = visits;
		this.value = value;
		this.children = children;
	}
	
	public Tree(Igra game) {
		this(game, new Poteza(0, 0), 0, 0, new ArrayList<Tree>());
	}
	
	public boolean isRoot() {
		return (parent == null);
	}
	
	public void setVisit() {
		isVisited = true;
	}
	
}
