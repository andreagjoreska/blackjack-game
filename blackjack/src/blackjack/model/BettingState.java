package blackjack.model;

/**
 * Classe représentant l'état de mise dans le jeu de blackjack.
 * Cette classe gère l'action de placer une mise et vérifie si tous les joueurs ont placé leurs mises.
 */
public class BettingState implements State {

    /**
     * Permet à un joueur de placer une mise pendant l'état de mise.
     * Si le joueur a assez d'argent, il peut ouvrir une nouvelle main avec la mise.
     * @param context Le contexte actuel du jeu.
     * @param player Le joueur qui place la mise.
     * @param value La valeur de la mise.
     * @return true si la mise a été placée avec succès, false sinon.
     */
    @Override
    public boolean placeBet(GameContext context, Player player, int value) {
        if (player.isBettable(value)) {
            player.openHand(value, context.getGame());
            System.out.println("Mise placée: " + value);
            return true;
        }
        System.out.println("Le solde est insuffisant");
        return false;
    }

    /**
     * Démarre un nouveau tour si tous les joueurs ont placé leur mise.
     * Si tous les joueurs ont placé leur mise, l'état passe à l'état de distribution des cartes.
     * @param context Le contexte actuel du jeu.
     * @return true si le tour a démarré, false si tous les joueurs n'ont pas placé leur mise.
     */
    @Override
    public boolean startRound(GameContext context) {
        boolean betsPut = true;
        for (Player p : context.getGame().getPlayers()) {
            if (p.getHands().isEmpty()) {
                betsPut = false;
                break;
            }
        }

        if (betsPut) {
            context.setState(context.getDealingState());
            context.getGame().startRound();
            context.setState(context.getPlayingState());
            return true;
        }
        System.out.println("Tous les joueurs doivent placer une mise");
        return false;
    }

    /**
     * Action de tirer une carte, non autorisée pendant l'état de mise.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de tirer une carte.
     * @return false, car cette action n'est pas autorisée pendant l'état "MISE".
     */
    @Override
    public boolean hit(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de rester pour un joueur, non autorisée pendant l'état de mise.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de rester.
     * @return false, car cette action n'est pas autorisée pendant l'état "MISE".
     */
    @Override
    public boolean stand(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de doubler la mise, non autorisée pendant l'état de mise.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de doubler.
     * @return false, car cette action n'est pas autorisée pendant l'état "MISE".
     */
    @Override
    public boolean putDouble(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de se séparer, non autorisée pendant l'état de mise.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur qui essaie de se séparer.
     * @return false, car cette action n'est pas autorisée pendant l'état "MISE".
     */
    @Override
    public boolean split(GameContext context, PlayerHand hand) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Action de terminer le tour, non autorisée pendant l'état de mise.
     * @param context Le contexte actuel du jeu.
     * @return false, car cette action n'est pas autorisée pendant l'état "MISE".
     */
    @Override
    public boolean endRound(GameContext context) {
        System.out.println("Action non autorisée en état: " + getStateName());
        return false;
    }

    /**
     * Retourne le nom de l'état actuel du jeu, qui est "MISE".
     * @return Le nom de l'état actuel.
     */
    @Override
    public String getStateName() {
        return "MISE";
    }

    /**
     * Vérifie si une transition vers un autre état est possible.
     * La transition est possible uniquement vers l'état "DealingState".
     * @param newState L'état vers lequel la transition est demandée.
     * @return true si la transition est possible, false sinon.
     */
    @Override
    public boolean canTrans(State newState) {
        return newState instanceof DealingState;
    }
}

