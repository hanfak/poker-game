package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// TODO unit test
public class PokerHandsCards implements Comparable<PokerHandsCards> {
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
    //TODO Unit test this
    private List<Card> orderCards(List<Card> cards) {
        List<Card> collect = cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode())) // TODO use ordinal instead
                .collect(Collectors.toList());

        // Ordering full house cards so three of a kind is first and pair is second
        Map<Rank, List<Card>> collect1 = collect.stream().collect(Collectors.groupingBy(x -> x.rank));
        if (collect.size() == 5 && collect1.values().size() == 2) {
            //TODO Extract ordeirng to injected class
            return collect1.values().stream().
                    sorted(Comparator.comparingInt(List::size)).
                    flatMap(Collection::stream).
                    collect(Collectors.toList());
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
