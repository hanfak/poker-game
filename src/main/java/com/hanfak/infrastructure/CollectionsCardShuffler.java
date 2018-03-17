package com.hanfak.infrastructure;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.deck.CardShuffler;

import java.util.Collections;
import java.util.List;

// TODO Find a better way of doing this without mutating argument
public class CollectionsCardShuffler implements CardShuffler {
    @Override
    public List<Card> shuffle(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }
}
