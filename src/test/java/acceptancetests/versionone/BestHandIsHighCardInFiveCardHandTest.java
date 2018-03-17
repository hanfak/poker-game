package acceptancetests.versionone;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.cards.Card;
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
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.*;

@RunWith(SpecRunner.class)
public class BestHandIsHighCardInFiveCardHandTest extends TestState implements WithAssertions {
   // extend junit
    //static classes and wiring

    @Test
    public void playerOneWinsOneRoundGameWithTheSameBestHandWithDifferentCards() throws Exception {
        givenADeckDealsOutASetOfRandomCardsToPlayerOne();
        andADeckDealsOutASetOfRandomCardsToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    @Test
    public void playerTwoWinsOneRoundGameWithTheSameBestHandWithDifferentCardsButNextHighestCardIsDifferent() throws Exception {
        givenADeckDealsOutASetOfRandomCardsToPlayerOneblah();
        andADeckDealsOutASetOfRandomCardsToPlayerTwoBlah();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    // TODO: test same best hand, but with next highest card is same but next card isdifferent...UNIIT TEST in HandEvaluator???

    @Test
    public void playersDrawIfBothHaveTheSameHand() throws Exception {
        givenBothPlayersAreDealtAHand();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    private void givenADeckDealsOutASetOfRandomCardsToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_ONE_HIGH_CARD_CARDS_ONE).thenReturn(PLAYER_WITH_HIGH_CARD_CARDS_TWO);

    }

    private void andADeckDealsOutASetOfRandomCardsToPlayerTwo() {
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_HIGH_CARD_CARDS_ONE.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand", PLAYER_WITH_HIGH_CARD_CARDS_TWO.stream().map(Card::toString).collect(Collectors.joining(", ")));
        // For readability
    }

    private void givenADeckDealsOutASetOfRandomCardsToPlayerOneblah() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_HIGH_CARD_CARDS_THREE).thenReturn(PLAYER_TWO_WITH_HIGH_CARD_CARDS_FOUR);
    }

    private void andADeckDealsOutASetOfRandomCardsToPlayerTwoBlah() {
        testState().interestingGivens.add("Player One Hand", PLAYER_WITH_HIGH_CARD_CARDS_THREE.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand", PLAYER_TWO_WITH_HIGH_CARD_CARDS_FOUR.stream().map(Card::toString).collect(Collectors.joining(", ")));
        // For readability
    }

    private void givenBothPlayersAreDealtAHand() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_ONE_DRAW_CARDS).thenReturn(PLAYER_TWO_DRAW_CARDS);
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand",PLAYER_TWO_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }


    // TODO extract thens to another class, extend
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

    private void andPlayerTwoHasDrawn() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.DRAW);
    }

    private void thenPlayerOneHasDrawn() {
        Result result = play.get(0).result;
        assertThat(result).isEqualTo(Result.DRAW);
    }
    private List<PlayerResult> play;

    private PokerGame pokerGame;

    private static final  String VERSION = "1.0";

    private static final Player playerTwo = player("Player Two");
    private static final Player playerOne = player("Player One");
    private final CardDealer cardDealer = Mockito.mock(CardDealer.class); // TODO use a stub

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
