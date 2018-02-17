package com.hanfak.wiring;

import com.hanfak.domain.Player;
import com.hanfak.domain.PlayerResult;

import java.util.List;
import java.util.Scanner;

import static com.hanfak.domain.Player.player;

public class PokerGame {
    private final String version;
    private final Player playerOne;
    private final Player playerTwo;

    public static void main(String[] args) {
        // TODO extract method
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter player one name: ");
        String playerOneName = reader.next();
        System.out.println(playerOneName);
        System.out.println("Enter player two name: ");
        String playerTwoName = reader.next();
        System.out.println(playerTwoName);

        PokerGame pokerGame = new PokerGame(args[0], player(playerOneName), player(playerTwoName));

        List<PlayerResult> play = pokerGame.play();

        // TODO output of results
    }

    public PokerGame(String version, Player playerOne, Player playerTwo) {
        this.version = version;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    // TODO change to hashmap better for multiple players
    public List<PlayerResult> play() {
        return null;
    }
}
