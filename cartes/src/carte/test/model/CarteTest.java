package test.model;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import carte.model.*;
import carte.model.Factory.Couleur;
import carte.model.Factory.Valeur;

public class CarteTest {
	
	private Carte carte;
	
	@Before
	public void setUp() {
		this.carte = new Carte(Valeur.DEUX, Couleur.COEUR);
	}
	
	@Test
	public void TestGetValeur() {
		assertEquals(Valeur.DEUX ,this.carte.getValeur());
	}
	
	@Test
	public void TestGetCouleur() {
		assertEquals(Couleur.COEUR ,this.carte.getCouleur());
	}
	
	@Test
	public void TestSetValeur() {
		this.carte.setValeur(Valeur.ROI);
		assertEquals(Valeur.ROI ,this.carte.getValeur());
		
		this.carte.setValeur(null);
		assertNull(this.carte.getValeur());
	}
	
	@Test
	public void TestSetCouleur() {
		this.carte.setCouleur(Couleur.PIQUE);
		assertEquals(Couleur.PIQUE ,this.carte.getCouleur());
		
		this.carte.setCouleur(null);
		assertNull(this.carte.getCouleur());
	}
	
	@Test
	public void TestToString() {
		assertEquals("carte(DEUX, COEUR)" ,this.carte.toString());
	}
	
	@After
	public void tearDown() {
		this.carte = null;
	}
		
}
