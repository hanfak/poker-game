package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;


public class CardDealer {
    private final Deck deck;
    private final CardShuffler cardShuffler;

    public CardDealer(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.cardShuffler = cardShuffler;
    }

    @SuppressWarnings("SameParameterValue")
    public List<Card> dealHand(int numberOfCards) {
        List<Card> shuffledDeck = cardShuffler.shuffle(deck.cardsInDeck);

        List<Card> dealtCards = getHand(shuffledDeck, numberOfCards);

        deck.removeCards(dealtCards);

        return dealtCards;
    }

    private List<Card> getHand(List<Card> deck, int numberOfCards) {
        if(deck.size() >= 5) {
            return orderCards(deck.subList(0, numberOfCards));
        } else {
            throw new IllegalArgumentException("Not enough cards left over");
        }
    }

    private List<Card> orderCards(List<Card> cards) {
        List<Card> collect = cards.stream().
                sorted(Comparator.comparingInt(card -> card.rank.getLevelCode())). // TODO use ordinal instead
                collect(Collectors.toList());
        int difference = abs(collect.get(1).rank.ordinal() - collect.get(collect.size() - 1).rank.ordinal());
        if (difference == 3 && cards.stream().filter(card -> card.rank.equals(Rank.ACE)).count() == 1) {
            List<Card> cards2 = Collections.singletonList(collect.get(0));
            List<Card> cards1 = collect.subList(1, collect.size());
            return Stream.of(cards1, cards2).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return collect;
    }
}

// TODO edge case when cards run out??? exception thrown?? limit game to x players???