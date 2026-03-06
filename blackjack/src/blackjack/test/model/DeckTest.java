package test.model;

import blackjack.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DeckTest {
    private Deck d;
    
    @Before 
    public void setUp() {
        this.d = new Deck();
    }
    @After
    public void tearDown() {
        this.d = null;
    }
    
    @Test 
    public void testBurnFirstCard() {
        int size = this.d.getSize();
        this.d.removeFirstCard();
        assertEquals(this.d.getSize(), size - 1);
    }
    
    @Test
    public void testDistribute() {
        int size = this.d.getSize();
        this.d.distribute(new DealerHand());
        assertEquals(this.d.getSize(), size - 1);
    }
}
