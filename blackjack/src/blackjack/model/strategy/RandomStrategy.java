package blackjack.model.strategy;

import carte.model.Carte;
import java.util.Random;
import blackjack.model.*;

/**
 * Stratégie aléatoire où les décisions du robot sont prises de manière aléatoire (strategie pas efficace).
 */
public class RandomStrategy implements Strategy {
    
    private Random random; 
    
    /**
     * Constructeur de la stratégie aléatoire.
     * Initialise l'objet Random pour générer des coups aléatoires.
     */
    public RandomStrategy() {
        this.random = new Random();
    }
    
    /**
     * Méthode qui décide du coup à jouer en fonction de la main du joueur et de la carte du dealer.
     * Les décisions sont prises de manière aléatoire parmi les actions possibles.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le coup à jouer (STAY, SPLIT, DOUBLE, HIT) choisi aléatoirement.
     */
    @Override
    public Move decideMove(PlayerHand hand, Carte carte) {
        int total = hand.count();
        
        if (total == 21) {
            return Move.STAY; 
        }
        
        int moves = 2; 
        boolean split = hand.canSplit(); 
        boolean peutDouble = hand.canDouble(); 
        
        if (split) {
            moves++; 
        }
        if (peutDouble) {
            moves++; 
        }
        
        int choix = random.nextInt(moves);
        
        if (choix == 0) {
            return Move.HIT; 
        } else if (choix == 1) {
            return Move.STAY; 
        } else if (choix == 2 && split) {
            return Move.SPLIT; 
        } else if (choix == 2 && peutDouble) {
            return Move.DOUBLE; 
        } else if (choix == 3 && peutDouble) {
            return Move.DOUBLE; 
        }
        
        return Move.STAY; 
    }
}

