package com.hanfak.wiring;

import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.deck.Deck;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.evaluators.HandEvaluator;
import com.hanfak.domain.game.evaluators.MultipleHandEvaluator;
import com.hanfak.domain.game.evaluators.PokerHandChecker;
import com.hanfak.infrastructure.CollectionsCardShuffler;
import com.hanfak.usecases.VersionOneGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static com.hanfak.domain.game.Player.player;

public class PokerGame {
    private final String version;

    public static void main(String[] args) throws IOException {
        // TODO extract method
//        Scanner reader = new Scanner(System.in);
//        System.out.println("Enter player one name: ");
//        String playerOneName = reader.next();
//        System.out.println(playerOneName);
//        System.out.println("Enter player two name: ");
//        String playerTwoName = reader.next();
//        System.out.println(playerTwoName);
//
//        PokerGame pokerGame = new PokerGame(args[0]);
        PokerGame pokerGame = new PokerGame("one");

        // TODO for multple games have a do while loop, with exit on typing exit
//        List<PlayerResult> results = pokerGame.play(new Deck(), player(playerOneName, null),
//                player(playerTwoName,null));


//        List<PlayerResult> results = pokerGame.play(new CardDealer(new Deck(), new CollectionsCardShuffler()), player("one"),
//                player("two"));
//        // TODO output of results
//        System.out.println(results);
        String file = "./src/main/java/com/hanfak/results.txt";
        String s = "";
        StringBuilder a = new StringBuilder();
        IntStream.range(1,100).forEach(x -> {
            List<PlayerResult> result = pokerGame.play(new CardDealer(new Deck(), new CollectionsCardShuffler()), player("one"),
                    player("two"), player("three"), player("four"), player("five"));
            String blah = result.toString() + "\n\n";
            System.out.println("ind result " + blah);
            a.append(x + ": " + blah);
        });
        System.out.println("Result " + a);
        writeUsingOutputStream(a, file);
        // TODO when run, start gui and
    }

    private static void writeUsingOutputStream(StringBuilder data, String pathname) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathname))) {
            writer.write(data.toString());
        }
    }
    public PokerGame(String version) {
        this.version = version;
    }

    // TODO change to hashmap better for multiple players???
    // TODO Wiring
    public List<PlayerResult> play(CardDealer cardDealer, Player... player) {
        HandEvaluator handEvaluator = new HandEvaluator(new PokerHandChecker());
        MultipleHandEvaluator multipleHandEvaluator = new MultipleHandEvaluator();
        VersionOneGame versionOneGame = new VersionOneGame(cardDealer, handEvaluator, multipleHandEvaluator);
        return versionOneGame.playGame(player);
        // TODO formatter
    }
}
