package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.playershand.BestHand;
import com.hanfak.domain.game.playershand.CardsOfWinningHand;
import com.hanfak.domain.game.playershand.Hand;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// TODO Unit test for each type of hand
// TODO extract to class the evaluator for each bestHand
public class HandEvaluator {

    public CardsOfWinningHand scoreHand(Hand hand) {
        System.out.println(hand.cards);

        Map<Rank, List<Card>> cardsGroupedByRank = hand.cards.stream()
                .collect(Collectors.groupingBy(x -> x.rank));

        List<List<Card>> numberOfPairs = listWinningBestHand(cardsGroupedByRank, 2);
        List<Card> cardsInWinningHand = setCardsInWinningHand(numberOfPairs);
        System.out.println("Pair(s) " + cardsInWinningHand);

        List<List<Card>> winningBestHand = listWinningBestHand(cardsGroupedByRank, 3);
        List<Card> cardsInWinningHand1 = setCardsInWinningHand(winningBestHand);
        System.out.println("3 of Kind " + cardsInWinningHand);

        if (thereExistsThreeOfAKindOfSameRank(winningBestHand)) {
            return new CardsOfWinningHand(BestHand.THREE_OF_A_KIND, cardsInWinningHand1);
        }

        if (thereExistsTwoPairOfCardsOfSameRank(numberOfPairs)) {
            return new CardsOfWinningHand(BestHand.TWO_PAIR, cardsInWinningHand);
        }

        if (thereExistsOnePairOfCardsOfSameRank(numberOfPairs)) {
            return new CardsOfWinningHand(BestHand.PAIR, cardsInWinningHand);
        }

        return new CardsOfWinningHand(BestHand.HIGH_CARD, Collections.singletonList(hand.cards.get(0)));
    }

    private List<List<Card>> listWinningBestHand(Map<Rank, List<Card>> cardsGroupedByRank, int numberOfGroups) {
        return cardsGroupedByRank.values().stream().
                filter(filterOutWinningHand(numberOfGroups)).
                collect(Collectors.toList());
    }

    private Predicate<List<Card>> filterOutWinningHand(int numberOfGroups) {
        return x -> numberOfGroups == x.size();
    }

    private List<Card> setCardsInWinningHand(List<List<Card>> numberOfPairs) {
        return numberOfPairs.stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private boolean thereExistsThreeOfAKindOfSameRank(List<List<Card>> numberOfThreeOfKinds) {
        return !numberOfThreeOfKinds.isEmpty();
    }

    private boolean thereExistsOnePairOfCardsOfSameRank(List<List<Card>> numberOfPairs) {
        return 1 == numberOfPairs.size();
    }

    private boolean thereExistsTwoPairOfCardsOfSameRank(List<List<Card>> numberOfPairs) {
        return 2 == numberOfPairs.size();
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