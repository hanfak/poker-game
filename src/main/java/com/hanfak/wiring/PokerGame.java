package com.hanfak.wiring;

import com.hanfak.domain.cards.Deck;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.evaluators.HandEvaluator;
import com.hanfak.domain.game.evaluators.MultipleHandEvaluator;
import com.hanfak.usecases.VersionOneGame;

import java.util.List;
import java.util.Scanner;

import static com.hanfak.domain.game.Player.player;

public class PokerGame {
    private final String version;

    public static void main(String[] args) {
        // TODO extract method
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter player one name: ");
        String playerOneName = reader.next();
        System.out.println(playerOneName);
        System.out.println("Enter player two name: ");
        String playerTwoName = reader.next();
        System.out.println(playerTwoName);

        PokerGame pokerGame = new PokerGame(args[0]);

        // TODO for multple games have a do while loop, with exit on typing exit
        List<PlayerResult> results = pokerGame.play(new Deck(), player(playerOneName, null),
                player(playerTwoName,null));

        // TODO output of results
        System.out.println(results);
    }

    public PokerGame(String version) {
        this.version = version;
    }

    // TODO change to hashmap better for multiple players???
    // TODO Wiring
    public List<PlayerResult> play(Deck deck, Player... player) {
        HandEvaluator handEvaluator = new HandEvaluator();
        MultipleHandEvaluator multipleHandEvaluator = new MultipleHandEvaluator();
        VersionOneGame versionOneGame = new VersionOneGame(deck, handEvaluator, multipleHandEvaluator);
        return versionOneGame.playGame(player);
    }
}
