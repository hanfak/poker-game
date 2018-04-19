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

// TODO move some to unit test for MultipleHand
public class MultiplePlayersTest extends TestState implements WithAssertions {

    // all ordered uniquely 1,2,3
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

    // All equal cards 1,1,1
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

    // Test 1,2,2 where two hands are equal due to kicker cards
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

    // Test 1,2,2 where two hands are equal due to Poker cards
    @Test
    public void twoPlayersWithSameHandsWithOnlyPokerHandGetSameRankingAndOnePlayerWithBetterHandGetsHighestRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithASameStraightToPlayerThree();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerOneIsSecondPositon();
        andPlayerThreeIsInSecondPositon();
    }

    // 1,1,3
    @Test
    public void twoPlayersWithSameHandsWithOnlyPokerHandGetSameRankingAndOnePlayerWithLowerHandGetsHighestRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFlushtToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerOneIsThirdPositon();
        andPlayerThreeIsInFirstPositon();
    }

    // 1,1,1,4

    @Test
    public void threePlayersWithSameHandsWithOnlyPokerHandGetSameRankingAndOnePlayerWithLowerHandGetsHighestRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFlushtToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerFour();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenFourPlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerOneIsFourthPositon();
        andPlayerThreeIsInFirstPositon();
        andPlayerFourIsInFirstPositon();
    }

    private void andPlayerOneIsFourthPositon() {
        blah(4, "Player One");

    }

    private void andPlayerFourIsInFirstPositon() {
        blah(1, "Player Four");
    }

    private void andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerFour() {

    }

    private void andPlayerThreeIsInFirstPositon() {
        blah(1, "Player Three");
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAFlushtToPlayerOne() {
            Mockito.when(cardDealer.dealHand(5)).
                    thenReturn(new DealtCards(PLAYER_WITH_FLUSH_ONE)).
                    thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE)).
                    thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_FOUR));
    }

    private void andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree() {

    }

    // TODO test 1,1,1,4
    // TODO Test 1,1,2
    // TODO Test 1,2,2,4
    // TODO Test 1,2,3,3

    private void andADeckDealsOutASetOfRandomCardsWithASameStraightToPlayerThree() {

    }

    private void andADeckDealsOutASetOfRandomCardsWithAFullHouseToPlayerTwo() {
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_FOUR));
    }

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
                thenReturn(new DealtCards(PLAYER_WITH_TWO_PAIR_CARDS_SIX));
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

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenFourPlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo, playerThree, playerFour);
    }

    private void andPlayerTwoIsFirstPositon() {
        blah(1, "Player Two");
    }

    private void andPlayerOneIsThirdPositon() {
        blah(3, "Player One");
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
    private static final Player playerFour = player("Player Four");
    private static final Player playerOne = player("Player One");
    private final CardDealer cardDealer = Mockito.mock(CardDealer.class); // TODO use a stub

    private List<PlayerResult> play;
    private PokerGame pokerGame;

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
