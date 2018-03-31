package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;
import com.hanfak.domain.game.playershand.KickerCards;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Player> playersOrderedByRankingOfPokerHand = players.stream()
                .sorted(Comparator.<Player>comparingInt(p -> p.pokerHand.ranking()).reversed())
                .collect(Collectors.toList());
        return setWinnersAndLosersPlayerResults(playersOrderedByRankingOfPokerHand);
    }

    private List<PlayerResult> determinePlayerResultByPokerHandCards(List<Player> players) {
        List<Player> orderPlayers = players.stream()
                .sorted(Comparator.comparing(p -> p.pokerHand.getPokerHandsCards()))
                .collect(Collectors.toList());
        return setWinnersAndLosersPlayerResults(orderPlayers);
    }

    private List<PlayerResult> determinePlayerResultByKickerCards(List<Player> players) {
        List<Player> orderPlayers = players.stream().sorted(Comparator.comparing(p -> p.pokerHand.getKickerCards())).collect(Collectors.toList());
        if (kickerCardsAreTheSame(orderPlayers)) {
            return setDrawAsPlayerResults(orderPlayers);
        }
        return setWinnersAndLosersPlayerResults(orderPlayers);
    }

    private List<PlayerResult> setWinnersAndLosersPlayerResults(List<Player> orderPlayers) {
        PlayerResult winner = PlayerResult.playerResult(orderPlayers.get(0).playerName, Result.WIN, orderPlayers.get(0).pokerHand);
        PlayerResult loser = PlayerResult.playerResult(orderPlayers.get(1).playerName, Result.LOSS, orderPlayers.get(1).pokerHand);
        return Arrays.asList(winner, loser);
    }

    private List<PlayerResult> setDrawAsPlayerResults(List<Player> orderPlayers) {
        PlayerResult drawOne = PlayerResult.playerResult(orderPlayers.get(0).playerName, Result.DRAW, orderPlayers.get(0).pokerHand);
        PlayerResult drawTwo = PlayerResult.playerResult(orderPlayers.get(1).playerName, Result.DRAW, orderPlayers.get(1).pokerHand);
        return Arrays.asList(drawOne, drawTwo);
    }

    private boolean kickerCardsAreTheSame(List<Player> orderPlayers) {
        return orderPlayers.stream().map(x -> x.pokerHand.getKickerCards())
                .map(KickerCards::getCards).map(x -> x.stream().map(x1 -> x1.rank).collect(Collectors.toList()))
                .distinct().count() == 1;
    }

    private boolean pokerHandCardsAreTheSame(List<Player> players) {
        return players.get(0).pokerHand.getPokerHandsCards().getCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList())
                .equals(players.get(1).pokerHand.getPokerHandsCards().getCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList()));
    }
}
