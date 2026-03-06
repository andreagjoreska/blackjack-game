package blackjack.model;

import java.util.ArrayList;
import carte.model.*;
import carte.util.observer.*;

/**
 * Classe Game gère les joueurs, le croupier, le paquet de cartes, les statistiques,
 * et les différentes étapes du jeu, comme le début et la fin d'un tour, les assurances, 
 * et les paiements des mains.
 */
public class Game extends AbstractModeleEcoutable {
    private ArrayList<Player> players;
    private DealerHand hand;
    private DeckInterface deck;
    private StatisticsManager stats;
    private GameContext context;
    private boolean insurance = false;

    /**
     * Constructeur pour initialiser une partie avec une liste de joueurs.
     * @param players La liste des joueurs de la partie.
     */
    public Game(ArrayList<Player> players) {
        super(new ArrayList<EcouteurModele>());
        this.players = players;
        this.deck = new DeckProxy();
        this.stats = new StatisticsManager();
        this.context = new GameContext(this);

        for (Player p : players) {
            if (p instanceof PlayerStatisticsAdapter) {
                PlayerStatisticsAdapter adapter = (PlayerStatisticsAdapter) p;
                stats.putPlayer(adapter.getStatistics());
            }
        }
    }

    /**
     * Démarre un nouveau tour de jeu en initialisant la main du croupier 
     * et en distribuant les cartes.
     */
    public void startRound() {
        this.hand = new DealerHand();
        this.insurance = false;
        this.firstDistribute();

        for (Player p : this.players) {
            if (p instanceof PlayerStatisticsAdapter) {
                PlayerStatisticsAdapter adapter = (PlayerStatisticsAdapter) p;
                for (PlayerHand pHand : p.getHands()) {
                    if (pHand.isBlackjack()) {
                        adapter.getStatistics().recordBlackjack();
                    }
                }
            }
        }

        fireChangement();
    }

    /**
     * Vérifie si une assurance peut être prise.
     * @return true si une assurance peut être prise, false sinon.
     */
    public boolean canInsurance() {
        if (insurance) {
            return false;
        }
        if (this.hand.getHand().getPaquetSize() >= 2) {
            Carte dealerCartes = this.hand.getHand().getCardAt(1);
            return dealerCartes.getValeur() == Factory.Valeur.AS;
        }

        return false;
    }

    /**
     * Définit si l'assurance est possible pour cette partie.
     * @param canIns true si l'assurance peut être prise, false sinon.
     */
    public void setInsurance(boolean canIns) {
        this.insurance = canIns;
    }

