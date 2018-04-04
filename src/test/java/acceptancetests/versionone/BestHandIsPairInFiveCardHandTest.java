package acceptancetests.versionone;

import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.hanfak.domain.cards.Card;
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
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.*;

public class BestHandIsPairInFiveCardHandTest extends TestState implements WithAssertions {

    @Test
    public void playerWinsWithABetterHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAPairToPlayerTwo();

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
    public void playerWinsWithHigherKicker() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAPairToPlayerTwo();

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

    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_FIVE)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_ONE));
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_FIVE)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_ONE));
    }

    private void givenADeckDealsOutASetOfRandomCardsWithALowestPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_TWO)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_THREE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAHighestPairToPlayerTwo() {

    }

    private void givenBothPlayersAreDealtAHandWithMatchingPair() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_THREE)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_FOUR));
        testState().interestingGivens.add("Player One Hand", PLAYER_WITH_HIGH_CARD_CARDS_SEVEN.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand", PLAYER_WITH_HIGH_CARD_CARDS_SIX.stream().map(Card::toString).collect(Collectors.joining(", ")));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAPairToPlayerTwo() {
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
