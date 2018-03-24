package com.hanfak.domain.game.pokerhandsabstract;

public abstract class PokerHand implements Comparable<PokerHand> {
    protected int ranking;

    PokerHand(int ranking) {
        this.ranking = ranking;
    }
}

// Issues if pair what pair, will need to look at dealtCards for what cards made the pair when giving the
// results... OR just say the winner won with a pair of 2s and leave it at that
// Flush just compare highest card
// Full house set like two pair, or use pair and three of kind a fields
// four of a kind and three of kind will be like a pair
// straight and straight flush will use the compareKickers