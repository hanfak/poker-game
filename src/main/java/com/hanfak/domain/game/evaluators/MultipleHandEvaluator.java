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
    // Extract to dependency???
    private final Comparator<Player> comparingByKickerCards = comparing(y1 -> y1.pokerHand.getKickerCards());
    private final Comparator<Player> comparingByPokerHandRankings = comparingInt(p -> p.pokerHand.ranking());
    private final Comparator<List<Player>> comparingByPokerHandRankingsInCollection = comparingInt(x -> x.get(0).pokerHand.ranking());

    public MultipleHandEvaluator(ResultSetter resultSetter) {
        this.resultSetter = resultSetter;
    }

    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        List<List<Player>> sortedRankingsWhereAllPokerHandsSortedByPokerHandCards = sortByPokerHandCardsForEachPokerHandRanking(sortByPokerHandRanking(players));
        List<Map<List<Rank>, List<Player>>> groupedByPokerHandCards = groupByPokerHandCards(sortedRankingsWhereAllPokerHandsSortedByPokerHandCards);
        if (pokerHandCardsHasOnlyOneHand(groupedByPokerHandCards)) {
            return resultSetter.setPlayerResults(flattenList(sortedRankingsWhereAllPokerHandsSortedByPokerHandCards));
        }
        return resultSetter.setPlayerResults(sortByKickerCardsForEachPokerHandRanking(groupedByPokerHandCards));
    }

    private List<List<Player>> sortByPokerHandCardsForEachPokerHandRanking(List<List<Player>> groupByPokerHandRanking) {
        return groupByPokerHandRanking.stream().
                map(p -> p.stream().sorted(comparing(x -> x.pokerHand.getPokerHandsCards())).collect(toList())).
                collect(toList());
    }

    private List<List<Player>> sortByPokerHandRanking(List<Player> players) {
        List<Player> collect = players.stream().sorted(comparingByPokerHandRankings.reversed()).collect(toList());
        return groupBySortedPokerHandRanking(collect);
    }

    private List<List<Player>> groupBySortedPokerHandRanking(List<Player> sortByPokerHandRanking) {
        Collection<List<Player>> values = sortByPokerHandRanking.stream().collect(groupingBy(x -> x.pokerHand.ranking())).values();
        return values.stream().sorted(comparingByPokerHandRankingsInCollection.reversed()).collect(toList());
    }

    private List<Map<List<Rank>, List<Player>>> groupByPokerHandCards(List<List<Player>> playersGroupedByPokerRanking) {
        return playersGroupedByPokerRanking.stream().map(this::getCollect).collect(toList());
    }

    private Map<List<Rank>, List<Player>> getCollect(List<Player> x) {
        return x.stream().collect(groupingBy(x1 -> x1.pokerHand.getPokerHandCardsAsRanks()));
    }

    private boolean pokerHandCardsHasOnlyOneHand(List<Map<List<Rank>, List<Player>>> groupByPokerHandCards) {
        return groupByPokerHandCards.stream().filter(x -> x.values().size() == 1).collect(toList()).isEmpty();
    }

    // extract out, static method
    private List<Player> flattenList(List<List<Player>> nestedList) {
        return nestedList.stream().flatMap(Collection::stream).collect(toList());
    }

    private List<Player> sortByKickerCardsForEachPokerHandRanking(List<Map<List<Rank>, List<Player>>> groupByPokerHandCards) {
        List<List<Player>> collect = groupByPokerHandCards.stream().map(Map::values).collect(toList()).stream().
                map(x -> x.stream().map(this::sortPlayersWithSamePokerHandCardsByKickers).
                        sorted(comapringPokerHandCardsOfSamePokerRanking()).collect(toList())).
                flatMap(Collection::stream).collect(toList());
        return flattenList(collect);
    }

    private List<Player> sortPlayersWithSamePokerHandCardsByKickers(List<Player> players) {
        if (players.size() > 1) {
            return players.stream().sorted(comparingByKickerCards).collect(toList());
        }
        return players;
    }

    private Comparator<List<Player>> comapringPokerHandCardsOfSamePokerRanking() {
        return comparingInt(x1 -> x1.get(0).pokerHand.getPokerHandCardsAsRanks().get(0).getLevelCode());
    }
}
