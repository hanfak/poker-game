package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

public class HighCard extends PokerHand {
    public HighCard(PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        super(1, pokerHandCards, kickerCards);
    }
}
