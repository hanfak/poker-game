package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;

import java.util.List;

public interface CardShuffler {
    List<Card> shuffle(List<Card> cards);
}
