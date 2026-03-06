package blackjack.model.strategy;

import carte.model.Carte;
import blackjack.model.*;

/**
 * Stratégie conservative où le robot évite de prendre des risques inutiles (strategie efficace).
 */
public class ConservativeStrategy implements Strategy {

    /**
     * Méthode qui décide du coup à jouer en fonction de la main du joueur et de la carte visible du dealer.
     * Cette stratégie cherche à minimiser les risques, en prenant des décisions prudentes.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le coup à jouer (STAY, SPLIT, DOUBLE, HIT) selon la stratégie conservatrice.
     */
    @Override
    public Move decideMove(PlayerHand hand, Carte carte) {
        int total = hand.count(); 
        int dealerVal = carte.getValeur().valeur; 
        if (hand.canSplit()) {
            if (hand.getHand().getCardAt(0).getValeur().valeur == 1 || 
                hand.getHand().getCardAt(0).getValeur().valeur == 8) {
                return Move.SPLIT;
            }
        }
        
        if (total >= 17) {
            return Move.STAY;
        }
        
        if (total >= 12 && total <= 16) {
            if (dealerVal >= 2 && dealerVal <= 6) {
                return Move.STAY;
            }
        }
        
        if (total == 11 && hand.canDouble() && dealerVal <= 6) {
            return Move.DOUBLE;
        }
        
        return Move.HIT;
    }
}

