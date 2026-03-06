package blackjack.view;

import carte.util.observer.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import blackjack.model.*;
import carte.model.*;

/**
 * Cette classe gère l'affichage des paquets de cartes visibles dans le jeu de blackjack.
 * Elle permet d'afficher les cartes du joueur, du croupier et du robot avec différentes positions.
 */
public class ViewPacketVisible extends ViewPacket{
	
	private static final long serialVersionUID = 1L;
	public Dimension sizeEcran;
	public Paquet paquet;
	public Paquet paquet2;
	BufferedImage[] allImages;
	public boolean hideCardDealer ; 
	public String typeOfPlayer = "playerSimple" ; 
	public boolean doublePlay = true ; 
	public boolean doublePlay2 = true ; 
	public int indicationPlayer; 

	/**
	 * Constructeur qui initialise la vue avec un seul paquet.
	 * @param paquet Le paquet de cartes à afficher
	 */
	public ViewPacketVisible(Paquet paquet) {
		this.paquet = paquet;
		this.paquet2 = new Paquet();
	    this.sizeEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.hideCardDealer = true;
		this.indicationPlayer = 1;
		this.paquet.ajoutEcouteur(this);
	}
	
	/**
	 * Constructeur qui initialise la vue avec deux paquets (utilisé pour le split).
	 * @param paquet Le premier paquet de cartes
	 * @param paquet2 Le deuxième paquet de cartes
	 */
	public ViewPacketVisible(Paquet paquet, Paquet paquet2) {
		this.paquet = paquet;
		this.paquet2 = paquet2;		
	    this.sizeEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.hideCardDealer = true;
		this.indicationPlayer = 1; 
		this.paquet.ajoutEcouteur(this);
	}
	
	/**
	 * Constructeur par défaut qui crée deux paquets vides.
	 */
	public ViewPacketVisible() {
		this.paquet = new Paquet();
		this.paquet2 = new Paquet();
	    this.sizeEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.hideCardDealer = true;
		this.indicationPlayer = 1; 
		this.paquet.ajoutEcouteur(this);
	}
	
