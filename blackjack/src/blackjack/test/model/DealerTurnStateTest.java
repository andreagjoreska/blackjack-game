package test.model;

import blackjack.model.*;
import carte.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class DealerTurnStateTest {

    private Game g;
    private GameContext ctx;
    private DealerTurnState dealerState;
    private Player p;
    private PlayerHand pHand;
    private DealerHand dHand;

    class FakeGame extends Game {
        public FakeGame(ArrayList<Player> players) {
            super(players);
        }

        @Override
        public void distribute(Hand hand) {
            hand.add(new Carte(Factory.Valeur.CINQ, Factory.Couleur.COEUR));
        }
    }

    @Before
    public void setUp() {
        p = new Player(100);
        ArrayList<Player> ps = new ArrayList<>();
        ps.add(p);

        g = new FakeGame(ps);
        ctx = g.getGameContext();
        dealerState = new DealerTurnState();

        pHand = new PlayerHand(10, p, g);
        p.addHand(pHand);

        dHand = new DealerHand();
        g.startRound(); 
        ctx.setState(dealerState);
    }

    @Test
    public void testDealerTurnLogic() {
        boolean result = dealerState.endRound(ctx);

        assertTrue(result);
        assertEquals("MISE", ctx.getCurrentStateName());
    }
}
