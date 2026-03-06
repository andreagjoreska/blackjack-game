package blackjack.model;

import java.util.ArrayList;
import carte.model.Carte;
import carte.util.observer.*;
import blackjack.model.strategy.*;

/**
 * Classe qui adapte un joueur pour inclure la gestion des statistiques du joueur. 
 * Elle étend la classe Player et gère les statistiques de jeu pour chaque joueur.
 */
public class PlayerStatisticsAdapter extends Player {

    private PlayerStatistics statistics;

    /**
     * Constructeur pour créer un joueur avec des statistiques, sans stratégie.
     * @param balance Le solde initial du joueur.
     * @param name Le nom du joueur.
     */
    public PlayerStatisticsAdapter(int balance, String name) {
        super(balance);
        this.statistics = new PlayerStatistics(name, false, balance);
    }

    /**
     * Constructeur pour créer un joueur avec des statistiques et une stratégie.
     * @param balance Le solde initial du joueur.
     * @param name Le nom du joueur.
     * @param strategy La stratégie du joueur.
     */
    public PlayerStatisticsAdapter(int balance, String name, Strategy strategy) {
        super(balance, strategy);
        this.statistics = new PlayerStatistics(name, true, balance);
    }

    /**
     * Ouvre une nouvelle main pour le joueur et met à jour les statistiques du joueur.
     * @param bet La mise pour la nouvelle main.
     * @param game Le jeu auquel le joueur participe.
     */
    @Override
    public void openHand(int bet, Game game) {
        super.openHand(bet, game);
        statistics.updateBalance(this.getBalance());
    }

    /**
     * Ajoute un montant au solde du joueur et met à jour les statistiques.
     * @param amount Le montant à ajouter au solde du joueur.
     */
    @Override
    public void addToBalance(double amount) {
        super.addToBalance(amount);
        statistics.updateBalance(this.getBalance());
    }

    /**
     * Retourne les statistiques du joueur.
     * @return Les statistiques du joueur.
     */
    public PlayerStatistics getStatistics() {
        return this.statistics;
    }

    /**
     * Affiche les statistiques du joueur.
     */
    public void printStatistics() {
        System.out.println(statistics.getStatisticsSummary());
    }
}

