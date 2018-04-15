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
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.HandsExamples.*;

public class MultiplePlayersTest extends TestState implements WithAssertions {

    @Test
    public void playersWithDifferentHandsAreGivenUniqueRankings() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithAPairToPlayerThree();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerThreeIsSecondPositon();
        andPlayerOneIsThirdPositon();
    }

    @Test
    public void playersWithSameHandsGetSameRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAPairToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithAPairToPlayerThree();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers();

        andPlayerOneIsFirstPositon();
        andPlayerTwoIsInFirstPositon();
        andPlayerThreeIsFirstPositon();
    }

    @Test
    public void twoPlayersWithSameHandsGetSameRankingAndOnePlayerWithBetterHandGetsHighestRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAStraightToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerThree();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerOneIsSecondPositon();
        andPlayerThreeIsInSecondPositon();
    }

    // TODO Test 1,2,2 where two hands are equal due to kicker cards
    // TODO Test 1,2,2
    // TODO Test 1,1,2
    // TODO Test 1,2,2,3
    // TODO Test 1,2,3,3

    private void andPlayerThreeIsInSecondPositon() {
        blah(2, "Player Three");
    }

    private void andPlayerOneIsSecondPositon() {
        blah(2, "Player One");
    }

    private void blah(int result, String playerName) {
        List<PlayerResult> collect = play.stream().
                filter(presult -> presult.result == result).
                filter(playerResult -> playerResult.playerName.equals(playerName)).
                collect(Collectors.toList());
        assertThat(collect).isNotEmpty();
    }

    private void givenADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_ONE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerThree() {

    }

    private void andADeckDealsOutASetOfRandomCardsWithAStraightToPlayerTwo() {
    }

    private void andPlayerTwoIsInFirstPositon() {
        blah(1, "Player Two");
    }

    private void andPlayerThreeIsFirstPositon() {
        blah(1, "Player Three");
    }

    private void andPlayerOneIsFirstPositon() {
        blah(1, "Player Two");
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAPairToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_ONE_HIGH_CARD_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_ONE_HIGH_CARD_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_ONE_HIGH_CARD_CARDS_ONE));
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo, playerThree);
    }

    private void andPlayerTwoIsFirstPositon() {
        Integer result = play.get(0).result;
        assertThat(result).isEqualTo(1);
        assertThat(play.get(0).playerName).isEqualTo("Player Two");
    }

    private void andPlayerOneIsThirdPositon() {
        Integer result = play.get(2).result;
        assertThat(result).isEqualTo(3);
        assertThat(play.get(2).playerName).isEqualTo("Player One");
    }

    private void andPlayerThreeIsSecondPositon() {
        Integer result = play.get(1).result;
        assertThat(result).isEqualTo(2);
        assertThat(play.get(1).playerName).isEqualTo("Player Three");
    }

    private void andADeckDealsOutASetOfRandomCardsWithATwoPairToPlayerTwo() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_ONE_HIGH_CARD_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_ONE));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAPairToPlayerTwo() {

    }

    private void andADeckDealsOutASetOfRandomCardsWithAPairToPlayerThree() {

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_FIVE)).thenReturn(new DealtCards(PLAYER_WITH_PAIR_CARDS_ONE));
    }

    private static final String VERSION = "1.0";
    private static final Player playerTwo = player("Player Two");
    private static final Player playerThree = player("Player Three");
    private static final Player playerOne = player("Player One");
    private final CardDealer cardDealer = Mockito.mock(CardDealer.class); // TODO use a stub

    private List<PlayerResult> play;
    private PokerGame pokerGame;

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
