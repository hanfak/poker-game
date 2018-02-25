package com.hanfak.domain.game;

public class PlayerResult {

    public final String playerName;
    public final Result result;
    public final Hand hand;

    private PlayerResult(String playerName, Result result, Hand hand) {
        this.playerName = playerName;
        this.result = result;
        this.hand = hand;
    }

    public static PlayerResult playerResult(String playerName, Result result, Hand hand) {
        return new PlayerResult(playerName, result, hand);
    }

    @Override
    public String toString() {
        return "PlayerResult{" +
                "playerName='" + playerName + '\'' +
                ", result=" + result +
                ", hand=" + hand.cards +
                '}';
    }
}