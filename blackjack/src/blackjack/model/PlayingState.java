package blackjack.model;

/**
 * Classe représentant l'état du jeu où les joueurs peuvent jouer leurs mains (tirer, rester, doubler, séparer).
 * Elle implémente l'interface State et gère les actions possibles pendant le tour des joueurs, 
 * ainsi que les transitions vers l'état du tour du croupier ou l'état de mise.
 */
public class PlayingState implements State {
    
    /**
     * Action de placer une mise, non autorisée pendant l'état de jeu.
     * @param context Le contexte actuel du jeu.
     * @param player Le joueur qui essaie de placer la mise.
     * @param value La valeur de la mise.
     * @return false, car cette action n'est pas autorisée pendant l'état "JEU".
     */
    @Override
    public boolean placeBet(GameContext context, Player player, int value) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }
    
    /**
     * Action de démarrer un nouveau tour, non autorisée pendant l'état de jeu.
     * @param context Le contexte actuel du jeu.
     * @return false, car cette action n'est pas autorisée pendant l'état "JEU".
     */
    @Override
    public boolean startRound(GameContext context) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }
    
    /**
     * Action de tirer une carte pour un joueur pendant son tour.
     * Si la main du joueur est terminée, elle ne peut plus tirer.
     * Si tous les joueurs ont terminé, le jeu passe à l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui tire une carte.
     * @return true si le tirage a été effectué, false si la main est terminée.
     */
    @Override
    public boolean hit(GameContext context, PlayerHand hand) {
        if (!hand.isEnd()) {
            hand.hit();
            
            if (context.getGame().handFinished()) {
                context.setState(context.getDealerTurnState());
            }
            
            return true;
        }
        System.out.println("Cette main est terminée");
        return false;
    }
    
    /**
     * Action de rester pour un joueur pendant son tour.
     * Si la main du joueur est terminée, cette action est ignorée.
     * Si tous les joueurs ont terminé, le jeu passe à l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui choisit de rester.
     * @return true si l'action a réussi, false si la main est déjà terminée.
     */
    @Override
    public boolean stand(GameContext context, PlayerHand hand) {
        if (!hand.isEnd()) {
            hand.stay();
            
            if (context.getGame().handFinished()) {
                context.setState(context.getDealerTurnState());
            }
            
            return true;
        }
        System.out.println("Cette main est déjà terminée");
        return false;
    }
    
    /**
     * Action de doubler la mise pour un joueur pendant son tour.
     * Si le joueur peut doubler et que sa main n'est pas terminée, l'action est effectuée.
     * Si tous les joueurs ont terminé, le jeu passe à l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui choisit de doubler.
     * @return true si l'action a réussi, false si le double est impossible.
     */
    @Override
    public boolean putDouble(GameContext context, PlayerHand hand) {
        if (!hand.isEnd() && hand.canDouble()) {
            hand.doDouble();
            
            if (context.getGame().handFinished()) {
                context.setState(context.getDealerTurnState());
            }
            
            return true;
        }
        System.out.println("Le double est impossible");
        return false;
    }
    
    /**
     * Action de se séparer pour un joueur pendant son tour.
     * Si la main du joueur n'est pas terminée et qu'il peut se séparer, l'action est effectuée.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui choisit de se séparer.
     * @return true si l'action a réussi, false si le split est impossible.
     */
    @Override
    public boolean split(GameContext context, PlayerHand hand) {
        if (!hand.isEnd() && hand.canSplit()) {
            hand.split();
            return true;
        }
        System.out.println("Le split est impossible");
        return false;
    }
    
    /**
     * Terminer le tour des joueurs et passer à l'état du tour du croupier.
     * Si tous les joueurs ont terminé, le jeu passe à l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @return true si le tour a été terminé, false si tous les joueurs n'ont pas terminé.
     */
    @Override
    public boolean endRound(GameContext context) {
        if (context.getGame().handFinished()) {
            context.setState(context.getDealerTurnState());
            context.endRound();
            return true;
        }
        System.out.println("Tous les joueurs n'ont pas terminé");
        return false;
    }
    
    /**
     * Retourne le nom de l'état actuel du jeu.
     * @return Le nom de l'état, qui est "JEU" dans cet état.
     */
    @Override
    public String getStateName() {
        return "JEU";
    }
    
    /**
     * Vérifie si une transition vers un autre état est possible.
     * La transition est possible vers l'état "DealerTurnState" ou "BettingState".
     * @param newState L'état vers lequel la transition est demandée.
     * @return true si la transition est possible, false sinon.
     */
    @Override
    public boolean canTrans(State newState) {
        return newState instanceof DealerTurnState || newState instanceof BettingState;
    }
}

