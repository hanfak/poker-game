package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;

// TODO implement equals
public class PokerHandsCards  {
    private final List<Card> cards;
    // TODO Order by rank or by three of a kind
    public PokerHandsCards(List<Card> cards) {
        this.cards = orderCards(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    private List<Card> orderCards(List<Card> cards) {
        List<Card> collect = cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode())) // TODO use ordinal instead
                .collect(Collectors.toList());
        if (collect.size() == 5) {
            // tODO order three of a kind first then pair here

            int difference = abs(collect.get(1).rank.ordinal() - collect.get(collect.size() - 1).rank.ordinal());
            if (difference == 3 && cards.stream().filter(card -> card.rank.equals(Rank.ACE)).count() == 1) {
                List<Card> cards2 = Collections.singletonList(collect.get(0));
                List<Card> cards1 = collect.subList(1, collect.size());
                return Stream.of(cards1, cards2).flatMap(Collection::stream).collect(Collectors.toList());
            }
        }

        return collect;
    }
}
