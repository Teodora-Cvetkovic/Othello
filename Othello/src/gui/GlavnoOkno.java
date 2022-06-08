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
	private JButton pomoc;
	private JMenuItem clovekC;
	private JMenuItem racunalnikC;
	private JMenuItem clovekB;
	private JMenuItem racunalnikB;
	
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;

	public GlavnoOkno() {
		
		this.setTitle("Othello");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
	
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);
		
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
		
		// pokaže najboljšo potezo / pokaže možne poteze
		pomoc = dodajGumb(menu_bar, "Pomoč");

		igraClovekRacunalnik = new JMenuItem("Človek vs. računalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Računalnik vs. človek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("Človek vs. človek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Računalnik vs. računalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

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
		status.setText("Izberite igro!");
		
		
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
		
		stDiskov.setText("Črni: 2 * Beli: 2");
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
		Vodja.kdoIgra = new EnumMap<Igralec, KdoIgra>(Igralec.class);
		if(source == clovekC) {
			if( Vodja.vrstaIgralca.containsKey(Igralec.CRNI) && Vodja.kdoIgra.containsKey(Igralec.CRNI)) {
				Vodja.vrstaIgralca.remove(Igralec.CRNI);
				Vodja.kdoIgra.remove(Igralec.CRNI);
			}
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
			String ime = JOptionPane.showInputDialog(this, "VaÃ…Â¡e ime;");
			Vodja.kdoIgra.put(Igralec.CRNI, new KdoIgra(ime));
		}
		else if(source == clovekB) {
			if( Vodja.vrstaIgralca.containsKey(Igralec.BELI) && Vodja.kdoIgra.containsKey(Igralec.BELI)) {
				Vodja.vrstaIgralca.remove(Igralec.CRNI);
				Vodja.kdoIgra.remove(Igralec.CRNI);
			}
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			String ime = JOptionPane.showInputDialog("VaÃ…Â¡e ime;");
			Vodja.kdoIgra.put(Igralec.BELI, new KdoIgra(ime));
		}
		else if(source == racunalnikC) {
			if( Vodja.vrstaIgralca.containsKey(Igralec.CRNI) && Vodja.kdoIgra.containsKey(Igralec.CRNI)) {
				Vodja.vrstaIgralca.remove(Igralec.CRNI);
				Vodja.kdoIgra.remove(Igralec.CRNI);
			}
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R);
			Vodja.kdoIgra.put(Igralec.CRNI, Vodja.racunalnikovaInteligenca);
		}
		else if(source == racunalnikB) {
			if( Vodja.vrstaIgralca.containsKey(Igralec.BELI) && Vodja.kdoIgra.containsKey(Igralec.BELI)) {
				Vodja.vrstaIgralca.remove(Igralec.CRNI);
				Vodja.kdoIgra.remove(Igralec.CRNI);
			}
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			Vodja.kdoIgra.put(Igralec.BELI, Vodja.racunalnikovaInteligenca);
		}
		else if(source == igramo) {
			Vodja.igramoNovoIgro();
		}
		else if(source == pomoc) {
			
		}
		
		else if(e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec, KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.CRNI, new KdoIgra("Človek"));
			Vodja.kdoIgra.put(Igralec.BELI, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.CRNI, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.BELI, new KdoIgra("Človek")); 
			Vodja.igramoNovoIgro();
		}
		else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.CRNI, new KdoIgra("Človek")); 
			Vodja.kdoIgra.put(Igralec.BELI, new KdoIgra("Človek"));
			Vodja.igramoNovoIgro();
		}
		else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.CRNI, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.BELI, Vodja.racunalnikovaInteligenca); 
			Vodja.igramoNovoIgro();
		}
		else if(e.getSource() == pomoc) {
			
		}
	}

	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU: 
				status.setText("Na potezi je " + Vodja.igra.naPotezi.nasprotni().getIgralca() + 
						" - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi.nasprotni())); 
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + " * Beli: " + Vodja.igra.steviloBelih);
				break;
			case ZMAGA_CRNI: 
				status.setText("Zmagal je Črni - " + 
						Vodja.kdoIgra.get(Vodja.igra.naPotezi.nasprotni()));
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + " * Beli: " + Vodja.igra.steviloBelih);
				break;
			case ZMAGA_BELI: 
				status.setText("Zmagal je Beli - " + 
						Vodja.kdoIgra.get(Vodja.igra.naPotezi.nasprotni()));
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + " * Beli: " + Vodja.igra.steviloBelih);
				break;
			case BLOKIRANO:
				status.setText("Blokirano! Na potezi je " + Vodja.igra.naPotezi.getIgralca() + 
						" - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi)); 
				stDiskov.setText("Črni: " + Vodja.igra.steviloCrnih + " * Beli: " + Vodja.igra.steviloBelih);
				break;
			}
		}
		polje.repaint();
	}
	
}
