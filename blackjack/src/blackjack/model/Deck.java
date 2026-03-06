package blackjack.model;

import carte.model.*;
import java.util.*;
import carte.util.observer.*;

/**
 * Classe implémente l'interface DeckInterface et gère les opérations 
 * comme mélanger, retirer la première carte, distribuer des cartes et vérifier si le paquet est vide.
 */
public class Deck extends AbstractModeleEcoutable implements DeckInterface {
    private Paquet deck;

    /**
     * Constructeur pour créer un nouveau paquet de cartes, le mélanger, 
     * et retirer la première carte du paquet.
     */
    public Deck() {
        super(new ArrayList<EcouteurModele>());
        this.deck = Factory.createPaquetBlackjack();
        this.shuffle();
        this.removeFirstCard();
    }

    /**
     * Vérifie si le paquet est vide.
     * @return true si le paquet est vide, false sinon.
     */
    public boolean isEmpty() {
        return this.deck.getPaquetSize() < 1;
    }

    /**
     * Mélange les cartes du paquet.
     */
    public void shuffle() {
        this.deck.shuffleGame();
        fireChangement();
    }

    /**
     * Retire la première carte du paquet.
     */
    public void removeFirstCard() {
        this.deck.removeFirstCard();
        fireChangement();
    }

    /**
     * Distribue une carte à une main donnée.
     * @param hand La main à laquelle distribuer la carte.
     */
    public void distribute(Hand hand) {
        this.deck.distribuer(hand.getHand());
        fireChangement();
    }

    /**
     * Retourne le paquet de cartes.
     * @return Le paquet de cartes.
     */
    public Paquet getDeck() {
        return this.deck; 
    }

    /**
     * Retourne la taille du paquet.
     * @return La taille du paquet.
     */
    public int getSize() {
        return this.deck.getPaquetSize(); 
    }
}
