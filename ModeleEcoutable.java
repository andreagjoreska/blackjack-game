package carte.model;

import java.util.ArrayList;
import java.util.Collections;
import carte.util.observer.*;

/**
 * Représente un paquet de cartes pouvant être manipulé (pioche, mélange, coupe...).
 */
public class Paquet extends AbstractModeleEcoutable {
    
    public ArrayList<Carte> paquet;

    /**
     * Crée un paquet à partir d'une liste de cartes.
     * @param paquet liste de cartes initiale
     */
	public Paquet(ArrayList<Carte> paquet) {
		super(new ArrayList<>());
		this.paquet = paquet;
	}

    /**
     * Crée un paquet vide.
     */
	public Paquet() {
		super(new ArrayList<>());
		this.paquet = new ArrayList<>();
	}

    /**
     * Ajoute une carte au-dessus du paquet.
     * @param c1 carte à ajouter
     */
	public void ajoutCarteAbove(Carte c1) {
		this.paquet.add(c1);
		fireChangement();
	}

    /**
     * Ajoute une carte en bas du paquet.
     * @param c carte à ajouter
     */
	public void ajoutCarteBelow(Carte c) {
		this.paquet.add(0, c);
		fireChangement();
	}

    /**
     * Tire une carte aléatoire dans le paquet.
     * @return la carte tirée
     */
	public Carte getRandomCard() {
		int index = (int)(Math.random() * this.paquet.size());
		return this.getCardAt(index);
	}

    /**
     * Retourne la carte à une position donnée.
     * @param i index de la carte
     * @return carte à l'index i
     */
	public Carte getCardAt(int i) {
		return this.paquet.get(i);
	}

    /**
     * Mélange les cartes du paquet.
     */
	public void shuffleGame() {
		Collections.shuffle(this.paquet);
		fireChangement();
	}

    /**
     * Coupe le paquet en deux à une position aléatoire.
     * @return la deuxième partie du paquet après la coupe
     */
	public Paquet couperPaquet() {
		int index = (int)((Math.random() * ((this.paquet.size() - 3) - 3)) + 3);

		Paquet newPaquet = new Paquet(new ArrayList<>(this.paquet.subList(index, this.paquet.size())));
		this.paquet = new ArrayList<>(this.paquet.subList(0, index));

		fireChangement();
		return newPaquet;	
	}

    /**
     * @return la liste interne des cartes du paquet
     */
	public ArrayList<Carte> getPaquet() {
		return this.paquet;
	}

    /**
     * Retire et retourne la première carte du paquet.
     * @return la carte retirée
     */
	public Carte removeFirstCard() {
		Carte card = this.removeCarte(0);
		fireChangement();
		return card;
	}

    /**
     * Retire et retourne la carte à une position donnée.
     * @param i index de la carte
     * @return la carte retirée
     */
	public Carte removeCarte(int i) {
		Carte card = this.paquet.remove(i);
		fireChangement();
		return card;
	}

    /**
     * @return le nombre de cartes dans le paquet
     */
	public int getPaquetSize() {
		return this.paquet.size();
	}

    /**
     * Distribue la première carte du paquet à un autre paquet.
     * @param p paquet recevant la carte
     */
	public void distribuer(Paquet p) {
		p.ajoutCarteAbove(this.removeFirstCard());
		fireChangement();
	}

    /**
     * @return représentation textuelle du paquet
     */
	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < this.paquet.size(); i++) {
			res += this.paquet.get(i) + ", ";
		}
		return res;
	}
}