	/**
	 * Méthode qui dessine les cartes à l'écran selon le type de joueur.
	 * Elle gère l'affichage pour le joueur simple, le croupier, le robot et le mode split.
	 * @param g Le contexte graphique utilisé pour dessiner
	 */
	@Override
	  public void paint(Graphics g) {
		
		  super.paintComponent(g);
		  Graphics2D g2d = (Graphics2D) g;
		  g2d.setFont(new Font("Vous jouez ici", Font.PLAIN, 18));
		  g2d.setColor(new Color(255, 255, 255	));
		  
		  // Position de départ pour le premier paquet 
		  Rectangle bounds = new Rectangle(0,this.getHeight()-100, 70, 110 );	  
		  // Position de départ pour le deuxième paquet
		  Rectangle bounds2 = new Rectangle(this.getWidth()-100,this.getHeight()-100, 70, 110);
		  
	      switch(typeOfPlayer){
			  case "playerSimple":
				  // Affichage des cartes pour un joueur simple
				  for(int i=0; i<this.paquet.getPaquetSize(); i++) {
					  Carte c = this.paquet.getPaquet().get(i);
					  if (i==0){
						  try{
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);} 
						  catch (IOException e) {
							  e.printStackTrace();}
						  bounds.translate(0, -110);
						  if(indicationPlayer == 1)
						  {
							  g2d.drawString("Vous jouez ici", 90, 450);
						  }
						  
						  
					  }
					  else if (i==1){
						  try {
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
						  } 
						  catch (IOException e) {
							  e.printStackTrace();
						  } 
						  bounds.translate(10, -70);

					  }	  
					  else {
						  if(doublePlay == false )
						  {
							  try {
								  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
								  g2d.drawImage(this.transformImage(filename.getAbsolutePath()),(int)bounds.getX(), (int)bounds.getY(),this);
							  } 
							  catch (IOException e) {
								  e.printStackTrace();
							  } 
						  }
						  else {
							  try {
								  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
								  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
							  } 
							  catch (IOException e) {
								  e.printStackTrace();
							  } 
							  bounds.translate(30, -50);
						  }
					  }
				  }  	  
			  break;
			  case "Dealer":
				  // Affichage des cartes du croupier
			  	g2d.drawString("Croupier", 50, 40);
				  for(int i=0; i<this.paquet.getPaquetSize(); i++) {
					  Carte c = this.paquet.getPaquet().get(i);
					  if (i==0){
						  try {
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
						  } 
						  catch (IOException e) {
							  e.printStackTrace();
						  }	
						  bounds.translate(100, 0);	  
					  }
					  else if (i==1){
						  if(!this.hideCardDealer){
							  try {
								  File filenamehide = new File("src/images/Medium/ARRIERE.png"		  	 );
								  g2d.drawImage(this.transformImage(filenamehide.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
							  } 
							  catch (IOException e) {
								  e.printStackTrace();
							  }
							  bounds.translate(30, -50);	  
						  }
						  else{
							  try {
								  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
								  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
							  } 
							  catch (IOException e) {
								  e.printStackTrace();
							  }
							  bounds.translate(30,-50);	
						  }  					  
					  }			  
					  else{
						  try {
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null); 		
						  } 
						  catch (IOException e) {
							  e.printStackTrace();
						  }  
						  bounds.translate(40, -20);	   
					  }
				  }
				  break;
			  case "Robot":
				  // Affichage des cartes du robot
				if (this.paquet.getPaquetSize() > 0) {
					g2d.drawString("Robot", 90, 250);
				}
				
				for(int i=0; i<this.paquet.getPaquetSize(); i++) {
					Carte c = this.paquet.getPaquet().get(i);
					if (i==0){
						try{
						    File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png");
						    g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
						} 
						catch (IOException e) {
						    e.printStackTrace();
						}
						bounds.translate(0, -110);
					}
					else if (i==1){
						try {
						    File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png");
						    g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
						} 
						catch (IOException e) {
						    e.printStackTrace();
						} 
						bounds.translate(10, -70);
					}	  
					else {
						try {
						    File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png");
						    g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
						} 
						catch (IOException e) {
						    e.printStackTrace();
						} 
						bounds.translate(30, -50);
					}
				}
				break;
			  case "playerSplit":
				  // Affichage du premier paquet en mode split
				  for(int i=0; i<this.paquet.getPaquetSize(); i++) {
					  Carte c = this.paquet.getPaquet().get(i);

					  if (i==0){
						  try{
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null); }
						  catch (IOException e) {
							  e.printStackTrace();}
						  bounds.translate(0, -110);
						  if(indicationPlayer == 1)
						  {
							  g2d.drawString("Vous jouez ici", 90, 450);
						  }

					  }
				  else if (i==1){
					  try {
						  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
						  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);}		  
					  catch (IOException e) {
						  e.printStackTrace();
					  }
					  bounds.translate(30,-50);	
				  }  			  
				  else{
					  if(doublePlay == false )
					  {
						  try {
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()),(int)bounds.getX(), (int)bounds.getY(),this);
						  } 
						  catch (IOException e) {
							  e.printStackTrace();
						  } 
					  }
					  else {
						  try {
							  File filename = new File("src/images/Medium/" +c.getValeur() + "_" + c.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename.getAbsolutePath()), (int)bounds.getX(), (int)bounds.getY(),null);
						  } 
						  catch (IOException e) {
							  e.printStackTrace();
						  } 
						  bounds.translate(30, -50);
					  }
				  }	  
				  }
			  }
			  
			  // Affichage du deuxième paquet (utilisé pour le split)
			  for(int i=0; i<this.paquet2.getPaquetSize(); i++) {
				  Carte c2 = this.paquet2.getPaquet().get(i);
					  if (i==0){
						  try{
							  File filename2 = new File("src/images/Medium/" +c2.getValeur() + "_" + c2.getCouleur() +".png"		  	 );
							  g2d.drawImage(this.transformImage(filename2.getAbsolutePath()), (int)bounds2.getX(), (int)bounds2.getY(),null);} 
						  catch (IOException e) {
							  e.printStackTrace();}	 
						  bounds2.translate(0, -110);
						  if(indicationPlayer == 2)
						  {
							  g2d.drawString("Vous jouez ici", 90, 450);
						  }
					  }

					  else if (i==1){
							  try {
								  File filename2 = new File("src/images/Medium/" +c2.getValeur() + "_" + c2.getCouleur() +".png"		  	 );
								  g2d.drawImage(this.transformImage(filename2.getAbsolutePath()), (int)bounds2.getX(), (int)bounds2.getY(),null);
							  } 
							  catch (IOException e) {
								  e.printStackTrace();
							  }
							  bounds2.translate(-60, -50);
					  }		 
					  else{
							  if(doublePlay2 == false )
							  {
								  try {

									  File filename2 = new File("src/images/Medium/" +c2.getValeur() + "_" + c2.getCouleur() +".png"		  	 );
									  g2d.drawImage(this.transformImage(filename2.getAbsolutePath()),(int)bounds2.getX(), (int)bounds2.getY(),this);

								  			} 
								  	catch (IOException e) {
								  		e.printStackTrace();
								  	} 
							  }
							  else {
								  try {
									  File filename2 = new File("src/images/Medium/" +c2.getValeur() + "_" + c2.getCouleur() +".png"		  	 );
									  g2d.drawImage(this.transformImage(filename2.getAbsolutePath()), (int)bounds2.getX(), (int)bounds2.getY(),null);
								  			} 
								  	catch (IOException e) {
								  		e.printStackTrace();
								  	} 
								  bounds2.translate(-30, -50);
							  }}	  
					  }
				  } 

}
