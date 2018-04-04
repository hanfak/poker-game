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
import static testinfrastructure.HandsExamples.*;

public class BestHandIsTwoPairInFiveCardHandTest extends TestState implements WithAssertions {

    @Test
    public void playerWinsWithABetterHandOfTwoPair() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerTwo(); // tODO change pairs to diff suits

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    @Test
    public void playerWinsWithBetterTwoPairWhereHighestPairIsBigger() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithALowerTwoPairToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    @Test
    public void playerWinsWithBetterTwoPairWhereHighestPairIsEqualLowestPairIsBigger() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairAndLowestPairIsHigherToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithALowerTwoPairAndLowestPairIsLowerToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    @Test
    public void playerWinsWithSameTwoPairWhereBothPairsAreTheSame() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsHigherToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsLowerToPlayerTwo();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        andPlayerOneHasWon();
        andPlayerTwoHasLost();
    }

    @Test
    public void playersDrawIfBothHaveTheSameHandWithATwoPair() throws Exception {
        givenBothPlayersAreDealtAHandWithMatchingTwoPair();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    private void andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerTwo() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_ONE)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_ONE));

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_ONE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithALowerTwoPairToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_FIVE)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_ONE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithALowerTwoPairAndLowestPairIsLowerToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHigherTwoPairAndLowestPairIsHigherToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_FOUR)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_TWO));
    }

    private void andADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsLowerToPlayerTwo() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithATwoPairAndHighCardIsHigherToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_THREE));

    }

    private void givenBothPlayersAreDealtAHandWithMatchingTwoPair() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_FIVE));

    }

    private void andPlayerTwoHasDrawn() {
        Integer result = play.get(1).result;
        assertThat(result).isEqualTo(1);
    }

    private void thenPlayerOneHasDrawn() {
        Integer result = play.get(0).result;
        assertThat(result).isEqualTo(1);
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }

    private void andPlayerOneHasWon() {
        Integer result = play.get(0).result;
        assertThat(result).isEqualTo(1);
        assertThat(play.get(0).playerName).isEqualTo("Player One");
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerOneHasLost() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player One".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(2);
        assertThat(first.get().playerName).isEqualTo("Player One");

    }

    private void andPlayerTwoHasLost() {
        Integer result = play.get(1).result;
        assertThat(result).isEqualTo(2);
        assertThat(play.get(1).playerName).isEqualTo("Player Two");
    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerTwoHasWon() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player Two".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(1);
        assertThat(first.get().playerName).isEqualTo("Player Two");
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
