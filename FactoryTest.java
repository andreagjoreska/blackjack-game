package carte;

import carte.model.*;

/**
 * Classe principale permettant de tester les fonctionnalités
 * de manipulation d'un paquet de cartes.
 */
public class Main {

    /**
     * Point d'entrée de l'application.
     * Affiche quelques opérations sur un paquet de 32 cartes.
     */
    public static void main(String[] args) {

        Paquet paquet32 = Factory.createPaquet32();  
        System.out.println("Taille du paquet de 32 cartes : " + paquet32.getPaquetSize());

	System.out.println("Carte en haut du paquet : " + paquet32.getCardAt(0)); 
        paquet32.shuffleGame();
        System.out.println("\nCarte en haut du paquet après mélange : " + paquet32.getCardAt(0)); 
        
        Paquet newPaquet = paquet32.couperPaquet();
        System.out.println("\nPremière carte du paquet après coupe : " + paquet32.getCardAt(0));
        System.out.println("Première carte du nouveau paquet : " + newPaquet.getCardAt(0)); 
        
        Carte carte = paquet32.getRandomCard();
        System.out.println("\nCarte tirée au hasard : " + carte);

        Carte firstCard = paquet32.removeFirstCard();
        System.out.println("\nPremière carte retirée : " + firstCard);
        System.out.println("Carte en haut du paquet après retrait : " + paquet32.getCardAt(0));  

        paquet32.distribuer(newPaquet);
        System.out.println("\nCarte en haut du paquet original : " + paquet32.getCardAt(0));
        System.out.println("Carte en haut du nouveau paquet : " + newPaquet.getCardAt(0));
    }
}

