package test.model;

import blackjack.model.*;
import carte.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PlayerHandTest {

    private Player p;
    private Game g;
    private PlayerHand h;

    class FakeGame extends Game {
        Carte fixedCard;

        public FakeGame(Carte c) {
            super(new ArrayList<>());
            this.fixedCard = c;
        }

        @Override
        public void distribute(Hand h) {
            h.add(fixedCard);
        }
    }

    @Before
    public void setUp() {
        p = new Player(100);
        g = new FakeGame(new Carte(Factory.Valeur.CINQ, Factory.Couleur.COEUR));
        h = new PlayerHand(10, p, g);
        p.addHand(h);
    }

    @After
    public void tearDown() {
        h = null;
        p = null;
    }


    @Test
    public void testHit() {
        int sizeBefore = h.getHand().getPaquetSize();
        h.hit();
        assertEquals(sizeBefore + 1, h.getHand().getPaquetSize());
    }

    @Test
    public void testStay() {
        h.stay();
        assertTrue(h.isEnd());
    }

    @Test
    public void testDoDouble() {
        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.CARREAU));
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.CARREAU));

        double balanceBefore = p.getBalance();

        h.doDouble();

        assertEquals(3, h.getHand().getPaquetSize());

        assertEquals(balanceBefore - 10, p.getBalance(), 0.001);

        assertTrue(h.isEnd());
        assertTrue(h.isDouble());
    }

    @Test
    public void testPay() {
        double before = p.getBalance();
        h.pay(2); 
        assertEquals(before + 20, p.getBalance(), 0.001);
    }


    @Test
    public void testSplit() {
        h.add(new Carte(Factory.Valeur.HUIT, Factory.Couleur.COEUR));
        h.add(new Carte(Factory.Valeur.HUIT, Factory.Couleur.PIQUE));

        int nbHand = p.getHands().size();
        double balanceBefore = p.getBalance();

        h.split();

        assertEquals(nbHand + 1, p.getHands().size());

        assertEquals(balanceBefore - h.getBet(), p.getBalance(), 0.001);

        assertEquals(1, h.getHand().getPaquetSize());
        assertEquals(1, p.getHands().get(1).getHand().getPaquetSize());
    }


    @Test
    public void testCanSplit() {
        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.CARREAU));
        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.PIQUE));
        assertTrue(h.canSplit());

        h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.CARREAU));
        assertFalse(h.canSplit());
    }

    @Test
    public void testCanDouble() {
        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.CARREAU));
        h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.PIQUE));
        assertTrue(h.canDouble());

        h.add(new Carte(Factory.Valeur.TROIS, Factory.Couleur.COEUR));
        assertFalse(h.canDouble());
    }


    @Test
    public void testIsEnd() {
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.CARREAU));
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.PIQUE));
        h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.COEUR));
        assertTrue(h.isEnd());
    }

    @Test
    public void testIsBlackjack() {
        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.CARREAU));
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.CARREAU));
        assertTrue(h.isBlackjack());
    }

    @Test
    public void testCount() {
        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.CARREAU));
        assertEquals(11, h.count());

        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.CARREAU));
        assertEquals(21, h.count());

        h.add(new Carte(Factory.Valeur.CINQ, Factory.Couleur.COEUR));
        assertEquals(16, h.count());

        h.add(new Carte(Factory.Valeur.AS, Factory.Couleur.PIQUE));
        assertEquals(17, h.count());
    }


    @Test
    public void testCanInsurance() {
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.COEUR));
        h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.COEUR));
        assertFalse(h.canInsurance(new Carte(Factory.Valeur.TROIS, Factory.Couleur.TREFLE)));
        assertTrue(h.canInsurance(new Carte(Factory.Valeur.AS, Factory.Couleur.COEUR)));
    }

    @Test
    public void testTakeInsurance() {
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.COEUR));
        h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.COEUR));

        double before = p.getBalance();

        boolean ok = h.takeInsurance(5);
        assertTrue(ok);

        assertEquals(before - 5, p.getBalance(), 0.001);
        assertEquals(5, h.getinsurance());
        assertTrue(h.hasIns());
    }

    @Test
    public void testResultInsurance() {
        h.add(new Carte(Factory.Valeur.DIX, Factory.Couleur.COEUR));
        h.add(new Carte(Factory.Valeur.DEUX, Factory.Couleur.COEUR));
        h.takeInsurance(10);

        double before = p.getBalance();
        h.resultInsurance(true);
        assertEquals(before + 20, p.getBalance(), 0.001); // -10 + 30 = +20 net
    }
}
