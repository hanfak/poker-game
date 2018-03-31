package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class FourOfAKind extends PokerHand {
    public FourOfAKind(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(8, pokerHandCards, kickerCards);
    }
}
