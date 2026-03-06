package blackjack.model.strategy;

import carte.model.Carte;
import carte.model.Factory;
import blackjack.model.*;

/**
 * Stratégie agressive où le robot prend plus de risques pour obtenir les maximales gains.
 */
public class AggresiveStrategy implements Strategy {

    /**
     * Méthode qui décide du coup à jouer en fonction de la main du joueur et de la carte visible du dealer.
     * Cette stratégie est plus risquée, avec des choix comme doubler ou frapper plus souvent.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le coup à jouer (STAY, SPLIT, DOUBLE, HIT).
     */
    @Override
    public Move decideMove(PlayerHand hand, Carte carte) {
        int total = hand.count();
        int dealerVal = carte.getValeur().valeur; 
        if (hand.canSplit()) {
            int paireVal = hand.getHand().getCardAt(0).getValeur().valeur;
            if (paireVal != 10 && paireVal != 5) {
                return Move.SPLIT; 
            }
        }
        if (hand.canDouble() && hand.getHand().getPaquetSize() == 2) {
            if (total >= 9 && total <= 11) {
                return Move.DOUBLE; 
            }
            
            if (hasAce(hand) && total >= 13 && total <= 18) {
                return Move.DOUBLE; 
            }
        }
        
        if (total <= 16) {
            return Move.HIT;
        }
        
        if (total == 17 && (dealerVal == 1 || dealerVal >= 10)) {
            return Move.HIT;
        }
        
        return Move.STAY;
    }
    
    /**
     * Vérifie si le joueur a un As dans sa main.
     * 
     * @param hand La main du joueur.
     * @return true si la main contient un As, sinon false.
     */
    private boolean hasAce(PlayerHand hand) {
        for (int i = 0; i < hand.getHand().getPaquetSize(); i++) {
            if (hand.getHand().getCardAt(i).getValeur() == Factory.Valeur.AS) {
                return true; 
            }
        }
        return false;
    }
}

