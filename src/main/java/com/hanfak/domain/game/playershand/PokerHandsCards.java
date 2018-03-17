package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// TODO implement equals
public class PokerHandsCards  {
    private final List<Card> cards;
    // TODO Order by rank or by three of a kind
    public PokerHandsCards(List<Card> cards) {
        this.cards = orderCards(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    private List<Card> orderCards(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparingInt(card -> card.rank.getLevelCode()))
                .collect(Collectors.toList());
    }
}
