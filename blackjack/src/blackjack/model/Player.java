package blackjack.model;

import java.util.ArrayList;
import carte.model.Carte;
import carte.util.observer.*;
import blackjack.model.strategy.*;

/**
 * Classe représentant un joueur dans le jeu de blackjack.
 * Elle gère les mains du joueur, son solde, et permet de jouer selon une stratégie.
 * Elle permet d'ouvrir une main, de jouer le meilleur coup selon la stratégie, 
 * d'ajouter des mains, et d'effectuer des opérations sur le solde du joueur.
 */
public class Player extends AbstractModeleEcoutable {
    private ArrayList<PlayerHand> hands;  
    private double balance;
    private boolean isBot = false;
    private Strategy strategy;

    /**
     * Constructeur pour créer un joueur avec un solde initial.
     * @param balance Le solde initial du joueur.
     */
    public Player(int balance) {
        super(new ArrayList<EcouteurModele>());
        this.hands = new ArrayList<>();
        this.balance = balance;
        this.strategy = null;
    }

    /**
     * Constructeur pour créer un joueur avec un solde initial et une stratégie.
     * @param balance Le solde initial du joueur.
     * @param strategy La stratégie que le joueur suivra.
     */
    public Player(int balance, Strategy strategy) {
        this(balance);
        this.isBot = true;
        this.strategy = strategy;
    }

    /**
     * Ouvre une nouvelle main pour le joueur avec une mise donnée.
     * @param bet La mise pour la nouvelle main.
     * @param game Le jeu auquel le joueur participe.
     */
    public void openHand(int bet, Game game) {
        if (this.isBettable(bet)) {
            PlayerHand hand = new PlayerHand(bet, this, game);
            this.hands.add(hand);
            this.balance -= bet;
            fireChangement();
        } 
    }

    /**
     * Joue le meilleur coup selon la stratégie du joueur en fonction de la main et de la carte du croupier.
     * @param hand La main du joueur à jouer.
     * @param dealerCard La carte visible du croupier.
     */
    public void playBestMove(PlayerHand hand, Carte dealerCard) {
        if (strategy != null) {
            Strategy.Move bestMove = strategy.decideMove(hand, dealerCard);

            switch (bestMove) {
                case STAY:
                    hand.stay();
                    System.out.print("Stay \n");
                    break;
                case HIT:
                    hand.hit();
                    System.out.print("Hit \n");
                    break;
                case DOUBLE:
                    if (hand.canDouble()) {
                        hand.doDouble();
                        System.out.print("Double \n");
                    } else {
                        hand.hit();
                        System.out.print("Hit \n");
                    }
                    break;
                case SPLIT:
                    hand.split();
                    System.out.print("Split \n");
                    break;
            }
            fireChangement();
        }
    }

    /**
     * Ajoute une main au joueur.
     * @param hand La main à ajouter.
     */
    public void addHand(PlayerHand hand) {
        this.hands.add(hand);
        fireChangement();
    }

    /**
     * Vide toutes les mains du joueur.
     */
    public void clearHands() {
        this.hands.clear();
        fireChangement();
    }

    /**
     * Vérifie si le joueur a assez d'argent pour parier une certaine somme.
     * @param value La valeur à parier.
     * @return true si le joueur peut parier, false sinon.
     */
    public boolean isBettable(int value) {
        return this.balance >= value;
    }

    /**
     * Retourne la liste des mains du joueur.
     * @return La liste des mains.
     */
    public ArrayList<PlayerHand> getHands() {
        return this.hands;
    }

    /**
     * Retourne le solde actuel du joueur.
     * @return Le solde du joueur.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Ajoute ou retire un montant du solde du joueur.
     * @param f Le montant à ajouter ou retirer si f est négatif.
     */
    public void addToBalance(double f) {
        this.balance += f;
        fireChangement();
    }

    /**
     * Vérifie si le joueur est un robot ou un humain.
     * @return true si le joueur est un robot, false si c'est un humain.
     */
    public boolean isBot() {
        return this.isBot;
    }

    /**
     * Retourne une chaîne de caractères représentant le type de joueur.
     * @return "Robot" si le joueur est un bot, "Humain" sinon.
     */
    @Override
    public String toString() {
        if (this.isBot) {
            return "Robot";
        }
        return "Humain";
    }
}

