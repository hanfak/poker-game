package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class Straight extends PokerHand {
    public Straight(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(5, pokerHandCards, kickerCards);
    }
}
