package acceptancetests.versionone;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.cards.Card;
import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;
import com.hanfak.domain.game.playershand.Hand;
import com.hanfak.wiring.PokerGame;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hanfak.domain.cards.Card.card;
import static com.hanfak.domain.cards.Rank.*;
import static com.hanfak.domain.cards.Suit.CLUB;
import static com.hanfak.domain.cards.Suit.DIAMOND;
import static com.hanfak.domain.cards.Suit.HEART;
import static com.hanfak.domain.cards.Suit.SPADE;
import static com.hanfak.domain.game.Player.player;

@RunWith(SpecRunner.class)
public class VersionOneFiveCardInitialHandGameTest extends TestState implements WithAssertions {

    // NOTE no need to test the cards exists as we have provided it in the givens
    @Test
    public void playerOneWinsOneRoundGameWithTheSameBestHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsToPlayerOne();
        andADeckDealsOutASetOfRandomCardsToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasAHandOf(ACE_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES, TWO_OF_SPADES, FIVE_OF_SPADES);
        andPlayerTwoHasAHandOf(EIGHT_OF_SPADES, THREE_OF_DIAMONDS, NINE_OF_DIAMONDS, JACK_OF_DIAMONDS, KING_OF_SPADES);
        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    @Test
    public void playerTwoWinsOneRoundGame() throws Exception {
        givenADeckDealsOutASetOfRandomCardsToPlayerOneblah();
        andADeckDealsOutASetOfRandomCardsToPlayerTwoBlah();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playersDrawIfBothHaveTheSameHand() throws Exception {
        givenBothPlayersAreDealtAHand();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    // TODO test cards with different best hands ie pair over highcard
    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {
        Hand firstHandDealt = Hand.hand(PLAYER_WITH_OTHER_CARDS_TWO, null);
        Hand secondHandDealt = Hand.hand(PLAYER_WITH_PAIR_CARDS, null);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);
    }
    // TODO test cards for opp player
    // TODO test best hand, but different pair
    // TODO test for same pair but draw
    // TODO test for same pair but different high card

    // TODO test multiple players, record place - 1 - won, 2 to x - loss OR 1 - Draw, 1 - Draw, 3 to x - loss
    // who won or drew only if at the top, everyone else loses
    // Who will win money

    // TODO test multiple games, keep track of scores

    private void andADeckDealsOutASetOfRandomCardsToPlayerTwo() {
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand",PLAYER_TWO_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        // For readability
    }

    private void andADeckDealsOutASetOfRandomCardsToPlayerTwoBlah() {
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_OTHER_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand",PLAYER_TWO_OTHER_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        // For readability
    }

    private void givenADeckDealsOutASetOfRandomCardsToPlayerOne() {
        Hand firstHandDealt = Hand.hand(PLAYER_ONE_CARDS, null);
        Hand secondHandDealt = Hand.hand(PLAYER_TWO_CARDS, null);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);

    }

    private void givenADeckDealsOutASetOfRandomCardsToPlayerOneblah() {
        Hand firstHandDealt = Hand.hand(PLAYER_ONE_OTHER_CARDS, null);
        Hand secondHandDealt = Hand.hand(PLAYER_TWO_OTHER_CARDS, null);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);
    }

    private void andPlayerTwoHasDrawn() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.DRAW);
    }

    private void thenPlayerOneHasDrawn() {
        Result result = play.get(0).result;
        assertThat(result).isEqualTo(Result.DRAW);
    }

    private void givenBothPlayersAreDealtAHand() {
        Hand firstHandDealt = Hand.hand(PLAYER_ONE_DRAW_CARDS, null);
        Hand secondHandDealt = Hand.hand(PLAYER_TWO_DRAW_CARDS, null);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand",PLAYER_TWO_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
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

    @SuppressWarnings("ConstantConditions")
    private void andPlayerOneHasLost() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player One".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(Result.LOSS);
    }

    private void andPlayerTwoHasLost() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.LOSS);
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerTwoHasWon() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player Two".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(Result.WIN);
    }

    //  TODO: Mover to utility class or extend from a super class ie AcceptanceTest
    private static final Card THREE_OF_SPADES = card(THREE, SPADE);
    private static final Card ACE_OF_SPADES = card(ACE, SPADE);
    private static final Card ACE_OF_HEARTS = card(ACE, HEART);
    private static final Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
    private static final Card TWO_OF_HEARTS = card(TWO, HEART);
    private static final Card FOUR_OF_CLUBS = card(FOUR, CLUB);
    private static final Card FIVE_OF_SPADES = card(FIVE, SPADE);
    private static final Card FIVE_OF_HEARTS = card(FIVE, HEART);
    private static final Card EIGHT_OF_SPADES = card(EIGHT, SPADE);
    private static final Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);
    private static final Card NINE_OF_DIAMONDS = card(NINE, DIAMOND);
    private static final Card JACK_OF_DIAMONDS = card(JACK, DIAMOND);
    private static final Card KING_OF_SPADES = card(KING, SPADE);
    private static final Card KING_OF_HEARTS = card(KING, HEART);
    private static final Card TWO_OF_SPADES = card(TWO, SPADE);

    private static final List<Card> PLAYER_ONE_CARDS = Arrays.asList(ACE_OF_SPADES, FIVE_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_WITH_OTHER_CARDS_TWO = Arrays.asList(ACE_OF_SPADES, EIGHT_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_WITH_PAIR_CARDS = Arrays.asList(KING_OF_SPADES, FIVE_OF_SPADES, FIVE_OF_HEARTS, FOUR_OF_CLUBS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_TWO_CARDS = Arrays.asList(KING_OF_SPADES, JACK_OF_DIAMONDS, NINE_OF_DIAMONDS, EIGHT_OF_SPADES, THREE_OF_DIAMONDS);
    private static final List<Card> PLAYER_ONE_OTHER_CARDS = Arrays.asList(ACE_OF_HEARTS, FIVE_OF_SPADES, FOUR_OF_DIAMONDS, TWO_OF_SPADES, THREE_OF_SPADES);
    private static final List<Card> PLAYER_TWO_OTHER_CARDS = Arrays.asList(ACE_OF_SPADES, JACK_OF_DIAMONDS, FOUR_OF_CLUBS, TWO_OF_HEARTS, THREE_OF_DIAMONDS);
    private static final List<Card> PLAYER_ONE_DRAW_CARDS = Arrays.asList(ACE_OF_SPADES, FIVE_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_TWO_DRAW_CARDS = Arrays.asList(ACE_OF_HEARTS, FIVE_OF_HEARTS, TWO_OF_HEARTS, FOUR_OF_CLUBS, THREE_OF_DIAMONDS);
    private static final  String VERSION = "1.0";

    private static final Player playerTwo = player("Player Two");
    private static final Player playerOne = player("Player One");
    private final CardDealer cardDealer = Mockito.mock(CardDealer.class); // TODO use a stub

    private List<PlayerResult> play;

    private PokerGame pokerGame;

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
