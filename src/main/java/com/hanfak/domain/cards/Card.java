package com.hanfak.domain.cards;

public class Card implements Comparable<Card> {
    public final Rank rank;
    public final Suit suit;

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

        return this.rank == card.rank && this.suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        // TODO move to Suit enum
        String suitSymbol = "";

        switch (suit) {
            case SPADE:
                suitSymbol = "\u2660";
                break;
            case DIAMOND:
                suitSymbol = "\u2666";
                break;
            case CLUB:
                suitSymbol = "\u2663";
                break;
            case HEART:
                suitSymbol = "\u2764";
                break;
        }
        return rank.name().charAt(0) + rank.name().substring(1).toLowerCase() +
                suitSymbol;
    }

    @Override
    public int compareTo(Card o) {
        return rank.compareTo(o.rank);
    }
}
