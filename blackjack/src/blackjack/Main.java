package blackjack;

import java.util.ArrayList;
import blackjack.model.*;
import blackjack.model.strategy.*;

/**
 * C'est la classe principale qui lance l'application de Blackjack.
 */
public class Main {

    /**
     * Méthode principale qui initialise et affiche le menu du jeu.
     *
     * @param args les arguments passés en ligne de commande 
     */
    public static void main(String[] args) {
        Menu menu = new Menu(); 
        menu.display(); 
    }

}

