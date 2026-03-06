package blackjack.model;

/**
 * Interface représentant un état du jeu dans le modèle de machine à états.
 * Chaque état du jeu doit implémenter ces méthodes pour gérer les actions 
 * possibles ainsi que les transitions entre les états.
 */
public interface State {

    /**
     * Permet à un joueur de placer une mise pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @param player Le joueur qui place la mise.
     * @param value La valeur de la mise.
     * @return true si la mise a été placée avec succès, false sinon.
     */
    boolean placeBet(GameContext context, Player player, int value);

    /**
     * Démarre un nouveau tour de jeu pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @return true si le tour a démarré avec succès, false sinon.
     */
    boolean startRound(GameContext context);

    /**
     * Permet à un joueur de tirer une carte pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur à laquelle tirer une carte.
     * @return true si le tirage a réussi, false sinon.
     */
    boolean hit(GameContext context, PlayerHand hand);

    /**
     * Permet à un joueur de rester pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur.
     * @return true si l'action de rester a réussi, false sinon.
     */
    boolean stand(GameContext context, PlayerHand hand);

    /**
     * Permet à un joueur de doubler sa mise pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur.
     * @return true si l'action de doubler a réussi, false sinon.
     */
    boolean putDouble(GameContext context, PlayerHand hand);

    /**
     * Permet à un joueur de se séparer pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @param hand La main du joueur à séparer.
     * @return true si l'action de séparation a réussi, false sinon.
     */
    boolean split(GameContext context, PlayerHand hand);

    /**
     * Termine le tour de jeu pendant l'état actuel.
     * @param context Le contexte actuel du jeu.
     * @return true si le tour a été terminé avec succès, false sinon.
     */
    boolean endRound(GameContext context);

    /**
     * Retourne le nom de l'état actuel du jeu.
     * @return Le nom de l'état actuel.
     */
    String getStateName();

    /**
     * Vérifie si une transition vers un nouvel état est possible.
     * @param newState L'état vers lequel la transition est demandée.
     * @return true si la transition est possible, false sinon.
     */
    boolean canTrans(State newState);
}

