package blackjack.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import blackjack.model.Game;
import blackjack.model.Player;
import blackjack.model.PlayerStatisticsAdapter;
import blackjack.model.PlayerStatistics;
import blackjack.model.StatisticsManager;
import carte.util.observer.*;

/**
 * C'est la classe principale de la vue du jeu de Blackjack.
 * Elle gère l'interface graphique complète avec tous les composants visuels et les interactions.
 */
public class View extends JFrame implements EcouteurModele{

	private static final long serialVersionUID = 1L;
	private Game game;
	protected JPanel panel;
	public ViewPacketHidden viewDeck;
	public ViewPacketVisible viewPlayer;
	public ViewPacketVisible viewPlayer2;
	public ViewPacketVisible viewDealer;
	public JPanel panelJoueur;
	public BufferedImage myPicture;
	private JButton buttonSplit  = new JButton("Split");
	private JButton buttonHit = new JButton("Hit");
	private JButton buttonStand  = new JButton("Stand");
	private JButton buttonDouble  = new JButton("Double");
	private JButton buttonInsurance = new JButton("Insurance"); 
	private JTextField bet = new JTextField("Entrez votre mise :"); 
	private JButton boutonField = new JButton ("Appliquer votre mise") ; 
	private JLabel balance = new JLabel() ; 
	private JLabel P = new JLabel("Vous jouez ici !");
	private Runnable onExitCallback = null;
	
