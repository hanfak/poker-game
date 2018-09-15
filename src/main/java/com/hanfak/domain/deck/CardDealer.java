package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.cards.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.cards.Card.card;


public class CardDealer {
    private final CardShuffler cardShuffler;

    public CardDealer(CardShuffler cardShuffler) {
        this.cardShuffler = cardShuffler;
    }

    @SuppressWarnings("SameParameterValue")
    public DealtCards dealHand(int numberOfCards) {
        List<Card> shuffledDeck = cardShuffler.shuffle(Deck.getCardsInDeck());
        List<Card> dealtCards = getHand(shuffledDeck, numberOfCards);

        Deck.removeCards(dealtCards);

        return new DealtCards(dealtCards);
    }
    //Hard to test
    public void endGame() {
        Deck.resetDeck();
    }

    private List<Card> getHand(List<Card> deck, int numberOfCards) {
        if (deck.size() >= 5) {
            return deck.subList(0, numberOfCards);
        } else {
            throw new IllegalArgumentException("Not enough cards left over");
        }
    }
}

class Deck {
    // TODO should be a Set, to keep uniqueness
    private static List<Card> cardsInDeck = createDeck();

    static void removeCards(List<Card> dealtCards) {
        cardsInDeck = removeCardsDealtFromDeck(dealtCards);
    }

    static void resetDeck() {
        cardsInDeck = createDeck();
    }

    static List<Card> getCardsInDeck() {
        return cardsInDeck;
    }

    private static List<Card> removeCardsDealtFromDeck(List<Card> dealtCards) {
        return cardsInDeck.stream().
                filter(card -> !dealtCards.contains(card)).
                collect(Collectors.toList());
    }

    private static List<Card> createDeck() {
        final Rank[] ranks = Rank.values();
        final Suit[] suits = Suit.values();
        List<Card> cardsInDeck = new ArrayList<>();

        for (Rank rank : ranks) {
            for (Suit suit : suits) {
                cardsInDeck.add(card(rank, suit));
            }
        }
        return cardsInDeck;
    }
}

// TODO edge case when cards run out??? exception thrown?? limit game to x players???