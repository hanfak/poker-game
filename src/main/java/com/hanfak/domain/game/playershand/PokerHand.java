package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;

import java.util.List;

// TODO comparable??
public abstract class PokerHand {
    private final int ranking;
    private final PokerHandsCards pokerHandCards;
    private final KickerCards kickerCards;

    public PokerHand(int ranking, PokerHandsCards pokerHandCards, KickerCards kickerCards) {
        this.ranking = ranking;
        this.pokerHandCards = pokerHandCards;
        this.kickerCards = kickerCards;
    }

    public Integer ranking() {
        return ranking;
    }

    public PokerHandsCards getPokerHandsCards() {
        return pokerHandCards;
    }

    //TODO test
    public List<Rank> getPokerHandCardsAsRanks() {
        return pokerHandCards.getRankOfCards();
    }
    //TODO test
    public List<Card> getListOFPokerHandsCards() {
        return pokerHandCards.getCards();
    }


    public KickerCards getKickerCards() {
        return kickerCards;
    }

    //TODO test
    public List<Card> getListOFKickCards() {
        return kickerCards.getCards();
    }

    @Override
    public String toString() {
        return "PokerHand{" +
                "ranking=" + ranking +
                ", pokerHandCards=" + pokerHandCards +
                ", kickerCards=" + kickerCards +
                '}';
    }
}


/*
* Cards
* High card ... one cards
* Pair.. the two cards of the pair
* 2 pair ... the four cards
* 3 of kind ... the three cards
* straight ... 5 cards
* flush ... 5 cards
* 4 of kind ... the fours cards
* straight flush ... 5 cards
* */