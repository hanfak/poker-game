package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class ThreeOfAKind extends PokerHand {
    public ThreeOfAKind(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(4, pokerHandCards, kickerCards);
    }
}
