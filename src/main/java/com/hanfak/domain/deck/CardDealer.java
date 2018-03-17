package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
        return cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode()))
                .collect(Collectors.toList());
    }
}

// TODO edge case when cards run out??? exception thrown?? limit game to x players???