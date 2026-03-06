package test.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import blackjack.model.*;

public class PlayerTest {
	private Player p;
	private Game g;
	
	@Before
	public void setUp() {
		this.p = new Player(100);
		this.g = new Game(new ArrayList<>());
	}
	
	@After
	public void tearDown() {
		this.p = null;
	}
	
	@Test
	public void testOpenHand() {
		this.p.openHand(10, this.g);
		assertEquals(this.p.getHands().size(), 1);		
	}
	
	@Test
	public void testAddToBalance() {
		double balance = this.p.getBalance();
		this.p.addToBalance(20);
		assertEquals(this.p.getBalance(), balance + 20, 0.0001);
	}
	
	@Test
	public void testAddHand() {
		int size = this.p.getHands().size();
		this.p.addHand(new PlayerHand(10, this.p ,this.g));
		assertEquals(this.p.getHands().size(), size + 1);
	}
	
}
