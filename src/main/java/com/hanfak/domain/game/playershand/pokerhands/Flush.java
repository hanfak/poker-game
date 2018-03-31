package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class Flush extends PokerHand {
    // TODO for full house have another field for three and pair
    public Flush(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(6, pokerHandCards, kickerCards);
    }
}
