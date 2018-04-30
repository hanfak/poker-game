package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class PokerHandsCards implements Comparable<PokerHandsCards> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokerHandsCards that = (PokerHandsCards) o;

        return cards != null ? cards.equals(that.cards) : that.cards == null;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }

    private final List<Card> cards;

    public PokerHandsCards(List<Card> cards) {
        this.cards = orderCards(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(PokerHandsCards otherPokerHandCards) {
        return COMPARATOR.compare(this, otherPokerHandCards);
    }

    private List<Card> orderCards(List<Card> cards) {
        List<Card> collect = cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode())) // TODO use ordinal instead
                .collect(Collectors.toList());

        // Ordering full house cards so three of a kind is first and pair is second
        Map<Rank, List<Card>> collect1 = collect.stream().collect(Collectors.groupingBy(x -> x.rank));
        if (collect.size() == 5 && collect1.values().size() == 2) {
            //TODO Extract ordeirng to injected class
            return collect1.values().stream().
                    sorted((x,y) -> y.size() - x.size()).
                    flatMap(Collection::stream).
                    collect(Collectors.toList());
        }
        if(collect.size() > 1) {
//       TODO  Extract out
            int difference = abs(collect.get(1).rank.ordinal() - collect.get(collect.size() - 1).rank.ordinal());
            if (difference == 3 && collect.get(0).rank == Rank.ACE && collect.get(1).rank != Rank.KING) {
                List<Card> cards2 = Collections.singletonList(collect.get(0));
                List<Card> cards1 = collect.subList(1, collect.size());
                return Stream.of(cards1, cards2).flatMap(Collection::stream).collect(Collectors.toList());
            }
        }

        return collect;
    }

    private static final Comparator<PokerHandsCards> COMPARATOR =
            (pokerHandCardsOne, pokerHandsCardsTwo) -> IntStream.range(0, pokerHandCardsOne.getCards().size()).
                    map(i -> Integer.compare(pokerHandCardsOne.getCards().get(i).rank.getLevelCode(), pokerHandsCardsTwo.getCards().get(i).rank.getLevelCode())).
                    filter(rankValue -> rankValue != 0).
                    findFirst().
                    orElse(0);

    @Override
    public String toString() {
        return "PokerHandsCards{" +
                "cards=" + cards +
                '}';
    }
}
