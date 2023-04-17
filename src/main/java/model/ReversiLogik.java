package model;

import java.util.Random;

public class ReversiLogik {
	private int x;
	private int y;
	private int counterAnGueltigenSpielzuegen;
	private int spieler;
	private char spielerFarbe;

	private char[][] spielfeld = new char[10][10];

	public ReversiLogik() {
		randomSpieler();
		spielerauswahl();
		spielfeldBefuellen();
	}

	private void spielfeldBefuellen() {
		for (int yz = 0; yz <= 9; yz++) {
			for (int xz = 0; xz <= 9; xz++) {
				if (yz == 0 || yz == 9) {
					spielfeld[yz][xz] = '*';
				} else if (xz == 0 || xz == 9) {
					spielfeld[yz][xz] = '*';
				} else {
					spielfeld[yz][xz] = '-';
				}
			}
		}
		spielfeld[4][4] = 'b';
		spielfeld[4][5] = 'r';
		spielfeld[5][4] = 'r';
		spielfeld[5][5] = 'b';
	}

	// zufälliges auswählen des Anfangspielers
	private void randomSpieler() {
		Random random = new Random();
		spieler = random.nextInt(2);
	}

	// Spieler 0/2 ist rot, Spieler 1 ist blau
	public void spielerauswahl() {
		if (spieler == 0) {
			spielerFarbe = 'r';
		} else {
			spielerFarbe = 'b';
		}
	}

	public int spielerwechsel() {
		if (spieler == 0) {
			spieler = 1;
			return 1;
		} else {
			spieler = 0;
			return 0;
		}
	}

	// 0 rot 1 blau 2 unentschieden 3 Spiel nicht fertig
	public int spielBeendet() {
		int counterBlau = 0;
		int counterRot = 0;
		for (int yz = 1; yz < 9; yz++) {
			for (int xz = 1; xz < 9; xz++) {
				if (spielfeld[yz][xz] == 'b') {
					counterBlau++;
				} else if (spielfeld[yz][xz] == 'r') {
					counterRot++;
				}
			}
		}
		if (counterBlau + counterRot == 64) {
			if (counterBlau == counterRot) {
				return 2;
			} else if (counterBlau > counterRot) {
				return 1;
			} else {
				return 0;
			}
		}
		return 3;
	}

	public int anzahlAnGueltigenSpielzuegen() {
		return counterAnGueltigenSpielzuegen;
	}

	
	public boolean fehlermeldungWennUngueltigerZug(int wert) {
		if (wert == 0) {
			return false;
		}
		return true;
	}

	// Spielzüge------------------------------------------------------------------------------------------

	// Diese Funktionen prüfen immer in die angegebene Richtung auf einen gültigen
	// Spielzug. Wenn er gültig ist, wird auch gleichzeitig
	// der Spielzug durchgeführt und die Steine farblich gedreht
	private boolean pruefenObGesetztesFeldLeer() {
		if (spielfeld[y][x] == '-') {
			return true;
		}
		return false;
	}

	private boolean spielzugNachRechtsPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int range = x;
		do {
			range++;
		} while (spielfeld[y][range] != farbe && spielfeld[y][range] != '*' && spielfeld[y][range] != '-');

