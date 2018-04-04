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
import static testinfrastructure.HandsExamples.PLAYER_WITH_FULL_HOUSE_CARDS_ONE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_FULL_HOUSE_CARDS_THREE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_FULL_HOUSE_CARDS_TWO;
import static testinfrastructure.HandsExamples.PLAYER_WITH_THREE_OF_A_KIND_CARDS_ONE;

public class BestHandIsAFullHouseInFiveCardHandTest extends TestState implements WithAssertions {
    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playerWinsWithBetterFullHouseWhereGroupOfThreeIsHigher() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE));
    }

    @Test
    public void playerWinsWithBetterFullHouseWhereGroupOfThreeIsTheSameAndPairIsHigher() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFullHouseWithHigherPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFullHouseWithLowPairToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void andADeckDealsOutASetOfRandomCardsWithAFullHouseWithLowPairToPlayerTwo() {
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAFullHouseWithHigherPairToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_THREE));
    }

    @Test
    public void playersDrawWithSameHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAnotherFullHouseToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAnotherFullHouseoPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    private void andADeckDealsOutASetOfRandomCardsWithAnotherFullHouseoPlayerTwo() {
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAnotherFullHouseToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_THREE)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_THREE));
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAThreeOfAKindToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_THREE_OF_A_KIND_CARDS_ONE)).thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo() {
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
