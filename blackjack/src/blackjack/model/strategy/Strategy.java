package blackjack.model.strategy;

import carte.model.Carte;
import blackjack.model.*;

/**
 * Cet interface représente la stratégie d'un joueur de type "robot" dans le jeu de Blackjack.
 */
public interface Strategy {

    /**
     * Enum représentant les différents types de coups qu'un joueur peut effectuer.
     */
    enum Move {
        STAY, 
        SPLIT, 
        DOUBLE,
        HIT     
    }
    
    /**
     * Méthode permettant de décider de l'action à réaliser en fonction de la main du joueur et de la carte du dealer.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le coup à effectuer (STAY, SPLIT, DOUBLE, HIT).
     */
    Move decideMove(PlayerHand hand, Carte carte);
}