		if (range - x <= 1 || spielfeld[y][range] != farbe) {
			return false;
		} else {
			for (int j = x; j < range; j++) {
				spielfeld[y][j] = farbe;
			}
			return true;
		}
	}

	private boolean spielzugNachLinksPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int range = x;
		do {
			range--;
		} while (spielfeld[y][range] != farbe && spielfeld[y][range] != '*' && spielfeld[y][range] != '-');

		if (x - range <= 1 || spielfeld[y][range] != farbe) {
			return false;
		} else {
			for (int j = range + 1; j <= x; j++) {
				spielfeld[y][j] = farbe;
			}
			return true;
		}
	}

	private boolean spielzugNachObenPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int range = y;
		do {
			range--;
		} while (spielfeld[range][x] != farbe && spielfeld[range][x] != '*' && spielfeld[range][x] != '-');

		if (y - range <= 1 || spielfeld[range][x] != farbe) {
			return false;
		} else {
			for (int j = range + 1; j <= y; j++) {
				spielfeld[j][x] = farbe;
			}
			return true;
		}
	}

	private boolean spielzugNachUntenPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int range = y;
		do {
			range++;
		} while (spielfeld[range][x] != farbe && spielfeld[range][x] != '*' && spielfeld[range][x] != '-');

		if (range - y <= 1 || spielfeld[range][x] != farbe) {
			return false;
		} else {
			for (int j = y; j < range; j++) {
				spielfeld[j][x] = farbe;
			}
			return true;
		}
	}

	private boolean spielzugNachObenLinksPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int rangey = y;
		int rangex = x;
		do {
			rangey--;
			rangex--;
		} while (spielfeld[rangey][rangex] != farbe && spielfeld[rangey][rangex] != '*'
				&& spielfeld[rangey][rangex] != '-');
		if (y - rangey <= 1 || spielfeld[rangey][rangex] != farbe) {
			return false;
		} else {
			int xMove = x;
			for (int j = y; j > rangey; j--) {
				spielfeld[j][xMove] = farbe;
				xMove--;
			}
			return true;
		}
	}

	private boolean spielzugNachUntenRechtsPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int rangey = y;
		int rangex = x;
		do {
			rangey++;
			rangex++;
		} while (spielfeld[rangey][rangex] != farbe && spielfeld[rangey][rangex] != '*'
				&& spielfeld[rangey][rangex] != '-');
		if (rangey - y <= 1 || spielfeld[rangey][rangex] != farbe) {
			return false;
		} else {
			int xMove = x;
			for (int j = y; j < rangey; j++) {
				spielfeld[j][xMove] = farbe;
				xMove++;
			}
			return true;
		}
	}

	private boolean spielzugNachObenRechtsPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int rangey = y;
		int rangex = x;
		do {
			rangey--;
			rangex++;
		} while (spielfeld[rangey][rangex] != farbe && spielfeld[rangey][rangex] != '*'
				&& spielfeld[rangey][rangex] != '-');

		if (y - rangey <= 1 || spielfeld[rangey][rangex] != farbe) {
			return false;
		} else {
			int xMove = x;
			for (int j = y; j > rangey; j--) {
				spielfeld[j][xMove] = farbe;
				xMove++;
			}
			return true;
		}
	}

	private boolean spielzugNachUntenLinksPruefenUndSetzen() {
		char farbe = spielerFarbe;
		int rangey = y;
		int rangex = x;

		do {
			rangey++;
			rangex--;
		} while (spielfeld[rangey][rangex] != farbe && spielfeld[rangey][rangex] != '*'
				&& spielfeld[rangey][rangex] != '-');

		if (rangey - y <= 1 || spielfeld[rangey][rangex] != farbe) {
			return false;
		} else {
			int xMove = x;
			for (int j = y; rangey > j; j++) {
				spielfeld[j][xMove] = farbe;
				xMove--;
			}
			return true;
		}
	}

	// nach Spielerwechsel wird auf den neuen Spieler geprüft, ob dieser einen Spielzug machen kann, sonst ist automatisch
	// der andere Spieler wieder an der Reihe
	private boolean pruefenObSpielzugMoeglich() {
		int speicherx = x;
		int speichery = y;
		int counter = 0;
		char[][] spielfeldk = new char[10][10];
		for(int yz = 0; yz < 10; yz++) {
			for(int xz = 0; xz < 10; xz++) {
				spielfeldk[yz][xz] = spielfeld[yz][xz];
			}
		}
		
		for (int i = 1; i < 9; i++) {
			boolean falsch = true;
			y = i;
			for (int j = 1; j < 9; j++) {
				x = j;
				counter = counterFuerAlleSpielzuege();
				if (counter > 0) {
					
					falsch = false;
					break;
				}
			}
			if(!falsch) {
				break;
			}
		}
		for(int yz = 0; yz < 10; yz++) {
			for(int xz = 0; xz < 10; xz++) {
				spielfeld[yz][xz] = spielfeldk[yz][xz];
			}
		}
		x = speicherx;
		y = speichery;

		if(counter > 0) {
			return true;
		} else {
			return false;
		}
	}
	// -------------------------------------------------------------------------------------

	private int counterFuerAlleSpielzuege() {
		int counter = 0;
		if (spielzugNachRechtsPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachLinksPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachUntenPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachObenLinksPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachObenPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachUntenLinksPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachUntenRechtsPruefenUndSetzen()) {
			counter++;
		}
		if (spielzugNachObenRechtsPruefenUndSetzen()) {
			counter++;
		}
		return counter;
	}

	public char[][] alleSpielzuege() {
		if (pruefenObGesetztesFeldLeer()) {
			int counter = counterFuerAlleSpielzuege();
			counterAnGueltigenSpielzuegen = counter;
			if (counter == 0) {
				fehlermeldungWennUngueltigerZug(counter);
			} else {
				spielerwechsel();
				spielerauswahl();
				if(!pruefenObSpielzugMoeglich()) {
					spielerwechsel();
					spielerauswahl();
				}
			}
			return spielfeld;
		} else {
			return spielfeld;
		}
	}

	// getter/setter ---------------------------------------------
	// werden hauptsächlich zum Testen benötigt
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpieler() {
		return spieler;
	}

	public void setSpieler(char spielerFarbe) {
		this.spielerFarbe = spielerFarbe;
	}

	public void setSpielfeld(char[][] spielfeld) {
		this.spielfeld = spielfeld;
	}

	public char[][] getSpielfeld() {
		return spielfeld;
	}

	public char getSpielfeldFelder(int y, int x) {
		return spielfeld[y][x];
	}

	public void spielfeldFuellen() {
		spielfeldBefuellen();
	}

	public void setSpielfeldFeld(int y, int x, char farbe) {
		spielfeld[y][x] = farbe;
	}

	public boolean getSpielzugNachRechts() {
		return spielzugNachRechtsPruefenUndSetzen();
	}

	public boolean getSpielzugNachLinks() {
		return spielzugNachLinksPruefenUndSetzen();
	}

	public boolean getSpielzugNachOben() {
		return spielzugNachObenPruefenUndSetzen();
	}

	public boolean getSpielzugNachUnten() {
		return spielzugNachUntenPruefenUndSetzen();
	}

	public boolean getSpielzugNachLinksOben() {
		return spielzugNachObenLinksPruefenUndSetzen();
	}

	public boolean getSpielzugNachRechtsUnten() {
		return spielzugNachUntenRechtsPruefenUndSetzen();
	}

	public boolean getSpielzugNachRechtsOben() {
		return spielzugNachObenRechtsPruefenUndSetzen();
	}

	public boolean getSpielzugNachLinksUnten() {
		return spielzugNachUntenLinksPruefenUndSetzen();
	}
}