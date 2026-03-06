package blackjack.model;

import carte.model.Paquet;

/**
 * Interface représentant un paquet de cartes utilisé dans le jeu de blackjack.
 */
public interface DeckInterface {

    /**
     * Vérifie si le paquet est vide.
     * @return true si le paquet est vide, false sinon.
     */
    boolean isEmpty();

    /**
     * Mélange les cartes du paquet.
     */
    void shuffle();

    /**
     * Retire la première carte du paquet.
     */
    void removeFirstCard();

    /**
     * Distribue une carte à une main.
     * @param hand La main à laquelle distribuer la carte.
     */
    void distribute(Hand hand);

    /**
     * Retourne le paquet de cartes.
     * @return Le paquet de cartes.
     */
    Paquet getDeck();

    /**
     * Retourne la taille du paquet (le nombre de cartes restantes).
     * @return La taille du paquet.
     */
    int getSize();
}

