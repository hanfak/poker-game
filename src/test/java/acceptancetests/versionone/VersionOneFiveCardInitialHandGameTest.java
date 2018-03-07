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

    @Test
    public void playerOneWinsOneRoundGameWithTheSameBestHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsToPlayerOne();
        andADeckDealsOutASetOfRandomCardsToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

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

    @Test
    public void highestPairWins() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithALowestPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAHighestPairToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playersDrawIfBothHaveTheSameHandWithAPair() throws Exception {
        givenBothPlayersAreDealtAHandWithMatchingPair();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    // TODO test for same pair but different high card

    private void andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo() {
        // TODO add interesting givens
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {
        Hand firstHandDealt = Hand.hand(PLAYER_WITH_OTHER_CARDS_TWO);
        Hand secondHandDealt = Hand.hand(PLAYER_WITH_PAIR_CARDS);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);
    }

    // TODO test cards for opp player // unit test

    private void givenADeckDealsOutASetOfRandomCardsWithALowestPairToPlayerOne() {
        Hand firstHandDealt = Hand.hand(PLAYER_WITH_PAIR_CARDS_TWO);
        Hand secondHandDealt = Hand.hand(PLAYER_WITH_PAIR_CARDS_THREE);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);
    }

    private void andADeckDealsOutASetOfRandomCardsWithAHighestPairToPlayerTwo() {

    }


    // TODo test for 2 pair
    // TODo test for full house
    // TODo test for straight
    // TODo test for flush
    // TODo test for straight flush vs straight vs flush


    // TODO (new class) test multiple players, record place - 1 - won, 2 to x - loss OR 1 - Draw, 1 - Draw, 3 to x - loss
    // who won or drew only if at the top, everyone else loses
    // Who will win money

    // TODO (new class) test multiple games, keep track of scores

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
        Hand firstHandDealt = Hand.hand(PLAYER_ONE_CARDS);
        Hand secondHandDealt = Hand.hand(PLAYER_TWO_CARDS);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);

    }

    private void givenADeckDealsOutASetOfRandomCardsToPlayerOneblah() {
        Hand firstHandDealt = Hand.hand(PLAYER_ONE_OTHER_CARDS);
        Hand secondHandDealt = Hand.hand(PLAYER_TWO_OTHER_CARDS);
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
        Hand firstHandDealt = Hand.hand(PLAYER_ONE_DRAW_CARDS);
        Hand secondHandDealt = Hand.hand(PLAYER_TWO_DRAW_CARDS);
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(firstHandDealt).thenReturn(secondHandDealt);
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand",PLAYER_TWO_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
    }

    private void givenBothPlayersAreDealtAHandWithMatchingPair() {
        Hand firstHandDealt = Hand.hand(PLAYER_WITH_PAIR_CARDS_THREE );
        Hand secondHandDealt = Hand.hand(PLAYER_WITH_PAIR_CARDS_FOUR);
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
        assertThat(play.get(0).playerName).isEqualTo("Player One");
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerOneHasLost() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player One".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(Result.LOSS);
        assertThat(first.get().playerName).isEqualTo("Player One");

    }

    private void andPlayerTwoHasLost() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.LOSS);
        assertThat(play.get(1).playerName).isEqualTo("Player Two");
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerTwoHasWon() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player Two".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(Result.WIN);
        assertThat(first.get().playerName).isEqualTo("Player Two");
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
    private static final Card EIGHT_OF_DIAMONDS = card(EIGHT, DIAMOND);
    private static final Card EIGHT_OF_CLUBS = card(EIGHT, CLUB);
    private static final Card EIGHT_OF_HEARTS = card(EIGHT, HEART);
    private static final Card SEVEN_OF_DIAMONDS = card(SEVEN, DIAMOND);
    private static final Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);
    private static final Card NINE_OF_DIAMONDS = card(NINE, DIAMOND);
    private static final Card JACK_OF_DIAMONDS = card(JACK, DIAMOND);
    private static final Card KING_OF_SPADES = card(KING, SPADE);
    private static final Card KING_OF_HEARTS = card(KING, HEART);
    private static final Card KING_OF_DIAMONDS = card(KING, DIAMOND);
    private static final Card TWO_OF_SPADES = card(TWO, SPADE);

    private static final List<Card> PLAYER_ONE_CARDS = Arrays.asList(ACE_OF_SPADES, FIVE_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_WITH_OTHER_CARDS_TWO = Arrays.asList(ACE_OF_SPADES, EIGHT_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_WITH_PAIR_CARDS = Arrays.asList(KING_OF_SPADES, FIVE_OF_SPADES, FIVE_OF_HEARTS, FOUR_OF_CLUBS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_WITH_PAIR_CARDS_TWO = Arrays.asList(KING_OF_SPADES, FIVE_OF_SPADES, FIVE_OF_HEARTS, NINE_OF_DIAMONDS, THREE_OF_SPADES);
    private static final List<Card> PLAYER_WITH_PAIR_CARDS_THREE = Arrays.asList(KING_OF_HEARTS, EIGHT_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, EIGHT_OF_DIAMONDS);
    private static final List<Card> PLAYER_WITH_PAIR_CARDS_FOUR = Arrays.asList(KING_OF_DIAMONDS, EIGHT_OF_CLUBS, TWO_OF_HEARTS, FOUR_OF_CLUBS, EIGHT_OF_HEARTS);
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
