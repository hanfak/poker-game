package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

// TODO need to unit test
public class MultipleHandEvaluator {
    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        List<PlayerResult> undeterminedPlayerResults = players.stream().
                map(player -> playerResult(player.playerName, null, player.hand)).
                collect(Collectors.toList());

        // Get array that matches PlayerResults, and turns unequal cards into indexes, and equal cards into 0
        List<Integer> collect = IntStream.range(0, 5).
                map(index -> {
                    if (undeterminedPlayerResults.get(0).hand.cards.get(index).rank.equals(undeterminedPlayerResults.get(1).hand.cards.get(index).rank)) {
                        return 0;
                    } else {
                        return index + 1;
                    }
                }).boxed().collect(Collectors.toList());
        System.out.println("collect = " + collect);

        // Remove all the 0
        List<Integer> collect1 = collect.stream().
                filter(checkedIndex -> checkedIndex != 0).collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);

        // GEt first element, which is index which will be have the first different element in both Players hand
        Optional<Integer> first = collect1.stream().findFirst();
        System.out.println("first = " + first);

        // If there is a non 0 entry, then compare that card at the index in the players hand
        if (first.isPresent()) {
            Integer indexOFDifferentCard = first.get() - 1;
            if (undeterminedPlayerResults.get(0).hand.cards.get(indexOFDifferentCard).rank.getLevelCode() < (undeterminedPlayerResults.get(1).hand.cards.get(indexOFDifferentCard).rank).getLevelCode()) {
                // PLAYER ONE NON SAME HIGH CARD IS GREATER THAN PLAYER TWO
                PlayerResult playerTwoResult = PlayerResult.playerResult(undeterminedPlayerResults.get(1).playerName, Result.LOSS, undeterminedPlayerResults.get(1).hand);
                PlayerResult playerOneResult = PlayerResult.playerResult(undeterminedPlayerResults.get(0).playerName, Result.WIN, undeterminedPlayerResults.get(0).hand);
                System.out.println("a" + Arrays.asList(playerOneResult, playerTwoResult));
                return Arrays.asList(playerOneResult, playerTwoResult);
            } else {
                // PLAYER ONE NON SAME HIGH CARD IS LESS THAN PLAYER TWO
                PlayerResult playerTwoResult = PlayerResult.playerResult(undeterminedPlayerResults.get(1).playerName, Result.WIN, undeterminedPlayerResults.get(1).hand);
                PlayerResult playerOneResult = PlayerResult.playerResult(undeterminedPlayerResults.get(0).playerName, Result.LOSS, undeterminedPlayerResults.get(0).hand);
                System.out.println("b" + Arrays.asList(playerOneResult, playerTwoResult));

                return Arrays.asList(playerOneResult, playerTwoResult);
            }
        } else { // If no elements, then all cards are the same
            PlayerResult playerTwoResult = PlayerResult.playerResult(undeterminedPlayerResults.get(1).playerName, Result.DRAW, undeterminedPlayerResults.get(1).hand);
            PlayerResult playerOneResult = PlayerResult.playerResult(undeterminedPlayerResults.get(0).playerName, Result.DRAW, undeterminedPlayerResults.get(0).hand);
            System.out.println("draw" +Arrays.asList(playerOneResult, playerTwoResult));

            return Arrays.asList(playerOneResult, playerTwoResult);
        }
    }
}
