package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    // TODO cards can be mutated, use getters or look at ideas.md for possible solution
    public final List<Card> cards;
    public final CardsOfWinningHand cardsOfWinningHand;

    private Hand(List<Card> cards, CardsOfWinningHand cardsOfWinningHand) {
        this.cards = orderCards(cards);
        this.cardsOfWinningHand = cardsOfWinningHand;
    }

    private List<Card> orderCards(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode()))
                .collect(Collectors.toList());
    }

    // TODO If cards.size() > 4 then find winning hand???

    public static Hand hand(List<Card> cards, CardsOfWinningHand cardsOfWinningHand) {
        return new Hand(cards, cardsOfWinningHand);
    }

    public static Hand hand(List<Card> cards) {
        return new Hand(cards, null);
    }
}
