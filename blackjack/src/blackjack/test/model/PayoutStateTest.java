package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import blackjack.model.Game;
import blackjack.model.GameContext;
import blackjack.model.PayoutState;
import blackjack.model.Player;
import blackjack.model.PlayerHand;

import carte.model.Carte;
import carte.model.Factory;

/**
 * Test de l'état PayoutState sans utiliser la vraie DealerHand.
 */
public class PayoutStateTest {

    /**
     * Faux jeu qui ne dépend pas de DealerHand.
     * On surcharge payHand pour avoir un comportement déterministe.
     */
    private static class FakeGame extends Game {
        public FakeGame(ArrayList<Player> players) {
            super(players);
        }

        @Override
        public void payHand(PlayerHand pHand) {
            // Paiement fixe : 2 x la mise (comme si le joueur gagnait)
            pHand.pay(2.0);
        }
    }

    private FakeGame g;
    private GameContext ctx;
    private Player p;
    private PlayerHand hand;
    private PayoutState payoutState;

    @Before
    public void setUp() {
        p = new Player(100);
        ArrayList<Player> ps = new ArrayList<>();
        ps.add(p);

        g = new FakeGame(ps);
        ctx = g.getGameContext();

        payoutState = new PayoutState();
        ctx.setState(payoutState);

        hand = new PlayerHand(10, p, g);
        p.addHand(hand);

        hand.add(new Carte(Factory.Valeur.CINQ, Factory.Couleur.COEUR));
        hand.add(new Carte(Factory.Valeur.SEPT, Factory.Couleur.CARREAU));
    }

    @Test
    public void testEndRoundPayout() {
        double before = p.getBalance();
        boolean res = payoutState.endRound(ctx);
        assertTrue(res);
        assertEquals(before + 20, p.getBalance(), 0.001);
        assertEquals(0, p.getHands().size());
        assertEquals("MISE", ctx.getCurrentStateName());
    }
}

