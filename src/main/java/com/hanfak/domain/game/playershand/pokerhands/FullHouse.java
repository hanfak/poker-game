package com.hanfak.domain.game.playershand.pokerhands;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHand;
import com.hanfak.domain.game.playershand.PokerHandsCards;

import java.util.List;

public class FullHouse implements PokerHand {
    private final PokerHandsCards pokerHandCards; // ORdered list and make it a type with comparable on rank
    private final KickerCards kickers; // Ordered
    // TODO for full house have another field for three and pair
    public FullHouse(PokerHandsCards pokerHandCards, KickerCards kickers) {
        this.pokerHandCards = pokerHandCards;
        this.kickers = kickers;
    }

    // TODO use enum valuation instead
    @Override
    public Integer ranking() {
        return 7;
    }

    @Override
    public List<Card> getPokerHandsCards() {
        return pokerHandCards.getCards();
    }

    @Override
    public List<Card> getKickerCards() {
        return kickers.getCards();
    }


    /*
        * Logic to
        *  - compare if all cards the same -> draw/0
        *  - if pokerHandCards are the same
        *    then compare pokerHandCards
        *       if same comapare kickers
        *    else choose winner
        *
        * */
}
