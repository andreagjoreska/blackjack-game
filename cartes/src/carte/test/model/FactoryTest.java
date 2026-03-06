package test.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import carte.model.*;

public class FactoryTest {

	private Paquet paquet;

	@Before
	public void setUp() {
		this.paquet = Factory.createPaquetBlackjack();
	}

	@Test
	public void TestCreatePaquetBlackjack() {
	 	assertTrue(this.paquet.getPaquetSize() == 312);
	}

	@Test
	public void TestCreatePaquet52() {
		this.paquet = Factory.createPaquet52();
		assertTrue(this.paquet.getPaquetSize() == 52);
	}

	@Test
	public void TestCreatePaquet32() {
		this.paquet = Factory.createPaquet32();
		assertTrue(this.paquet.getPaquetSize() == 32);
	}

	@After
	public void tearDown() {
		this.paquet = null;
	}
}
