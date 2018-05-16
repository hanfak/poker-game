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
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo();
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
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenThreePlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerOneIsThirdPositon();
        andPlayerThreeIsInFirstPositon();
    }

    // 1,1,1,4
    @Test
    public void threePlayersWithSameHandsWithOnlyPokerHandGetSameRankingAndOnePlayerWithLowerHandGetsLowestRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAFlushtToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerFour();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenFourPlayers();

        andPlayerTwoIsFirstPositon();
        andPlayerOneIsFourthPositon();
        andPlayerThreeIsInFirstPositon();
        andPlayerFourIsInFirstPositon();
    }

    // 1,2,2,4
    @Test
    public void twoPlayersWithSameHandsWithOnlyPokerHandGetSameRankingAndOnePlayerWithLowerHandGetsHighestRankingAndAnotherGetsTheLowerRanking() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAStraightFlushtToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree();
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerFour();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenFourPlayers();

        andPlayerOneIsFourthPositon();
        andPlayerTwoIsInSecondPositon();
        andPlayerThreeIsInSecondPositon();
        andPlayerFourIsInFirstPositon();
    }

    @Test
    public void multiplePlayersWithSamePokerRankingHand() throws Exception {
        givenADeckDealsOutASetOfRandomCardsWithAAnotherStraightFlushtToPlayerOne();
        andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo();
        andADeckDealsOutASetOfRandomCardsWithAdiffHighCardToPlayerThree();
        andADeckDealsOutASetOfRandomCardsWithdiffHighCardToPlayerFour();
        andADeckDealsOutASetOfRandomCardsWithdiffHighCardToPlayerFive();

        whenAGameOfOneHandWithFiveCardsIsPlayedBetweenFivePlayers();

        andPlayerOneIsFirstPositon();
        andPlayerTwoIsInSecondPositon();
        andPlayerThreeIsInThirdPositon();
        andPlayerFourIsInFourthPositon();
        andPlayerFiveIsInFifthPositon();
    }

    private void andADeckDealsOutASetOfRandomCardsWithdiffHighCardToPlayerFive() {
    }

    private void whenAGameOfOneHandWithFiveCardsIsPlayedBetweenFivePlayers() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo, playerThree, playerFour, playerFive);
    }

    private void andPlayerFiveIsInFifthPositon() {
        assertResultMatchesForPlayer(5, "Player Five");
    }

    private void andPlayerFourIsInFourthPositon() {
        assertResultMatchesForPlayer(4, "Player Four");
    }

    private void andPlayerThreeIsInThirdPositon() {
        assertResultMatchesForPlayer(3, "Player Three");
    }

    private void andADeckDealsOutASetOfRandomCardsWithdiffHighCardToPlayerFour() {
    }

    private void andADeckDealsOutASetOfRandomCardsWithAdiffHighCardToPlayerThree() {
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAAnotherStraightFlushtToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_FLUSH_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_FOUR)).
                thenReturn(new DealtCards(PLAYER_ONE_HIGH_CARD_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_TWO)).
                thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_EIGHT));
    }

    //Test 1,1,3,4,4

    private void andPlayerTwoIsInSecondPositon() {
        assertResultMatchesForPlayer(2, "Player Two");

    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightFlushtToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_WITH_HIGH_CARD_CARDS_FOUR)).
                thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_FOUR)).
                thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_FLUSH_TWO));
    }

    private void andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerFour() {
    }

    private void andPlayerOneIsFourthPositon() {
        assertResultMatchesForPlayer(4, "Player One");
    }

    private void andPlayerFourIsInFirstPositon() {
        assertResultMatchesForPlayer(1, "Player Four");
    }

    private void andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerFour() {

    }

    private void andPlayerThreeIsInFirstPositon() {
        assertResultMatchesForPlayer(1, "Player Three");
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAFlushtToPlayerOne() {
            Mockito.when(cardDealer.dealHand(5)).
                    thenReturn(new DealtCards(PLAYER_WITH_FLUSH_ONE)).
                    thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_FOUR)).
                    thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE)).
                    thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_FOUR));
    }

    private void andADeckDealsOutASetOfRandomCardsWithASameFullHouseToPlayerThree() {

    }


    private void andADeckDealsOutASetOfRandomCardsWithASameStraightToPlayerThree() {

    }

    private void andADeckDealsOutASetOfRandomCardsWithAHighCardToPlayerTwo() {
    }

    private void givenADeckDealsOutASetOfRandomCardsWithAStraightToPlayerOne() {
        Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_FULL_HOUSE_CARDS_ONE)).
                thenReturn(new DealtCards(PLAYER_WITH_STRAIGHT_FOUR));
    }

    private void andPlayerThreeIsInSecondPositon() {
        assertResultMatchesForPlayer(2, "Player Three");
    }

    private void andPlayerOneIsSecondPositon() {
        assertResultMatchesForPlayer(2, "Player One");
    }

    private void assertResultMatchesForPlayer(int result, String playerName) {
        List<PlayerResult> collect = play.stream().
                filter(presult -> presult.result == result).
                filter(playerResult -> playerResult.playerName.equals(playerName)).
                collect(Collectors.toList());
        assertThat(collect).isNotEmpty();
        //tODO but a descirbe for
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
        assertResultMatchesForPlayer(1, "Player Two");
    }

    private void andPlayerThreeIsFirstPositon() {
        assertResultMatchesForPlayer(1, "Player Three");
    }

    private void andPlayerOneIsFirstPositon() {
        assertResultMatchesForPlayer(1, "Player One");
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
        assertResultMatchesForPlayer(1, "Player Two");
    }

    private void andPlayerOneIsThirdPositon() {
        assertResultMatchesForPlayer(3, "Player One");
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
    private static final Player playerFive = player("Player Five");
    private static final Player playerOne = player("Player One");
    private final CardDealer cardDealer = Mockito.mock(CardDealer.class); // TODO use a stub

    private List<PlayerResult> play;
    private PokerGame pokerGame;

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
