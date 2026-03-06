package carte.util.observer;
import java.util.*;

/**
 * Classe abstraite implémentant le pattern Observateur.
 * Permet à un modèle d'être écouté par plusieurs observateurs.
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

	public List<EcouteurModele> ecouteurs;

	/**
	 * Constructeur.
	 * @param ecouteurs liste d'écouteurs initiale
	 */
	public AbstractModeleEcoutable(List<EcouteurModele> ecouteurs) {
		this.ecouteurs = ecouteurs;
	}

	/**
	 * Ajoute un écouteur au modèle.
	 * @param e écouteur à ajouter
	 */
	public void ajoutEcouteur(EcouteurModele e) {
		this.ecouteurs.add(e);
	}

	/**
	 * Retire un écouteur du modèle.
	 * @param e écouteur à retirer
	 */
	public void retraitEcouteur(EcouteurModele e) {
		this.ecouteurs.remove(e);
	}

	/**
	 * Notifie tous les écouteurs d'un changement dans le modèle.
	 */
	protected void fireChangement() {
		for (EcouteurModele e : this.ecouteurs) {
			e.modeleMisAJour(this);
		}
	}
}

