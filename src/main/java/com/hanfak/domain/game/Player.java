package com.hanfak.domain.game;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.game.playershand.PokerHand;

import java.util.ArrayList;
import java.util.List;

// tODO field dealtCards for list of cards
// Hand is just the PokerHand
public class Player {
    public final String playerName;
    private final List<Card> dealtHand; // Type with comparable(??)
    public final PokerHand pokerHand;

    private Player(String playerName, List<Card> dealtHand, PokerHand pokerHand) {
        this.playerName = playerName;
        this.dealtHand = dealtHand;
        this.pokerHand = pokerHand;
    }

    public static Player player(String playerName, List<Card> dealtHand, PokerHand pokerHand) {
        return new Player(playerName, dealtHand, pokerHand);
    }

    public static Player player(String playerName) {
        return new Player(playerName, new ArrayList<>(), null);
    }
}
