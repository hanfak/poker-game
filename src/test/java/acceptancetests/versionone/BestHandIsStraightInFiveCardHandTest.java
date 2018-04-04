package acceptancetests.versionone;

import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.deck.CardDealer;
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
import static testinfrastructure.HandsExamples.*;

// TODO Fix this
public class BestHandIsStraightInFiveCardHandTest extends TestState implements WithAssertions {
    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightAceHighoPlayerTwo();
        givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {

    }

    @Test
    public void playerWinsWithABetterHandWhereAceIsAOne() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAStraightoPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playerWinsWithABetterHandWhereAceIsNotIncluded() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightButNoAceToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithThreeOfAKindPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    @Test
    public void playerDoesNotWinIfDuplicateCard() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithADuplicateCardButNoAceToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithThreeOfAKindPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    // tODO move to unit test in multiple hand evaluator/pokerHandChecker
    @Test
    public void playerDoesNotWinIfIllegalStraight() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAIllegalStraightButNoAceToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithThreeOfAKindPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }


    private void givenADeckDealsOutASetOfRandomCardsWithAIllegalStraightButNoAceToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_NOT_A_STRAIGHT_TWO).thenReturn(PLAYER_WITH_THREE_OF_A_KIND_CARDS_ONE);
    }

    private void givenADeckDealsOutASetOfRandomCardsWithADuplicateCardButNoAceToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_NOT_A_STRAIGHT_ONE).thenReturn(PLAYER_WITH_THREE_OF_A_KIND_CARDS_ONE);
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightButNoAceToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_STRAIGHT_THREE).thenReturn(PLAYER_WITH_THREE_OF_A_KIND_CARDS_ONE);
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightAceHighoPlayerTwo() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_PAIR_CARDS_ONE).thenReturn(PLAYER_WITH_STRAIGHT_ONE);
    }
    // TODO test player two wins with three vs two
    // TODO test same bestHand, but better rank wins
    // TODO test Draw case
    // TODO test same bestHand with same rank, but next highest card is different


    private void givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_THREE_OF_A_KIND_CARDS_ONE).thenReturn(PLAYER_WITH_STRAIGHT_TWO);
    }

    private void andADeckDealsOutASetOfRandomCardsWithAStraightoPlayerTwo() {
        // TODO add interesting givens
    }

    private void andADeckDealsOutASetOfRandomCardsWithThreeOfAKindPlayerTwo() {
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
