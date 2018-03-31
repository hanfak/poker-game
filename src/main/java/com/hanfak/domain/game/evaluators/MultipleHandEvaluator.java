package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.playershand.KickerCards;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

// TODO need to unit test
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

    private boolean pokerHandsHaveDifferentRankings(List<Player> players) {
        return players.stream().map(x -> x.pokerHand.ranking()).distinct().count() != 1;
    }

    private List<PlayerResult> determinePlayerResultWherePokerHandsAreDifferentRankings(List<Player> players) {
        List<Player> playersOrderedByRankingOfPokerHand = players.stream().
                sorted(Comparator.<Player>comparingInt(p -> p.pokerHand.ranking()).reversed()).
                collect(Collectors.toList());
        return setWinnersAndLosersPlayerResults(playersOrderedByRankingOfPokerHand);
    }

    private List<PlayerResult> setWinnersAndLosersPlayerResults(List<Player> orderPlayers) {
        return IntStream.range(0, orderPlayers.size()).
                mapToObj(p -> playerResult(orderPlayers.get(p).playerName, p + 1, orderPlayers.get(p).pokerHand)).
                collect(Collectors.toList());
    }

    private boolean pokerHandCardsAreTheSame(List<Player> players) {
        return players.get(0).pokerHand.getPokerHandsCards().getCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList()).
                equals(players.get(1).pokerHand.getPokerHandsCards().getCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList()));
    }

    private List<PlayerResult> determinePlayerResultByKickerCards(List<Player> players) {
        List<Player> orderPlayers = players.stream().sorted(Comparator.comparing(p -> p.pokerHand.getKickerCards())).collect(Collectors.toList());
        if (kickerCardsAreTheSame(orderPlayers)) {
            return setDrawAsPlayerResults(orderPlayers);
        }
        return setWinnersAndLosersPlayerResults(orderPlayers);
    }

    private boolean kickerCardsAreTheSame(List<Player> orderPlayers) {
        return 1 == orderPlayers.stream().map(player -> player.pokerHand.getKickerCards()).
                map(KickerCards::getCards).map(listOfCards -> listOfCards.stream().map(x1 -> x1.rank).collect(Collectors.toList())).
                distinct().count();
    }

    private List<PlayerResult> determinePlayerResultByPokerHandCards(List<Player> players) {
        List<Player> orderPlayers = players.stream().
                sorted(Comparator.comparing(p -> p.pokerHand.getPokerHandsCards())).
                collect(Collectors.toList());
        return setWinnersAndLosersPlayerResults(orderPlayers);
    }

    private List<PlayerResult> setDrawAsPlayerResults(List<Player> orderPlayers) {
        return orderPlayers.stream().
                map(orderPlayer -> playerResult(orderPlayer.playerName, 1, orderPlayer.pokerHand)).
                collect(Collectors.toList());
    }
}
