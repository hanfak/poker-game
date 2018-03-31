package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class TwoPair extends PokerHand {
    public TwoPair(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(3, pokerHandCards, kickerCards);
    }
}