    /**
     * Vérifie si toutes les mains des joueurs sont terminées.
     * @return true si toutes les mains sont terminées, false sinon.
     */
    public boolean handFinished() {
        for (int i = 0; i < this.getAllPlayersHand().size(); i++) {
            if (!this.getAllPlayersHand().get(i).isEnd()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Termine le tour en calculant les résultats de l'assurance et des mains,
     * puis en payant les joueurs.
     */
    public void endRound() {
        boolean hasBJ = this.hand.isBlackjack();
        for (PlayerHand pHand : this.getAllPlayersHand()) {
            pHand.resultInsurance(hasBJ);
        }

        if (!hasBJ) {
            while (!this.hand.isEnd()) {
                this.distribute(this.hand);
            }
        }

        for (int i = 0; i < this.getAllPlayersHand().size(); i++) {
            PlayerHand pHand = this.getAllPlayersHand().get(i);

            boolean won = false;
            boolean hit = false;
            double payRes = calculatePayRes(pHand);

            if (payRes == 0) {
                won = false;
                hit = false;
            } else if (payRes == 1) {
                won = false;
                hit = true;
            } else {
                won = true;
                hit = false;
            }

            this.payHand(pHand);

            if (pHand.getPlayer() instanceof PlayerStatisticsAdapter) {
                PlayerStatisticsAdapter adapter = (PlayerStatisticsAdapter) pHand.getPlayer();
                adapter.getStatistics().updateBalance(adapter.getBalance());
            }
        }

        for (Player p : this.players)
            p.clearHands();

        fireChangement();
    }

    /**
     * Calcule le résultat du paiement d'une main.
     * @param pHand La main à évaluer.
     * @return 2 pour une victoire, 1 pour une égalité, 0 pour une perte.
     */
    private double calculatePayRes(PlayerHand pHand) {
        int player = pHand.count();
        int dealer = this.hand.count();

        if (player > 21) {
            return 0;
        }
        if (dealer > 21) {
            return pHand.isBlackjack() ? 2.5 : 2;
        }
        if (pHand.isBlackjack() && this.hand.isBlackjack()) {
            return 1;
        }
        if (pHand.isBlackjack()) {
            return 2.5;
        }
        if (this.hand.isBlackjack()) {
            return 0;
        }
        if (player > dealer) {
            return 2;
        } else if (player == dealer) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Effectue le paiement pour une main donnée.
     * @param pHand La main du joueur.
     */
    public void payHand(PlayerHand pHand) {
        pHand.pay(calculatePayRes(pHand));
        fireChangement();
    }

    /**
     * Retourne toutes les mains des joueurs.
     * @return La liste de toutes les mains des joueurs.
     */
    public ArrayList<PlayerHand> getAllPlayersHand() {
        ArrayList<PlayerHand> res = new ArrayList<>();
        for (Player p : this.players)
            for (PlayerHand hand : p.getHands())
                res.add(hand);
        return res;
    }

    /**
     * Effectue la première distribution des cartes.
     */
    public void firstDistribute() {
        this.deck.removeFirstCard();
        for (int i = 0; i < 2; i++) {
            for (PlayerHand hand : this.getAllPlayersHand())
                this.distribute(hand);
            this.distribute(this.hand);
        }
    }

    /**
     * Distribue une carte à la main donnée.
     * @param hand La main à laquelle distribuer la carte.
     */
    public void distribute(Hand hand) {
        this.deck.distribute(hand);
        fireChangement();
    }

    /**
     * Retourne le paquet de cartes du jeu.
     * @return Le paquet de cartes.
     */
    public Paquet getDeck() {
        return this.deck.getDeck();
    }

    /**
     * Retourne l'interface du paquet de cartes.
     * @return L'interface du paquet de cartes.
     */
    public DeckInterface getDeckInterface() {
        return this.deck;
    }

    /**
     * Retourne la main du croupier.
     * @return La main du croupier.
     */
    public DealerHand getHand() {
        return this.hand;
    }

    /**
     * Retourne la liste des joueurs.
     * @return La liste des joueurs.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Retourne la carte visible du croupier.
     * @return La carte visible du croupier.
     */
    public Carte getVisibleCard() {
        return this.hand.getHand().getCardAt(1);
    }

    /**
     * Retourne le solde du premier joueur.
     * @return Le solde du premier joueur, ou 0 si la liste des joueurs est vide.
     */
    public double getPlayerBalance() {
        if (!players.isEmpty()) {
            return players.get(0).getBalance();
        }
        return 0;
    }

    /**
     * Retourne le contexte actuel du jeu.
     * @return Le contexte actuel du jeu.
     */
    public GameContext getGameContext() {
        return this.context;
    }

    /**
     * Retourne le nom de l'état actuel du jeu.
     * @return Le nom de l'état actuel du jeu.
     */
    public String getCurrentStateName() {
        return this.context.getCurrentStateName();
    }

    /**
     * Retourne le gestionnaire de statistiques du jeu.
     * @return Le gestionnaire de statistiques.
     */
    public StatisticsManager getStatisticsManager() {
        return this.stats;
    }

    /**
     * Affiche toutes les statistiques du jeu.
     */
    public void showStatistics() {
        stats.printAllStatistics();
        System.out.println(stats.getComparativeSummary());
    }
}

