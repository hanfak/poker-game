package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class FullHouse extends PokerHand {
    public FullHouse(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(7, pokerHandCards, kickerCards);
    }
}
