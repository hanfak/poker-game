package acceptancetests.versionone;

import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;
import com.hanfak.wiring.PokerGame;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.PLAYER_WITH_STRAIGHT;
import static testinfrastructure.HandsExamples.PLAYER_WITH_STRAIGHT_ONE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_THREE_OF_A_KIND_CARDS;

public class BestHandIsStraightInFiveCardHandTest extends TestState implements WithAssertions {
    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightAceHighoPlayerTwo();
        givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playerWinsWithABetterHandWhereAceIsAOne() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAStraightoPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightAceHighoPlayerTwo() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_THREE_OF_A_KIND_CARDS).thenReturn(PLAYER_WITH_STRAIGHT);
    }
    // TODO test player two wins with three vs two
    // TODO test same bestHand, but better rank wins
    // TODO test Draw case
    // TODO test same bestHand with same rank, but next highest card is different


    private void givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_THREE_OF_A_KIND_CARDS).thenReturn(PLAYER_WITH_STRAIGHT_ONE);
    }

    private void andADeckDealsOutASetOfRandomCardsWithAStraightoPlayerTwo() {
        // TODO add interesting givens
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerOneHasLost() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player One".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(Result.LOSS);
        assertThat(first.get().playerName).isEqualTo("Player One");

    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerTwoHasWon() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player Two".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(Result.WIN);
        assertThat(first.get().playerName).isEqualTo("Player Two");
    }

    private void andPlayerTwoHasLost() {
        Result result = play.get(1).result;
        assertThat(result).isEqualTo(Result.LOSS);
        assertThat(play.get(1).playerName).isEqualTo("Player Two");
    }

    private void andPlayerOneHasWon() {
        Result result = play.get(0).result;
        assertThat(result).isEqualTo(Result.WIN);
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
