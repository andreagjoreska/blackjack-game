package blackjack.model;

/**
 * Classe représentant l'état de paiement dans le jeu de blackjack.
 * Pendant cet état, les paiements sont effectués pour les joueurs en fonction des résultats,
 * et la partie se prépare pour une nouvelle manche.
 */
public class PayoutState implements State {

    /**
     * Action de placer une mise, non autorisée pendant l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @param player Le joueur qui essaie de placer la mise.
     * @param value La valeur de la mise.
     * @return false, car cette action n'est pas autorisée pendant l'état "PAIEMENT".
     */
    @Override
    public boolean placeBet(GameContext context, Player player, int value) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de démarrer un nouveau tour, non autorisée pendant l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @return false, car cette action n'est pas autorisée pendant l'état "PAIEMENT".
     */
    @Override
    public boolean startRound(GameContext context) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de tirer une carte pour un joueur, non autorisée pendant l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de tirer une carte.
     * @return false, car cette action n'est pas autorisée pendant l'état "PAIEMENT".
     */
    @Override
    public boolean hit(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de rester pour un joueur, non autorisée pendant l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de rester.
     * @return false, car cette action n'est pas autorisée pendant l'état "PAIEMENT".
     */
    @Override
    public boolean stand(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de doubler la mise pour un joueur, non autorisée pendant l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de doubler.
     * @return false, car cette action n'est pas autorisée pendant l'état "PAIEMENT".
     */
    @Override
    public boolean putDouble(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de se séparer pour un joueur, non autorisée pendant l'état de paiement.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de se séparer.
     * @return false, car cette action n'est pas autorisée pendant l'état "PAIEMENT".
     */
    @Override
    public boolean split(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Termine l'état de paiement, effectue les paiements des joueurs et passe à l'état de mise pour la nouvelle manche.
     * @param context Le contexte actuel du jeu.
     * @return true si les paiements ont été effectués et la nouvelle manche est prête, false sinon.
     */
    @Override
    public boolean endRound(GameContext context) {
        Game game = context.getGame();
        
        for (PlayerHand hand : game.getAllPlayersHand()) {
            game.payHand(hand);
        }
        
        for (Player p : game.getPlayers()) {
            p.clearHands();
        }
        
        context.setState(context.getBettingState());
        
        System.out.println("Les paiements sont effectués et la nouvelle manche est prête.");
        return true;
    }

    /**
     * Retourne le nom de l'état actuel du jeu, qui est "PAIEMENT".
     * @return Le nom de l'état actuel.
     */
    @Override
    public String getStateName() {
        return "PAIEMENT";
    }

    /**
     * Vérifie si une transition vers un autre état est possible.
     * La transition est possible uniquement vers l'état "BettingState".
     * @param newState L'état vers lequel la transition est demandée.
     * @return true si la transition est possible, false sinon.
     */
    @Override
    public boolean canTrans(State newState) {
        return newState instanceof BettingState;
    }
}

