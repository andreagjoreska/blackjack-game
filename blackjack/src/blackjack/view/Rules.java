package blackjack.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Cette classe affiche les règles du jeu de Blackjack.
 */
public class Rules extends JFrame {

    /**
     * Constructeur de la fenêtre des règles du jeu.
     */
    public Rules() {
        setTitle("Règles du Blackjack");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Définir la couleur de fond de la fenêtre
        getContentPane().setBackground(new Color(0, 128, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 0));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Règles du Blackjack");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Zone de texte contenant les règles du jeu
        JTextArea texte = new JTextArea(
            "1. Le but est d'obtenir une main d'une valeur de 21 ou aussi proche que possible sans la dépasser.\n" +
            "2. Les cartes de 2 à 10 valent leur valeur faciale.\n" +
            "3. Les figures (Valet, Dame, Roi) valent 10 points.\n" +
            "4. Un As peut valoir 1 ou 11 points, selon ce qui est le plus avantageux.\n" +
            "5. Un Blackjack est une main composée d'un As et d'une carte de 10 points.\n" +
            "6. Vous pouvez tirer une carte (Hit), rester avec votre main (Stay), diviser (Split) ou doubler (Double).\n" +
            "7. Le croupier doit tirer une carte si sa main est inférieure à 17.\n" +
            "8. Si vous dépassez 21, vous perdez.\n" +
            "9. Le paiement pour une victoire est de 1:1, mais un Blackjack paie 3:2.\n"
        + "10. Vous pouvez utiliser votre clavier pour jouer (h - hit, s - split, t - stand, i - insurance, d - double)"
        );
        texte.setEditable(false);
        texte.setFont(new Font("Serif", Font.PLAIN, 18));
        texte.setForeground(Color.WHITE);
        texte.setBackground(new Color(0, 128, 0));
        texte.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        texte.setCaretColor(Color.WHITE);

        // Panneau contenant la zone de texte avec les règles
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(0, 128, 0));
        textPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        textPanel.setLayout(new BorderLayout());
        textPanel.add(texte, BorderLayout.CENTER);

        // Bouton pour fermer la fenêtre
        JButton close = new JButton("Fermer");
        close.setFont(new Font("Serif", Font.BOLD, 20));
        close.setBackground(new Color(255, 69, 0));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.setPreferredSize(new Dimension(150, 40));
        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        close.addActionListener(e -> dispose());

        // Ajouter tous les composants au panneau
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        panel.add(textPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); 
        panel.add(close);

        getContentPane().add(panel);

        setVisible(true); 
    }
}

