package carte.model;

import carte.model.Factory.Couleur;
import carte.model.Factory.Valeur;

/**
 * Représente une carte avec une valeur et une couleur.
 */
public class Carte {
    
    protected Valeur valeur;
    protected Couleur couleur;

    /**
     * Crée une nouvelle carte.
     * @param valeur valeur de la carte
     * @param couleur couleur de la carte
     */
    public Carte(Valeur valeur, Couleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }
    
    /**
     * @return la valeur de la carte
     */
    public Valeur getValeur() {
        return this.valeur;
    }

    /**
     * @return la couleur de la carte
     */
    public Couleur getCouleur() {
        return this.couleur;
    }

    /**
     * Modifie la valeur de la carte.
     * @param valeur nouvelle valeur
     */
    public void setValeur(Valeur valeur) {
        this.valeur = valeur;
    }

    /**
     * Modifie la couleur de la carte.
     * @param couleur nouvelle couleur
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * @return représentation textuelle de la carte
     */
    @Override
    public String toString() {
        return "carte(" + this.valeur + ", " + this.couleur + ')';
    }  
    
}

