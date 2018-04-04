package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.deck.DealtCards;
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

    public PokerHand setPokerHand(DealtCards dealtCards) {
        System.out.println(dealtCards.cards);

        if (pokerHandChecker.thereExistsAStraightFlushIn(dealtCards.cards)) {
            return new StraightFlush(new PokerHandsCards(dealtCards.cards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAFourOfAKind(dealtCards.cards)){
            return new FourOfAKind(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfFourOfAKind(dealtCards.cards))), new KickerCards(kickersForGroupingsOfCards(dealtCards.cards, pokerHandChecker.listOfFourOfAKind(dealtCards.cards))));
        }

        if (pokerHandChecker.thereExistsAFullHouseIn(dealtCards.cards)) {
            return new FullHouse(new PokerHandsCards(dealtCards.cards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAFlush(dealtCards.cards)) {
            return new Flush(new PokerHandsCards(dealtCards.cards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAStraightIn(dealtCards.cards)) {
            return new Straight(new PokerHandsCards(dealtCards.cards), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsThreeOfAKindOfSameRank(dealtCards.cards)) {
            return new ThreeOfAKind(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfThreeOfAKind(dealtCards.cards))), new KickerCards(kickersForGroupingsOfCards(dealtCards.cards, pokerHandChecker.listOfThreeOfAKind(dealtCards.cards))));
        }

        if (pokerHandChecker.thereExistsTwoPairOfCardsOfSameRank(dealtCards.cards)) {
            return new TwoPair(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfPairs(dealtCards.cards))), new KickerCards(kickersForGroupingsOfCards(dealtCards.cards, pokerHandChecker.listOfPairs(dealtCards.cards))));
        }

        if (pokerHandChecker.thereExistsOnePairOfCardsOfSameRank(dealtCards.cards)) {
            return new Pair(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfPairs(dealtCards.cards))), new KickerCards(kickersForGroupingsOfCards(dealtCards.cards, pokerHandChecker.listOfPairs(dealtCards.cards))));
        }

        return new HighCard(new PokerHandsCards(Collections.singletonList(dealtCards.cards.get(0))), new KickerCards(dealtCards.cards.subList(1, dealtCards.cards.size())));
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