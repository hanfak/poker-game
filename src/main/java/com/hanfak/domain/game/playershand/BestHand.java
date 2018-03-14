package com.hanfak.domain.game.playershand;

// TODO Rethink this

// abstract class
// each type extends then implement comparable and equals, field of rank/order
public enum BestHand {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH
}