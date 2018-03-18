package acceptancetests.versionone;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;
import com.hanfak.wiring.PokerGame;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.*;

@RunWith(SpecRunner.class)
public class VersionOneFiveCardInitialHandGameTest extends TestState implements WithAssertions {



    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_HIGH_CARD_CARDS_FIVE).thenReturn(PLAYER_WITH_PAIR_CARDS_ONE);
    }

    // TODO test cards for opp player // unit test




    // TODO move to separate class
    // TODo test for 2 pair
    @Test
    public void playerWinsWithABetterHandOfTwoPair() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerTwo(); // tODO change pairs to diff suits

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerTwo() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_PAIR_CARDS_ONE).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_ONE);

    }

    @Test
    public void playerWinsWithBetterTwoPairWhereHighestPairIsBigger() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithALowerTwoPairToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_TWO).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_ONE);
    }

    private void andADeckDealsOutASetOfRandomCardsWithALowerTwoPairToPlayerTwo() {

    }


    @Test
    public void playerWinsWithBetterTwoPairWhereLowestPairIsBigger() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairAndLowestPairIsHigherToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithALowerTwoPairAndLowestPairIsLowerToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    private void andADeckDealsOutASetOfRandomCardsWithALowerTwoPairAndLowestPairIsLowerToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairAndLowestPairIsHigherToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_FOUR).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_TWO);

    }

    @Test
    public void playerWinsWithSameTwoPairWhereBothPairsAreTheSame() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsHigherToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsLowerToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    private void andADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsLowerToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsHigherToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_TWO).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_THREE);

    }


    // TODO: test 2 pairs draw
    @Test
    public void playersDrawIfBothHaveTheSameHandWithATwoPair() throws Exception {
        givenBothPlayersAreDealtAHandWithMatchingTwoPair();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    private void givenBothPlayersAreDealtAHandWithMatchingTwoPair() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_TWO).thenReturn(PLAYER_WITH_TWO_PAIR_CARDS_FIVE);

    }
    // TODo test for full house
    // TODo test for straight
    // TODo test for flush
    // TODo test for straight flush vs straight vs flush


    // TODO (new class) test multiple players, record place - 1 - won, 2 to x - loss OR 1 - Draw, 1 - Draw, 3 to x - loss
    // who won or drew only if at the top, everyone else loses
    // Who will win money

    // TODO (new class) test multiple games, keep track of scores


    private void andPlayerTwoHasDrawn() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.DRAW);
    }

    private void thenPlayerOneHasDrawn() {
        Result result = play.get(0).result;
        assertThat(result).isEqualTo(Result.DRAW);
    }



    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }

//    private void andPlayerTwoHasAHandOf(Card... cards) {
//        Hand hand = play.get(1).hand;
//
//        thenPlayerHasTheseCards(cards, hand);
//    }
//
//    private void thenPlayerOneHasAHandOf(Card... cards) {
//        Hand hand = play.get(0).hand;
//        System.out.println("hand = " + hand.cards);
//        thenPlayerHasTheseCards(cards, hand);
//    }
//
//    private void thenPlayerHasTheseCards(Card[] cards, Hand actual) {
//        List<String> expected = Arrays.stream(cards).map(Card::toString).collect(Collectors.toList());
//        String[] myArray = expected.toArray(new String[expected.size()]);
//
//        assertThat(actual.cards.stream().map(Card::toString).collect(Collectors.toList()))
//                .containsExactlyInAnyOrder(myArray);
//    }

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
