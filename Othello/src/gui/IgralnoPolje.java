package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Polje;
import splosno.Poteza;
import vodja.Vodja;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener{
	
	private final static double LINE_WIDTH = 0.08;
	private final static double PADDING = 0.18;
	
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / 8;
	}
	
	public IgralnoPolje() {
		setBackground(new Color(36, 120, 34));
		this.addMouseListener(this);
	}
	
	// nariše črne diske
	private void paintC(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer diska
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.drawOval((int)x, (int)y, (int)r , (int)r);
		g2.fillOval((int)x, (int)y, (int)r, (int)r);
	}
	
	// nariše bele diske
	private void paintB(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer diska
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.drawOval((int)x, (int)y, (int)d, (int)d);
		g2.fillOval((int)x, (int)y, (int)d, (int)d);
	}
	
	// nariše možne poteze
	private void paintM(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer diska
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH / 2)));
		g2.drawOval((int)x, (int)y, (int)d, (int)d);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}
	
	// narišemo trenutno stanje igre
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;	
		double w = squareWidth();
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		
		// narišemo tablo
		for (int i = 1; i < 8; i++) {
			g2.drawLine((int)(i * w),
					    (int)(0),
					    (int)(i * w),
					    (int)(8 * w));
			g2.drawLine((int)(0),
					    (int)(i * w),
					    (int)(8 * w),
					    (int)(i * w));
		}
		
		// narišemo postavljene diske
		Polje[][] tabla;
		if(Vodja.igra != null) {
			tabla = Vodja.igra.tabla;
			for(int i = 0; i < 8; i ++) {
				for(int j = 0; j < 8; j++) {
					switch(tabla[i][j]) {
					case CRNO: paintC(g2, i, j); break;
					case BELO: paintB(g2, i, j); break;
					default: break;
					}
				}
			}
			
			// narišemo možne poteze
			for(Poteza p : Vodja.igra.izracunajMozne()) {
				paintM(g2, p.getX(), p.getY());
			}
		}
	}
	

	// nariše odigrano potezo
	@Override
	public void mouseClicked(MouseEvent e) {
		if(Vodja.clovekNaVrsti) {
			int x = e.getX();
			int y = e.getY();
			int w = (int) squareWidth();
			int i = x / w ;
			double di = (x % w) / squareWidth();
			int j = y / w ;
			double dj = (y % w) / squareWidth();
			if (0 <= i && i < 8 &&
					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
					0 <= j && j < 8 && 
					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
				Vodja.igrajClovekovoPotezo (new Poteza(i, j));
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
