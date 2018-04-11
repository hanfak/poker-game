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

// TODO extract to class the evaluator for each bestHand
// MOve most of the logic to classes represnting the type of hand

public class HandEvaluator {
    private final PokerHandChecker pokerHandChecker;

    public HandEvaluator(PokerHandChecker pokerHandChecker) {
        this.pokerHandChecker = pokerHandChecker;
    }

    public PokerHand setPokerHand(DealtCards dealtCards) {
        System.out.println(dealtCards.getCards());

        if (pokerHandChecker.thereExistsAStraightFlushIn(dealtCards.getCards())) {
            return new StraightFlush(new PokerHandsCards(dealtCards.getCards()), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAFourOfAKind(dealtCards.getCards())){
            return new FourOfAKind(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfFourOfAKind(dealtCards.getCards()))), new KickerCards(kickersForGroupingsOfCards(dealtCards.getCards(), pokerHandChecker.listOfFourOfAKind(dealtCards.getCards()))));
        }

        if (pokerHandChecker.thereExistsAFullHouseIn(dealtCards.getCards())) {
            return new FullHouse(new PokerHandsCards(dealtCards.getCards()), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAFlush(dealtCards.getCards())) {
            return new Flush(new PokerHandsCards(dealtCards.getCards()), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsAStraightIn(dealtCards.getCards())) {
            return new Straight(new PokerHandsCards(dealtCards.getCards()), new KickerCards(emptyList()));
        }

        if (pokerHandChecker.thereExistsThreeOfAKindOfSameRank(dealtCards.getCards())) {
            return new ThreeOfAKind(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfThreeOfAKind(dealtCards.getCards()))), new KickerCards(kickersForGroupingsOfCards(dealtCards.getCards(), pokerHandChecker.listOfThreeOfAKind(dealtCards.getCards()))));
        }

        if (pokerHandChecker.thereExistsTwoPairOfCardsOfSameRank(dealtCards.getCards())) {
            return new TwoPair(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfPairs(dealtCards.getCards()))), new KickerCards(kickersForGroupingsOfCards(dealtCards.getCards(), pokerHandChecker.listOfPairs(dealtCards.getCards()))));
        }

        if (pokerHandChecker.thereExistsOnePairOfCardsOfSameRank(dealtCards.getCards())) {
            return new Pair(new PokerHandsCards(setCardsInWinningHand(pokerHandChecker.listOfPairs(dealtCards.getCards()))), new KickerCards(kickersForGroupingsOfCards(dealtCards.getCards(), pokerHandChecker.listOfPairs(dealtCards.getCards()))));
        }

        return new HighCard(new PokerHandsCards(Collections.singletonList(dealtCards.getCards().get(0))), new KickerCards(dealtCards.getCards().subList(1, dealtCards.getCards().size())));
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