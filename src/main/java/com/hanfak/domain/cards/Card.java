package com.hanfak.domain.cards;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (rank != card.rank) return false;
        return suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return rank.name().charAt(0) + rank.name().substring(1).toLowerCase() +
                " Of " +
                suit.name().charAt(0) + suit.name().substring(1).toLowerCase() + "s";
    }
}
