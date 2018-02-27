package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.game.playershand.Hand;

import java.util.List;

import static com.hanfak.domain.game.playershand.Hand.hand;

public class CardDealer {
    private final Deck deck;
    private final CardShuffler cardShuffler;

    public CardDealer(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.cardShuffler = cardShuffler;
    }

    @SuppressWarnings("SameParameterValue")
    public Hand dealHand(int numberOfCards) {
        List<Card> shuffledDeck = cardShuffler.shuffle(deck.cardsInDeck);

        List<Card> dealtCards = getHand(shuffledDeck, numberOfCards);

        deck.removeCards(dealtCards);

        return hand(dealtCards,null);
    }

    private List<Card> getHand(List<Card> deck, int numberOfCards) {
        if(deck.size() >= 5) {
            return deck.subList(0, numberOfCards);
        } else {
            throw new IllegalArgumentException("Not enough cards left over");
        }
    }
}

// TODO edge case when cards run out??? exception thrown?? limit game to x players???