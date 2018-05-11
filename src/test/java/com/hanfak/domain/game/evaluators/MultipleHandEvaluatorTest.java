package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHandsCards;
import com.hanfak.domain.game.playershand.pokerhands.Flush;
import com.hanfak.domain.game.playershand.pokerhands.StraightFlush;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static testinfrastructure.CardsExamples.*;
import static testinfrastructure.HandsExamples.PLAYER_WITH_FLUSH_THREE;
import static testinfrastructure.HandsExamples.PLAYER_WITH_STRAIGHT_FLUSH_FOUR;

public class MultipleHandEvaluatorTest implements WithAssertions {
    @Test
    public void evaluateTwoHandsWithDifferentPokerHandRankingsWhere1stHandWins() throws Exception {
        MultipleHandEvaluator multipleHandEvaluator = new MultipleHandEvaluator(new ResultSetter()); // tODO use mock
        StraightFlush straightFlush = new StraightFlush(new PokerHandsCards(Arrays.asList(QUEEN_OF_SPADES, JACK_OF_SPADES, TEN_OF_SPADES, NINE_OF_SPADES, EIGHT_OF_SPADES)), new KickerCards(new ArrayList<>()));
        Player player1 = Player.player("Player One", PLAYER_WITH_STRAIGHT_FLUSH_FOUR, straightFlush);
        Flush flush = new Flush(new PokerHandsCards(Arrays.asList(FIVE_OF_DIAMONDS, JACK_OF_DIAMONDS, FOUR_OF_DIAMONDS, ACE_OF_DIAMONDS, NINE_OF_DIAMONDS)), new KickerCards(new ArrayList<>()));
        Player player2 = Player.player("Player Two", PLAYER_WITH_FLUSH_THREE, flush);
        List<PlayerResult> playerResults = multipleHandEvaluator.compareAllPlayersHands(Arrays.asList(player2, player1));

        assertThat(playerResults.get(0).hand).isEqualTo(straightFlush);
        assertThat(playerResults.get(1).hand).isEqualTo(flush);
    }

    @Test
    public void evaluateTwoHandsWithDifferentPokerHandRankingsWhere2ndHandWins() throws Exception {

    }

    @Test
    public void evaluateTwoHandsWithSameRankingPokerHandOfExactRankingCardsAndKickersAreTheSame() throws Exception {

    }

    @Test
    public void evaluateTwoHandsWithSameRankingPokerHandOfExactRankingCardsAndKickersAreNotThere() throws Exception {

    }

    @Test
    public void evaluateTwoHandsWithSameRankingPokerHandOfExactRankingCardsAndKickersAreHigherForFirstHand() throws Exception {

    }

    @Test
    public void evaluateTwoHandsWithSameRankingPokerHandOfExactRankingCardsAndKickersAreHigherForSecondHand() throws Exception {

    }

    @Test
    public void evaluateTwoHandsWithSameRankingPokerHandOfDifferentCardsAreHigherForFirstHand() throws Exception {

    }

    @Test
    public void evaluateTwoHandsWithSameRankingPokerHandOfDifferentCardsAreHigherForSecondHand() throws Exception {

    }

    // TODO test for three players
    // - all players draw
    // - first two draw
    // - last two draw
    // - distinct hands ordered properly

}