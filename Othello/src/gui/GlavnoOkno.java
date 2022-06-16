package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import logika.Igralec;
import splosno.KdoIgra;
import vodja.Vodja;
import vodja.VrstaIgralca;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{

	protected IgralnoPolje polje;
	
	private JLabel status;
	private JLabel stDiskov;
	private JButton igramo;
	private JButton stop;
	private JMenuItem clovekC;
	private JMenuItem racunalnikC;
	private JMenuItem clovekB;
	private JMenuItem racunalnikB;

	public GlavnoOkno() {
		
		this.setTitle("Othello");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
	
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		
		// meni za izbiro črnega igralca
		JMenu crniIgralec = dodajMenu(menu_bar, "Izberite črnega igralca");
		
		
		// meni za izbiro belega igralca
		JMenu beliIgralec = dodajMenu(menu_bar, "Izberite belega igralca");
		
		
		// črni igralec je človek
		clovekC = dodajVrstico(crniIgralec, "Človek");
		
		
		// črni igralec je računalnik
		racunalnikC = dodajVrstico(crniIgralec, "Računalnik");
		
		
		// beli igralec je človek
		clovekB = dodajVrstico(beliIgralec, "Človek");
		
		
		// beli igralec je računalnik
		racunalnikB = dodajVrstico(beliIgralec, "Računalnik");
		
		
		// gumb, ki požene igro
		igramo = dodajGumb(menu_bar, "Nova igra");
		
		
		// gumb, ki ustavi igro
		stop = dodajGumb(menu_bar, "Ustavi igro");

		
		// igralno polje
		polje = new IgralnoPolje();
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		status.setText("Izberite igralce in kliknite \"Nova igra\"!");
		
		
		// Šteje diske
		stDiskov = new JLabel();
		stDiskov.setFont(new Font(status.getFont().getName(),
			    				  status.getFont().getStyle(),
			    				  20));
		GridBagConstraints stDiskov_layout = new GridBagConstraints();
		stDiskov_layout.gridx = 0;
		stDiskov_layout.gridy = 2;
		stDiskov_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(stDiskov, stDiskov_layout);
		
		stDiskov.setText("Črni: 2  ||  Beli: 2");
	}
	
	
	// doda podmeni
	private JMenuItem dodajVrstico (JMenu menu, String naslov) {
		JMenuItem menuNovi = new JMenuItem(naslov);
		menu.add(menuNovi);
		menuNovi.addActionListener(this);
		return menuNovi;
	}
	
	
	// doda meni
	private JMenu dodajMenu (JMenuBar menubar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menubar.add(menu);
		return menu;
	}
	
	
	// doda gumb
	private JButton dodajGumb(JMenuBar menubar, String naslov) {
		JButton gumb = new JButton(naslov);
		menubar.add(gumb);
		gumb.addActionListener(this);
		return gumb;
	}
	
	
	// pomožni množici, kjer shranjujemo, vrsto igralca in kdo je na potezi
	EnumMap<Igralec, VrstaIgralca> pomozna1 = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
	EnumMap<Igralec, KdoIgra> pomozna2 = new EnumMap<Igralec, KdoIgra>(Igralec.class);
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == clovekC) {
			if( pomozna1.containsKey(Igralec.CRNI) && pomozna2.containsKey(Igralec.CRNI)) {
				pomozna1.remove(Igralec.CRNI);
				pomozna2.remove(Igralec.CRNI);
			}
			pomozna1.put(Igralec.CRNI, VrstaIgralca.C);
			String ime = JOptionPane.showInputDialog(this, "Vaše ime;");
			pomozna2.put(Igralec.CRNI, new KdoIgra(ime));
		}
		else if(source == clovekB) {
			if( pomozna1.containsKey(Igralec.BELI) && pomozna2.containsKey(Igralec.BELI)) {
				pomozna1.remove(Igralec.CRNI);
				pomozna2.remove(Igralec.CRNI);
			}
			pomozna1.put(Igralec.BELI, VrstaIgralca.C);
			String ime = JOptionPane.showInputDialog("Vaše ime;");
			pomozna2.put(Igralec.BELI, new KdoIgra(ime));
		}
		else if(source == racunalnikC) {
			if( pomozna1.containsKey(Igralec.CRNI) && pomozna2.containsKey(Igralec.CRNI)) {
				pomozna1.remove(Igralec.CRNI);
				pomozna2.remove(Igralec.CRNI);
			}
			pomozna1.put(Igralec.CRNI, VrstaIgralca.R);
			pomozna2.put(Igralec.CRNI, Vodja.racunalnikovaInteligenca);
		}
		else if(source == racunalnikB) {
			if( pomozna1.containsKey(Igralec.BELI) && pomozna2.containsKey(Igralec.BELI)) {
				pomozna1.remove(Igralec.CRNI);
				pomozna2.remove(Igralec.CRNI);
			}
			pomozna1.put(Igralec.BELI, VrstaIgralca.R);
			pomozna2.put(Igralec.BELI, Vodja.racunalnikovaInteligenca);
		}
		else if(source == igramo) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, pomozna1.get(Igralec.CRNI));
			Vodja.vrstaIgralca.put(Igralec.BELI, pomozna1.get(Igralec.BELI));
			Vodja.kdoIgra = new EnumMap<Igralec, KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.CRNI, pomozna2.get(Igralec.CRNI));
			Vodja.kdoIgra.put(Igralec.BELI, pomozna2.get(Igralec.BELI));
			Vodja.igramoNovoIgro();
		}
		else if(source == stop) {
			Vodja.vTeku = false;
			pomozna1.clear();
			pomozna2.clear();
		}
	}

	
	// osveži vmesnik z novim stanjem igre
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case NEODLOCENO:
				status.setText("Neodločeno!");
				pomozna1.clear();
				pomozna2.clear();
				break;
			case V_TEKU: 
				status.setText("Na potezi je " + Vodja.igra.naPotezi.getIgralca() + 
						" - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi)); 
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + "  ||  Beli: " + Vodja.igra.steviloBelih);
				break;
			case ZMAGA_CRNI: 
				status.setText("Zmagal je ČRNI igralec - " + 
						Vodja.kdoIgra.get(Vodja.igra.naPotezi.nasprotni()));
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + "  ||  Beli: " + Vodja.igra.steviloBelih);
				pomozna1.clear();
				pomozna2.clear();
				break;
			case ZMAGA_BELI: 
				status.setText("Zmagal je BELI igralec - " + 
						Vodja.kdoIgra.get(Vodja.igra.naPotezi.nasprotni()));
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + "  ||  Beli: " + Vodja.igra.steviloBelih);
				pomozna1.clear();
				pomozna2.clear();
				break;
			case BLOKIRANO:
				status.setText("Blokirano! Na potezi je " + Vodja.igra.naPotezi.getIgralca() + 
						" - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi)); 
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + "  ||  Beli: " + Vodja.igra.steviloBelih);
				break;
			}
		}
		polje.repaint();
	}
	
}
