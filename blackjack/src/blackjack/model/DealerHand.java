package blackjack.model;

import carte.util.observer.*;

/**
 * Classe représentant la main du croupier dans le jeu de blackjack.
 * Elle hérite de la classe BlackJackHand et détermine si la main du croupier est terminée 
 * en fonction des règles du jeu.
 */
public class DealerHand extends BlackJackHand {

    /**
     * Constructeur pour créer une nouvelle main pour le croupier.
     */
    public DealerHand() {
        super();
    }

    /**
     * Vérifie si la main du croupier est terminée. Le croupier doit tirer jusqu'à ce qu'il 
     * ait 17 points ou plus.
     * @return true si la main du croupier est terminée, false sinon.
     */
    public boolean isEnd() {
        if (this.count() < 17) {
            return false;
        }
        fireChangement();
        return true;
    }
}

