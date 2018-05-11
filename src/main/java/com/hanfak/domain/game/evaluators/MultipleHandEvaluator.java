package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class MultipleHandEvaluator {
    private final ResultSetter resultSetter;

    public MultipleHandEvaluator(ResultSetter resultSetter) {
        this.resultSetter = resultSetter;
    }

    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        List<Player> sortedPokerHandRanking = sortByPokerHandRanking(players);
        List<List<Player>> groupedByPokerHandRanking = groupByPokerHandRanking(sortedPokerHandRanking);
        List<List<Player>> sortedPokerHandCardsForEachRanking = sortByPokerHandCardsForEachPokerHandRanking(groupedByPokerHandRanking);
        List<Map<List<Rank>, List<Player>>> groupedByPokerHandCards = groupByPokerHandCards(sortedPokerHandCardsForEachRanking);
        if (pokerHandRankingHasOnlyOneHand(groupedByPokerHandCards)) {
            return resultSetter.setPlayerResults(flattenList(sortedPokerHandCardsForEachRanking));
        }
        List<List<Player>> sortByKickerCardsForEachPokerHandRanking = sortByKickerCardsForEachPokerHandRanking(groupedByPokerHandCards);
        return resultSetter.setPlayerResults(flattenList(sortByKickerCardsForEachPokerHandRanking));
    }

    private boolean pokerHandRankingHasOnlyOneHand(List<Map<List<Rank>, List<Player>>> groupByPokerHandCards) {
        return groupByPokerHandCards.stream().filter(x -> x.values().size() == 1).collect(toList()).isEmpty();
    }

    private List<Player> flattenList(List<List<Player>> nestedList) {
        return nestedList.stream().flatMap(Collection::stream).collect(toList());
    }

    private List<List<Player>> sortByKickerCardsForEachPokerHandRanking(List<Map<List<Rank>, List<Player>>> groupByPokerHandCards) {
        return groupByPokerHandCards.stream().
                map(Map::values).
                collect(toList()).stream().
                    map(x -> x.stream().map(this::sortPlayersWithSamePokerHandCardsByKickers).
                            sorted(comapringPokerHandCardsOfSamePokerRanking()).
                            collect(toList())).
                    flatMap(Collection::stream).collect(toList());
    }

    private Comparator<List<Player>> comapringPokerHandCardsOfSamePokerRanking() {
        return comparingInt(x1 -> x1.get(0).pokerHand.getPokerHandsCards().getRankOfCards().get(0).getLevelCode());
    }

    private List<Player> sortPlayersWithSamePokerHandCardsByKickers(List<Player> players) {
        if (players.size() > 1) {
            return players.stream().
                    sorted(comparing(y1 -> y1.pokerHand.getKickerCards())).
                    collect(toList());
        } else {
            return players;
        }
    }

    private List<Map<List<Rank>, List<Player>>> groupByPokerHandCards(List<List<Player>> playersGroupedByPokerRanking) {
        return playersGroupedByPokerRanking.stream().map(this::getCollect).collect(toList());
    }

    private Map<List<Rank>, List<Player>> getCollect(List<Player> x) {
        return x.stream().collect(groupingBy(x1 -> x1.pokerHand.getPokerHandsCards().getRankOfCards()));
    }

    private List<List<Player>> sortByPokerHandCardsForEachPokerHandRanking(List<List<Player>> groupByPokerHandRanking) {
        return groupByPokerHandRanking.stream().map(p -> {
            Comparator<Player> comparingByPokerHandCards = comparing(x -> x.pokerHand.getPokerHandsCards());
            return p.stream().sorted(comparingByPokerHandCards).collect(toList());
        }).collect(toList());
    }

    private List<List<Player>> groupByPokerHandRanking(List<Player> sortByPokerHandRanking) {
        Collection<List<Player>> values = sortByPokerHandRanking.stream().collect(groupingBy(x -> x.pokerHand.ranking())).values();
        Comparator<List<Player>> comparingByPokerHandRankings = comparingInt(x -> x.get(0).pokerHand.ranking());
        return values.stream().sorted(comparingByPokerHandRankings.reversed()).collect(toList());
    }

    private List<Player> sortByPokerHandRanking(List<Player> players) {
        Comparator<Player> comparingByPokerHandRankings = comparingInt(p -> p.pokerHand.ranking());
        return players.stream().sorted(comparingByPokerHandRankings.reversed()).collect(toList());
    }
}
