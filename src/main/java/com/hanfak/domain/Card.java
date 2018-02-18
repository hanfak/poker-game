package com.hanfak.domain;

public class Card {
    public final Rank rank;
    private final Suit suit;

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card card(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }

    @Override
    public String toString() {
        return rank.name().charAt(0) + rank.name().substring(1).toLowerCase() +
                " Of " +
                suit.name().charAt(0) + suit.name().substring(1).toLowerCase() + "s";
    }
}
