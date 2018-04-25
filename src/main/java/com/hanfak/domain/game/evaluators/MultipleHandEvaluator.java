package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.playershand.KickerCards;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

public class MultipleHandEvaluator {
    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        if (pokerHandsHaveDifferentRankings(players)) {
            return determinePlayerResultWherePokerHandsAreDifferentRankings(players);
        }
        if (pokerHandCardsAreTheSame(players)) {
            return determinePlayerResultByKickerCards(players);
        } else {
            return determinePlayerResultByPokerHandCards(players);
        }
    }

    // Extract to HandComparer
    private boolean pokerHandsHaveDifferentRankings(List<Player> players) {
        return 1 != players.stream().map(x -> x.pokerHand.ranking()).distinct().count();
    }

    private boolean pokerHandCardsAreTheSame(List<Player> players) {
        long collect2 = players.stream().map(p -> p.pokerHand.getPokerHandsCards().getCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList())).distinct().count();
        return 1 == collect2;
    }

    private boolean kickerCardsAreTheSame(List<Player> orderPlayers) {
        return 1 == orderPlayers.stream().map(player -> player.pokerHand.getKickerCards()).
                map(KickerCards::getCards).map(listOfCards -> listOfCards.stream().map(x1 -> x1.rank).collect(Collectors.toList())).
                distinct().count();
    }

    //Extract to HandOrderer
    private List<PlayerResult> determinePlayerResultWherePokerHandsAreDifferentRankings(List<Player> players) {
        List<Player> playersOrderedByRankingOfPokerHand = players.stream().
                sorted(Comparator.<Player>comparingInt(p -> p.pokerHand.ranking()).reversed()).
                collect(Collectors.toList());
        return setWinnersAndLosersPlayerResults(playersOrderedByRankingOfPokerHand);
    }
    //Extract to HandOrderer
    private List<PlayerResult> determinePlayerResultByKickerCards(List<Player> players) {
        List<Player> playersOrderedByKickerCards = players.stream().
                sorted(Comparator.comparing(p -> p.pokerHand.getKickerCards())).
                collect(Collectors.toList());
        if (kickerCardsAreTheSame(playersOrderedByKickerCards)) {
            return setDrawAsPlayerResults(playersOrderedByKickerCards);
        }
        return setWinnersAndLosersPlayerResults(playersOrderedByKickerCards);
    }
    //Extract to HandOrderer
    private List<PlayerResult> determinePlayerResultByPokerHandCards(List<Player> players) {
        List<Player> orderPlayers = players.stream().
                sorted(Comparator.comparing(p -> p.pokerHand.getPokerHandsCards())).
                collect(Collectors.toList());
        return setWinnersAndLosersPlayerResults(orderPlayers);
    }

    //Extract to ResultSetter
    private List<PlayerResult> setDrawAsPlayerResults(List<Player> orderPlayers) {
        return orderPlayers.stream().
                map(orderPlayer -> playerResult(orderPlayer.playerName, 1, orderPlayer.pokerHand)).
                collect(Collectors.toList());
    }

    //Extract to ResultSetter
    private List<PlayerResult> setWinnersAndLosersPlayerResults1(List<Player> orderPlayers) {
        return IntStream.range(0, orderPlayers.size()).
                mapToObj(p -> playerResult(orderPlayers.get(p).playerName, p + 1, orderPlayers.get(p).pokerHand)).
                collect(Collectors.toList());
    }

    //Extract to ResultSetter
    private List<PlayerResult> setWinnersAndLosersPlayerResults(List<Player> orderPlayers) {
        List<PlayerResult> collect = setWinnersAndLosersPlayerResults1(orderPlayers);
        System.out.println("collect = " + collect);

        return collect.stream().map(k -> {
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

}
