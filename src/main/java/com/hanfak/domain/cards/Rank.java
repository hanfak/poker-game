package com.hanfak.domain.cards;

// TODO check Rank has ordering
public enum Rank {
    // TODO reverse order for easier comparison
    TWO(14),
    THREE(13),
    FOUR(12),
    FIVE(11),
    SIX(10),
    SEVEN(9),
    EIGHT(8),
    NINE(7),
    TEN(6),
    JACK(5),
    QUEEN(4),
    KING(3),
    ACE(2);

    // TODO Change name
    private final int levelCode;

    Rank(Integer levelCode) {
        this.levelCode = levelCode;
    }

    public Integer getLevelCode() {
        return this.levelCode;
    }


    // TODO turn worded numbers into ints, and face cards into first capitalised first letter
}
