package blackjack.model;

import carte.model.*;
import carte.util.observer.*;
import java.util.*;

/**
 * Classe abstraite représentant une main de blackjack.
 * Elle implémente l'interface Hand et gère un paquet de cartes, permettant 
 * d'ajouter des cartes, de compter les points, et de vérifier si la main est un blackjack.
 */
abstract class BlackJackHand extends AbstractModeleEcoutable implements Hand {
    
    private Paquet paquet;

    /**
     * Constructeur de la main de blackjack. Crée un paquet vide.
     */
    public BlackJackHand() {
        super(new ArrayList<EcouteurModele>());
        this.paquet = new Paquet(); 
    }
    
    /**
     * Retourne le paquet de cartes de la main.
     * @return Le paquet de cartes.
     */
    public final Paquet getHand() {
        return this.paquet;
    }

    /**
     * Ajoute une carte à la main et notifie les écouteurs du changement.
     * @param c La carte à ajouter.
     */
    public final void add(Carte c) {
        this.paquet.ajoutCarteAbove(c);
        fireChangement();
    }

    /**
     * Retourne la taille de la main (le nombre de cartes).
     * @return La taille de la main.
     */
    public final int getSize() {
        return this.paquet.getPaquetSize();
    }
    
    /**
     * Compte le nombre de points dans la main.
     * Si la somme dépasse 21, on réduit les points en enlevant 10.
     * @return Le total des points de la main.
     */
    public final int count() {
        int nbAs = 0;
        int count = 0;
        
        for (int i = 0; i < this.paquet.getPaquetSize(); i++) {
            count += this.paquet.getCardAt(i).getValeur().valeur;
            if (this.paquet.getCardAt(i).getValeur() == Factory.Valeur.AS)
                nbAs += 1;
        }
        while (count > 21) {
            if (nbAs == 0) {
                break;
            }
            count -= 10;
            nbAs -= 1;
        }
        
        return count;
    }
    
    /**
     * Vérifie si la main est un blackjack.
     * @return true si c'est un blackjack, false sinon.
     */
    public final boolean isBlackjack() {
        return (this.getSize() == 2 && this.count() == 21) ? true : false;
    }
    
    /**
     * Méthode abstraite qui détermine si la partie est terminée. 
     * @return true si la partie est terminée, false sinon.
     */
    public abstract boolean isEnd();
}

