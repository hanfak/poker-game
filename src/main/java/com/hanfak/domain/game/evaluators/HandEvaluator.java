package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;
import com.hanfak.domain.game.playershand.pokerhands.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

// TODO Unit test for each type of hand
// TODO extract to class the evaluator for each bestHand
// MOve most of the logic to classes represnting the type of hand

public class HandEvaluator {
    private final PokerHandChecker pokerHandChecker;

    public HandEvaluator(PokerHandChecker pokerHandChecker) {
        this.pokerHandChecker = pokerHandChecker;
    }

    public PokerHand setPokerHand(List<Card> dealtCards) {
        System.out.println(dealtCards);

//        if (pokerHandChecker.thereExistsAStraightFlushIn(dealtCards)) {
//            return new StraightFlush(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
//        }

        if (pokerHandChecker.thereExistsAFourOfAKind(dealtCards)){
            return new FourOfAKind(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfFourOfAKind(dealtCards))), new KickerCards(kickersForGroupingsOfCards(dealtCards, pokerHandChecker.listOfFourOfAKind(dealtCards))));
        }

        if (pokerHandChecker.thereExistsAFullHouseIn(dealtCards)) {
            return new FullHouse(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAFlush(dealtCards)) {
            return new Flush(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAStraightIn(dealtCards)) {
            return new Straight(new PokerHandsCards(dealtCards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsThreeOfAKindOfSameRank(dealtCards)) {
            return new ThreeOfAKind(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfThreeOfAKind(dealtCards))), new KickerCards(kickersForGroupingsOfCards(dealtCards, pokerHandChecker.listOfThreeOfAKind(dealtCards))));
        }

        if (pokerHandChecker.thereExistsTwoPairOfCardsOfSameRank(dealtCards)) {
            return new TwoPair(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfPairs(dealtCards))), new KickerCards(kickersForGroupingsOfCards(dealtCards, pokerHandChecker.listOfPairs(dealtCards))));
        }

        if (pokerHandChecker.thereExistsOnePairOfCardsOfSameRank(dealtCards)) {
            return new Pair(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfPairs(dealtCards))), new KickerCards(kickersForGroupingsOfCards(dealtCards, pokerHandChecker.listOfPairs(dealtCards))));
        }

        return new HighCard(new PokerHandsCards(Collections.singletonList(dealtCards.get(0))), new KickerCards(dealtCards.subList(1, dealtCards.size())));
    }

    private List<Card> kickersForGroupingsOfCards(List<Card> dealtCards, List<List<Card>> numberOfGroupings) {
        return dealtCards.stream().
                filter(card -> !setCardsInWinningHand(numberOfGroupings).contains(card)).
                collect(Collectors.toList());
    }

    private List<Card> setCardsInWinningHand(List<List<Card>> numberOfGroupings) {
        return numberOfGroupings.stream().
                flatMap(Collection::stream).
                collect(Collectors.toList());
    }
}