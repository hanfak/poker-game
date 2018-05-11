package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

public class ResultSetter {
    public List<PlayerResult> setPlayerResults(List<Player> orderPlayers) {
        List<PlayerResult> collect = setWinnersAndLosersPlayerResults(orderPlayers);

        return collect.stream().map(k -> {
            // tODO check this again
            List<PlayerResult> collect1 = collect.subList(0, collect.indexOf(k)).stream().filter(y -> y.hand.getPokerHandsCards().getCards().stream().map(x -> x.rank).collect(Collectors.toList()).
                    containsAll(k.hand.getPokerHandsCards().getCards().stream().map(x -> x.rank).collect(Collectors.toList())) &&
                    y.hand.getKickerCards().getCards().stream().map(x -> x.rank).collect(Collectors.toList()).
                            containsAll(k.hand.getKickerCards().getCards().stream().map(x -> x.rank).collect(Collectors.toList()))).collect(Collectors.toList());

            if (collect1.isEmpty()) {
                return k;
            }
            return playerResult(k.playerName, collect1.get(0).result, k.hand);
        }).collect(Collectors.toList());
        // TODO another idea split by winner, group by rankings of pokerhands and in each value set result to the lowest one there,
    }

    private List<PlayerResult> setWinnersAndLosersPlayerResults(List<Player> orderPlayers) {
        return IntStream.range(0, orderPlayers.size()).
                mapToObj(p -> PlayerResult.playerResult(orderPlayers.get(p).playerName, p + 1, orderPlayers.get(p).pokerHand)).
                collect(Collectors.toList());
    }
}