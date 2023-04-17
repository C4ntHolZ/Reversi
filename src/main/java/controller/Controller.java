package controller;


import model.ReversiLogik;

public class Controller {
	
	ReversiLogik logik = new ReversiLogik();
	
	// Abfangen des Namens vom Button. Zerlegen in zwei einzelne Zahlen, um diese
	// dann direkt ins Array einsetzen zu können
	public char[][]zuweisenDerButtonListeAufDasArray(String name) {
		int position = Integer.valueOf(name);
		logik.setX(position % 10);
		logik.setY(position / 10);
		return ausfuehrenSpiellogik();
	}
	
	public char[][] ausfuehrenSpiellogik() {
		return logik.alleSpielzuege();
	}
	
	public int spielBeendet() {
		return logik.spielBeendet();
	}
	
	public boolean spielzugGueltig() {
		return logik.fehlermeldungWennUngueltigerZug(logik.anzahlAnGueltigenSpielzuegen());
	}
	
	public int spielerAmZug() {
		return logik.getSpieler();
	}
}