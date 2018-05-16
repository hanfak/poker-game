package com.hanfak.domain.game;

import com.hanfak.domain.game.playershand.PokerHand;

import java.util.Arrays;

public class PlayerResult {

    public final String playerName; //TODO Should this be Player object?
    public final Integer result;
    public final PokerHand hand;

    // TODO add what hand won ie the enum WinningHand
    private PlayerResult(String playerName, Integer result, PokerHand hand) {
        this.playerName = playerName;
        this.result = result;
        this.hand = hand;
    }

    public static PlayerResult playerResult(String playerName, Integer result, PokerHand hand) {
        return new PlayerResult(playerName, result, hand);
    }

    @Override
    public String toString() {
        return "PlayerResult{" +
                "playerName='" + playerName + '\'' +
                ", result=" + result +
                ", hand=" + Arrays.asList(hand.getListOFPokerHandsCards(), hand.getListOFPokerHandsCards()) +
                ", pokerHand=" + hand.getClass().getSimpleName() +
                "}\n";
    }
}
