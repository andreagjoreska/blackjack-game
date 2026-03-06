package blackjack.model;

/**
 * Classe représentant l'état du tour du croupier dans le jeu de blackjack.
 * Elle implémente l'interface State et gère les actions et transitions possibles
 * pendant le tour du croupier.
 */
public class DealerTurnState implements State {

    /**
     * Action de placer une mise, non autorisée pendant l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param player Le joueur qui essaie de placer la mise.
     * @param value La valeur de la mise.
     * @return false, car cette action n'est pas autorisée pendant l'état "TOUR DE CROUPIER".
     */
    @Override
    public boolean placeBet(GameContext context, Player player, int value) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de démarrer un nouveau tour, non autorisée pendant l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @return false, car cette action n'est pas autorisée pendant l'état "TOUR DE CROUPIER".
     */
    @Override
    public boolean startRound(GameContext context) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de tirer une carte pour un joueur, non autorisée pendant l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de tirer une carte.
     * @return false, car cette action n'est pas autorisée pendant l'état "TOUR DE CROUPIER".
     */
    @Override
    public boolean hit(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de rester pour un joueur, non autorisée pendant l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de rester.
     * @return false, car cette action n'est pas autorisée pendant l'état "TOUR DE CROUPIER".
     */
    @Override
    public boolean stand(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de doubler la mise pour un joueur, non autorisée pendant l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de doubler.
     * @return false, car cette action n'est pas autorisée pendant l'état "TOUR DE CROUPIER".
     */
    @Override
    public boolean putDouble(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de se séparer pour un joueur, non autorisée pendant l'état du tour du croupier.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de se séparer.
     * @return false, car cette action n'est pas autorisée pendant l'état "TOUR DE CROUPIER".
     */
    @Override
    public boolean split(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Termine le tour du croupier en distribuant des cartes jusqu'à ce que sa main soit terminée.
     * Ensuite, le jeu passe à l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @return true si le tour du croupier a été terminé avec succès, false sinon.
     */
    @Override
    public boolean endRound(GameContext context) {
        Game game = context.getGame();
        DealerHand hand = game.getHand();
        
        while (!hand.isEnd()) {
            game.distribute(hand);
        }
        
        context.setState(context.getPayoutState());
        context.endRound();

        return true;
    }

    /**
     * Retourne le nom de l'état actuel du jeu, qui est "TOUR DE CROUPIER".
     * @return Le nom de l'état actuel.
     */
    @Override
    public String getStateName() {
        return "TOUR DE CROUPIER";
    }

    /**
     * Vérifie si une transition vers un autre état est possible.
     * La transition est possible uniquement vers l'état "PayoutState".
     * @param newState L'état vers lequel la transition est demandée.
     * @return true si la transition est possible, false sinon.
     */
    @Override
    public boolean canTrans(State newState) {
        return newState instanceof PayoutState;
    }
}

