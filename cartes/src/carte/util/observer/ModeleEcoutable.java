package carte.util.observer;

/**
 * Interface définissant un modèle observable.
 */
public interface ModeleEcoutable {

    /**
     * Ajoute un écouteur au modèle.
     * @param e écouteur à ajouter
     */
    public void ajoutEcouteur(EcouteurModele e);

    /**
     * Retire un écouteur du modèle.
     * @param e écouteur à retirer
     */
    public void retraitEcouteur(EcouteurModele e);
}

