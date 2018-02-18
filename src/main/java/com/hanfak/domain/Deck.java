package com.hanfak.domain;

import java.util.Arrays;

import static com.hanfak.domain.Card.card;
import static com.hanfak.domain.Rank.ACE;
import static com.hanfak.domain.Rank.FIVE;
import static com.hanfak.domain.Rank.FOUR;
import static com.hanfak.domain.Rank.THREE;
import static com.hanfak.domain.Suit.DIAMOND;
import static com.hanfak.domain.Suit.SPADE;

// TODO Finish this see notes below
public class Deck {
    private static final Card THREE_OF_SPADES = card(THREE, SPADE);
    private static final Card ACE_OF_SPADES = card(ACE, SPADE);
    private static final Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
    private static final Card FOUR_OF_SPADES = card(FOUR, SPADE);
    private static final Card FIVE_OF_SPADES = card(FIVE, SPADE);
    public Hand dealHand() {
        return Hand.hand(Arrays.asList(ACE_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES, FOUR_OF_SPADES, FIVE_OF_SPADES), null);
    }
}

/*
*
* Factory for creating card objects,
*
* records what was created, and  when need to deal a card or deal hand, checks that cards have not been
* duplicated on creation
*
* */
