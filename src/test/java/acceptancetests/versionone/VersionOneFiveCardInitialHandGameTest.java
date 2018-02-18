package acceptancetests.versionone;

import com.googlecode.yatspec.junit.SpecRunner;
import com.hanfak.domain.*;
import com.hanfak.wiring.PokerGame;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.Card.card;
import static com.hanfak.domain.Player.player;
import static com.hanfak.domain.Rank.*;
import static com.hanfak.domain.Suit.DIAMOND;
import static com.hanfak.domain.Suit.SPADE;

@RunWith(SpecRunner.class)
public class VersionOneFiveCardInitialHandGameTest implements WithAssertions {

    @Test
    public void playerWinsOneRoundGame() throws Exception {
        // TODO Given to mock the deck.dealHand to give exactly the cards needed

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasAHandOf(ACE_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES, FOUR_OF_SPADES, FIVE_OF_SPADES);
        andPlayerTwoHasAHandOf(EIGHT_OF_SPADES, THREE_OF_DIAMONDS, NINE_OF_DIAMONDS, JACK_OF_DIAMONDS, KING_OF_SPADES);
        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    // TODO test draw

    // TODO test multiple players, record place

    // TODO test multiple games, keep track of scores

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(playerOne, playerTwo);
        System.out.println("play = " + play.get(0).hand.cards);
    }

    private void andPlayerTwoHasAHandOf(Card... cards) {
        Hand hand = play.get(1).hand;

        thenPlayerHasTheseCards(cards, hand);
    }

    private void thenPlayerOneHasAHandOf(Card... cards) {
        Hand hand = play.get(0).hand;
        System.out.println("hand = " + hand.cards);
        thenPlayerHasTheseCards(cards, hand);
    }

    private void thenPlayerHasTheseCards(Card[] cards, Hand actual) {
        List<String> expected = Arrays.stream(cards).map(Card::toString).collect(Collectors.toList());
        String[] myArray = expected.toArray(new String[expected.size()]);

        assertThat(actual.cards.stream().map(Card::toString).collect(Collectors.toList()))
                .containsExactlyInAnyOrder(myArray);
    }

    private void andPlayerOneHasWon() {
        Result result = play.get(0).result;
        assertThat(result).isEqualTo(Result.WIN);
    }

    private void andPlayerTwoHasLost() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.LOSS);
    }

    private static final Card THREE_OF_SPADES = card(THREE, SPADE);
    private static final Card ACE_OF_SPADES = card(ACE, SPADE);
    private static final Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
    private static final Card FOUR_OF_SPADES = card(FOUR, SPADE);
    private static final Card FIVE_OF_SPADES = card(FIVE, SPADE);
    private static final Card EIGHT_OF_SPADES = card(EIGHT, SPADE);
    private static final Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);
    private static final Card NINE_OF_DIAMONDS = card(NINE, DIAMOND);
    private static final Card JACK_OF_DIAMONDS = card(JACK, DIAMOND);
    private static final Card KING_OF_SPADES = card(KING, SPADE);
    private static final  String VERSION = "1.0";

    private static final Player playerTwo = player("Player Two", null);
    private static final Player playerOne = player("Player One", null);

    private List<PlayerResult> play;

    private PokerGame pokerGame;

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
