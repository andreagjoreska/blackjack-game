package blackjack.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import carte.util.observer.EcouteurModele;  

/**
 * Cette classe abstraite représente un paquet de vues dans l'interface graphique du jeu.
 * Elle écoute les mises à jour du modèle et gère l'affichage.
 */
public abstract class ViewPacket extends JPanel implements EcouteurModele {

    private static final long serialVersionUID = 1L;


    /**
     * Méthode appelée lorsque le modèle est mis à jour.
     * Cela déclenche un rafraîchissement de l'affichage de la vue (repaint).
     * 
     * @param o L'objet mis à jour dans le modèle.
     */
    @Override
    public void modeleMisAJour(Object o){
        repaint(); 
    }

    /**
     * Méthode qui transforme une image depuis un fichier en un objet BufferedImage.
     * Cette méthode est utilisée pour charger une image à partir d'un fichier.
     * 
     * @param fileName Le nom du fichier image à charger.
     * @return L'image sous forme de BufferedImage.
     * @throws IOException Si un problème survient lors de la lecture du fichier.
     */
    public BufferedImage transformImage(String fileName) throws IOException {
        File file = new File(fileName); 
        //System.out.println(file.getAbsolutePath()); 
        FileInputStream fis = new FileInputStream(file); 
        BufferedImage image = ImageIO.read(fis);
        return image; 
    }
}

