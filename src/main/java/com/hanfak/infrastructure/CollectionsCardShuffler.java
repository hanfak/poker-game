package com.hanfak.infrastructure;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.deck.CardShuffler;

import java.util.Collections;
import java.util.List;

public class CollectionsCardShuffler implements CardShuffler {
    @Override
    public List<Card> shuffle(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }
}
