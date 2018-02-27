package com.hanfak.domain.game;

import com.hanfak.domain.game.playershand.Hand;

public class Player {
    public final String playerName;
    public final Hand hand;

    private Player(String playerName, Hand hand) {
        this.playerName = playerName;
        this.hand = hand;
    }

    public static Player player(String playerName, Hand hand) {
        return new Player(playerName, hand);
    }

    public static Player player(String playerName) {
        return new Player(playerName, null);
    }

}
