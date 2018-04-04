package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;
// TODO unit test
public class PokerHandsCards implements Comparable<PokerHandsCards> {
    private final List<Card> cards;
    // TODO Order by rank or by three of a kind
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
    //TODO Unit test this
    private List<Card> orderCards(List<Card> cards) {
        List<Card> collect = cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode())) // TODO use ordinal instead
                .collect(Collectors.toList());

        // Ordering full house cards so three of a kind is first and pair is second
        Map<Rank, List<Card>> collect1 = collect.stream().collect(Collectors.groupingBy(x -> x.rank));
        if (collect.size() == 5 && collect1.values().size() == 2) {
            return collect1.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        }

        // TODO rethink how to do this Ace High straight not showing correctly
        if (collect.size() == 5) {

            int difference = abs(collect.get(1).rank.ordinal() - collect.get(collect.size() - 1).rank.ordinal());
            if (difference == 3 && cards.stream().filter(card -> card.rank.equals(Rank.ACE) && !card.rank.equals(Rank.KING)).count() == 1) {
                List<Card> cards2 = Collections.singletonList(collect.get(0));
                List<Card> cards1 = collect.subList(1, collect.size());
                return Stream.of(cards1, cards2).flatMap(Collection::stream).collect(Collectors.toList());
            }
        }

        return collect;
    }
    // TODO unit test
    private static final Comparator<PokerHandsCards> COMPARATOR =
            (pokerHandCardsOne, pokerHandsCardsTwo) -> IntStream.range(0, pokerHandCardsOne.getCards().size()).
                    map(i -> Integer.compare(pokerHandCardsOne.getCards().get(i).rank.getLevelCode(), pokerHandsCardsTwo.getCards().get(i).rank.getLevelCode())).
                    filter(rankValue -> rankValue != 0).
                    findFirst().
                    orElse(0);
}
