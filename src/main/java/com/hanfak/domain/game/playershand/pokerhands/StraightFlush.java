package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class StraightFlush extends PokerHand {
    public StraightFlush(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(9, pokerHandCards, kickerCards);
    }
}
