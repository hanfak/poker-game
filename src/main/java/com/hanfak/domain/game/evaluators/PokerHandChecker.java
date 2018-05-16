package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

public class PokerHandChecker {

    public boolean thereExistsAStraightFlushIn(List<Card> dealtCards) {
        return thereExistsAFlush(dealtCards) && thereExistsAStraightIn(dealtCards);
    }

    public boolean thereExistsAFullHouseIn(List<Card> dealtCards) {
        return 2 == cardsGroupedByRank(dealtCards).values().size();
    }

    public boolean thereExistsAFlush(List<Card> dealtCards) {
        return dealtCards.stream().map(card -> card.suit).distinct().count() == 1;
    }

    // TODO tidy up
    public boolean thereExistsAStraightIn(List<Card> dealtCards) {
        List<Card> dealtCardsWithAce = dealtCards.stream().filter(card -> card.rank.equals(Rank.ACE)).collect(toList());
        if (dealtCards.stream().map(card -> card.rank).distinct().count() != 5) {
            return false;
        }
        if (dealtCardsWithAce.isEmpty()) {
            int difference = abs(dealtCards.get(0).rank.ordinal() - dealtCards.get(dealtCards.size() - 1).rank.ordinal());
            return 4 == difference;
        } else {
            List<Rank> collect = dealtCards.stream().map(card -> card.rank).collect(toList());
            boolean b = collect.containsAll(Arrays.asList(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE));
            boolean c = collect.containsAll(Arrays.asList(Rank.ACE, Rank.KING,Rank.QUEEN, Rank.JACK, Rank.TEN));
            return b || c;
        }
    }

    public boolean thereExistsAFourOfAKind(List<Card> dealtCards) {
        return !listOfFourOfAKind(dealtCards).isEmpty();
    }

    public boolean thereExistsThreeOfAKindOfSameRank(List<Card> dealtCards) {
        return !listOfThreeOfAKind(dealtCards).isEmpty();
    }

    public boolean thereExistsOnePairOfCardsOfSameRank(List<Card> dealtCards) {
        return 1 == listOfPairs(dealtCards).size();
    }

    public boolean thereExistsTwoPairOfCardsOfSameRank(List<Card> dealtCards) {
        return 2 == listOfPairs(dealtCards).size();
    }

    // Extract to grouping class
    public List<List<Card>> listOfFourOfAKind(List<Card> dealtCards) {
        return listWinningBestHand(cardsGroupedByRank(dealtCards), 4);
    }

    public List<List<Card>> listOfThreeOfAKind(List<Card> dealtCards) {
        return listWinningBestHand(cardsGroupedByRank(dealtCards), 3);
    }

    public List<List<Card>> listOfPairs(List<Card> dealtCards) {
        return listWinningBestHand(cardsGroupedByRank(dealtCards), 2);
    }

    private Map<Rank, List<Card>> cardsGroupedByRank(List<Card> dealtCards) {
        return dealtCards.stream().
                collect(Collectors.groupingBy(x -> x.rank));
    }

    private List<List<Card>> listWinningBestHand(Map<Rank, List<Card>> cardsGroupedByRank, int numberOfGroups) {
        return cardsGroupedByRank.values().stream().
                filter(filterOutWinningHand(numberOfGroups)).
                collect(toList());
    }

    private Predicate<List<Card>> filterOutWinningHand(int numberOfGroups) {
        return x -> numberOfGroups == x.size();
    }
    // Extract to grouping class
}
