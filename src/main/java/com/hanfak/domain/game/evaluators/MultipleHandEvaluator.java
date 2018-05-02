package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

public class MultipleHandEvaluator {
    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        List<Player> sortedPokerHandRanking = sortByPokerHandRanking(players);
        List<List<Player>> groupedByPokerHandRanking = groupByPokerHandRanking(sortedPokerHandRanking);
        List<List<Player>> sortedPokerHandCardsForEachRanking = sortByPokerHandCardsForEachPokerHandRanking(groupedByPokerHandRanking);
        List<Map<List<Rank>, List<Player>>> groupedByPokerHandCards = groupByPokerHandCards(sortedPokerHandCardsForEachRanking);
        if (pokerHandRankingHasOnlyOneHand(groupedByPokerHandCards)) {
            return setWinnersAndLosersPlayerResults(flattenList(sortedPokerHandCardsForEachRanking));
        }
        List<List<Player>> sortByKickerCardsForEachPokerHandRanking = sortByKickerCardsForEachPokerHandRanking(groupedByPokerHandCards);
        return setWinnersAndLosersPlayerResults(flattenList(sortByKickerCardsForEachPokerHandRanking));
    }

    private boolean pokerHandRankingHasOnlyOneHand(List<Map<List<Rank>, List<Player>>> groupByPokerHandCards) {
        return groupByPokerHandCards.stream().filter(x -> x.values().size() == 1).collect(Collectors.toList()).size() == 0;
    }

    private List<Player> flattenList(List<List<Player>> collect8) {
        return collect8.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<List<Player>> sortByKickerCardsForEachPokerHandRanking(List<Map<List<Rank>, List<Player>>> groupByPokerHandCards) {
        List<List<Player>> collect7 = groupByPokerHandCards.stream().
                map(Map::values).
                flatMap(Collection::stream).
                collect(Collectors.toList());
        System.out.println("collect7 = " + collect7);

        return collect7.stream().map(x -> {
            System.out.println(x.size());
            if (x.size() > 1) {
                Comparator<Player> comparing = Comparator.comparing(y -> y.pokerHand.getKickerCards());
                return x.stream().sorted(comparing).
                        collect(Collectors.toList());
            } else
                return x;
        }).collect(Collectors.toList());
    }

    private List<Map<List<Rank>, List<Player>>> groupByPokerHandCards(List<List<Player>> collect2) {
        List<Map<List<Rank>, List<Player>>> collect4 = collect2.stream().
                map(x -> x.stream().
                        collect(Collectors.groupingBy(x1 -> x1.pokerHand.getPokerHandsCards().getCards().
                                stream().
                                map(y -> y.rank).
                                collect(Collectors.toList())))).
                collect(Collectors.toList());
        System.out.println("collect4 = " + collect4);
        return collect4;
    }

    private List<List<Player>> sortByPokerHandCardsForEachPokerHandRanking(List<List<Player>> groupByPokerHandRanking) {
        return groupByPokerHandRanking.stream().map(p -> {
            Comparator<Player> comparing = Comparator.comparing(x -> x.pokerHand.getPokerHandsCards());
            return p.stream().sorted(comparing).
                    collect(Collectors.toList());
        }).collect(Collectors.toList());
    }

    private List<List<Player>> groupByPokerHandRanking(List<Player> sortByPokerHandRanking) {
        Map<Integer, List<Player>> collect1 = sortByPokerHandRanking.stream().collect(Collectors.groupingBy(x -> x.pokerHand.ranking()));
        System.out.println("poker hands groupedby rannking = " + collect1);

        Collection<List<Player>> values = collect1.values();
        System.out.println("values = " + values);

        Comparator<List<Player>> listComparator = Comparator.comparingInt(x -> x.get(0).pokerHand.ranking());
        List<List<Player>> collect5 = values.stream().sorted(listComparator.reversed()).collect(Collectors.toList());
        System.out.println("sorted after groupby = " + collect5);
        return collect5;
    }

    private List<Player> sortByPokerHandRanking(List<Player> players) {
        Comparator<Player> playerComparator = Comparator.comparingInt(p -> p.pokerHand.ranking());
        List<Player> collect = players.stream().sorted(playerComparator.reversed()).collect(Collectors.toList());
        System.out.println("collect = " + collect.stream().map(x -> x.pokerHand).collect(Collectors.toList()));
        return collect;
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
}
