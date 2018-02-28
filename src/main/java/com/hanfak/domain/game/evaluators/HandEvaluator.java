package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.playershand.Hand;
import com.hanfak.domain.game.playershand.WinningHand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandEvaluator {
    public Hand scoreHand(Hand hand) {
        System.out.println(hand.cards);
        Map<Rank, List<Card>> cardsGroupedByRank = hand.cards.stream().collect(Collectors.groupingBy(x -> x.rank));
        List<List<Card>> numberOfPairs = cardsGroupedByRank.values().stream().filter(x -> x.size() == 2).collect(Collectors.toList());
        if (!numberOfPairs.isEmpty()) {
            return Hand.hand(hand.cards, WinningHand.PAIR);
        }
        // TODO Game logic, for first test can check for uniqueness of all cards and no 5 adjacent cards
        return Hand.hand(hand.cards, WinningHand.HIGH_CARD);
    }
}

/*

Order cards in terms of their hands ie AA543 for high/pairs/three/four, for straight/flush/full no need to

* Order hand.cards
* Look for each type of hand in Winning hand, start from best to worst
* if match return that winningHand
*
* Could inject list of evaluators for each hand, flush evaluator, straight evaluator,
* all implementing evaluator interface, to allow for different impl but this logic should never change
*
*
* */