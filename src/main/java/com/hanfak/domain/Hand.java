package com.hanfak.domain;

import java.util.List;

public class Hand {
    public final List<Card> cards;

    private Hand(List<Card> cards) {
        this.cards = cards;
    }

    public static Hand hand(List<Card> cards) {
        return new Hand(cards);
    }
}
