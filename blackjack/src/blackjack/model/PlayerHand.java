package blackjack.model;

import carte.model.*;
import carte.util.observer.*;

/**
 * Classe représentant la main d'un joueur dans le jeu de blackjack.
 * Elle gère les différentes actions d'un joueur, comme prendre une assurance, doubler, 
 * se séparer, et calculer les résultats de la partie.
 */
public class PlayerHand extends BlackJackHand {

    private int bet;
    private Player player;
    private Game game;
    private boolean isEnd = false;
    private boolean isDouble = false;
    private int insurance = 0;
    private boolean hasIns = false;

    /**
     * Constructeur pour créer une main de joueur avec la mise, le joueur, et le jeu.
     * @param bet La mise du joueur.
     * @param player Le joueur qui possède cette main.
     * @param game Le jeu auquel la main appartient.
     */
    public PlayerHand(int bet, Player player, Game game) {
        super();
        this.player = player;
        this.game = game;
        this.bet = bet;
    }

    /**
     * Permet au joueur de prendre une assurance, si possible.
     * @param valeur L'assurance à prendre.
     * @return true si l'assurance est prise avec succès, false sinon.
     */
    public boolean takeInsurance(int valeur) {
        
        if (this.getHand().getPaquetSize() != 2) {
            return false;
        }

        if (hasIns) {
            return false;
        }

        int max = this.bet / 2;
        if (valeur > max || valeur <= 0) {
            return false;
        }

        if (!this.player.isBettable(valeur)) {
            return false;
        }

        this.player.addToBalance(-valeur);
        this.insurance = valeur;
        this.hasIns = true;

        System.out.println(player + " prend une assurance de " + valeur);
        fireChangement();
        return true;
    }

    /**
     * Gère le résultat de l'assurance, paye l'assurance si le croupier a un blackjack.
     * @param dealerBJ true si le croupier a un blackjack, false sinon.
     */
    public void resultInsurance(boolean dealerBJ) {
        if (!hasIns) {
            return;
        }

        if (dealerBJ) {
            double pay = insurance * 3;
            this.player.addToBalance(pay);
            System.out.println("Assurance obtenue, paiement de " + pay);
        } else {
            System.out.println("Assurance perdue de " + insurance);
        }

        fireChangement();
    }

    /**
     * Permet au joueur de se séparer en créant une nouvelle main avec une carte retirée.
     */
    public void split() {
        PlayerHand newHand = new PlayerHand(this.bet, this.player, this.game);
        this.player.addToBalance(-this.bet);
        newHand.add(this.getHand().removeCarte(0));
        newHand.hit();
        this.hit();
        this.player.addHand(newHand);
        fireChangement();
    }

    /**
     * Permet au joueur de tirer une carte supplémentaire.
     */
    public void hit() {
        this.game.distribute(this);
        fireChangement();
    }

    /**
     * Permet au joueur de doubler sa mise, de tirer une carte, et de rester.
     */
    public void doDouble() {
        this.player.addToBalance(-this.bet);
        this.bet *= 2;
        this.isDouble = true;
        this.hit();
        this.stay();
        fireChangement();
    }

    /**
     * Le joueur reste, mettant fin à son tour.
     */
    public void stay() {
        this.isEnd = true;
        fireChangement();
    }

    /**
     * Effectue un paiement pour la mise du joueur en fonction du multiplicateur passé en paramètre.
     * @param f Le multiplicateur du paiement.
     */
    public void pay(double f) {
        this.player.addToBalance(this.bet * f);
        fireChangement();
    }

    /**
     * Vérifie si le joueur peut se séparer.
     * @return true si le joueur peut se séparer, false sinon.
     */
    public boolean canSplit() {
        if (this.canDouble() && this.getHand().getPaquetSize() == 2 && this.getHand().getCardAt(0).getValeur().valeur == this.getHand().getCardAt(1).getValeur().valeur) {
            return true;
        }
        return false;
    }

    /**
     * Vérifie si le joueur peut doubler sa mise.
     * @return true si le joueur peut doubler, false sinon.
     */
    public boolean canDouble() {
        if (this.player.getBalance() >= this.bet && this.getHand().getPaquetSize() == 2) {
            return true;
        }
        return false;
    }

    /**
     * Vérifie si le joueur peut prendre une assurance, en fonction de la carte du croupier.
     * @param dealerCarte La carte visible du croupier.
     * @return true si l'assurance peut être prise, false sinon.
     */
    public boolean canInsurance(Carte dealerCarte) {

        if (dealerCarte.getValeur() != Factory.Valeur.AS) {
            return false;
        }

        if (this.getHand().getPaquetSize() != 2) {
            return false;
        }

        if (hasIns) {
            return false;
        }

        if (this.player.getBalance() < 1) {
            return false;
        }

        return true;
    }

    /**
     * Vérifie si la main du joueur est terminée.
     * @return true si la main est terminée, false sinon.
     */
    public boolean isEnd() {
        if (this.count() >= 21 || this.isEnd) {
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la main du joueur est un double.
     * @return true si le joueur a doublé, false sinon.
     */
    public boolean isDouble() {
        return this.isDouble;
    }

    /**
     * Retourne la mise du joueur pour cette main.
     * @return La mise du joueur.
     */
    public int getBet() {
        return this.bet;
    }

    /**
     * Retourne le joueur associé à cette main.
     * @return Le joueur associé à la main.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Retourne l'assurance du joueur.
     * @return L'assurance du joueur.
     */
    public int getinsurance() {
        return this.insurance;
    }

    /**
     * Vérifie si le joueur a pris une assurance.
     * @return true si le joueur a pris une assurance, false sinon.
     */
    public boolean hasIns() {
        return this.hasIns;
    }
}
