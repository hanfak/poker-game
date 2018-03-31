package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.cards.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.cards.Card.card;
// TODO refactor to stop immutability
public class Deck {

    public List<Card> cardsInDeck = createDeck();

    public void removeCards(List<Card> dealtCards) {
        cardsInDeck = removeCardsDealtFromDeck(dealtCards);
    }

    private List<Card> removeCardsDealtFromDeck(List<Card> dealtCards) {
        return cardsInDeck.stream().
                filter(card -> !dealtCards.contains(card)).
                collect(Collectors.toList());
    }

    private List<Card> createDeck() {
        final List<Rank> ranks = Arrays.asList(Rank.values());
        final List<Suit> suits = Arrays.asList(Suit.values());
        List<Card> cardsInDeck = new ArrayList<>();

        for(Rank rank : ranks) {
            for(Suit suit : suits) {
                cardsInDeck.add(card(rank, suit));
            }
        }
        return cardsInDeck;
    }
}