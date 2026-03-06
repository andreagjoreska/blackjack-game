package blackjack.view;

import java.awt.*;
import java.io.*;

import javax.swing.JPanel;

import carte.model.Carte;
import carte.model.Paquet;

/**
 * Cette classe représente une vue avec un paquet de cartes cachées.
 */
public class ViewPacketHidden extends ViewPacket {

    private static final long serialVersionUID = 1L;
    public Dimension sizeEcran;
    public Paquet paquet;
    public int startPaquetLarg;
    public int startPaquetLong;

    /**
     * Constructeur avec un paque.
     * @param paquet Le paquet à afficher.
     */
    public ViewPacketHidden(Paquet paquet) {
        this.paquet = paquet;
        this.startPaquetLarg = 0;
        this.startPaquetLong = 0;
        this.paquet.ajoutEcouteur(this); 
    }

    /**
     * Constructeur sans paramètre, initialise un paquet vide.
     */
    public ViewPacketHidden() {
        this(new Paquet());
        this.startPaquetLarg = 0;
        this.startPaquetLong = 0;
        this.paquet.ajoutEcouteur(this);
    }

    /**
     * Retourne la taille de l'écran.
     * @return La dimension de l'écran.
     */
    public Dimension getsizeEcran() {
        return this.sizeEcran;
    }

    /**
     * Méthode de dessin de la vue avec les cartes du paquet.
     * @param graphics Le graphique utilisé pour dessiner.
     */
    @Override
    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        Rectangle bounds = new Rectangle(this.startPaquetLarg + 20, this.startPaquetLong + 20, 103, 138);

        for (int i = 0; i < this.paquet.getPaquetSize(); i++) {
            if (i > 280) {
                if (i % 2 == 0) {
                    graphics.setColor(Color.white);
                } else {
                    graphics.setColor(Color.black);
                }
                g2d.draw(bounds);
                bounds.translate(1, 1); // Décale chaque carte
            }
        }

        try {
            File filename = new File("src/images/Medium/ARRIERE_LARGE.png");
            g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int) bounds.getX(), (int) bounds.getY(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

