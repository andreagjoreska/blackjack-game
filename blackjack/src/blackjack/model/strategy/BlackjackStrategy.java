package blackjack.model.strategy;

import carte.model.*;
import blackjack.model.*;


public class BlackjackStrategy implements Strategy{

	/**
     * Décide du coup à jouer en utilisant la stratégie de Blackjack en fonction de la main du joueur et de la carte du dealer.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le coup à jouer (STAY, SPLIT, DOUBLE, HIT) selon la stratégie.
     */
	@Override
    public Move decideMove(PlayerHand hand, Carte carte) {
        return getBestMove(hand, carte);
    }
    
    
    /**
     * Calcule le meilleur coup en fonction de la main du joueur et de la carte du dealer.
     * Cette méthode prend en compte les paires, les As et toutes les autres combinaisons.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le meilleur coup (STAY, SPLIT, DOUBLE, HIT).
     */
	public static Move getBestMove(PlayerHand hand, Carte carte) {
		if (hand.canSplit()) {
			return BlackjackStrategy.getBestMoveWithPair(hand, carte);
		} else if (hand.getHand().getPaquetSize() == 2 && hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.AS) {
			return BlackjackStrategy.getBestMoveWithAnAS(hand, carte, 1);
		} else if (hand.getHand().getPaquetSize() == 2 && hand.getHand().getCardAt(1).getValeur() == Factory.Valeur.AS) {
			return BlackjackStrategy.getBestMoveWithAnAS(hand, carte, 0);
		}
		return BlackjackStrategy.getBestMoveMismatch(hand, carte);
	}

	/**
     * Calcule le meilleur coup à jouer lorsqu'une paire est dans la main du joueur.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le meilleur coup (STAY, SPLIT, DOUBLE, HIT) pour une paire.
     */
	public static Move getBestMoveWithPair(PlayerHand hand, Carte carte) {
		if (hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.AS || hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.HUIT) {
			return Move.SPLIT;
		}
		if (hand.getHand().getCardAt(0).getValeur().valeur == 10) {
			return Move.STAY;
		}
		if (hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.NEUF) {
			if (carte.getValeur().valeur == 7 || carte.getValeur().valeur >= 10) {
				return Move.STAY;
			} else {
				return Move.SPLIT;
			}
		}
		if (hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.SEPT) {
			if (carte.getValeur().valeur >= 8) {
				return Move.HIT;
			} else {
				return Move.SPLIT;
			}
		}
		if (hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.SIX) {
			if (carte.getValeur().valeur >= 7) {
				return Move.HIT;
			} else {
				return Move.SPLIT;
			}
		}
		if (hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.CINQ) {
			if (carte.getValeur().valeur >= 10) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		if (hand.getHand().getCardAt(0).getValeur() == Factory.Valeur.QUATRE) {
			if (carte.getValeur().valeur <= 4 || carte.getValeur().valeur >= 7) {
				return Move.HIT;
			} else {
				return Move.SPLIT;
			}
		}
		if (hand.getHand().getCardAt(0).getValeur().valeur <= 3) {
			if (carte.getValeur().valeur >= 8) {
				return Move.HIT;
			}else {
				return Move.SPLIT;
			}
		}
		return Move.STAY;
	}
	
	/**
     * Calcule le meilleur coup à jouer lorsqu'un As est dans la main du joueur.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @param notAsIndex L'index de la carte qui n'est pas un As.
     * @return Le meilleur coup (STAY, SPLIT, DOUBLE, HIT) pour une main avec un As.
     */
	public static Move getBestMoveWithAnAS(PlayerHand hand, Carte carte, int notAsIndex) {
		if (hand.getHand().getCardAt(notAsIndex).getValeur().valeur >= 8) {
			return Move.STAY;
		}
		if (hand.getHand().getCardAt(notAsIndex).getValeur() == Factory.Valeur.SEPT) {
			if (carte.getValeur().valeur == 2 || carte.getValeur().valeur == 7 || carte.getValeur().valeur == 8) {
				return Move.STAY;
			} else if (carte.getValeur().valeur >= 9) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		if (hand.getHand().getCardAt(notAsIndex).getValeur().valeur == 6) {
			if (carte.getValeur().valeur == 2 || carte.getValeur().valeur >= 7) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		if (hand.getHand().getCardAt(notAsIndex).getValeur().valeur == 4 || hand.getHand().getCardAt(notAsIndex).getValeur().valeur == 5) {
			if (carte.getValeur().valeur <= 3 || carte.getValeur().valeur >= 7) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		if (hand.getHand().getCardAt(notAsIndex).getValeur().valeur == 2 || hand.getHand().getCardAt(notAsIndex).getValeur().valeur == 3) {
			if (carte.getValeur().valeur <= 4 || carte.getValeur().valeur >= 7) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		return Move.STAY;
	}

	/**
     * Calcule le meilleur coup à jouer dans les cas dans lesquels la main du joueur n'est ni une paire ni un As.
     *
     * @param hand La main du joueur.
     * @param carte La carte visible du dealer.
     * @return Le meilleur coup (STAY, SPLIT, DOUBLE, HIT) dans les autres situations.
     */
	public static Move getBestMoveMismatch(PlayerHand hand, Carte carte) {
		if (hand.count() >= 17) {
			return Move.STAY;
		}
		if (hand.count() >= 13 && hand.count() <= 16) {
			if (carte.getValeur().valeur >= 7) {
				return Move.HIT;
			} else {
				return Move.STAY;
			}
		}
		if (hand.count() == 12) {
			if (carte.getValeur().valeur >= 7 || carte.getValeur().valeur <= 3) {
				return Move.HIT;
			} else {
				return Move.STAY;
			}
		}
		if (hand.count() == 11) {
			if (carte.getValeur() == Factory.Valeur.AS) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		if (hand.count() == 10) {
			if (carte.getValeur().valeur >= 10) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		if (hand.count() == 9) {
			if (carte.getValeur().valeur == 2 || carte.getValeur().valeur >= 6) {
				return Move.HIT;
			} else {
				return Move.DOUBLE;
			}
		}
		return Move.HIT;
	}
	
	
}
