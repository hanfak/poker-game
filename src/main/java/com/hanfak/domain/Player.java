package com.hanfak.domain;

public class Player {
    private String playerName;

    private Player(String playerName) {
        this.playerName = playerName;
    }

    public static Player player(String playerName) {
        return new Player(playerName);
    }
}
