package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.playershand.KickerCards;
import com.hanfak.domain.game.playershand.PokerHandsCards;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.hanfak.domain.game.PlayerResult.playerResult;
// rethink
public class MultipleHandEvaluator {
    /*
    Check all cards are the same
        ->
    All cards different
        ->
            order by ranking
            group by ranking
            order by pokerhand cards
            if poker hand cards are the same
                ->
                    group by pokerhand cards
                    order by kickercards
    order for draws
    * */

    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        Comparator<Player> playerComparator = (p1, p2) -> p1.pokerHand.ranking() - p2.pokerHand.ranking();
        // Sort by poker ranking
        List<Player> collect = players.stream().sorted(playerComparator.reversed()).collect(Collectors.toList());
        System.out.println("collect = " + collect.stream().map(x->x.pokerHand).collect(Collectors.toList()));

        // groupby changes order
        Map<Integer, List<Player>> collect1 = collect.stream().collect(Collectors.groupingBy(x -> x.pokerHand.ranking()));
        System.out.println("collect1 = " + collect1);


        Collection<List<Player>> values = collect1.values();
        System.out.println("values = " + values);


        // Sort by poker hand cards
        List<List<Player>> collect2 = values.stream().map(p -> {
            Comparator<Player> comparing = Comparator.comparing(x -> x.pokerHand.getPokerHandsCards());
            return p.stream().sorted(comparing).
                    collect(Collectors.toList());
        }).collect(Collectors.toList());
        System.out.println("collect2 = " + collect2.stream().flatMap(Collection::stream).map(x->x.pokerHand).collect(Collectors.toList()));

        // if poker hands are the same order by kickers

//        collect2.stream().collect(Collectors.groupingBy(x -> x.pokerHand.ranking()))
        // Ignore any element size > 1
        // If pokerhands are the same sort by kicker cards

        List<Map<PokerHandsCards, List<Player>>> collect4 = collect2.stream().map(x -> x.stream().collect(Collectors.groupingBy(x1 -> x1.pokerHand.getPokerHandsCards()))).collect(Collectors.toList());
        System.out.println("collect4 = " + collect4);
        // sort out

        List<List<Player>> collect7 = collect4.stream().map(Map::values).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("collect7 = " + collect7);


        List<List<Player>> collect8 = collect7.stream().map(x -> {
            System.out.println(x.size());
            if (x.size() > 1) {
                Comparator<Player> comparing = Comparator.comparing(y -> y.pokerHand.getKickerCards());
                return x.stream().sorted(comparing).
                        collect(Collectors.toList());
            } else
                return x;
        }).collect(Collectors.toList());
        
        System.out.println("collect8 = " + collect8);
        List<Player> collect3 = collect8.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("collect8 flatten = " + collect3);
        collect3.sort(Collections.reverseOrder());
        return setWinnersAndLosersPlayerResults(collect3);
    }



//    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
//        if (pokerHandsHaveDifferentRankings(players)) {
//            return determinePlayerResultWherePokerHandsAreDifferentRankings(players);
//        }
//        if (pokerHandCardsAreTheSame(players)) {
//            return determinePlayerResultByKickerCards(players);
//        } else {
//            return determinePlayerResultByPokerHandCards(players);
//        }
//    }

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
