package com.hanfak.domain;

public class PlayerResult {

    private final String player;
    public final Result result;
    public final Hand hand;

    private PlayerResult(String player, Result result, Hand hand) {
        this.player = player;
        this.result = result;
        this.hand = hand;
    }

    public static PlayerResult PlayerResult(String player, Result result, Hand hand) {
        return new PlayerResult(player, result, hand);
    }
}
