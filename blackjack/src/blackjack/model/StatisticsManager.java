package blackjack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gère les statistiques de tous les joueurs dans le jeu.
 * Elle permet d'ajouter des statistiques de joueurs, de récupérer toutes les statistiques,
 * d'afficher les statistiques de tous les joueurs, et de générer un résumé comparatif des joueurs.
 */
public class StatisticsManager {
    
    private List<PlayerStatistics> playerStats;

    /**
     * Constructeur pour initialiser le gestionnaire de statistiques avec une liste vide.
     */
    public StatisticsManager() {
        this.playerStats = new ArrayList<>();
    }

    /**
     * Ajoute un joueur et ses statistiques au gestionnaire s'il n'est pas déjà présent.
     * @param stats Les statistiques du joueur à ajouter.
     */
    public void putPlayer(PlayerStatistics stats) {
        if (!playerStats.contains(stats)) {
            playerStats.add(stats);
        }
    }

    /**
     * Retourne la liste de toutes les statistiques des joueurs.
     * @return Une liste des statistiques de tous les joueurs.
     */
    public List<PlayerStatistics> getAllStatistics() {
        return new ArrayList<>(playerStats);
    }

    /**
     * Affiche les statistiques de tous les joueurs.
     */
    public void printAllStatistics() {
        System.out.println("\nStatistiques de tous les joueurs : \n");

        for (PlayerStatistics stats : playerStats) {
            System.out.println(stats.getStatisticsSummary());
        }
    }

    /**
     * Génère un résumé comparatif des joueurs, incluant leur nom, type (robot ou humain),
     * solde actuel et profit.
     * @return Un résumé comparatif sous forme de chaîne de caractères.
     */
    public String getComparativeSummary() {
        if (playerStats.isEmpty()) {
            return "Il n'y a pas de joueurs.";
        }

        StringBuilder sb = new StringBuilder();
        for (PlayerStatistics stats : playerStats) {
            sb.append("Joueur : ").append(stats.getPlayerName()).append(" ");
            sb.append("Type : ").append(stats.isBot() ? "Robot" : "Humain").append(" ");
            sb.append("Balance : ").append(stats.getCurrentBalance()).append(" ");
            sb.append("Profit : ").append(stats.getProfit()).append(" ");
            sb.append("\n");
        }

        return sb.toString();
    }
}

