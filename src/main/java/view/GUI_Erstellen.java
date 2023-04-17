package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import controller.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class GUI_Erstellen extends JFrame {
	private List<JButton> buttonListe = new ArrayList<>();
	private List<String> buttonNamen = new ArrayList<>();
	private HashMap<String, JButton> mappingButtons = new HashMap<String, JButton>();

	
	JFrame frame = new JFrame();
	JFrame frameRegeln = new JFrame();
	JLabel textRegeln = new JLabel();
	JPanel panelSpielbrett = new JPanel();
	JPanel panelRegelwerk = new JPanel();
	JPanel panelMenu = new JPanel();
	JPanel panelSpieleranzeige = new JPanel();
	JLabel spieler = new JLabel();
	ImageIcon icon = new ImageIcon("ReversiBild.png");
	JButton buttonRegeln = new JButton("Regeln");
	Border blackline = BorderFactory.createLineBorder(Color.black, 2);
	Controller controller = new Controller();

	public GUI_Erstellen() {
		guiErstellen();
		buttonNamenErstellen();
		hashMapBefuellen();
		setButtonFarbeSpielstart();
		spielerWechsel(controller.spielerAmZug());
		
		for (JButton button : buttonListe) {
			panelSpielbrett.add(button);
			button.addActionListener(e -> spielFertig(controller.spielBeendet()));
			button.addActionListener(e -> spielerWechsel(controller.spielerAmZug()));
			button.addActionListener(e -> ungueltigerSpielzug());
			button.addActionListener(e -> farbwechsel(controller.zuweisenDerButtonListeAufDasArray(button.getName())));
		}
		buttonRegeln.addActionListener(e -> regelnOeffnen());
		frame.setResizable(false);
		frameRegeln.setResizable(false);
		frame.setLayout(null);
		frameRegeln.setLayout(null);
		frame.setVisible(true);
	}

	
	private void guiErstellen() {
		frame.setIconImage(icon.getImage());
		frameRegeln.setIconImage(icon.getImage());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frameRegeln.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setSize(900, 800);
		frameRegeln.setSize(820, 300);
		spieler.setForeground(Color.white);
		spieler.setFont(new Font("TimesRoman", Font.BOLD, 14));
		buttonRegeln.setFont(new Font("Arial", Font.PLAIN, 20));
		panelSpielbrett.setBounds(185, 1, 700, 760);
		panelRegelwerk.setBounds(3, 0, 800, 260);
		panelRegelwerk.setBorder(blackline);
		panelMenu.setBounds(15, 80, 150, 110);
		panelSpieleranzeige.setBounds(15, 0, 150, 70);
		panelSpieleranzeige.setBorder(blackline);
		frame.setLayout(new BorderLayout());
		frame.add(panelSpielbrett);
		frame.add(panelMenu);
		frame.add(panelSpieleranzeige);
		frameRegeln.add(panelRegelwerk);
		panelMenu.add(buttonRegeln);
		panelRegelwerk.add(textRegeln);
		panelSpieleranzeige.add(spieler);
		panelSpielbrett.setLayout(new GridLayout(8, 8, 3, 3));

	}
	
	// Button Namen für das Spielfeld erstellen und setzten der Buttons auf die
	// ButtonListe
	private void buttonNamenErstellen() {
		for(int i = 1; i < 9; i++) {
			for(int j = 1; j < 9; j++) {
				buttonNamen.add(i + "" + j);
			}
		}
		for (int i = 0; i < 64; i++) {
			JButton button = new JButton();
			button.setName(buttonNamen.get(i));
			buttonListe.add(button);
		}
	}

	// verknüpfen der Namen bzw der daraus resultierenden Positionen mit den
	// einzelnen Buttons aus der ButtonListe
	private void hashMapBefuellen() {
		for (int i = 0; i < 64; i++) {
			mappingButtons.put(buttonNamen.get(i), buttonListe.get(i));
		}
	}

	private JButton zuweisungArrayPositionAufDieButtonListe(int y, int x) {
		int intButtonName = y * 10 + x;
		String buttonName = Integer.toString(intButtonName);
		JButton button = null;
		button = mappingButtons.get(buttonName);
		return button;
	}

	private void setButtonFarbeSpielstart() {
		JButton button = buttonListe.get(27);
		button.setBackground(Color.blue);
		button = buttonListe.get(28);
		button.setBackground(Color.red);
		button = buttonListe.get(35);
		button.setBackground(Color.red);
		button = buttonListe.get(36);
		button.setBackground(Color.blue);
	}

	public void farbwechsel(char[][] spielfeld) {
		for (int y = 1; y < 9; y++) {
			for (int x = 1; x < 9; x++) {
				JButton buttonAusListe = zuweisungArrayPositionAufDieButtonListe(y, x);
				if (spielfeld[y][x] == 'r') {
					buttonAusListe.setBackground(Color.RED);
				} else if (spielfeld[y][x] == 'b') {
					buttonAusListe.setBackground(Color.blue);
				}
			}
		}
	}

	// auswerten des Spielfeldes, wenn Spielfeld voll wird Nachricht ausgegeben
	public void spielFertig(int wert) {
		String fertig = "Fertig";
		if (wert == 0) {
			JOptionPane.showMessageDialog(null, "Rot hat gewonnen!", fertig, JOptionPane.WARNING_MESSAGE);
		} else if (wert == 1) {
			JOptionPane.showMessageDialog(null, "Blau hat gewonnen!", fertig, JOptionPane.WARNING_MESSAGE);
		} else if (wert == 2) {
			JOptionPane.showMessageDialog(null, "Unentschieden!", fertig, JOptionPane.WARNING_MESSAGE);
		}
	}

	// falls ungültig kommt Fehlermeldung
	private void ungueltigerSpielzug() {
		if (!controller.spielzugGueltig()) {
			JOptionPane.showMessageDialog(null, "Spielzug nicht gueltig", "Achtung!", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void spielerWechsel(int player) {
		if (player == 0) {
			panelSpieleranzeige.setBackground(Color.red);
			spieler.setText("<html>Spieler 2<br> Rot ist an der Reihe  </html>");
		} else if (player == 1) {
			panelSpieleranzeige.setBackground(Color.blue);
			spieler.setText("<html>Spieler 1<br> Blau ist an der Reihe </html>");
		}
	}

	private void regelnOeffnen() {
		textRegeln.setText("<html>" + "<font size='4' color='black'> Regeln <br> "
				+ "o	Gespielt wird mit zwei unterschiedlichen Farben. <br> "
				+ "o	Begonnen wird auf den mittleren Spielfeldern mit jeweils zwei Steinen der gleichen Farbe.<br> <&#160> Dabei müssen die Farben in abwechselnder Reihenfolge auf den oben genannten Feldern positioniert werden.<br>"
				+ "o	Welcher Spieler beginnen darf wird anfangs zufällig ausgewählt.<br>"
				+ "o	Ein Spielzug darf nur gemacht werden, wenn die gleiche Farbe mindestens eine andere Farbe umschließt.<br> <&#160> Dabei wird die Farbe, welche eingeschlossen ist, zur anderen Farbe umgewandelt.<br> <&#160> Umgewandelt wird aber nur, vom neu gelegten Stein bis zum bereits gelegten Stein der gleichen Farbe.<br> "
				+ "o	Wenn ein Spieler keinen Zug machen kann, ist automatisch der andere Spieler an der Reihe.<br>"
				+ "o	Eine andere Farbe darf man horizontal, vertikal und diagonal umschließen.<br> "
				+ "o	Gewonnen hat derjenige, der zum Schluss mehr Steine seiner Farbe auf dem Spielfeld liegen hat.<br> "
				+ "o	Das Spiel ist fertig sobald alle Felder befüllt sind.<br> " + "</font>");
		frameRegeln.setVisible(true);
	}
}