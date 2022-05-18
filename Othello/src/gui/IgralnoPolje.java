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

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener{
	
	private final static double LINE_WIDTH = 0.08;
	private double SQUARE_WIDTH = Math.min(getHeight(), getWidth()) / 8;
	private final static double PADDING = 0.18;
	
	public IgralnoPolje() {
		setBackground(new Color(36, 120, 34));
		this.addMouseListener(this);
	}
	
	private void paintC(Graphics2D g2, int i, int j) {
		double w = SQUARE_WIDTH;
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // sirina X
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.drawOval((int)x, (int)y, (int)r , (int)r);
		g2.fillOval((int)x, (int)y, (int)r, (int)r);
	}
	
	private void paintO(Graphics2D g2, int i, int j) {
		double w = SQUARE_WIDTH;
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.drawOval((int)x, (int)y, (int)d, (int)d);
		g2.fillOval((int)x, (int)y, (int)d, (int)d);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		double w = SQUARE_WIDTH;
		
		Polje[][] tabla;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
