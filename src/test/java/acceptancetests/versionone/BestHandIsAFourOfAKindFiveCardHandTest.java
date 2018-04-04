package acceptancetests.versionone;

import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.deck.DealtCards;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.wiring.PokerGame;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.PLAYER_WITH_FOUR_OF_KIND_ONE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_FOUR_OF_KIND_THREE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_FOUR_OF_KIND_TWO;
import static testinfrastructure.HandsExamples.PLAYER_WITH_TWO_PAIR_CARDS_TWO;

public class BestHandIsAFourOfAKindFiveCardHandTest extends TestState implements WithAssertions {
    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFourOfAKindToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playerWithHigherFourOfAKindWin() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFourOfAKindToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFourOfAKindToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAFourOfAKindToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_ONE)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_TWO));
    }

    @Test
    public void playerWithSameFourOfAKindButHigherKickerWins() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFourOfAKindLowerKickerToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFourOfAKindHigherKickerToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void andADeckDealsOutASetOfRandomCardsWithAFourOfAKindHigherKickerToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAFourOfAKindLowerKickerToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_TWO)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_THREE));
    }

    @Test
    public void playerWithSameHandDraws() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAnotherFourOfAKindToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAndTheSameFourOfAKindToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAnotherFourOfAKindToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_TWO)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_TWO));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAndTheSameFourOfAKindToPlayerTwo() {

    }


    private void givenADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerOne() {
        // tODO make field
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_FOUR_OF_KIND_ONE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAFourOfAKindToPlayerTwo() {
        // TODO add interesting givens
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerOneHasLost() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player One".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(2);
        assertThat(first.get().playerName).isEqualTo("Player One");

    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerTwoHasWon() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player Two".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(1);
        assertThat(first.get().playerName).isEqualTo("Player Two");
    }

    private void andPlayerTwoHasLost() {
        Integer result = play.get(1).result;
        assertThat(result).isEqualTo(2);
        assertThat(play.get(1).playerName).isEqualTo("Player Two");
    }

    private void andPlayerOneHasWon() {
        Integer result = play.get(0).result;
        assertThat(result).isEqualTo(1);
        assertThat(play.get(0).playerName).isEqualTo("Player One");
    }

    private void andPlayerTwoHasDrawn() {
        Integer result = play.get(1).result;
        assertThat(result).isEqualTo(1);
    }

    private void thenPlayerOneHasDrawn() {
        Integer result = play.get(0).result;
        assertThat(result).isEqualTo(1);
    }

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
