package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;
import com.hanfak.domain.game.playershand.pokerhands.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.util.Collections.emptyList;

// TODO Unit test for each type of hand
// TODO extract to class the evaluator for each bestHand
// MOve most of the logic to classes represnting the type of hand

public class HandEvaluator {

    public PokerHand scoreHand(List<Card> dealtCards) {
        System.out.println(dealtCards);

        List<List<Card>> listOfFourOfAKind = listWinningBestHand(cardsGroupedByRank(dealtCards), 4);
        List<List<Card>> listOfThreeOfAKind = listWinningBestHand(cardsGroupedByRank(dealtCards), 3);
        List<List<Card>> listOfPairs = listWinningBestHand(cardsGroupedByRank(dealtCards), 2);

        if (thereExistsAFourOfAKind(listOfFourOfAKind)){
            List<Card> kickers = dealtCards.stream().filter(card -> !setCardsInWinningHand(listOfFourOfAKind).contains(card)).collect(Collectors.toList());
            return new FourOfAKind(new PokerHandsCards(setCardsInWinningHand(listOfFourOfAKind)), new KickerCards(kickers));
        }

        if (thereExistsAFullHouseIn(dealtCards)) {
            return new FullHouse(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
        }

        if (thereExistsAFlush(dealtCards)) {
            return new Flush(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
        }

        if (thereExistsAStraightIn(dealtCards)) {
            // tODO test 3 2 A K Q is only high card not straight
            return new Straight(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
        }

        if (thereExistsThreeOfAKindOfSameRank(listOfThreeOfAKind)) {
            List<Card> kickers = dealtCards.stream().filter(card -> !setCardsInWinningHand(listOfThreeOfAKind).contains(card)).collect(Collectors.toList());
            return new ThreeOfAKind(new PokerHandsCards(setCardsInWinningHand(listOfThreeOfAKind)), new KickerCards(kickers));
        }

        if (thereExistsTwoPairOfCardsOfSameRank(listOfPairs)) {
            List<Card> kickers = dealtCards.stream().filter(card -> !setCardsInWinningHand(listOfPairs).contains(card)).collect(Collectors.toList());
            return new TwoPair(new PokerHandsCards(setCardsInWinningHand(listOfPairs)), new KickerCards(kickers));
        }

        if (thereExistsOnePairOfCardsOfSameRank(listOfPairs)) {
            List<Card> kickers = dealtCards.stream().filter(card -> !setCardsInWinningHand(listOfPairs).contains(card)).collect(Collectors.toList());
            return new Pair(new PokerHandsCards(setCardsInWinningHand(listOfPairs)), new KickerCards(kickers));
        }

        return new HighCard(new PokerHandsCards(Collections.singletonList(dealtCards.get(0))), new KickerCards(dealtCards.subList(1, dealtCards.size())));
    }

    private Map<Rank, List<Card>> cardsGroupedByRank(List<Card> dealtCards) {
        return dealtCards.stream()
                .collect(Collectors.groupingBy(x -> x.rank));
    }

    private boolean thereExistsAFullHouseIn(List<Card> dealtCards) {
        return 2 == cardsGroupedByRank(dealtCards).values().size();
    }


    private boolean thereExistsAFlush(List<Card> dealtCards) {
        return dealtCards.stream().map(card -> card.suit).distinct().count() == 1;
    }

    private boolean thereExistsAStraightIn(List<Card> dealtCards) {
        int difference = abs(dealtCards.get(0).rank.ordinal() - dealtCards.get(dealtCards.size() - 1).rank.ordinal());
        boolean thereIsAnAceWhichActsAsAOne = difference == 9 && dealtCards.stream().filter(card -> card.rank.equals(Rank.ACE)).count() == 1;
        return difference == 5 || thereIsAnAceWhichActsAsAOne;
    }

    private List<List<Card>> listWinningBestHand(Map<Rank, List<Card>> cardsGroupedByRank, int numberOfGroups) {
        return cardsGroupedByRank.values().stream().
                filter(filterOutWinningHand(numberOfGroups)).
                collect(Collectors.toList());
    }

    private Predicate<List<Card>> filterOutWinningHand(int numberOfGroups) {
        return x -> numberOfGroups == x.size();
    }

    private List<Card> setCardsInWinningHand(List<List<Card>> numberOfGroupings) {
        return numberOfGroupings.stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private boolean thereExistsAFourOfAKind(List<List<Card>> listOfFourOfAKind) {
        return !listOfFourOfAKind.isEmpty();
    }

    private boolean thereExistsThreeOfAKindOfSameRank(List<List<Card>> listOfThreeOfKinds) {
        return !listOfThreeOfKinds.isEmpty();
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