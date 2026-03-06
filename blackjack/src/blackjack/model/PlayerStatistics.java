package blackjack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant les statistiques d'un joueur dans le jeu de blackjack.
 * Elle suit les informations comme le solde initial et actuel du joueur, 
 * le nombre de blackjacks reçus, et calcule les gains ou pertes du joueur.
 */
public class PlayerStatistics {
    
    private String name; 
    private boolean isBot; 
    private double initialBal; 
    private double currentBal; 
    private int nbBlackjacks; 
    
    /**
     * Constructeur pour initialiser les statistiques d'un joueur.
     * @param name Le nom du joueur.
     * @param isBot true si le joueur est un robot, false si c'est un humain.
     * @param initialBal Le solde initial du joueur.
     */
    public PlayerStatistics(String name, boolean isBot, double initialBal) {
        this.name = name;
        this.isBot = isBot;
        this.initialBal = initialBal;
        this.currentBal = initialBal;
        this.nbBlackjacks = 0;
    }
    
    /**
     * Enregistre un blackjack pour le joueur.
     */
    public void recordBlackjack() {
        nbBlackjacks++;
    }
    
    /**
     * Met à jour le solde actuel du joueur.
     * @param balance Le nouveau solde du joueur.
     */
    public void updateBalance(double balance) {
        this.currentBal = balance;
    }

    /**
     * Retourne le nom du joueur.
     * @return Le nom du joueur.
     */
    public String getPlayerName() {
        return name;
    }
    
    /**
     * Vérifie si le joueur est un robot.
     * @return true si le joueur est un robot, false sinon.
     */
    public boolean isBot() {
        return isBot;
    }
    
    /**
     * Retourne le solde initial du joueur.
     * @return Le solde initial du joueur.
     */
    public double getInitialBalance() {
        return initialBal;
    }
    
    /**
     * Retourne le solde actuel du joueur.
     * @return Le solde actuel du joueur.
     */
    public double getCurrentBalance() {
        return currentBal;
    }
    
    /**
     * Calcule le profit ou la perte du joueur en comparant le solde actuel et le solde initial.
     * @return Le profit ou la perte du joueur.
     */
    public double getProfit() {
        return currentBal - initialBal;
    }
    
    /**
     * Retourne le nombre de blackjacks reçus par le joueur.
     * @return Le nombre de blackjacks reçus.
     */
    public int getBlackjacksReceived() {
        return nbBlackjacks;
    }

    /**
     * Génère un résumé des statistiques du joueur.
     * @return Le résumé des statistiques du joueur sous forme de chaîne de caractères.
     */
    public String getStatisticsSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Statistiques de ").append(name).append("\n");
        sb.append("Type de joueur : ").append(isBot ? "Bot" : "Humain").append("\n");
        sb.append("Solde initial : ").append(String.format("%.2f", initialBal)).append("€\n");
        sb.append("Solde final : ").append(String.format("%.2f", currentBal)).append("€\n");
        sb.append("Gain/Perte : ").append(String.format("%.2f", getProfit())).append("€\n");
        sb.append("Blackjacks : ").append(nbBlackjacks).append("\n");
        
        return sb.toString();
    }
}

