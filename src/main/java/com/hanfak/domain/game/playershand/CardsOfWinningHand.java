package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;

import java.util.List;

public class CardsOfWinningHand {
    public final BestHand bestHand;
    public final List<Card> cardsInBestHand; // TODO Avoid list for immutablity

    public CardsOfWinningHand(BestHand bestHand, List<Card> cardsInBestHand) {
        this.bestHand = bestHand;
        this.cardsInBestHand = cardsInBestHand;
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