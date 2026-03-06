package blackjack.model;

import carte.model.Paquet;
import carte.model.Carte;

/**
 * Interface représentant une main de cartes dans le jeu de blackjack.
 */
public interface Hand {
    
    /**
     * Retourne le paquet de cartes de la main.
     * @return Le paquet de cartes.
     */
    Paquet getHand();

    /**
     * Ajoute une carte à la main.
     * @param c La carte à ajouter.
     */
    void add(Carte c);

    /**
     * Retourne la taille de la main (le nombre de cartes).
     * @return La taille de la main.
     */
    int getSize();

    /**
     * Vérifie si la main est un blackjack.
     * @return true si c'est un blackjack, false sinon.
     */
    boolean isBlackjack();

    /**
     * Compte le nombre de points dans la main.
     * @return Le total des points de la main.
     */
    int count();

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est terminée, false sinon.
     */
    boolean isEnd();
}
