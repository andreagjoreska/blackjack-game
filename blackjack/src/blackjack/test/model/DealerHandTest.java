package test.model;

import blackjack.model.*;
import carte.model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

public class DealerHandTest {
	private DealerHand h;
	
	@Before
	public void setUp() {
		this.h = new DealerHand();
	}
	@After 
	public void tearDown() {
		this.h = null;
	}
	
	@Test
	public void testAdd() {
		int size = this.h.getSize(); 
		this.h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.COEUR));
		assertEquals(this.h.getSize(), size + 1);
	}
	@Test 
	public void testIsBlackjack() {
		this.h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.COEUR));
		this.h.add(new Carte(Factory.Valeur.VALET, Factory.Couleur.COEUR));
		assertEquals(this.h.isBlackjack(), true);
		this.h = new DealerHand();
		this.h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.COEUR));
		this.h.add(new Carte(Factory.Valeur.TROIS, Factory.Couleur.COEUR));
		assertEquals(this.h.isBlackjack(), false);	
	}
	@Test
	public void testIsEnd() {
		this.h.add(new Carte(Factory.Valeur.HUIT, Factory.Couleur.COEUR));
		this.h.add(new Carte(Factory.Valeur.VALET, Factory.Couleur.COEUR));
		assertEquals(this.h.isEnd(), true);
		this.h = new DealerHand();
		this.h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.COEUR));
		this.h.add(new Carte(Factory.Valeur.TROIS, Factory.Couleur.COEUR));
		this.h.add(new Carte(Factory.Valeur.TROIS, Factory.Couleur.COEUR));
		this.h.add(new Carte(Factory.Valeur.TROIS, Factory.Couleur.COEUR));
		assertEquals(this.h.isEnd(), false);	
	}
}