	/**
	 * Constructeur qui initialise toute l'interface graphique du jeu.
	 * @param game L'instance du jeu qui sera affichée
	 */
	public View(Game game) {
		
		this.game = game;
		this.setTitle("Jeu de Blackjack");
		Container contentPane = this.getContentPane();
		Font font = new Font("Monospace", Font.PLAIN, 20);
		
		panelJoueur = new JPanel();

		viewPlayer= new ViewPacketVisible();	
		viewPlayer.setBackground(new Color(0,102,0));
		viewPlayer.setBounds(300,100,400,400);
		viewPlayer.add(P);
		
		P.setBackground(new Color(0,102,255));
	
		viewPlayer2 = new ViewPacketVisible();
		viewPlayer2.setBackground(new Color(0,102,0));
		viewPlayer2.setBounds(100,100,300,300);
		viewPlayer2.setPreferredSize(new Dimension(400,400));
		viewPlayer.setPreferredSize(new Dimension(400,400));
		
		panelJoueur.setLayout(new GridLayout());
		panelJoueur.setBackground(Color.blue);
		panelJoueur.add(viewPlayer);
		
		viewDealer = new ViewPacketVisible();
		viewDealer.setBackground(new Color(0,102,0));
		viewDealer.setPreferredSize(new Dimension(700,200));
		
		// Configuration de la vue du deck (paquet caché)
		viewDeck = new ViewPacketHidden();
		viewDeck.setPreferredSize(new Dimension(300,250));
		viewDeck.setBackground(new Color(0,102,0));

		balance.setForeground(Color.white);
		
		JPanel topButtonPanel = new JPanel(new GridLayout(1, 5, 10, 10)); 
		topButtonPanel.setBackground(Color.black);
		topButtonPanel.add(buttonHit);
		topButtonPanel.add(buttonStand);
		topButtonPanel.add(buttonSplit);
		topButtonPanel.add(buttonDouble);
		topButtonPanel.add(buttonInsurance);  
		
		// Panneau du bas avec le champ de mise
		JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		bottomButtonPanel.setBackground(Color.black);
		bottomButtonPanel.add(bet);       
		bottomButtonPanel.add(boutonField); 

		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS)); 
		gridPanel.setBorder(new EmptyBorder(30, 20, 20, 20));
		gridPanel.setBackground(Color.black);

		gridPanel.add(topButtonPanel);  
		gridPanel.add(bottomButtonPanel); 
		bet.setPreferredSize(new Dimension(200, 30));
		
		// Panneau principal avec la balance et les contrôles
		this.panel = new JPanel(new BorderLayout());
		this.panel.setBackground(Color.black);
		this.panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.panel.add(balance, BorderLayout.NORTH);
		this.panel.add(gridPanel, BorderLayout.CENTER);
		this.panel.setPreferredSize(new Dimension(100, 200));

		balance.setForeground(Color.white);
		balance.setHorizontalAlignment(SwingConstants.CENTER);
		balance.setFont(new Font("Monospace", Font.BOLD, 18));

		JPanel dealer = new JPanel();
		JPanel text = new JPanel();
		
		// Chargement de l'image et redimensionnement
		try {
		    File imageFile = new File("src/images/png4.png");
		    this.myPicture = ImageIO.read(imageFile);
		} catch (IOException e) {
		    e.printStackTrace(); 
		}

		ImageIcon imageIcon = new ImageIcon(this.myPicture);
		Image image = imageIcon.getImage();  
		Image newimg = image.getScaledInstance(350, 300,  java.awt.Image.SCALE_SMOOTH);  
		imageIcon = new ImageIcon(newimg); 

		JLabel picLabel = new JLabel(imageIcon);
		picLabel.setSize(120,120);

		picLabel.setVerticalAlignment(SwingConstants.TOP);
		picLabel.setBorder(new EmptyBorder(-20, 0, 0, 0)); 
		
		text.setLayout(new BorderLayout());
		text.add(picLabel, BorderLayout.CENTER);
		text.setPreferredSize(new Dimension(120,120));
		text.setBackground(new Color(0,102,0));
		
		// Organisation du panneau du croupier
		dealer.setLayout(new BorderLayout());
		dealer.add(viewDealer , BorderLayout.WEST);	
		dealer.add(viewDeck , BorderLayout.EAST);
		
		panelJoueur.add(text);
		panelJoueur.add(viewPlayer2);
		
		contentPane.add(dealer , BorderLayout.NORTH);	
		contentPane.add(panel , BorderLayout.SOUTH);
		contentPane.add(panelJoueur, BorderLayout.CENTER);	

		this.setSize(1000, 1000);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new BorderLayout());	
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		game.ajoutEcouteur(this);
	}
	
	/**
	 * Méthode appelée automatiquement quand le modèle est mis à jour.
	 * Rafraîchit tous les composants visuels pour afficher les changements.
	 * @param source L'objet qui a déclenché la mise à jour
	 */
	@Override
	public void modeleMisAJour(Object source) {
		viewDeck.repaint(); 
		viewPlayer.repaint();
		viewDealer.repaint();
		viewPlayer2.repaint();
		panelJoueur.repaint();
    	panelJoueur.revalidate();
		if (source instanceof Game) {
		    Game game = (Game) source;
		    SetJlabelBalance(String.valueOf(game.getPlayerBalance()));
		}
	}

	/**
	 * Retourne la vue du paquet caché.
	 * @return L'instance de ViewPacketHidden
	 */
	public ViewPacketHidden getViewPacketHidden() {
		return viewDeck;
	}

	/**
	 * Retourne la vue du paquet du joueur.
	 * @return L'instance de ViewPacketVisible du joueur
	 */
	public ViewPacketVisible getVuePaquetJoueur() {
		return viewPlayer;
	}
	
	/**
	 * Retourne le bouton Hit.
	 * @return Le JButton pour tirer une carte
	 */
	public JButton getJButtonHit() {
		return buttonHit;
	}
	
	/**
	 * Retourne le bouton Split.
	 * @return Le JButton pour séparer les cartes
	 */
	public JButton getJButtonSplit() {
		return buttonSplit;
	}

	/**
	 * Retourne le bouton Stand.
	 * @return Le JButton pour rester
	 */
	public JButton getJButtonStand() {
		return buttonStand;
	}
	
	/**
	 * Retourne le bouton Double.
	 * @return Le JButton pour doubler la mise
	 */
	public JButton getJButtonDouble() {
		return buttonDouble;
	}
	
	/**
	 * Retourne le bouton Insurance.
	 * @return Le JButton pour prendre une assurance
	 */
	public JButton getJButtonInsurance() {
		return buttonInsurance;
	}

	/**
	 * Retourne le bouton pour appliquer la mise.
	 * @return Le JButton qui valide la mise
	 */
	public JButton getJButtonField() {
		return boutonField;
	}

	/**
	 * Modifie le texte affiché dans le champ de mise.
	 * @param string Le texte à afficher
	 */
	public void SetField(String string){
		bet.setText(string);
	}

	/**
	 * Retourne le champ de texte pour la mise.
	 * @return Le JTextField de la mise
	 */
	public JTextField getJTextField() {
		return bet;
	}
	
	/**
	 * Met à jour l'affichage de la balance du joueur.
	 * @param balancePlayer La balance à afficher
	 */
	public void SetJlabelBalance(String balancePlayer){
		balance.setText("Balance : " +  balancePlayer );
	}
	
	/**
	 * Retourne le label "Vous jouez ici".
	 * @return Le JLabel d'indication
	 */
	public JLabel getP(){
		return P;
	}
	
	/**
	 * Définit le callback à exécuter lors de la fermeture.
	 * @param callback La fonction à exécuter
	 */
	public void setOnExitCallback(Runnable callback) {
		this.onExitCallback = callback;
	}
	
	/**
	 * Affiche une fenêtre avec les statistiques du joueur humain.
	 * Montre le solde initial, final, les gains/pertes et le nombre de blackjacks.
	 * @param game L'instance du jeu contenant les statistiques
	 */
	public void showStatistics(Game game) {
		StatisticsManager statsManager = game.getStatisticsManager();
		
		JDialog statsDialog = new JDialog(this, "Statistiques de la partie", true);
		statsDialog.setLayout(new BorderLayout());
		statsDialog.setSize(500, 350);
		statsDialog.setLocationRelativeTo(this);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(0, 102, 0));
		JLabel titleLabel = new JLabel("Vos statistiques");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titlePanel.add(titleLabel);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
		
		// Recherche des statistiques du joueur humain
		for (Player p : game.getPlayers()) {
			if (p instanceof PlayerStatisticsAdapter) {
				PlayerStatisticsAdapter adapter = (PlayerStatisticsAdapter) p;
				PlayerStatistics stats = adapter.getStatistics();
				
				if (!stats.isBot()) {
					JPanel playerPanel = new JPanel();
					playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
					playerPanel.setBackground(Color.WHITE);
					playerPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
					
					JPanel gridPanel = new JPanel(new GridLayout(0, 2, 15, 10));
					gridPanel.setBackground(Color.WHITE);
					
					addStatRow(gridPanel, "Solde initial:", String.format("%.0f€", stats.getInitialBalance()));
					addStatRow(gridPanel, "Solde final:", String.format("%.0f€", stats.getCurrentBalance()));
					
					// Calcul et affichage du profit avec couleur differente pour gain et perte
					double profit = stats.getProfit();
					String profitText = String.format("%.0f€", profit);
					JLabel profitLabel = new JLabel("Gain/Perte:");
					profitLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
					JLabel profitValue = new JLabel(profitText);
					profitValue.setFont(new Font("SansSerif", Font.BOLD, 14));
					if (profit > 0) {
						profitValue.setForeground(new Color(0, 128, 0)); 
					} else if (profit < 0) {
						profitValue.setForeground(new Color(178, 34, 34)); 
					}
					gridPanel.add(profitLabel);
					gridPanel.add(profitValue);
					
					
					addStatRow(gridPanel, "Nombre de blackjacks obtenus:", String.valueOf(stats.getBlackjacksReceived()));
					
					playerPanel.add(gridPanel);
					contentPanel.add(playerPanel);
					
					break;
				}
			}
		}
		
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(null);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton closeButton = new JButton("Fermer");
		closeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		closeButton.setBackground(new Color(0, 102, 0));
		closeButton.setForeground(Color.WHITE);
		closeButton.setFocusPainted(false);
		closeButton.setPreferredSize(new Dimension(150, 40));
		closeButton.addActionListener(e -> statsDialog.dispose());
		buttonPanel.add(closeButton);
		
		statsDialog.add(titlePanel, BorderLayout.NORTH);
		statsDialog.add(scrollPane, BorderLayout.CENTER);
		statsDialog.add(buttonPanel, BorderLayout.SOUTH);
		
		statsDialog.setVisible(true);
	}
	
	/**
	 * Ajoute une ligne de statistique dans le panneau.
	 * @param panel Le panneau où ajouter la ligne
	 * @param label Le label de la statistique
	 * @param value La valeur de la statistique
	 */
	private void addStatRow(JPanel panel, String label, String value) {
		JLabel labelComp = new JLabel(label);
		labelComp.setFont(new Font("SansSerif", Font.PLAIN, 14));
		JLabel valueComp = new JLabel(value);
		valueComp.setFont(new Font("SansSerif", Font.BOLD, 14));	
		panel.add(labelComp);
		panel.add(valueComp);
	}
	
	/**
	 * Affiche un message de fin de jeu et ferme l'application.
	 * @param message Le message à afficher
	 */
	public void showEnd(String message){
		JOptionPane.showMessageDialog(this, message, "Jeu termine!", JOptionPane.ERROR_MESSAGE);
		if (onExitCallback != null) {
			onExitCallback.run();
		}
		System.exit(0);
	}
	
	/**
	 * Affiche un message d'erreur.
	 * @param message Le message d'erreur à afficher
	 */
	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Affiche une fenêtre de dialogue à la fin d'une manche pour demander si le joueur veut continuer.
	 * @param balance La balance actuelle du joueur
	 * @return La réponse du joueur (oui ou non)
	 */
	public int showEndGameDialog(double balance) {
        SetJlabelBalance(String.valueOf(balance));
        int response = JOptionPane.showConfirmDialog(this, 
            "Votre balance est : " + balance + "\nVoulez-vous continuer de jouer?", 
            "Tourne termine!", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        // Si le joueur ne veut pas continuer
        if (response == JOptionPane.NO_OPTION) {
            if (onExitCallback != null) {
                onExitCallback.run();
            }
            System.exit(0);
        } else if (response == JOptionPane.YES_OPTION) {
            // Sinon on démarre une nouvelle manche
            this.game.startRound();
            repaint();
        }
        return response;  
    }
}
