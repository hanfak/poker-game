package com.hanfak.domain.game;

import com.hanfak.domain.game.playershand.PokerHand;

import java.util.Arrays;

// TODO Add why won, ie better hand rank, kicker etc
public class PlayerResult {

    public final String playerName; //TODO Should this be Player object?
    public final Result result;
    public final PokerHand hand;

    // TODO add what hand won ie the enum WinningHand
    private PlayerResult(String playerName, Result result, PokerHand hand) {
        this.playerName = playerName;
        this.result = result;
        this.hand = hand;
    }

    public static PlayerResult playerResult(String playerName, Result result, PokerHand hand) {
        return new PlayerResult(playerName, result, hand);
    }

    @Override
    public String toString() {
        return "PlayerResult{" +
                "playerName='" + playerName + '\'' +
                ", result=" + result +
                ", hand=" + Arrays.asList(hand.getPokerHandsCards(), hand.getKickerCards()) +
                ", pokerHand=" + hand.getClass().getSimpleName() +
                "}\n";
    }
}
