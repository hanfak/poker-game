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
import static testinfrastructure.HandsExamples.PLAYER_WITH_STRAIGHT_FLUSH_ONE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_STRAIGHT_FLUSH_TWO;
import static testinfrastructure.HandsExamples.PLAYER_WITH_STRAIGHT_ONE;

public class BestHandIsStraightFlushInFiveCardHandTest extends TestState implements WithAssertions {
    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightFlushAceHighoPlayerTwo();
        givenADeckDealsOutASetOfRandomCardsWithAStraightToPlayerOne();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
        andPokerHandThatWonIsAStraightFlush();

    }

    @Test
    public void playerWinsWithABetterHandWhereAceIsAOne() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAStraightoPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightFlushAceHighoPlayerTwo() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_STRAIGHT_ONE).thenReturn(PLAYER_WITH_STRAIGHT_FLUSH_ONE);
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_STRAIGHT_ONE).thenReturn(PLAYER_WITH_STRAIGHT_FLUSH_TWO);
    }


    private void andADeckDealsOutASetOfRandomCardsWithAStraightoPlayerTwo() {
        // TODO add interesting givens
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }

    // TODO test same bestHand with same rank, but next highest card is different
    // TODO test Draw case
    // TODO test same bestHand, but better rank wins
    // TODO test player two wins with three vs two
    private void andPokerHandThatWonIsAStraightFlush() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> 1 == playerResult.result).findFirst();
        assertThat(first.get().hand.getClass().getSimpleName()).isEqualTo("StraightFlush");
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
