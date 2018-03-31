package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class Pair extends PokerHand {
    public Pair(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(2, pokerHandCards, kickerCards);
    }
}
