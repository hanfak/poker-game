package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    public final List<Card> cards;
    public final WinningHand winningHand; // TODO remove this
    public final CardsOfWinningHand cardsOfWinningHand;

    private Hand(List<Card> cards, WinningHand winningHand, CardsOfWinningHand cardsOfWinningHand) {
        this.cards = orderCards(cards);
        this.winningHand = winningHand;
        this.cardsOfWinningHand = cardsOfWinningHand;
    }

    private List<Card> orderCards(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparingInt((Card card) -> card.rank.getLevelCode()))
                .collect(Collectors.toList());
    }

    // TODO If cards.size() > 4 then find winning hand???

    public static Hand hand(List<Card> cards, WinningHand winningHand, CardsOfWinningHand cardsOfWinningHand) {
        return new Hand(cards, winningHand, cardsOfWinningHand);
    }
}
