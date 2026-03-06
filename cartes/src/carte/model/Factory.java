package carte.model;

import java.util.ArrayList;

/**
 * Classe utilitaire permettant de créer différents types de paquets de cartes.
 */
public class Factory {
	
    /**
     * Couleurs possibles d'une carte.
     */
	public enum Couleur {
		COEUR, PIQUE, TREFLE, CARREAU;
	}

    /**
     * Valeurs possibles d'une carte, associées à leur valeur numérique.
     */
	public enum Valeur {
		DEUX(2),
		TROIS(3),
		QUATRE(4),
		CINQ(5),
		SIX(6),
		SEPT(7),
		HUIT(8),
		NEUF(9),
		DIX(10),
		VALET(10),
		DAME(10),
		ROI(10),
		AS(11);

	    public final int valeur;

	    private Valeur(int valeur) {
	        this.valeur = valeur;
	    }
	}

    /**
     * Crée un paquet de blackjack composé de 6 jeux de 52 cartes.
     * @return un paquet de 312 cartes
     */
	public static Paquet createPaquetBlackjack() {
		ArrayList<Carte> cartes = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			for (Couleur c : Couleur.values()) {
				for (Valeur v : Valeur.values()) {
					cartes.add(new Carte(v, c));
				}
			}
		}
		return new Paquet(cartes);
	}

    /**
     * Crée un paquet classique de 52 cartes.
     * @return un paquet de 52 cartes
     */
	public static Paquet createPaquet52() {
		ArrayList<Carte> cartes = new ArrayList<>();
		
		for (Couleur c : Couleur.values()) {
			for (Valeur v : Valeur.values()) {
				cartes.add(new Carte(v, c));
			}
		}
		return new Paquet(cartes);
	}

    /**
     * Crée un paquet de 32 cartes (du 7 à l'As).
     * @return un paquet de 32 cartes
     */
	public static Paquet createPaquet32() {
		ArrayList<Carte> cartes = new ArrayList<>();
		
		for (Couleur c : Couleur.values()) {
			for (int i = 5; i <= 12; i++) {
				cartes.add(new Carte(Valeur.values()[i], c));
			}
		}
		return new Paquet(cartes);
	}
}

