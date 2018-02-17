package com.hanfak.domain;

import java.util.Arrays;
import java.util.List;

public class Card {
    public final Rank rank;
    public final Suit suit;

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card card(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }


    public static void main(String[] args) {
        Card card1 = Card.card(Rank.TWO, Suit.CLUB);
        Card card2 = Card.card(Rank.ACE, Suit.CLUB);
        Card card3 = Card.card(Rank.FIVE, Suit.CLUB);

        List<Card> cards = Arrays.asList(card1, card2, card3);
        System.out.println(card3.rank.getLevelCode()); // for ordering and comparing cards

//        Arrays.stream(Rank.values()).forEach(System.out::println);

    }

}
