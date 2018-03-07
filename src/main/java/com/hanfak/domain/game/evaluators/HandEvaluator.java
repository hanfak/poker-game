package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.playershand.CardsOfWinningHand;
import com.hanfak.domain.game.playershand.Hand;
import com.hanfak.domain.game.playershand.WinningHand;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO Unit test for each type of hand
public class HandEvaluator {
    public CardsOfWinningHand scoreHand(Hand hand) {
        System.out.println(hand.cards);
        Map<Rank, List<Card>> cardsGroupedByRank = hand.cards.stream().collect(Collectors.groupingBy(x -> x.rank));
        List<List<Card>> numberOfPairs = cardsGroupedByRank.values().stream().filter(x -> x.size() == 2).collect(Collectors.toList());
        if(thereExistsTwoPairOfCardsOfSameRank(numberOfPairs)) {
            List<Card> pairOfCards = numberOfPairs.stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());
            System.out.println("2 Pair " + pairOfCards);
            return new CardsOfWinningHand(WinningHand.TWO_PAIR, pairOfCards);
        }
        if (thereExistsOnePairOfCardsOfSameRank(numberOfPairs)) {
            List<Card> pairOfCards = numberOfPairs.stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());
            System.out.println("Pair " + pairOfCards);
            return new CardsOfWinningHand(WinningHand.PAIR, pairOfCards);
//            return Hand.hand(hand.cards, new CardsOfWinningHand(WinningHand.PAIR, pairOfCards));
        }
        // TODO Game logic, for first test can check for uniqueness of all cards and no 5 adjacent cards
//        return Hand.hand(hand.cards, new CardsOfWinningHand(WinningHand.HIGH_CARD, hand.cards));
        return new CardsOfWinningHand(WinningHand.HIGH_CARD, Collections.singletonList(hand.cards.get(0)));
    }

    private boolean thereExistsOnePairOfCardsOfSameRank(List<List<Card>> numberOfPairs) {
        return numberOfPairs.size() == 1;
    }

    private boolean thereExistsTwoPairOfCardsOfSameRank(List<List<Card>> numberOfPairs) {
        return numberOfPairs.size() == 2;
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