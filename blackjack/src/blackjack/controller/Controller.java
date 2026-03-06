package blackjack.controller;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import blackjack.model.*;
import blackjack.view.View;
import carte.model.Paquet;

/**
 * Le contrôleur qui gère les interactions entre la vue et le modèle du jeu de blackjack.
 * Il est responsable des actions de l'utilisateur, telles que les clics de souris, les frappes de touches et les événements associés aux boutons de la vue.
 */
public class Controller implements ActionListener, KeyListener {
	
	public View view;
	public Game game;
	

    /**
     * Constructeur qui initialise le contrôleur avec les joueurs et configure les événements de la vue.
     * @param joueurs Liste des joueurs qui participeront au jeu.
     */
	public Controller(ArrayList<Player> joueurs){
		this.game = new Game(joueurs);
		this.view = new View(this.game);
		this.view.getJButtonSplit().addActionListener(this);		
		this.view.getJButtonHit().addActionListener(this);		
		this.view.getJButtonField().addActionListener(this);	
		this.view.getJButtonDouble().addActionListener(this);		
		this.view.getJButtonStand().addActionListener(this);
		this.view.getJButtonInsurance().addActionListener(this);
		
		this.view.SetField("Quelle mise ?");
		view.viewDeck.paquet = game.getDeck();
		
		updateButtonStates();
		
		this.view.SetJlabelBalance(String.valueOf(game.getPlayers().get(0).getBalance()));
		this.view.setFocusable(true);
		this.view.addKeyListener(this);
		JTextField bet = this.view.getJTextField();
		
		bet.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				bet.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {		
			}		
		});
		
		this.view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				showFinalStatistics();
				System.exit(0);
			}
		});
		
		this.view.setOnExitCallback(new Runnable() {
			@Override
			public void run() {
				showFinalStatistics();
			}
		});
	}

    /**
     * Affiche la vue du jeu à l'écran.
     */
	public void displayView() {
		this.view.setSize(1000,1000);
		this.view.setLocationRelativeTo(null);
		this.view.setVisible(true);
	}

    /**
     * Gère les actions liées aux boutons cliqués par l'utilisateur.
     * @param e L'événement d'action généré par un bouton.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getPlayers().get(0).getBalance() <= 0) {
			view.showEnd("Jeu terminé ! Vous n'avez plus de solde.");
			showFinalStatistics();
			disableGameActions();
			return;  
		}

		GameContext context = game.getGameContext();
		String currentState = context.getCurrentStateName();

		if(e.getSource() == view.getJButtonField()) {	
			if (!currentState.equals("MISE")) {
				view.showError("Vous ne pouvez pas parier maintenant.");
				return;
			}
			
			handleBetPlacement();
			return; 
		}
		
		if (!currentState.equals("JEU")) {
			view.showError("Action non autorisée dans l'état actuel: " + currentState);
			return;
		}
		
		if(e.getSource() == view.getJButtonHit()) {
			handleHit();
		}
		else if(e.getSource() == view.getJButtonStand()) {
			handleStand();
		}
		else if(e.getSource() == view.getJButtonDouble()) {
			handleDouble();
		}
		else if(e.getSource() == view.getJButtonSplit()) {
			handleSplit();
		}
		else if(e.getSource() == view.getJButtonInsurance()) {
			handleInsurance();
		}
		
		updateButtonStates();
		view.repaint();
	}
	
    /**
     * Gère le placement de la mise par le joueur.
     */
	private void handleBetPlacement() {
		String betText = view.getJTextField().getText();
		
		try {
			int betAmount = Integer.parseInt(betText);
			
			if (betAmount <= 0) {
				view.showError("La mise doit être un numéro positif.");
				return;
			}
			
			GameContext context = game.getGameContext();
			
			for (Player player : game.getPlayers()) {
				if (!context.placeBet(player, betAmount)) {
					view.showError("Mise invalide. Balance insuffisante: " + player.getBalance());
					return;
				}
			}
			
			view.SetField(String.valueOf(betAmount));
			
			if (!context.startRound()) {
				view.showError("Impossible de démarrer la manche.");
				return;
			}
			
			context.startRound(); 
			setupViewForNewRound();
			checkAndOfferInsurance();
			immediateBlackjack();
			updateButtonStates();
			view.repaint();
			
		} catch (NumberFormatException ex) {
			view.showError("Mise invalide. La mise doit être un numéro positif.");
		}
	}
	
    /**
     * Configure la vue pour un nouveau tour de jeu.
     */
	private void setupViewForNewRound() {
		view.viewPlayer.doublePlay = true;
		view.viewPlayer.typeOfPlayer = "playerSimple";
		view.viewPlayer.paquet2 = new Paquet();
		view.viewPlayer2.indicationPlayer = 0;
		view.viewDealer.hideCardDealer = false;
		view.viewDealer.typeOfPlayer = "Dealer";
		
		Paquet dealerPaquet = game.getHand().getHand();
		Paquet paquetJoueur = game.getPlayers().get(0).getHands().get(0).getHand();
		Paquet paquetJoueur2 = game.getPlayers().get(1).getHands().get(0).getHand();
		
		view.viewDealer.paquet = dealerPaquet;
		view.viewPlayer.paquet = paquetJoueur; 
		view.viewPlayer2.paquet = paquetJoueur2;
	}
	

    /**
     * Vérifie si le joueur a un Blackjack immédiat.
     */
	private void immediateBlackjack() {
   	 Player humanPlayer = game.getPlayers().get(0);
	    if (!humanPlayer.getHands().isEmpty()) {
		PlayerHand humanHand = humanPlayer.getHands().get(0);
		if (humanHand.isBlackjack()) {
		    JOptionPane.showMessageDialog(view, 
		        "Blackjack ! Vous avez 21 !",
		        "Blackjack",
		        JOptionPane.INFORMATION_MESSAGE);
		    endRound();
		}
	    }
	}

    /**
     * Gère l'action de tirer une carte.
     */
	private void handleHit() {
	    view.getJButtonDouble().setEnabled(false);
	    view.getJButtonInsurance().setEnabled(false);
	    
	    GameContext context = game.getGameContext();
	    PlayerHand currentHand = getCurrentPlayerHand();
	    
	    if (currentHand == null) {
			view.showError("Aucune main active.");
			return;
	    }
	    
	    boolean result = context.hit(currentHand);
	    
	    if (!result) {
			view.showError("Impossible de tirer une carte.");
			return;
	    }
	    
	    if (currentHand.isBlackjack()) {
		JOptionPane.showMessageDialog(view, 
		    "BLACKJACK ! Vous avez 21 !",
		    "Blackjack",
		    JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    if (view.viewPlayer.typeOfPlayer.equals("playerSplit")) {
			if (view.viewPlayer.indicationPlayer == 1) {
				if (currentHand.isEnd()) {
				    view.viewPlayer.indicationPlayer = 2; 
				    updateButtonStates();
				}
			} else {
				if (currentHand.isEnd() || currentHand.isBlackjack()) {
				    endRound();
				}
			}
	    } else {
			if (currentHand.isEnd() || currentHand.isBlackjack()) {
				endRound();
			}
	    }
	    
	    updateButtonStates();
	    view.repaint();
	}

    /**
     * Gère l'action de rester pour le joueur.
     */
	private void handleStand() {
		GameContext context = game.getGameContext();
		PlayerHand currentHand = getCurrentPlayerHand();
		
		if (currentHand == null) {
			view.showError("Aucune main active.");
			return;
		}
		
		boolean result = context.stand(currentHand);
		
		if (!result) {
			view.showError("Impossible de rester.");
			return;
		}
		
		if (view.viewPlayer.typeOfPlayer.equals("playerSplit")) {
			if (view.viewPlayer.indicationPlayer == 1) {
				view.viewPlayer.indicationPlayer = 2;
				updateButtonStates();
			} else {
				endRound();
			}
		} else {
			endRound();
		}
		
		view.SetJlabelBalance(String.valueOf(game.getPlayers().get(0).getBalance()));
		view.repaint();
	}
	

    /**
     * Gère l'action de doubler la mise pour le joueur.
     */
	private void handleDouble() {
		view.getJButtonInsurance().setEnabled(false);
		
		GameContext context = game.getGameContext();
		PlayerHand currentHand = getCurrentPlayerHand();
		
		if (currentHand == null) {
			view.showError("Aucune main active.");
			return;
		}
		
		boolean result = context.putDouble(currentHand);
		
		if (!result) {
			view.showError("Impossible de doubler.");
			return;
		}
		
		if (view.viewPlayer.typeOfPlayer.equals("playerSplit")) {
			if (view.viewPlayer.indicationPlayer == 1) {
				view.viewPlayer.doublePlay = false;
				view.viewPlayer.indicationPlayer = 2; 
				updateButtonStates();
			} else {
				view.viewPlayer.doublePlay2 = false;
				endRound();
			}
		} else {
			view.viewPlayer.doublePlay = false;
			endRound();
		}
		
		view.repaint();
	}

    /**
     * Gère l'action de séparer la main du joueur.
     */
	private void handleSplit() {
	    GameContext context = game.getGameContext();
	    PlayerHand currentHand = getCurrentPlayerHand();
	    
	    if (currentHand == null) {
			view.showError("Aucune main active.");
			return;
	    }
	    
	    boolean result = context.split(currentHand);
	    
	    if (!result) {
			view.showError("Impossible de séparer.");
			return;
	    }
	    
	    view.viewPlayer.typeOfPlayer = "playerSplit";
	    
	    Paquet paquet1JoueurSplit = game.getPlayers().get(0).getHands().get(0).getHand();
	    Paquet paquet2JoueurSplit = game.getPlayers().get(0).getHands().get(1).getHand();
	    
	    view.viewPlayer.paquet = paquet1JoueurSplit; 
	    view.viewPlayer.paquet2 = paquet2JoueurSplit;
	    
	    boolean firstBlackjack = game.getPlayers().get(0).getHands().get(0).isBlackjack();
	    boolean secondBlackjack = game.getPlayers().get(0).getHands().get(1).isBlackjack();
	    
	    if (firstBlackjack || secondBlackjack) {
			String message = "";
		if (firstBlackjack && secondBlackjack) {
		    message = "DOUBLE BLACKJACK ! Les deux mains ont 21 !";
		} else if (firstBlackjack) {
		    message = "BLACKJACK sur la première main !";
		} else {
		    message = "BLACKJACK sur la deuxième main !";
		}
		
		JOptionPane.showMessageDialog(view, 
		    message,
		    "Blackjack",
		    JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    if (firstBlackjack) {
			game.getPlayers().get(0).getHands().get(0).stay();
	    }
	    
	    if (firstBlackjack && secondBlackjack) {
			endRound();
	    } else if (firstBlackjack) {
			view.viewPlayer.indicationPlayer = 2; 
	    } else {
			view.viewPlayer.indicationPlayer = 1; 
	    }
	    
	    view.getJButtonSplit().setEnabled(false);
	    updateButtonStates();
	    view.repaint();
	}
	
    /**
     * Gère l'action d'assurance pendant le jeu.
     */
	private void handleInsurance() {
		PlayerHand humanHand = game.getPlayers().get(0).getHands().get(0);
		
		if (!humanHand.canInsurance(game.getVisibleCard())) {
			view.showError("Assurance non disponible!");
			return;
		}
		
		int maxInsurance = humanHand.getBet() / 2;
		
		String input = JOptionPane.showInputDialog(view, 
			"Montant de l'assurance (max " + maxInsurance + "€):",
			maxInsurance);
		
		if (input == null) {
			view.getJButtonInsurance().setEnabled(false);
			return;
		}
		
		try {
			int insuranceAmount = Integer.parseInt(input);
			
			if (insuranceAmount <= 0 || insuranceAmount > maxInsurance) {
				view.showError("Montant invalide! Max: " + maxInsurance + "€");
				return;
			}
			
			boolean result = humanHand.takeInsurance(insuranceAmount);
			
			if (result) {
				view.SetJlabelBalance(String.valueOf(game.getPlayers().get(0).getBalance()));
				view.getJButtonInsurance().setEnabled(false);
				JOptionPane.showMessageDialog(view, 
					"Assurance prise: " + insuranceAmount + "€",
					"Assurance",
					JOptionPane.INFORMATION_MESSAGE);
			} else {
				view.showError("Impossible de prendre l'assurance!");
			}
			
		} catch (NumberFormatException ex) {
			view.showError("Montant invalide!");
		}
		
		view.repaint();
	}

    /**
     * Vérifie si une assurance peut être proposée et l'offre si possible.
     */
	private void checkAndOfferInsurance() {
		if (game.canInsurance()) {
			PlayerHand humanHand = game.getPlayers().get(0).getHands().get(0);
			
			if (humanHand.canInsurance(game.getVisibleCard())) {
				view.getJButtonInsurance().setEnabled(true);
				
				JOptionPane.showMessageDialog(view, 
					"Le croupier montre un As!\n" +
					"Vous pouvez prendre une assurance (max " + (humanHand.getBet() / 2) + "€)",
					"Assurance disponible",
					JOptionPane.INFORMATION_MESSAGE);
			}
			
			game.setInsurance(true);
		}
	}

    /**
     * Récupère la main active du joueur actuel.
     * @return La main active du joueur, ou null si aucune main n'est active.
     */
	private PlayerHand getCurrentPlayerHand() {
		Player player = game.getPlayers().get(0);
		
		if (player.getHands().isEmpty()) {
			return null;
		}
		
		if (view.viewPlayer.typeOfPlayer.equals("playerSplit")) {
			if (view.viewPlayer.indicationPlayer == 1) {
				return player.getHands().get(0);
			} else if (view.viewPlayer.indicationPlayer == 2) {
				return player.getHands().size() > 1 ? player.getHands().get(1) : null;
			}
		}
		
		return player.getHands().get(0);
	}

    /**
     * Met à jour l'état des boutons de la vue en fonction de l'état actuel du jeu.
     */
	private void updateButtonStates() {
		GameContext context = game.getGameContext();
		String currentState = context.getCurrentStateName();
		
		switch (currentState) {
			case "MISE":
				view.getJButtonField().setEnabled(true);
				view.getJButtonField().setFocusable(true);
				view.getJButtonHit().setEnabled(false);
				view.getJButtonStand().setEnabled(false);
				view.getJButtonDouble().setEnabled(false);
				view.getJButtonSplit().setEnabled(false);
				view.getJButtonInsurance().setEnabled(false);
				break;
				
			case "DISTRIBUTION":
				disableGameActions();
				break;
				
			case "JEU":
				view.getJButtonField().setEnabled(false);
				view.getJButtonField().setFocusable(false);
				view.getJButtonHit().setEnabled(true);
				view.getJButtonStand().setEnabled(true);
				
				PlayerHand currentHand = getCurrentPlayerHand();
				if (currentHand != null && !currentHand.isEnd()) {
					view.getJButtonDouble().setEnabled(currentHand.canDouble());
					view.getJButtonSplit().setEnabled(currentHand.canSplit());
				} else {
					view.getJButtonDouble().setEnabled(false);
					view.getJButtonSplit().setEnabled(false);
				}
				break;
				
			case "TOUR_CROUPIER":
			case "PAIEMENT":
				disableGameActions();
				break;
				
			default:
				disableGameActions();
				break;
		}
	}

    /**
     * Termine le tour actuel et prépare le jeu pour un nouveau tour.
     */
	private void endRound() {
		Player bot = game.getPlayers().get(1);
		if (!bot.getHands().isEmpty()) {
		    bot.playBestMove(bot.getHands().get(0), game.getHand().getHand().getCardAt(0));
		}
		
		game.endRound();
		
		GameContext context = game.getGameContext();
		context.setState(context.getBettingState());
		
		view.viewDealer.hideCardDealer = true;
		updateButtonStates(); 
		
		view.showEndGameDialog(game.getPlayers().get(0).getBalance());
		view.repaint();
		view.SetJlabelBalance(String.valueOf(game.getPlayers().get(0).getBalance()));
		
		if (game.getPlayers().get(0).getBalance() <= 0) {
		    view.showEnd("Jeu terminé ! Vous n'avez plus de solde.");
		    showFinalStatistics();
		    disableGameActions();
		}
	}

    /**
     * Désactive toutes les actions du jeu.
     */
    private void disableGameActions() {
        view.getJButtonHit().setEnabled(false);
        view.getJButtonStand().setEnabled(false);
        view.getJButtonDouble().setEnabled(false);
        view.getJButtonSplit().setEnabled(false);
        view.getJButtonInsurance().setEnabled(false);
    }

    /**
     * Affiche les statistiques finales du jeu.
     */
    private void showFinalStatistics() {
        game.showStatistics();
        view.showStatistics(game);
    }
	
    /**
     * Gère les événements de libération de touches du clavier.
     * @param k L'événement de touche libérée.
     */
	@Override
	public void keyReleased(KeyEvent k) {
		GameContext context = game.getGameContext();
		String currentState = context.getCurrentStateName();
		
		if (!currentState.equals("JEU")) {
			return;
		}
		
		if (k.getKeyChar() == 'h'){
			handleHit();
		}
		else if (k.getKeyChar() == 's'){
			handleSplit();
		}
		else if (k.getKeyChar() == 't'){
			handleStand();
		}
		else if (k.getKeyChar() == 'd'){
			handleDouble();
		}
		else if (k.getKeyChar() == 'i'){
			if (view.getJButtonInsurance().isEnabled()) {
				handleInsurance();
			}
		}
	}
	
    /**
     * Récupère la vue du jeu.
     * @return La vue actuelle du jeu.
     */
	public View getView(){
		return this.view;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}
}
