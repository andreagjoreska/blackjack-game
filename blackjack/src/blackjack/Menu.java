package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import blackjack.view.*;
import blackjack.model.*;
import java.util.*;
import blackjack.controller.Controller;
import blackjack.model.strategy.*;

/**
 * Cette classe représente le menu principal du jeu de Blackjack.
 * Elle permet de configurer la partie, afficher les règles et quitter le jeu.
 */
public class Menu {

    private JFrame frame;
    private JPanel panel;
    private JButton startBtn;
    private JButton rulesBtn;
    private JButton quitBtn;
    private JTextField startingMoneyField;  
    private JLabel balance;
    private JComboBox<String> strategy;
    private JLabel strategyLabel;
    private StatisticsManager statsManager;
    
    /**
     * Constructeur qui initialise le menu principal avec tous ses composants graphiques.
     */
    public Menu() {
        this.statsManager = new StatisticsManager();
        
        frame = new JFrame("Blackjack - Menu");
        frame.setSize(830, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setLayout(null); 
        panel.setBackground(new Color(0, 102, 0));
        
        JLabel titleLabel = new JLabel("Jeu de Blackjack", SwingConstants.CENTER);
        titleLabel.setBounds(200, 10, 400, 40);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        
        // Label pour la balance initiale
        balance = new JLabel("Mettre votre balance initiale :");
        balance.setBounds(200, 60, 200, 30);
        balance.setForeground(Color.WHITE);
        balance.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(balance);
        
        // Champ de texte pour entrer la balance (par défaut 1000)
        startingMoneyField = new JTextField("1000"); 
        startingMoneyField.setBounds(400, 60, 200, 30);
        startingMoneyField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(startingMoneyField);
        
        // Label pour le choix de stratégie du robot
        strategyLabel = new JLabel("Choisissez un robot :");
        strategyLabel.setBounds(200, 100, 200, 30);
        strategyLabel.setForeground(Color.WHITE);
        strategyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(strategyLabel);
        
        // Menu déroulant pour choisir la stratégie du robot
        String[] strategies = {
            "Mode normale", 
            "Mode conservatif", 
            "Mode aggressif", 
            "Mode aleatoire"
        };
        strategy = new JComboBox<>(strategies);
        strategy.setBounds(400, 100, 200, 30);
        strategy.setFont(new Font("SansSerif", Font.PLAIN, 14));
        strategy.setSelectedIndex(0);
        panel.add(strategy);
        
        //Boutons
        startBtn = new JButton("Commencer le jeu");
        startBtn.setBounds(200, 160, 400, 50);
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        startBtn.setBackground(new Color(34, 139, 34));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        panel.add(startBtn);
        
        rulesBtn = new JButton("Regles du jeu");
        rulesBtn.setBounds(200, 230, 400, 50);
        rulesBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        rulesBtn.setBackground(new Color(70, 130, 180));
        rulesBtn.setForeground(Color.WHITE);
        rulesBtn.setFocusPainted(false);
        panel.add(rulesBtn);
        
        quitBtn = new JButton("Quitter");
        quitBtn.setBounds(200, 300, 400, 50);
        quitBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        quitBtn.setBackground(new Color(178, 34, 34));
        quitBtn.setForeground(Color.WHITE);
        quitBtn.setFocusPainted(false);
        panel.add(quitBtn);
        
        // Écouteur pour le bouton de démarrage
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Récupération et validation de la balance initiale
                    String input = startingMoneyField.getText().trim();
                    if (input.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, 
                            "Veuillez entrer un montant pour commencer le jeu.");
                        return;
                    }
                    
                    int startingMoney = Integer.parseInt(input);
                    if (startingMoney <= 0) {
                        JOptionPane.showMessageDialog(frame, 
                            "Le montant doit être un nombre positif.");
                        return;
                    }
                    
                    ArrayList<Player> players = new ArrayList<>();
                    PlayerStatisticsAdapter humanPlayer = 
                        new PlayerStatisticsAdapter(startingMoney, "Player 1");
                    players.add(humanPlayer);
                    
                    statsManager.putPlayer(humanPlayer.getStatistics());
                    
                    Strategy selectedStrategy = getSelectedStrategy();
                    String botName = "Robot (" + getStrategyName() + ")";
                    PlayerStatisticsAdapter botPlayer = 
                        new PlayerStatisticsAdapter(startingMoney, botName, selectedStrategy);
                    players.add(botPlayer);
                    
                    statsManager.putPlayer(botPlayer.getStatistics());
                    
                    Controller control = new Controller(players);
                    control.displayView();
                    
                    frame.dispose();
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, 
                        "Veuillez entrer un montant valide.");
                }
            }
        });
        
        // Écouteur pour le bouton des règles
        rulesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rules rules = new Rules();
                rules.setVisible(true);
            }
        });
        
        // Écouteur pour le bouton quitter
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Affichage des statistiques finales s'il y en a
                if (statsManager.getAllStatistics().size() > 0) {
                    showFinalStatistics();
                }
                System.exit(0);
            }
        });
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Retourne la stratégie sélectionnée dans le menu déroulant.
     * @return La stratégie correspondant au choix de l'utilisateur
     */
    private Strategy getSelectedStrategy() {
        int index = strategy.getSelectedIndex();
        
        switch (index) {
            case 0:
                return new BlackjackStrategy();
            case 1:
                return new ConservativeStrategy();
            case 2:
                return new AggresiveStrategy();
            case 3:
                return new RandomStrategy();
            default:
                return new BlackjackStrategy();
        }
    }

    /**
     * Retourne le nom de la stratégie sélectionnée.
     * @return Le nom de la stratégie 
     */
    private String getStrategyName() {
        return (String) strategy.getSelectedItem();
    }

    /**
     * Affiche une fenêtre avec les statistiques finales de tous les joueurs.
     */
    private void showFinalStatistics() {
        String stats = statsManager.getComparativeSummary();
        
        JTextArea textArea = new JTextArea(stats);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        JOptionPane.showMessageDialog(null, scrollPane, 
            "Statistiques du jeu", JOptionPane.INFORMATION_MESSAGE);
        
        statsManager.printAllStatistics();
    }
    
    /**
     * Affiche la fenêtre du menu principal.
     */
    public void display() {
        frame.setVisible(true);
    }
}
