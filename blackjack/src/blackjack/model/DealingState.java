package blackjack.model;

/**
 * Classe représentant l'état de distribution des cartes dans le jeu de blackjack.
 * Pendant cet état, les cartes sont distribuées aux joueurs et au croupier avant le début du tour de jeu.
 * Aucune autre action n'est autorisée pendant cet état, excepté la transition vers l'état de jeu.
 */
public class DealingState implements State {

    /**
     * Action de placer une mise, non autorisée pendant l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @param player Le joueur qui essaie de placer une mise.
     * @param value La valeur de la mise.
     * @return false, car cette action n'est pas autorisée pendant l'état "DISTRIBUTION".
     */
    @Override
    public boolean placeBet(GameContext context, Player player, int value) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Démarre un nouveau tour de jeu après la distribution des cartes.
     * Passe à l'état "PlayingState" une fois les cartes distribuées.
     * @param context Le contexte actuel du jeu.
     * @return true si la distribution est terminée et le jeu commence, false sinon.
     */
    @Override
    public boolean startRound(GameContext context) {
        context.setState(context.getPlayingState());
        System.out.println("Distribution terminée, le jeu est prêt à commencer");
        return true;
    }

    /**
     * Action de tirer une carte, non autorisée pendant l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de tirer une carte.
     * @return false, car cette action n'est pas autorisée pendant l'état "DISTRIBUTION".
     */
    @Override
    public boolean hit(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de rester pour un joueur, non autorisée pendant l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de rester.
     * @return false, car cette action n'est pas autorisée pendant l'état "DISTRIBUTION".
     */
    @Override
    public boolean stand(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de doubler la mise pour un joueur, non autorisée pendant l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de doubler.
     * @return false, car cette action n'est pas autorisée pendant l'état "DISTRIBUTION".
     */
    @Override
    public boolean putDouble(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de se séparer pour un joueur, non autorisée pendant l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de se séparer.
     * @return false, car cette action n'est pas autorisée pendant l'état "DISTRIBUTION".
     */
    @Override
    public boolean split(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de terminer le tour, non autorisée pendant l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @return false, car cette action n'est pas autorisée pendant l'état "DISTRIBUTION".
     */
    @Override
    public boolean endRound(GameContext context) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Retourne le nom de l'état actuel du jeu, qui est "DISTRIBUTION".
     * @return Le nom de l'état actuel.
     */
    @Override
    public String getStateName() {
        return "DISTRIBUTION";
    }

    /**
     * Vérifie si une transition vers un autre état est possible.
     * La transition est possible uniquement vers l'état "PlayingState".
     * @param newState L'état vers lequel la transition est demandée.
     * @return true si la transition est possible, false sinon.
     */
    @Override
    public boolean canTrans(State newState) {
        return newState instanceof PlayingState;
    }
}

