package test.model;

import blackjack.model.*;
import carte.model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class GameTest {
    private Game g;
    private Player p; 
    private PlayerHand h;
    
    @Before
    public void setUp() {
        this.p = new Player(100);
        ArrayList<Player> pList = new ArrayList<>();
        pList.add(this.p);
        this.g = new Game(pList);
        this.h = new PlayerHand(10, this.p, this.g);
        this.p.addHand(this.h);
    }
    @After 
    public void tearDown() {
        this.p = null;
        this.h = null;
        this.g = null;
    }
    
    @Test
    public void testGetAllPlayersHand() {
        assertEquals(this.g.getAllPlayersHand().size(), 1);
        this.p.addHand(new PlayerHand(10, this.p, this.g));
        assertEquals(this.g.getAllPlayersHand().size(), 2);
    }
    
    @Test
    public void testStartRound() {
        assertEquals(this.h.getSize(), 0);
        assertEquals(this.g.getHand(), null);
        this.g.startRound();
        assertEquals(this.h.getSize(), 2);
        assertEquals(this.g.getHand().getSize(), 2);
    }
    
    @Test 
    public void testHandFinished() {
        assertEquals(this.g.handFinished(), false);
        this.h.stay();
        assertEquals(this.g.handFinished(), true);
    }
    
    @Test 
    public void testDistribute() {
        assertEquals(this.h.getSize(), 0);
        this.g.distribute(this.h);
        assertEquals(this.h.getSize(), 1);
        this.g.distribute(this.h);
        assertEquals(this.h.getSize(), 2);
    }
    
    @Test
    public void testPayHand() {
        this.g.startRound();
        this.g.getHand().getHand().removeCarte(0);
        this.g.getHand().getHand().removeCarte(0);
        this.g.getHand().add(new Carte(Factory.Valeur.SEPT, Factory.Couleur.CARREAU));
        this.g.getHand().add(new Carte(Factory.Valeur.SEPT, Factory.Couleur.CARREAU));
        
        double tmpBalance = this.p.getBalance();
        
        /* LOWEST */
        this.h.getHand().removeCarte(0);
        this.h.getHand().removeCarte(0);
        this.h.add(new Carte(Factory.Valeur.SEPT, Factory.Couleur.CARREAU));
        this.g.payHand(this.h);
        assertEquals(this.p.getBalance(), tmpBalance, 0.00001);
        
        /* EQUAL */
        tmpBalance += 10;
        this.h.add(new Carte(Factory.Valeur.SEPT, Factory.Couleur.CARREAU));
        this.g.payHand(this.h);
        assertEquals(this.p.getBalance(), tmpBalance, 0.00001);
        
        /* UPPER */
        tmpBalance += 20;
        this.h.add(new Carte(Factory.Valeur.SEPT, Factory.Couleur.CARREAU));
        this.g.payHand(this.h);
        assertEquals(this.p.getBalance(), tmpBalance, 0.00001);
        
        /* TOO UP */
        this.h.add(new Carte(Factory.Valeur.VALET, Factory.Couleur.CARREAU));
        this.g.payHand(this.h);
        assertEquals(this.p.getBalance(), tmpBalance, 0.00001);
        
        /* BLACKJACK */
        this.h.getHand().removeCarte(0);
        this.h.getHand().removeCarte(0);
        this.h.getHand().removeCarte(0);
        this.h.getHand().removeCarte(0);
        
        tmpBalance += 25;
        this.h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.CARREAU));
        this.h.add(new Carte(Factory.Valeur.VALET, Factory.Couleur.CARREAU));
        this.g.payHand(this.h);
        assertEquals(this.p.getBalance(), tmpBalance, 0.00001);
    }

}
