package carte.util.observer;

/**
 * Interface représentant un écouteur du modèle.
 */
public interface EcouteurModele {

    /**
     * Méthode appelée lorsqu'un modèle notifie un changement.
     * @param source modèle mis à jour
     */
	public void modeleMisAJour(Object source);
}

