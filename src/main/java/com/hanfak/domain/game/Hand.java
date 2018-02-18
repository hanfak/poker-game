package com.hanfak.domain.game;

import com.hanfak.domain.cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    public final List<Card> cards;
    public final WinningHand winningHand; // TODO instead of passing null have WinningHand.EMPTY

    private Hand(List<Card> cards, WinningHand winningHand) {
        this.cards = orderCards(cards);
        this.winningHand = winningHand;
    }

    private List<Card> orderCards(List<Card> cards) {
        List<Card> orderedHand = cards.stream()
                .sorted(Comparator.comparingInt((Card card) -> card.rank.getLevelCode()))
                .collect(Collectors.toList());
        return orderedHand;
    }

    public static Hand hand(List<Card> cards, WinningHand winningHand) {
        return new Hand(cards, winningHand);
    }
}
