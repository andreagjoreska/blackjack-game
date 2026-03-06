package blackjack.model;

/**
 * Classe représentant le contexte du jeu, gérant les différents états du jeu 
 * (parier, distribuer, jouer, tour du croupier, paiement).
 */
public class GameContext {
    
    private State current; 
    private Game game; 
    private final State bettingState; 
    private final State dealingState; 
    private final State playingState; 
    private final State dealerTurnState; 
    private final State payoutState; 

    /**
     * Constructeur pour initialiser les états du jeu et définir l'état initial.
     * @param game Le jeu auquel ce contexte appartient.
     */
    public GameContext(Game game) {
        this.game = game;
        this.bettingState = new BettingState();
        this.dealingState = new DealingState();
        this.playingState = new PlayingState();
        this.dealerTurnState = new DealerTurnState();
        this.payoutState = new PayoutState();
        this.current = bettingState; // L'état initial est l'état de mise
    }

    /**
     * Change l'état du jeu si la transition vers le nouvel état est valide.
     * @param newState Le nouvel état à atteindre.
     */
    public void setState(State newState) {
        if (current.canTrans(newState)) {
            System.out.println("État du jeu: " + current.getStateName() + " -> " + newState.getStateName());
            this.current = newState;
        } else {
            System.out.println("Transition invalide: " + current.getStateName() + " -> " + newState.getStateName());
        }
    }

    /**
     * Retourne l'état actuel du jeu.
     * @return L'état actuel du jeu.
     */
    public State getcurrent() {
        return current;
    }

    /**
     * Retourne le jeu.
     * @return Le jeu.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Retourne l'état de mise.
     * @return L'état de mise.
     */
    public State getBettingState() {
        return bettingState;
    }

    /**
     * Retourne l'état de distribution des cartes.
     * @return L'état de distribution des cartes.
     */
    public State getDealingState() {
        return dealingState;
    }

    /**
     * Retourne l'état de jeu.
     * @return L'état de jeu.
     */
    public State getPlayingState() {
        return playingState;
    }

    /**
     * Retourne l'état du tour du croupier.
     * @return L'état du tour du croupier.
     */
    public State getDealerTurnState() {
        return dealerTurnState;
    }

    /**
     * Retourne l'état de paiement après un tour.
     * @return L'état du paiement.
     */
    public State getPayoutState() {
        return payoutState;
    }

    /**
     * Permet à un joueur de placer une mise.
     * @param player Le joueur qui place la mise.
     * @param value La valeur de la mise.
     * @return true si la mise a été placée, false sinon.
     */
    public boolean placeBet(Player player, int value) {
        return current.placeBet(this, player, value);
    }

    /**
     * Démarre un nouveau tour de jeu.
     * @return true si le tour a démarré, false sinon.
     */
    public boolean startRound() {
        return current.startRound(this);
    }

    /**
     * Permet à un joueur de tirer une carte.
     * @param hand La main du joueur.
     * @return true si l'action de tirage a réussi, false sinon.
     */
    public boolean hit(PlayerHand hand) {
        return current.hit(this, hand);
    }

    /**
     * Permet à un joueur de rester.
     * @param hand La main du joueur.
     * @return true si l'action de rester a réussi, false sinon.
     */
    public boolean stand(PlayerHand hand) {
        return current.stand(this, hand);
    }

    /**
     * Permet à un joueur de doubler sa mise.
     * @param hand La main du joueur.
     * @return true si l'action de doubler a réussi, false sinon.
     */
    public boolean putDouble(PlayerHand hand) {
        return current.putDouble(this, hand);
    }

    /**
     * Permet à un joueur de se séparer si possible.
     * @param hand La main du joueur.
     * @return true si l'action de séparation a réussi, false sinon.
     */
    public boolean split(PlayerHand hand) {
        return current.split(this, hand);
    }

    /**
     * Termine le tour de jeu.
     * @return true si le tour a été terminé, false sinon.
     */
    public boolean endRound() {
        return current.endRound(this);
    }

    /**
     * Retourne le nom de l'état actuel du jeu.
     * @return Le nom de l'état actuel.
     */
    public String getCurrentStateName() {
        return current.getStateName();
    }
}

