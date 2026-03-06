package blackjack.model;

import carte.model.Paquet;

/**
 * Classe DeckProxy permet de contrôler les actions sur le paquet en fonction de l'état d'accès.
 */
public class DeckProxy implements DeckInterface {
    
    private Deck deck;
    private int nbCartes;
    private boolean access;

    /**
     * Constructeur pour initialiser un DeckProxy avec un paquet, un compteur de cartes et l'état d'accès.
     */
    public DeckProxy() {
        this.deck = new Deck();
        this.nbCartes = 0;
        this.access = true;
    }
    
    /**
     * Vérifie si le paquet est vide.
     * @return true si le paquet est vide, false sinon.
     */
    @Override
    public boolean isEmpty() {
        return deck.isEmpty();
    }
    
    /**
     * Mélange les cartes du paquet si l'accès est autorisé, sinon affiche un message d'erreur.
     */
    @Override
    public void shuffle() {
        if (!access) {
            System.out.println("Notre accès est refusé: le paquet est verrouillé");
            return;
        }
        System.out.println("On mélange le paquet");
        deck.shuffle();
    }
    
    /**
     * Retire la première carte du paquet si l'accès est autorisé, sinon affiche un message d'erreur.
     */
    @Override
    public void removeFirstCard() {
        if (!access) {
            System.out.println("Notre accès est refusé: le paquet est verrouillé");
            return;
        }
        System.out.println("On supprime la première carte");
        deck.removeFirstCard();
    }
    
    /**
     * Distribue une carte à la main donnée si l'accès est autorisé. Si le paquet est vide, il est recréé.
     * @param hand La main à laquelle distribuer la carte.
     */
    @Override
    public void distribute(Hand hand) {
        if (!access) {
            System.out.println("Notre accès est refusé: le paquet est verrouillé");
            return;
        }
        
        nbCartes++;
        System.out.println("Distribution carte " + nbCartes + " (il nous reste: " + deck.getSize() + ")");
        
        if (deck.isEmpty()) {
            System.out.println("Paquet vide! On crée un nouveau paquet");
            deck = new Deck();
        }
        
        deck.distribute(hand);
    }
    
    /**
     * Retourne le paquet de cartes.
     * @return Le paquet de cartes.
     */
    @Override
    public Paquet getDeck() {
        return deck.getDeck();
    }
    
    /**
     * Retourne la taille du paquet.
     * @return La taille du paquet.
     */
    @Override
    public int getSize() {
        return deck.getSize();
    }
    
    /**
     * Retourne le nombre de cartes distribuées jusqu'à présent.
     * @return Le nombre de cartes distribuées.
     */
    public int getnbCartes() {
        return nbCartes;
    }
    
    /**
     * Réinitialise le compteur de cartes distribuées à zéro.
     */
    public void resetCardCount() {
        this.nbCartes = 0;
    }
}

