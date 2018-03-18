package acceptancetests.versionone;

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
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.*;

public class BestHandIsPairInFiveCardHandTest extends TestState implements WithAssertions {

    // TODO player one wins with pair against high card
    // TODO player wins with kicker card when same pairs
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
    public void playersDrawIfBothHaveTheSameHandWithAPair() throws Exception {
        givenBothPlayersAreDealtAHandWithMatchingPair();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenTwoPlayers();

        thenPlayerOneHasDrawn();
        andPlayerTwoHasDrawn();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_OTHER_CARDS_TWO).thenReturn(PLAYER_WITH_PAIR_CARDS);
    }

    private void andPlayerOneHasWon() {
        Result result = play.get(0).result;
        assertThat(result).isEqualTo(Result.WIN);
        assertThat(play.get(0).playerName).isEqualTo("Player One");
    }

    private void givenADeckDealsOutASetOfRandomCardsWithALowestPairToPlayerOne() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_PAIR_CARDS_TWO).thenReturn(PLAYER_WITH_PAIR_CARDS_THREE);
    }

    private void andADeckDealsOutASetOfRandomCardsWithAHighestPairToPlayerTwo() {

    }

    private void givenBothPlayersAreDealtAHandWithMatchingPair() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).thenReturn(PLAYER_WITH_PAIR_CARDS_THREE).thenReturn(PLAYER_WITH_PAIR_CARDS_FOUR);
        testState().interestingGivens.add("Player One Hand", PLAYER_ONE_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
        testState().interestingGivens.add("Player Two Hand",PLAYER_TWO_DRAW_CARDS.stream().map(Card::toString).collect(Collectors.joining(", ")));
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
        assertThat(first.get().result).isEqualTo(Result.LOSS);
        assertThat(first.get().playerName).isEqualTo("Player One");

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
